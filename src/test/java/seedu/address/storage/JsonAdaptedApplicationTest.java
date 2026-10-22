package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedApplication.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.ApplicationStatus;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;

public class JsonAdaptedApplicationTest {

    private static final String VALID_NAME = "Software Engineer Intern";
    private static final String VALID_COMPANY = "Tech Corp";
    private static final String VALID_DESCRIPTION = "Applied through the company careers page.";
    private static final String VALID_DATE = "2026-10-07";
    private static final String VALID_STATUS = "INTERVIEWING";
    private static final Application VALID_APPLICATION = new Application(new ApplicationName(VALID_NAME),
            new Company(VALID_COMPANY), new Description(VALID_DESCRIPTION), new ApplicationDate(VALID_DATE));

    @Test
    public void toModelType_validApplicationDetails_returnsApplication() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_APPLICATION);

        assertEquals(VALID_APPLICATION, application.toModelType());
    }

    @Test
    public void toModelType_missingStatus_defaultsToApplied() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_COMPANY, VALID_DESCRIPTION,
                VALID_DATE, null);

        assertEquals(ApplicationStatus.APPLIED, application.toModelType().getStatus());
    }

    @Test
    public void toModelType_validStatus_returnsApplicationWithStatus() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_COMPANY, VALID_DESCRIPTION,
                VALID_DATE, VALID_STATUS);

        assertEquals(ApplicationStatus.INTERVIEWING, application.toModelType().getStatus());
    }

    @Test
    public void toModelType_applicationWithStatus_preservesStatus() throws Exception {
        Application applicationWithStatus = new Application(new ApplicationName(VALID_NAME), new Company(VALID_COMPANY),
                new Description(VALID_DESCRIPTION), new ApplicationDate(VALID_DATE), ApplicationStatus.INTERVIEWING);

        assertEquals(applicationWithStatus, new JsonAdaptedApplication(applicationWithStatus).toModelType());
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_COMPANY, VALID_DESCRIPTION,
                VALID_DATE, "PENDING");

        assertThrows(IllegalValueException.class, ApplicationStatus.MESSAGE_CONSTRAINTS, application::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication("", VALID_COMPANY, VALID_DESCRIPTION,
                VALID_DATE, VALID_STATUS);

        assertThrows(IllegalValueException.class, ApplicationName.MESSAGE_CONSTRAINTS, application::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(null, VALID_COMPANY, VALID_DESCRIPTION,
                VALID_DATE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ApplicationName.class.getSimpleName());

        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, "Tech*Corp", VALID_DESCRIPTION,
                VALID_DATE, VALID_STATUS);

        assertThrows(IllegalValueException.class, Company.MESSAGE_CONSTRAINTS, application::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, null, VALID_DESCRIPTION,
                VALID_DATE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());

        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_COMPANY, "", VALID_DATE,
                VALID_STATUS);

        assertThrows(IllegalValueException.class, Description.MESSAGE_CONSTRAINTS, application::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_COMPANY, null, VALID_DATE,
                VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());

        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_COMPANY, VALID_DESCRIPTION,
                "2026-02-30", VALID_STATUS);

        assertThrows(IllegalValueException.class, ApplicationDate.MESSAGE_CONSTRAINTS, application::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_COMPANY, VALID_DESCRIPTION,
                null, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ApplicationDate.class.getSimpleName());

        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }
}
