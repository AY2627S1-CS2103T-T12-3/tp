package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ApplicationNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ApplicationName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ApplicationName(""));
    }

    @Test
    public void constructor_nameWithWhitespace_normalizesWhitespace() {
        ApplicationName applicationName = new ApplicationName("  Software   Engineer Intern  ");

        assertEquals("Software Engineer Intern", applicationName.value);
    }

    @Test
    public void isValidName() {
        // null application name
        assertThrows(NullPointerException.class, () -> ApplicationName.isValidName(null));

        // invalid application names
        assertFalse(ApplicationName.isValidName("")); // empty string
        assertFalse(ApplicationName.isValidName("   ")); // blank string
        assertFalse(ApplicationName.isValidName("Software\nEngineer")); // control character
        assertFalse(ApplicationName.isValidName("a".repeat(ApplicationName.MAX_LENGTH + 1))); // too long

        // valid application names
        assertTrue(ApplicationName.isValidName("Software Engineer Intern"));
        assertTrue(ApplicationName.isValidName("SWE - New Grad"));
        assertTrue(ApplicationName.isValidName("R&D Engineer, Level 2"));
        assertTrue(ApplicationName.isValidName("C++ Developer"));
        assertTrue(ApplicationName.isValidName("SWE/ML Engineer (Platform)"));
        assertTrue(ApplicationName.isValidName("Ingénieur Logiciel"));
        assertTrue(ApplicationName.isValidName("a".repeat(ApplicationName.MAX_LENGTH))); // maximum length
    }
}
