package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import seedu.address.model.application.exceptions.ApplicationNotFoundException;
import seedu.address.model.application.exceptions.DuplicateApplicationException;
import seedu.address.model.person.Company;

public class UniqueApplicationListTest {

    private static final Application SOFTWARE_ENGINEER_INTERN = new Application(
            new ApplicationName("Software Engineer Intern"),
            new Company("Tech Corp"),
            new Description("Applied through the company careers page."),
            new ApplicationDate("2026-10-07"));
    private static final Application PRODUCT_MANAGER_INTERN = new Application(
            new ApplicationName("Product Manager Intern"),
            new Company("Other Corp"),
            new Description("Applied through a university careers portal."),
            new ApplicationDate("2026-10-08"));

    private final UniqueApplicationList uniqueApplicationList = new UniqueApplicationList();

    @Test
    public void contains_nullApplication_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.contains(null));
    }

    @Test
    public void contains_applicationNotInList_returnsFalse() {
        assertFalse(uniqueApplicationList.contains(SOFTWARE_ENGINEER_INTERN));
    }

    @Test
    public void contains_applicationInList_returnsTrue() {
        uniqueApplicationList.add(SOFTWARE_ENGINEER_INTERN);
        assertTrue(uniqueApplicationList.contains(SOFTWARE_ENGINEER_INTERN));
    }

    @Test
    public void contains_applicationWithSameIdentityFieldsInList_returnsTrue() {
        uniqueApplicationList.add(SOFTWARE_ENGINEER_INTERN);
        Application editedApplication = new Application(new ApplicationName("software engineer intern"),
                new Company("tech corp"), new Description("Interview scheduled."), new ApplicationDate("2026-10-08"));

        assertTrue(uniqueApplicationList.contains(editedApplication));
    }

    @Test
    public void add_nullApplication_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.add(null));
    }

    @Test
    public void add_duplicateApplication_throwsDuplicateApplicationException() {
        uniqueApplicationList.add(SOFTWARE_ENGINEER_INTERN);
        Application duplicate = new Application(new ApplicationName("software engineer intern"),
                new Company("tech corp"),
                new Description("Interview scheduled."), new ApplicationDate("2026-10-08"));

        assertThrows(DuplicateApplicationException.class, () -> uniqueApplicationList.add(duplicate));
    }

    @Test
    public void setApplications_nullUniqueApplicationList_throwsNullPointerException() {
        UniqueApplicationList nullApplicationList = null;
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.setApplications(nullApplicationList));
    }

    @Test
    public void setApplications_uniqueApplicationList_replacesOwnListWithProvidedUniqueApplicationList() {
        uniqueApplicationList.add(SOFTWARE_ENGINEER_INTERN);
        UniqueApplicationList expectedUniqueApplicationList = new UniqueApplicationList();
        expectedUniqueApplicationList.add(PRODUCT_MANAGER_INTERN);

        uniqueApplicationList.setApplications(expectedUniqueApplicationList);
        assertEquals(expectedUniqueApplicationList, uniqueApplicationList);
    }

    @Test
    public void setApplications_nullList_throwsNullPointerException() {
        List<Application> nullApplications = null;
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.setApplications(nullApplications));
    }

    @Test
    public void setApplications_list_replacesOwnListWithProvidedList() {
        uniqueApplicationList.add(SOFTWARE_ENGINEER_INTERN);
        uniqueApplicationList.setApplications(List.of(PRODUCT_MANAGER_INTERN));

        UniqueApplicationList expectedUniqueApplicationList = new UniqueApplicationList();
        expectedUniqueApplicationList.add(PRODUCT_MANAGER_INTERN);
        assertEquals(expectedUniqueApplicationList, uniqueApplicationList);
    }

    @Test
    public void setApplications_listWithDuplicateApplications_throwsDuplicateApplicationException() {
        Application duplicate = new Application(new ApplicationName("software engineer intern"),
                new Company("tech corp"),
                new Description("Interview scheduled."), new ApplicationDate("2026-10-08"));

        assertThrows(DuplicateApplicationException.class, () -> uniqueApplicationList
                .setApplications(List.of(SOFTWARE_ENGINEER_INTERN, duplicate)));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueApplicationList
                .asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueApplicationList.asUnmodifiableObservableList().toString(), uniqueApplicationList.toString());
    }

    @Test
    public void setApplication_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList
                .setApplication(null, SOFTWARE_ENGINEER_INTERN));
        assertThrows(NullPointerException.class, () -> uniqueApplicationList
                .setApplication(SOFTWARE_ENGINEER_INTERN, null));
    }

    @Test
    public void setApplication_missingTarget_throwsApplicationNotFoundException() {
        uniqueApplicationList.add(SOFTWARE_ENGINEER_INTERN);
        assertThrows(ApplicationNotFoundException.class, () -> uniqueApplicationList
                .setApplication(PRODUCT_MANAGER_INTERN, SOFTWARE_ENGINEER_INTERN));
        assertEquals(List.of(SOFTWARE_ENGINEER_INTERN), uniqueApplicationList.asUnmodifiableObservableList());
    }

    @Test
    public void setApplication_sameIdentityWithNewDetails_preservesPositionAndNotifiesObservers() {
        uniqueApplicationList.setApplications(List.of(SOFTWARE_ENGINEER_INTERN, PRODUCT_MANAGER_INTERN));
        Application updated = new Application(new ApplicationName("software engineer intern"),
                new Company("tech corp"), new Description("Interview scheduled."), new ApplicationDate("2026-10-15"));
        ObservableList<Application> observed = uniqueApplicationList.asUnmodifiableObservableList();
        List<Application> observedContents = new ArrayList<>();
        observed.addListener((ListChangeListener<Application>) change -> {
            observedContents.clear();
            observedContents.addAll(change.getList());
        });

        uniqueApplicationList.setApplication(SOFTWARE_ENGINEER_INTERN, updated);

        assertEquals(List.of(updated, PRODUCT_MANAGER_INTERN), observed);
        assertEquals(List.of(updated, PRODUCT_MANAGER_INTERN), observedContents);
    }

    @Test
    public void setApplication_changedIdentity_preservesPosition() {
        uniqueApplicationList.add(SOFTWARE_ENGINEER_INTERN);
        uniqueApplicationList.setApplication(SOFTWARE_ENGINEER_INTERN, PRODUCT_MANAGER_INTERN);
        assertEquals(List.of(PRODUCT_MANAGER_INTERN), uniqueApplicationList.asUnmodifiableObservableList());
    }

    @Test
    public void setApplication_duplicateIdentity_throwsWithoutChanges() {
        uniqueApplicationList.setApplications(List.of(SOFTWARE_ENGINEER_INTERN, PRODUCT_MANAGER_INTERN));
        Application duplicate = new Application(new ApplicationName("product manager intern"),
                new Company("other corp"), new Description("Different details."), new ApplicationDate("2026-10-15"));
        assertThrows(DuplicateApplicationException.class, () -> uniqueApplicationList
                .setApplication(SOFTWARE_ENGINEER_INTERN, duplicate));
        assertEquals(List.of(SOFTWARE_ENGINEER_INTERN, PRODUCT_MANAGER_INTERN),
                uniqueApplicationList.asUnmodifiableObservableList());
    }

    @Test
    public void remove_nullApplication_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueApplicationList.remove(null));
    }

    @Test
    public void remove_missingOrDifferentDetails_throwsWithoutChanges() {
        uniqueApplicationList.add(SOFTWARE_ENGINEER_INTERN);
        assertThrows(ApplicationNotFoundException.class, () -> uniqueApplicationList.remove(PRODUCT_MANAGER_INTERN));
        Application sameIdentity = new Application(SOFTWARE_ENGINEER_INTERN.getName(),
                SOFTWARE_ENGINEER_INTERN.getCompany(),
                new Description("Different details."), SOFTWARE_ENGINEER_INTERN.getDate());
        assertThrows(ApplicationNotFoundException.class, () -> uniqueApplicationList.remove(sameIdentity));
        assertEquals(List.of(SOFTWARE_ENGINEER_INTERN), uniqueApplicationList.asUnmodifiableObservableList());
    }

    @Test
    public void remove_existingApplication_updatesObservedList() {
        uniqueApplicationList.setApplications(List.of(SOFTWARE_ENGINEER_INTERN, PRODUCT_MANAGER_INTERN));
        ObservableList<Application> observed = uniqueApplicationList.asUnmodifiableObservableList();
        uniqueApplicationList.remove(SOFTWARE_ENGINEER_INTERN);
        assertEquals(List.of(PRODUCT_MANAGER_INTERN), observed);
        uniqueApplicationList.remove(PRODUCT_MANAGER_INTERN);
        assertTrue(observed.isEmpty());
    }
}
