package seedu.address.model.application;

import static java.util.Objects.requireNonNull;

import java.util.Locale;

/**
 * Represents the current stage of an internship or graduate-role application.
 */
public enum ApplicationStatus {
    APPLIED,
    INTERVIEWING,
    OFFERED,
    REJECTED,
    WITHDRAWN;

    public static final String MESSAGE_CONSTRAINTS = "Status must be one of: "
            + "APPLIED, INTERVIEWING, OFFERED, REJECTED, WITHDRAWN.";

    /**
     * Returns the status represented by {@code value}, ignoring letter case and surrounding whitespace.
     *
     * @throws IllegalArgumentException if the value does not represent a supported status.
     */
    public static ApplicationStatus fromString(String value) {
        requireNonNull(value);
        try {
            return valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS, exception);
        }
    }
}
