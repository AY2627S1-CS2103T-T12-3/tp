package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ApplicationDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ApplicationDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ApplicationDate("2026-02-30"));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> ApplicationDate.isValidDate(null));

        // invalid dates
        assertFalse(ApplicationDate.isValidDate("")); // empty string
        assertFalse(ApplicationDate.isValidDate("2026-2-03")); // incorrect month width
        assertFalse(ApplicationDate.isValidDate("2026-02-3")); // incorrect day width
        assertFalse(ApplicationDate.isValidDate("26-02-03")); // incorrect year width
        assertFalse(ApplicationDate.isValidDate("2026/02/03")); // incorrect separator
        assertFalse(ApplicationDate.isValidDate("2026-02-30")); // impossible date
        assertFalse(ApplicationDate.isValidDate("2025-02-29")); // non-leap-year date
        assertFalse(ApplicationDate.isValidDate("2026-13-01")); // invalid month
        assertFalse(ApplicationDate.isValidDate("0001-01-01")); // far past
        assertFalse(ApplicationDate.isValidDate("1999-12-31")); // just before earliest date
        assertFalse(ApplicationDate.isValidDate("2100-01-01")); // just after latest date
        assertFalse(ApplicationDate.isValidDate("9999-12-31")); // far future

        // valid dates
        assertTrue(ApplicationDate.isValidDate("2026-01-01"));
        assertTrue(ApplicationDate.isValidDate("2024-02-29")); // leap-year date
        assertTrue(ApplicationDate.isValidDate("2026-12-31"));
        assertTrue(ApplicationDate.isValidDate("2000-01-01")); // earliest date
        assertTrue(ApplicationDate.isValidDate("2099-12-31")); // latest date
    }
}
