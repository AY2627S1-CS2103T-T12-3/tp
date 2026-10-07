package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents the date an application was submitted.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}.
 */
public class ApplicationDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should be a valid calendar date in YYYY-MM-DD format.";

    private static final String DATE_FORMAT_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    public final String value;

    /**
     * Constructs an {@code ApplicationDate}.
     *
     * @param date A valid application date.
     */
    public ApplicationDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if {@code test} is a valid calendar date in {@code YYYY-MM-DD} format.
     */
    public static boolean isValidDate(String test) {
        requireNonNull(test);

        if (!test.matches(DATE_FORMAT_REGEX)) {
            return false;
        }

        try {
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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

        if (!(other instanceof ApplicationDate otherDate)) {
            return false;
        }

        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
