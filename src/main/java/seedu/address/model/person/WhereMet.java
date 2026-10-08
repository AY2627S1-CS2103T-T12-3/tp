package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents where a person was met.
 * Guarantees: immutable; is valid as declared in {@link #isValidWhereMet(String)}.
 */
public class WhereMet {

    public static final int MAX_LENGTH = 200;

    public static final String MESSAGE_CONSTRAINTS = "Where-met should not be blank, may only contain letters, "
            + "digits, spaces, and & . , ' -, and must not exceed " + MAX_LENGTH + " characters.";

    private static final String VALIDATION_REGEX = "[\\p{L}\\p{N}&.,'-][\\p{L}\\p{N}&.,' -]*";

    public final String value;

    /** Constructs a {@code WhereMet} with a valid description. */
    public WhereMet(String whereMet) {
        requireNonNull(whereMet);
        checkArgument(isValidWhereMet(whereMet), MESSAGE_CONSTRAINTS);
        value = whereMet;
    }

    /** Returns true if a given string is a valid WhereMet description. */
    public static boolean isValidWhereMet(String test) {
        requireNonNull(test);
        return test.length() <= MAX_LENGTH && test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof WhereMet otherWhereMet)) {
            return false;
        }
        return value.equals(otherWhereMet.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
