package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.ApplicationStatus;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;

public class MessagesTest {

    @Test
    public void format_application_includesStatus() {
        Application application = new Application(new ApplicationName("Software Engineer Intern"),
                new Company("Tech Corp"), new Description("Applied through the company careers page."),
                new ApplicationDate("2026-10-07"), ApplicationStatus.INTERVIEWING);

        assertEquals("Software Engineer Intern; Company: Tech Corp; Description: "
                + "Applied through the company careers page.; Date: 2026-10-07; Status: INTERVIEWING",
                Messages.format(application));
    }
}
