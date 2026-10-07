package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the notes recorded for an application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description should not be blank and must not exceed 500 characters.";

    public static final int MAX_LENGTH = 500;

    public final String value;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid application description.
     */
    public Description(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        checkArgument(isValidDescription(trimmedDescription), MESSAGE_CONSTRAINTS);
        value = trimmedDescription;
    }

    /**
     * Returns true if {@code test} is a valid application description.
     */
    public static boolean isValidDescription(String test) {
        requireNonNull(test);
        return !test.trim().isEmpty() && test.trim().length() <= MAX_LENGTH;
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

        if (!(other instanceof Description otherDescription)) {
            return false;
        }

        return value.equals(otherDescription.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
