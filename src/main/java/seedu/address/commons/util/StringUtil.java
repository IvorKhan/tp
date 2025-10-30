package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /*** Returns {@code true} if the given {@code sentence} contains the specified {@code substring},
     * ignoring character case.
     *
     * <p>Both parameters are trimmed and compared in a case-insensitive manner. The method does not
     * accept an empty or all-whitespace {@code substring} and will throw {@link IllegalArgumentException}
     * if {@code substring} is empty after trimming. Passing {@code null} for either argument will
     * result in a {@link NullPointerException}.
     *
     * @param sentence the string to search in; must not be {@code null}
     * @param substring the substring to search for; must not be {@code null} or empty (after trimming)
     * @return {@code true} if {@code sentence} contains {@code substring} ignoring case, {@code false} otherwise
     * @throws NullPointerException if {@code sentence} or {@code substring} is {@code null}
     * @throws IllegalArgumentException if {@code substring} is empty or contains only whitespace
     */
    public static boolean containsSubstringIgnoreCase(String sentence, String substring) {
        requireNonNull(sentence);
        requireNonNull(substring);

        String prepped = substring.trim();
        checkArgument(!prepped.isEmpty(), "Substring parameter cannot be empty");

        return sentence.toLowerCase().contains(prepped.toLowerCase());
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if the Levenshtein distance between {@code source} and {@code keyword}
     * is less than or equal to the specified {@code threshold}.
     * Matching is case-insensitive.
     *
     * @param source the source string to compare
     * @param keyword the keyword to compare against
     * @param threshold the maximum allowed Levenshtein distance for a match
     * @return true if the distance is within the threshold, false otherwise
     */
    public static boolean fuzzyMatch(String source, String keyword, int threshold) {
        source = source.toLowerCase();
        keyword = keyword.toLowerCase();
        int distance = levenshteinDistance(source, keyword);
        return distance <= threshold;
    }

    /**
     * Computes the Levenshtein distance between two strings.
     * The distance is the minimum number of single-character edits
     * (insertions, deletions, or substitutions) required to change one string into the other.
     *
     * @param a the first string
     * @param b the second string
     * @return the Levenshtein distance between {@code s1} and {@code s2}
     */
    private static int levenshteinDistance(String a, String b) {
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                            a.charAt(i - 1) == b.charAt(j - 1)
                                ? nw
                                : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
}
