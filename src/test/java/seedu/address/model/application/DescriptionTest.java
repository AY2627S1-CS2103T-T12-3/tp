package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Description(""));
    }

    @Test
    public void constructor_descriptionWithWhitespace_trimsWhitespace() {
        Description description = new Description("  Referred by Alice; phone screen scheduled.  ");

        assertEquals("Referred by Alice; phone screen scheduled.", description.value);
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid descriptions
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription("   ")); // blank string
        assertFalse(Description.isValidDescription("a".repeat(Description.MAX_LENGTH + 1))); // too long

        // valid descriptions
        assertTrue(Description.isValidDescription("Applied via referral from Alice Tan; backend team."));
        assertTrue(Description.isValidDescription("C++/Java, interview stage: phone screen (09:00)."));
        assertTrue(Description.isValidDescription("a".repeat(Description.MAX_LENGTH))); // maximum length
    }
}
