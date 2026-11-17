package lost.found;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;

/** One claim conversation per found report and claimant; all data is UTF-8 text. */
public final class ClaimStore {
    private static final Object WRITE_LOCK = new Object();
    private final Path data;
    private final Path claims;

    public record Conversation(String id, String reportId, String itemName,
            String finderId, String finderName, String claimantId, String claimantName,
            String createdAt) {
        public boolean includes(String userId) {
            return finderId.equals(userId) || claimantId.equals(userId);
        }
        public String otherName(String userId) {
            return finderId.equals(userId) ? claimantName : finderName;
        }
    }
    public record Message(String id, String senderId, String senderName, String sentAt, String text) {}

    public ClaimStore() { this(Path.of(System.getProperty("campusfind.data.dir", "data"))); }
    public ClaimStore(Path data) {
        this.data = data.toAbsolutePath().normalize();
        claims = this.data.resolve("claims");
    }

    /** Reload the report from disk; callers cannot invent its finder or current status. */
    public Conversation claim(String reportId, AccountStore.Account user) throws IOException {
        requireAccount(user);
        synchronized (ReportStore.STATE_LOCK) {
            Files.createDirectories(claims);
            try (FileChannel channel = FileChannel.open(claims.resolve(".write.lock"),
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                    var lock = channel.lock()) {
                ReportStore.Report report = new ReportStore(data).loadAll().reports().stream()
                        .filter(r -> r.id().equals(reportId)).findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("This report no longer exists."));
                if (!"Found".equals(report.type())) throw new IllegalArgumentException("Choose a found report to claim.");
                if (report.authorId().isBlank()) throw new IllegalArgumentException(
                        "This older report has no linked finder. The finder must sign in and submit it again.");
                if (report.authorId().equals(user.id())) throw new IllegalArgumentException("You cannot claim your own report.");
                // Reopening a conversation does not create another claim.
                for (Conversation existing : list(user)) {
                    if (existing.reportId().equals(report.id()) && existing.claimantId().equals(user.id())) return existing;
                }
                if (!"Active".equalsIgnoreCase(report.status())) throw new IllegalArgumentException("This report is no longer active.");
                AccountStore.Account finder = new AccountStore(data).findById(report.authorId())
                        .orElseThrow(() -> new IllegalArgumentException("The finder's account is unavailable."));
                Conversation result = new Conversation(UUID.randomUUID().toString(), report.id(),
                        report.itemName(), finder.id(), finder.fullName(), user.id(), user.fullName(),
                        Instant.now().toString());
                Properties p = new Properties();
                p.setProperty("id", result.id()); p.setProperty("reportId", result.reportId());
                p.setProperty("itemName", result.itemName()); p.setProperty("finderId", result.finderId());
                p.setProperty("finderName", result.finderName()); p.setProperty("claimantId", result.claimantId());
                p.setProperty("claimantName", result.claimantName()); p.setProperty("createdAt", result.createdAt());
                writeText(claims.resolve(result.id()+".txt"), p, "CampusFind claim conversation");
                return result;
            }
        }
    }

    /** Contact the other participant directly from a persisted lost/found match. */
    public Conversation contactMatch(String sourceId, String matchedId, AccountStore.Account user) throws IOException {
        requireAccount(user);
        List<ReportStore.Report> reports = new ReportStore(data).loadAll().reports();
        ReportStore.Report source = reports.stream().filter(r -> r.id().equals(sourceId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Refresh Matching Reports and choose a report."));
        ReportStore.Report matched = reports.stream().filter(r -> r.id().equals(matchedId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("This matched report is unavailable. Please refresh."));
        if (new MatchingEngine().findMatches(source, List.of(matched)).isEmpty())
            throw new IllegalArgumentException("These reports are no longer an active match. Please refresh.");
        ReportStore.Report found = "Found".equals(source.type()) ? source : matched;
        ReportStore.Report lost = "Lost".equals(source.type()) ? source : matched;
        // A person looking at a found result can contact its finder without owning the source report.
        if (!user.id().equals(found.authorId())) {
            if ("Lost".equals(matched.type()) && !user.id().equals(lost.authorId()))
                throw new IllegalArgumentException("Select your own found report to contact a lost-item reporter.");
            return claim(found.id(), user);
        }
        // Finders may also initiate the same conversation with the matched lost-report author.
        if (lost.authorId().isBlank())
            throw new IllegalArgumentException("This older lost report has no linked account to contact.");
        if (user.id().equals(lost.authorId()))
            throw new IllegalArgumentException("Both reports belong to you.");
        AccountStore.Account owner = new AccountStore(data).findById(lost.authorId())
                .orElseThrow(() -> new IllegalArgumentException("The matched reporter's account is unavailable."));
        return claim(found.id(), owner);
    }

    public List<Conversation> list(AccountStore.Account user) throws IOException {
        requireAccount(user);
        if (!Files.exists(claims)) return List.of();
        List<Conversation> result = new ArrayList<>();
        try (var paths = Files.newDirectoryStream(claims, "*.txt")) {
            for (Path path : paths) {
                if (!Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) continue;
                Conversation c = conversation(readText(path));
                if (!path.getFileName().toString().equals(c.id()+".txt")) throw new IOException("Invalid conversation file.");
                if (c.includes(user.id())) result.add(c);
            }
        }
        result.sort(Comparator.comparing(Conversation::createdAt).reversed().thenComparing(Conversation::id));
        return List.copyOf(result);
    }

    public List<Message> messages(String conversationId, AccountStore.Account user) throws IOException {
        Conversation c = authorized(conversationId, user);
        Path directory = claims.resolve(c.id()).resolve("messages");
        if (!Files.exists(directory)) return List.of();
        List<Message> result = new ArrayList<>();
        try (var paths = Files.newDirectoryStream(directory, "*.txt")) {
            for (Path path : paths) {
                if (!Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) continue;
                Properties p = readText(path);
                String sender = required(p,"senderId");
                if (!c.includes(sender)) throw new IOException("Invalid message sender.");
                String id = required(p,"id"); validId(id);
                if (!path.getFileName().toString().equals(id+".txt")) throw new IOException("Invalid message file.");
                String time = required(p,"sentAt");
                try { Instant.parse(time); } catch (RuntimeException ex) { throw new IOException("Invalid message date.",ex); }
                result.add(new Message(id,sender,sender.equals(c.finderId())?c.finderName():c.claimantName(),
                        time,required(p,"text")));
            }
        }
        result.sort(Comparator.comparing((Message m) -> Instant.parse(m.sentAt())).thenComparing(Message::id));
        return List.copyOf(result);
    }

    /** A separate, atomically published file for each message prevents partial/interleaved writes. */
    public void send(String conversationId, AccountStore.Account user, String text) throws IOException {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("Type a message first.");
        if (text.length() > 2000) throw new IllegalArgumentException("Keep messages within 2,000 characters.");
        synchronized (WRITE_LOCK) {
            Conversation c = authorized(conversationId,user);
            Path directory = claims.resolve(c.id()).resolve("messages");
            Files.createDirectories(directory);
            try (FileChannel channel = FileChannel.open(directory.resolve(".send.lock"),
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                    var lock = channel.lock()) {
                // Check membership again after acquiring the cross-process lock.
                authorized(conversationId,user);
                String id = UUID.randomUUID().toString();
                Properties p = new Properties();
                p.setProperty("id",id); p.setProperty("senderId",user.id());
                p.setProperty("sentAt",Instant.now().toString()); p.setProperty("text",text.strip());
                writeText(directory.resolve(id+".txt"),p,"CampusFind message");
            }
        }
    }

    private Conversation authorized(String id, AccountStore.Account user) throws IOException {
        requireAccount(user); validId(id);
        Conversation c = conversation(readText(claims.resolve(id+".txt")));
        if (!c.id().equals(id) || !c.includes(user.id())) throw new IllegalArgumentException("This conversation is not yours.");
        return c;
    }
    private void requireAccount(AccountStore.Account user) throws IOException {
        if (user == null) throw new IllegalArgumentException("Please log in first.");
        AccountStore.Account saved = new AccountStore(data).findById(user.id())
                .orElseThrow(() -> new IllegalArgumentException("Please log in with a registered account."));
        if (!saved.equals(user)) throw new IllegalArgumentException("Account details have changed. Please log in again.");
    }
    private static Conversation conversation(Properties p) throws IOException {
        String id=required(p,"id"); validId(id);
        return new Conversation(id,required(p,"reportId"),required(p,"itemName"),required(p,"finderId"),
                required(p,"finderName"),required(p,"claimantId"),required(p,"claimantName"),required(p,"createdAt"));
    }
    private static void validId(String id) {
        if (id == null || !id.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"))
            throw new IllegalArgumentException("Invalid conversation or message ID.");
    }
    private static String required(Properties p, String key) throws IOException {
        String value=p.getProperty(key);
        if (value==null || value.isBlank()) throw new IOException("Incomplete claim/message file: "+key);
        return value;
    }
    private static Properties readText(Path path) throws IOException {
        Properties p=new Properties();
        try(var reader=Files.newBufferedReader(path,StandardCharsets.UTF_8)) { p.load(reader); }
        catch (IllegalArgumentException ex) { throw new IOException("Unreadable claim/message file.",ex); }
        return p;
    }
    private static void writeText(Path target, Properties p, String heading) throws IOException {
        Files.createDirectories(target.getParent());
        Path temporary=Files.createTempFile(target.getParent(),"writing-",".tmp");
        try {
            try(var writer=Files.newBufferedWriter(temporary,StandardCharsets.UTF_8)) { p.store(writer,heading); }
            try { Files.move(temporary,target,StandardCopyOption.ATOMIC_MOVE); }
            catch(AtomicMoveNotSupportedException ex) { Files.move(temporary,target); }
        } finally { Files.deleteIfExists(temporary); }
    }
}
