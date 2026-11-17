package lost.found;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;
import javax.imageio.ImageIO;

/** Stores each report as a UTF-8 .txt file, with an optional copied photo. */
public final class ReportStore {
    private final Path dataDirectory;

    public ReportStore() {
        this(Path.of(System.getProperty("campusfind.data.dir", "data")));
    }

    public ReportStore(Path dataDirectory) {
        this.dataDirectory = dataDirectory.toAbsolutePath().normalize();
    }

    /** A report loaded from one of the existing text files. */
    public record Report(String id, String type, String itemName, String category,
            String description, String location, LocalDate date, String createdAt,
            String status, String photo) {
        public boolean matches(String query, String typeFilter) {
            if (!"All".equals(typeFilter) && !type.equals(typeFilter)) return false;
            String text = String.join(" ", itemName, category, description, location, type, date.toString());
            return text.toLowerCase(java.util.Locale.ROOT)
                    .contains(query.trim().toLowerCase(java.util.Locale.ROOT));
        }
    }

    public record LoadResult(java.util.List<Report> reports, int skippedFiles) {}

    /** Missing data means an empty list; an unreadable file does not hide other reports. */
    public LoadResult loadAll() throws IOException {
        Path directory = dataDirectory.resolve("reports");
        if (!Files.exists(directory)) return new LoadResult(java.util.List.of(), 0);
        java.util.List<Report> reports = new java.util.ArrayList<>();
        int skipped = 0;
        try (var files = Files.newDirectoryStream(directory, "*.txt")) {
            for (Path file : files) {
                if (!Files.isRegularFile(file, java.nio.file.LinkOption.NOFOLLOW_LINKS)) continue;
                try (var reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                    Properties values = new Properties();
                    values.load(reader);
                    for (String key : java.util.List.of("id", "type", "itemName", "category",
                            "description", "location", "date")) {
                        requireText(values.getProperty(key), key);
                    }
                    String type = values.getProperty("type");
                    if (!"Lost".equals(type) && !"Found".equals(type)) {
                        throw new IllegalArgumentException("Unknown report type");
                    }
                    reports.add(new Report(values.getProperty("id"), type,
                            values.getProperty("itemName"), values.getProperty("category"),
                            values.getProperty("description"), values.getProperty("location"),
                            LocalDate.parse(values.getProperty("date")),
                            values.getProperty("createdAt", ""), values.getProperty("status", "Active"),
                            values.getProperty("photo", "")));
                } catch (IOException | IllegalArgumentException | java.time.DateTimeException ex) {
                    skipped++;
                }
            }
        }
        reports.sort(java.util.Comparator.comparing(Report::date).reversed()
                .thenComparing(Report::createdAt, java.util.Comparator.reverseOrder())
                .thenComparing(Report::id));
        return new LoadResult(java.util.List.copyOf(reports), skipped);
    }

    /** Only use photos stored in this application's image folder. */
    public Path photoPath(Report report) {
        if (report.photo().isBlank()) return null;
        try {
            Path images = dataDirectory.resolve("images");
            Path photo = dataDirectory.resolve(report.photo()).normalize();
            return photo.startsWith(images) && !photo.equals(images) ? photo : null;
        } catch (java.nio.file.InvalidPathException ex) {
            return null;
        }
    }

    public Path save(String type, String itemName, String category, String description,
            String location, LocalDate date, Path photo) throws IOException {
        if (!"Lost".equals(type) && !"Found".equals(type)) {
            throw new IllegalArgumentException("Select Lost or Found as the report type.");
        }
        requireText(itemName, "Item name");
        requireText(category, "Category");
        if ("Select a category".equals(category)) {
            throw new IllegalArgumentException("Please select a category.");
        }
        requireText(description, "Description");
        requireText(location, "Location");
        if ("Select a campus location".equals(location.trim())) {
            throw new IllegalArgumentException("Please select a campus location.");
        }
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Enter today's date or an earlier date.");
        }
        if (photo != null) {
            if (!Files.isRegularFile(photo) || ImageIO.read(photo.toFile()) == null) {
                throw new IllegalArgumentException("Choose a readable PNG, JPG, GIF or BMP photo.");
            }
        }

        String id = UUID.randomUUID().toString();
        Path reportsDirectory = dataDirectory.resolve("reports");
        Files.createDirectories(reportsDirectory);
        Path reportFile = reportsDirectory.resolve(id + ".txt");
        Path temporaryFile = null;
        Path copiedPhoto = null;
        boolean saved = false;
        try {
            String relativePhoto = "";
            if (photo != null) {
                String fileName = photo.getFileName().toString();
                int dot = fileName.lastIndexOf('.');
                String extension = dot >= 0
                        ? fileName.substring(dot).toLowerCase(java.util.Locale.ROOT) : ".img";
                if (!java.util.Set.of(".png", ".jpg", ".jpeg", ".gif", ".bmp").contains(extension)) {
                    extension = ".img";
                }
                Path imagesDirectory = dataDirectory.resolve("images");
                Files.createDirectories(imagesDirectory);
                copiedPhoto = imagesDirectory.resolve(id + extension);
                Files.copy(photo, copiedPhoto);
                relativePhoto = "images/" + copiedPhoto.getFileName();
            }
            Properties values = new Properties();
            values.setProperty("id", id);
            values.setProperty("type", type);
            values.setProperty("itemName", itemName.trim());
            values.setProperty("category", category.trim());
            values.setProperty("description", description.trim());
            values.setProperty("location", location.trim());
            values.setProperty("date", date.toString());
            values.setProperty("createdAt", LocalDateTime.now().toString());
            values.setProperty("photo", relativePhoto);
            values.setProperty("status", "Active");
            temporaryFile = Files.createTempFile(reportsDirectory, "report-", ".tmp");
            try (Writer writer = Files.newBufferedWriter(temporaryFile, StandardCharsets.UTF_8)) {
                values.store(writer, "CampusFind report");
            }
            try {
                Files.move(temporaryFile, reportFile, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(temporaryFile, reportFile);
            }
            saved = true;
            return reportFile;
        } finally {
            if (temporaryFile != null) Files.deleteIfExists(temporaryFile);
            if (!saved && copiedPhoto != null) Files.deleteIfExists(copiedPhoto);
        }
    }

    private static void requireText(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " is required.");
        }
    }
}
