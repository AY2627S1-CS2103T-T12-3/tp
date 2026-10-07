package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the role or internship title in an application.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}.
 */
public class ApplicationName {

    public static final String MESSAGE_CONSTRAINTS =
            "Application names should not be blank, contain control characters, or exceed 100 characters.";

    public static final int MAX_LENGTH = 100;

    public final String value;

    /**
     * Constructs an {@code ApplicationName}.
     *
     * @param name A valid application name.
     */
    public ApplicationName(String name) {
        requireNonNull(name);
        String normalizedName = normalize(name);
        checkArgument(isValidName(normalizedName), MESSAGE_CONSTRAINTS);
        value = normalizedName;
    }

    /**
     * Returns true if {@code test} is a valid application name.
     */
    public static boolean isValidName(String test) {
        requireNonNull(test);
        return !test.trim().isEmpty()
                && test.length() <= MAX_LENGTH
                && test.codePoints().noneMatch(Character::isISOControl);
    }

    private static String normalize(String name) {
        return name.trim().replaceAll("\\s{2,}", " ");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ApplicationName otherName)) {
            return false;
        }

        return value.equals(otherName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
