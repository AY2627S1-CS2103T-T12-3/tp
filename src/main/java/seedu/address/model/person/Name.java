package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final int MAX_LENGTH = 100;

    public static final String MESSAGE_CONSTRAINTS = "Names should only contain letters, digits, spaces, hyphens "
            + "and apostrophes, must contain at least one letter or digit, and must not exceed 100 characters.";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        String normalizedName = normalize(name);
        checkArgument(isValidName(normalizedName), MESSAGE_CONSTRAINTS);
        fullName = normalizedName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        requireNonNull(test);
        String normalizedName = normalize(test);
        if (normalizedName.isEmpty() || normalizedName.codePointCount(0, normalizedName.length()) > MAX_LENGTH) {
            return false;
        }

        boolean hasLetterOrDigit = false;
        for (int offset = 0; offset < normalizedName.length();) {
            int codePoint = normalizedName.codePointAt(offset);
            if (Character.isLetterOrDigit(codePoint)) {
                hasLetterOrDigit = true;
            } else if (codePoint != ' ' && codePoint != '-' && codePoint != '\'') {
                return false;
            }
            offset += Character.charCount(codePoint);
        }
        return hasLetterOrDigit;
    }

    /** Trims leading and trailing whitespace and reduces internal runs to one space. */
    private static String normalize(String name) {
        StringBuilder normalized = new StringBuilder();
        boolean pendingSpace = false;
        for (int offset = 0; offset < name.length();) {
            int codePoint = name.codePointAt(offset);
            offset += Character.charCount(codePoint);
            if (Character.isWhitespace(codePoint) || Character.isSpaceChar(codePoint)) {
                pendingSpace = normalized.length() > 0;
                continue;
            }
            if (pendingSpace) {
                normalized.append(' ');
                pendingSpace = false;
            }
            normalized.appendCodePoint(codePoint);
        }
        return normalized.toString();
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name otherName)) {
            return false;
        }

        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
