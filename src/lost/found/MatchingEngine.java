package lost.found;

import java.text.Normalizer;
import java.time.temporal.ChronoUnit;
import java.util.*;

/** Explainable, weighted text matching. Scores are similarities, not probabilities. */
public final class MatchingEngine {
    private static final Set<String> STOP = Set.of("a", "an", "the", "my", "me", "i", "it",
            "is", "was", "were", "has", "have", "and", "or", "of", "to", "for", "from",
            "in", "on", "at", "near", "with", "this", "that", "lost", "found", "item", "report");
    private static final Set<String> DESCRIPTORS = Set.of("black", "blue", "red", "green", "white",
            "yellow", "pink", "purple", "brown", "grey", "gray", "silver", "gold", "small",
            "large", "big", "new", "old", "leather", "plastic", "metal", "wooden");
    private static final Map<String, String> SYNONYMS = Map.ofEntries(
            Map.entry("earbuds", "earphone"), Map.entry("earbud", "earphone"),
            Map.entry("earphones", "earphone"), Map.entry("earphone", "earphone"),
            Map.entry("airpods", "earphone"), Map.entry("airpod", "earphone"),
            Map.entry("headphones", "earphone"), Map.entry("headphone", "earphone"),
            Map.entry("mobile", "phone"), Map.entry("smartphone", "phone"),
            Map.entry("phones", "phone"), Map.entry("cellphone", "phone"),
            Map.entry("purse", "wallet"), Map.entry("wallets", "wallet"),
            Map.entry("keys", "key"), Map.entry("keyring", "key"), Map.entry("keychain", "key"),
            Map.entry("bottles", "bottle"), Map.entry("spectacles", "glasses"),
            Map.entry("backpack", "bag"), Map.entry("rucksack", "bag"));

    public record Match(ReportStore.Report report, int score, String explanation) {}

    public List<Match> findMatches(ReportStore.Report source, List<ReportStore.Report> reports) {
        if (source == null || !"Active".equalsIgnoreCase(source.status())) return List.of();
        List<Match> result = new ArrayList<>();
        Set<String> sourceName = tokens(source.itemName());
        Set<String> sourceIdentity = new HashSet<>(sourceName);
        sourceIdentity.removeAll(DESCRIPTORS);
        for (ReportStore.Report candidate : reports) {
            if (candidate.id().equals(source.id()) || candidate.type().equals(source.type())
                    || !"Active".equalsIgnoreCase(candidate.status())) continue;
            ReportStore.Report lost = "Lost".equals(source.type()) ? source : candidate;
            ReportStore.Report found = "Found".equals(source.type()) ? source : candidate;
            if (found.date().isBefore(lost.date())) continue;
            Set<String> candidateName = tokens(candidate.itemName());
            Set<String> identity = new HashSet<>(candidateName);
            identity.removeAll(DESCRIPTORS);
            identity.retainAll(sourceIdentity);
            // Shared color/category/location alone must not produce a match.
            if (identity.isEmpty()) continue;
            double namePoints = 50 * jaccard(sourceName, candidateName);
            double descriptionPoints = 20 * jaccard(tokens(source.description()), tokens(candidate.description()));
            int categoryPoints = source.category().equalsIgnoreCase(candidate.category())
                    && !"Other".equalsIgnoreCase(source.category()) ? 15 : 0;
            int locationPoints = source.location().equalsIgnoreCase(candidate.location()) ? 10 : 0;
            long days = ChronoUnit.DAYS.between(lost.date(), found.date());
            int datePoints = days == 0 ? 5 : days <= 3 ? 4 : days <= 7 ? 3 : days <= 30 ? 1 : 0;
            int score = (int) Math.round(namePoints + descriptionPoints + categoryPoints + locationPoints + datePoints);
            if (score < 40) continue;
            String why = String.format(Locale.ROOT,
                    "Name: %.1f/50; description: %.1f/20; category: %d/15; location: %d/10; date: %d/5.",
                    namePoints, descriptionPoints, categoryPoints, locationPoints, datePoints);
            result.add(new Match(candidate, score, why));
        }
        result.sort(Comparator.comparingInt(Match::score).reversed()
                .thenComparing(match -> match.report().date(), Comparator.reverseOrder())
                .thenComparing(match -> match.report().id()));
        return List.copyOf(result);
    }

    private static Set<String> tokens(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFKC).toLowerCase(Locale.ROOT)
                .replaceAll("\\b(?:student|identity|id)\\s+card\\b", " idcard ")
                .replaceAll("\\bwater\\s+bottles?\\b", " bottle ")
                .replaceAll("\\b(?:usb|pen|flash)\\s+drives?\\b", " usbdrive ");
        Set<String> result = new HashSet<>();
        for (String token : normalized.split("[^\\p{L}\\p{N}]+")) {
            if (!token.isBlank() && !STOP.contains(token)) result.add(SYNONYMS.getOrDefault(token, token));
        }
        return result;
    }

    private static double jaccard(Set<String> left, Set<String> right) {
        Set<String> union = new HashSet<>(left); union.addAll(right);
        if (union.isEmpty()) return 0;
        Set<String> intersection = new HashSet<>(left); intersection.retainAll(right);
        return (double) intersection.size() / union.size();
    }
}
