package lost.found;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.channels.FileChannel;
import java.security.*;
import java.util.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** Local accounts; only salted PBKDF2 password hashes are written to disk. */
public final class AccountStore {
    private static final Object WRITE_LOCK = new Object();
    private static final int ITERATIONS = 600_000;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private final Path usersDirectory;

    public record Account(String id, String fullName, String studentId, String email) {}

    public AccountStore() {
        this(Path.of(System.getProperty("campusfind.data.dir", "data")));
    }

    public AccountStore(Path dataDirectory) {
        usersDirectory = dataDirectory.toAbsolutePath().normalize().resolve("users");
    }

    public Account register(String fullName, String studentId, String email, char[] password)
            throws IOException {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Enter your full name.");
        }
        if (studentId == null || !studentId.trim().matches("[A-Za-z0-9-]+")) {
            throw new IllegalArgumentException("Enter a student ID using letters, numbers or hyphens.");
        }
        if (email == null || !email.trim().matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }
        if (password == null || password.length < 8 || password.length > 128) {
            throw new IllegalArgumentException("Use a password between 8 and 128 characters.");
        }
        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);
        synchronized (WRITE_LOCK) {
            Files.createDirectories(usersDirectory);
            try (FileChannel channel = FileChannel.open(usersDirectory.resolve(".accounts.lock"),
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                    var lock = channel.lock()) {
                for (Properties values : readUsers()) {
                    Account existing = account(values);
                    if (existing.email().equalsIgnoreCase(normalizedEmail)) {
                        throw new IllegalArgumentException("This email already has an account.");
                    }
                    if (existing.studentId().equalsIgnoreCase(studentId.trim())) {
                        throw new IllegalArgumentException("This student ID already has an account.");
                    }
                }
                Account result = new Account(UUID.randomUUID().toString(), fullName.trim(),
                        studentId.trim(), normalizedEmail);
                byte[] salt = new byte[16];
                new SecureRandom().nextBytes(salt);
                byte[] hash = derive(password, salt, ITERATIONS);
                Properties values = new Properties();
                values.setProperty("id", result.id());
                values.setProperty("fullName", result.fullName());
                values.setProperty("studentId", result.studentId());
                values.setProperty("email", result.email());
                values.setProperty("algorithm", ALGORITHM);
                values.setProperty("iterations", Integer.toString(ITERATIONS));
                values.setProperty("salt", Base64.getEncoder().encodeToString(salt));
                values.setProperty("passwordHash", Base64.getEncoder().encodeToString(hash));
                Arrays.fill(hash, (byte) 0);
                Path temporary = Files.createTempFile(usersDirectory, "account-", ".tmp");
                try {
                    try (var writer = Files.newBufferedWriter(temporary, StandardCharsets.UTF_8)) {
                        values.store(writer, "CampusFind account");
                    }
                    Path destination = usersDirectory.resolve(result.id() + ".txt");
                    try {
                        Files.move(temporary, destination, StandardCopyOption.ATOMIC_MOVE);
                    } catch (AtomicMoveNotSupportedException ex) {
                        Files.move(temporary, destination);
                    }
                } finally {
                    Files.deleteIfExists(temporary);
                }
                return result;
            }
        }
    }

    public Optional<Account> authenticate(String identifier, char[] password) throws IOException {
        if (identifier == null || identifier.isBlank() || password == null
                || password.length == 0 || password.length > 128) return Optional.empty();
        for (Properties values : readUsers()) {
            Account candidate = account(values);
            if (!candidate.email().equalsIgnoreCase(identifier.trim())
                    && !candidate.studentId().equalsIgnoreCase(identifier.trim())) continue;
            byte[] actual = null;
            try {
                if (!ALGORITHM.equals(values.getProperty("algorithm"))) {
                    throw new IllegalArgumentException("Unsupported password format");
                }
                int iterations = Integer.parseInt(values.getProperty("iterations"));
                if (iterations < 100_000 || iterations > 2_000_000) {
                    throw new IllegalArgumentException("Invalid password work factor");
                }
                byte[] salt = Base64.getDecoder().decode(values.getProperty("salt", ""));
                byte[] expected = Base64.getDecoder().decode(values.getProperty("passwordHash", ""));
                if (salt.length != 16 || expected.length != 32) {
                    throw new IllegalArgumentException("Invalid password data");
                }
                actual = derive(password, salt, iterations);
                return MessageDigest.isEqual(actual, expected) ? Optional.of(candidate) : Optional.empty();
            } catch (IllegalArgumentException ex) {
                throw new IOException("Could not read the saved account.", ex);
            } finally {
                if (actual != null) Arrays.fill(actual, (byte) 0);
            }
        }
        return Optional.empty();
    }

    public Optional<Account> findById(String id) throws IOException {
        for (Properties values : readUsers()) {
            Account user = account(values);
            if (user.id().equals(id)) return Optional.of(user);
        }
        return Optional.empty();
    }

    private List<Properties> readUsers() throws IOException {
        if (!Files.exists(usersDirectory)) return List.of();
        List<Properties> users = new ArrayList<>();
        try (var paths = Files.newDirectoryStream(usersDirectory, "*.txt")) {
            for (Path path : paths) {
                if (!Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) continue;
                Properties values = new Properties();
                try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                    values.load(reader);
                    account(values);
                } catch (IllegalArgumentException ex) {
                    throw new IOException("Could not read an account file.", ex);
                }
                users.add(values);
            }
        }
        return users;
    }

    private static Account account(Properties values) throws IOException {
        for (String key : List.of("id", "fullName", "studentId", "email")) {
            if (values.getProperty(key, "").isBlank()) {
                throw new IOException("Incomplete account file.");
            }
        }
        return new Account(values.getProperty("id"), values.getProperty("fullName"),
                values.getProperty("studentId"), values.getProperty("email"));
    }

    private static byte[] derive(char[] password, byte[] salt, int iterations) throws IOException {
        PBEKeySpec specification = new PBEKeySpec(password, salt, iterations, 256);
        try {
            return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(specification).getEncoded();
        } catch (GeneralSecurityException ex) {
            throw new IOException("Password hashing is unavailable.", ex);
        } finally {
            specification.clearPassword();
        }
    }
}
