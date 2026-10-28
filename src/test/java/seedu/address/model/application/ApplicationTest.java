package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Company;

public class ApplicationTest {

    private static final Application SOFTWARE_ENGINEER_INTERN = new Application(
            new ApplicationName("Software Engineer Intern"),
            new Company("Tech Corp"),
            new Description("Applied through the company careers page."),
            new ApplicationDate("2026-10-07"));

    @Test
    public void constructor_nullField_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Application(null, new Company("Tech Corp"),
                new Description("Applied through the company careers page."), new ApplicationDate("2026-10-07")));
        assertThrows(NullPointerException.class, () -> new Application(new ApplicationName("Software Engineer Intern"),
                new Company("Tech Corp"), new Description("Applied through the company careers page."),
                new ApplicationDate("2026-10-07"), null));
    }

    @Test
    public void constructor_defaultStatus_isApplied() {
        assertEquals(ApplicationStatus.APPLIED, SOFTWARE_ENGINEER_INTERN.getStatus());
    }

    @Test
    public void constructor_explicitStatus_returnsStatus() {
        Application application = new Application(new ApplicationName("Software Engineer Intern"),
                new Company("Tech Corp"), new Description("Applied through the company careers page."),
                new ApplicationDate("2026-10-07"), ApplicationStatus.INTERVIEWING);

        assertEquals(ApplicationStatus.INTERVIEWING, application.getStatus());
    }

    @Test
    public void isSameApplication() {
        // same object -> returns true
        assertTrue(SOFTWARE_ENGINEER_INTERN.isSameApplication(SOFTWARE_ENGINEER_INTERN));

        // null -> returns false
        assertFalse(SOFTWARE_ENGINEER_INTERN.isSameApplication(null));

        // same name and company, all other attributes different -> returns true
        Application editedApplication = new Application(new ApplicationName("Software Engineer Intern"),
                new Company("Tech Corp"), new Description("Interview scheduled."), new ApplicationDate("2026-10-08"));
        assertTrue(SOFTWARE_ENGINEER_INTERN.isSameApplication(editedApplication));

        // name and company differ only in case -> returns true
        editedApplication = new Application(new ApplicationName("software engineer intern"), new Company("tech corp"),
                new Description("Interview scheduled."), new ApplicationDate("2026-10-08"));
        assertTrue(SOFTWARE_ENGINEER_INTERN.isSameApplication(editedApplication));

        // company differs only in repeated internal whitespace -> returns true
        editedApplication = new Application(new ApplicationName("Software Engineer Intern"), new Company("Tech  Corp"),
                new Description("Interview scheduled."), new ApplicationDate("2026-10-08"));
        assertTrue(SOFTWARE_ENGINEER_INTERN.isSameApplication(editedApplication));

        // different name -> returns false
        editedApplication = new Application(new ApplicationName("Product Manager Intern"), new Company("Tech Corp"),
                new Description("Applied through the company careers page."), new ApplicationDate("2026-10-07"));
        assertFalse(SOFTWARE_ENGINEER_INTERN.isSameApplication(editedApplication));

        // different company -> returns false
        editedApplication = new Application(new ApplicationName("Software Engineer Intern"), new Company("Other Corp"),
                new Description("Applied through the company careers page."), new ApplicationDate("2026-10-07"));
        assertFalse(SOFTWARE_ENGINEER_INTERN.isSameApplication(editedApplication));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Application applicationCopy = new Application(new ApplicationName("Software Engineer Intern"),
                new Company("Tech Corp"), new Description("Applied through the company careers page."),
                new ApplicationDate("2026-10-07"));
        assertTrue(SOFTWARE_ENGINEER_INTERN.equals(applicationCopy));

        // same object -> returns true
        assertTrue(SOFTWARE_ENGINEER_INTERN.equals(SOFTWARE_ENGINEER_INTERN));

        // null -> returns false
        assertFalse(SOFTWARE_ENGINEER_INTERN.equals(null));

        // different type -> returns false
        assertFalse(SOFTWARE_ENGINEER_INTERN.equals(5));

        // different name -> returns false
        Application editedApplication = new Application(new ApplicationName("Product Manager Intern"),
                new Company("Tech Corp"), new Description("Applied through the company careers page."),
                new ApplicationDate("2026-10-07"));
        assertFalse(SOFTWARE_ENGINEER_INTERN.equals(editedApplication));

        // different company -> returns false
        editedApplication = new Application(new ApplicationName("Software Engineer Intern"), new Company("Other Corp"),
                new Description("Applied through the company careers page."), new ApplicationDate("2026-10-07"));
        assertFalse(SOFTWARE_ENGINEER_INTERN.equals(editedApplication));

        // different description -> returns false
        editedApplication = new Application(new ApplicationName("Software Engineer Intern"), new Company("Tech Corp"),
                new Description("Interview scheduled."), new ApplicationDate("2026-10-07"));
        assertFalse(SOFTWARE_ENGINEER_INTERN.equals(editedApplication));

        // different date -> returns false
        editedApplication = new Application(new ApplicationName("Software Engineer Intern"), new Company("Tech Corp"),
                new Description("Applied through the company careers page."), new ApplicationDate("2026-10-08"));
        assertFalse(SOFTWARE_ENGINEER_INTERN.equals(editedApplication));

        // different status -> returns false
        editedApplication = new Application(new ApplicationName("Software Engineer Intern"), new Company("Tech Corp"),
                new Description("Applied through the company careers page."), new ApplicationDate("2026-10-07"),
                ApplicationStatus.INTERVIEWING);
        assertFalse(SOFTWARE_ENGINEER_INTERN.equals(editedApplication));
    }

    @Test
    public void toStringMethod() {
        String expected = Application.class.getCanonicalName() + "{name=" + SOFTWARE_ENGINEER_INTERN.getName()
                + ", company=" + SOFTWARE_ENGINEER_INTERN.getCompany() + ", description="
                + SOFTWARE_ENGINEER_INTERN.getDescription() + ", date=" + SOFTWARE_ENGINEER_INTERN.getDate()
                + ", status=" + SOFTWARE_ENGINEER_INTERN.getStatus() + "}";

        assertEquals(expected, SOFTWARE_ENGINEER_INTERN.toString());
    }
}
