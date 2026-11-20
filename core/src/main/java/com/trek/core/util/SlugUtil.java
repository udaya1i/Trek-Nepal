package com.trek.core.util;

import lombok.experimental.UtilityClass;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * @author udaya
 */

@UtilityClass
public class SlugUtil {

    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-z0-9]+");
    private static final Pattern EDGE_HYPHENS = Pattern.compile("^-+|-+$");
    private static final int MAX_LENGTH = 200;

    public static String slugify(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "");
        String slug = NON_ALPHANUMERIC.matcher(normalized.toLowerCase()).replaceAll("-");
        slug = EDGE_HYPHENS.matcher(slug).replaceAll("");
        if (slug.length() > MAX_LENGTH) {
            slug = slug.substring(0, MAX_LENGTH);
            slug = EDGE_HYPHENS.matcher(slug).replaceAll("");
        }
        return slug;
    }

    /**
     * True for a plain positive integer, used to detect legacy numeric-id links
     * during the transition from id-based to slug-based public URLs.
     */
    public static boolean isNumeric(String value) {
        return value != null && !value.isBlank() && value.chars().allMatch(Character::isDigit);
    }
}
