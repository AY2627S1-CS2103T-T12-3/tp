package seedu.address.model.application;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.application.exceptions.ApplicationNotFoundException;
import seedu.address.model.application.exceptions.DuplicateApplicationException;

/**
 * A list of applications that enforces uniqueness between its elements and does not allow nulls.
 * An application is considered unique by comparing using {@code Application#isSameApplication(Application)}.
 *
 * Supports a minimal set of list operations.
 *
 * @see Application#isSameApplication(Application)
 */
public class UniqueApplicationList {

    private final ObservableList<Application> internalList = FXCollections.observableArrayList();
    private final ObservableList<Application> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent application as the given argument.
     */
    public boolean contains(Application toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameApplication);
    }

    /**
     * Adds an application to the list.
     * The application must not already exist in the list.
     */
    public void add(Application toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateApplicationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces {@code target} with {@code editedApplication}, preserving its position.
     * The target must exist and the edited identity must not duplicate another application.
     */
    public void setApplication(Application target, Application editedApplication) {
        requireAllNonNull(target, editedApplication);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ApplicationNotFoundException();
        }
        if (!target.isSameApplication(editedApplication) && contains(editedApplication)) {
            throw new DuplicateApplicationException();
        }

        internalList.set(index, editedApplication);
    }

    /**
     * Removes the application with exactly the same fields as {@code toRemove}.
     * The application must exist in the list.
     */
    public void remove(Application toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ApplicationNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code applications}.
     */
    public void setApplications(UniqueApplicationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code applications}.
     * {@code applications} must not contain duplicate applications.
     */
    public void setApplications(List<Application> applications) {
        requireAllNonNull(applications);
        if (!applicationsAreUnique(applications)) {
            throw new DuplicateApplicationException();
        }

        internalList.setAll(applications);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Application> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueApplicationList otherUniqueApplicationList)) {
            return false;
        }

        return internalList.equals(otherUniqueApplicationList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code applications} contains only unique applications.
     */
    private boolean applicationsAreUnique(List<Application> applications) {
        for (int i = 0; i < applications.size() - 1; i++) {
            for (int j = i + 1; j < applications.size(); j++) {
                if (applications.get(i).isSameApplication(applications.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
