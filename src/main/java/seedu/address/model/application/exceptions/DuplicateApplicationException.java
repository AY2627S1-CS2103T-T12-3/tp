package seedu.address.model.application.exceptions;

/**
 * Signals that the operation will result in duplicate applications. Applications are considered duplicates if they
 * have the same name and company.
 */
public class DuplicateApplicationException extends RuntimeException {
    public DuplicateApplicationException() {
        super("Operation would result in duplicate applications");
    }
}
