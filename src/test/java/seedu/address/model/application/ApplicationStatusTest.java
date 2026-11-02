package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ApplicationStatusTest {

    @Test
    public void fromString_validValue_returnsStatus() {
        assertEquals(ApplicationStatus.APPLIED, ApplicationStatus.fromString("APPLIED"));
        assertEquals(ApplicationStatus.INTERVIEWING, ApplicationStatus.fromString("interviewing"));
        assertEquals(ApplicationStatus.OFFERED, ApplicationStatus.fromString(" Offered "));
        assertEquals(ApplicationStatus.REJECTED, ApplicationStatus.fromString("REJECTED"));
        assertEquals(ApplicationStatus.WITHDRAWN, ApplicationStatus.fromString("withdrawn"));
    }

    @Test
    public void fromString_invalidValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ApplicationStatus.fromString("pending"));
        assertThrows(IllegalArgumentException.class, () -> ApplicationStatus.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> ApplicationStatus.fromString("  "));
    }

    @Test
    public void fromString_nullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ApplicationStatus.fromString(null));
    }
}
