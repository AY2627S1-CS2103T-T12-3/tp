package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WhereMetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WhereMet(null));
    }

    @Test
    public void constructor_invalidValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new WhereMet(""));
    }

    @Test
    public void isValidWhereMet() {
        assertThrows(NullPointerException.class, () -> WhereMet.isValidWhereMet(null));
        assertFalse(WhereMet.isValidWhereMet(""));
        assertFalse(WhereMet.isValidWhereMet(" "));
        assertFalse(WhereMet.isValidWhereMet(" Leading space"));
        assertFalse(WhereMet.isValidWhereMet("Career fair*"));
        assertFalse(WhereMet.isValidWhereMet("a".repeat(WhereMet.MAX_LENGTH + 1)));
        assertTrue(WhereMet.isValidWhereMet("Café & Careers, 2026"));
        assertTrue(WhereMet.isValidWhereMet("LinkedIn"));
        assertTrue(WhereMet.isValidWhereMet("a".repeat(WhereMet.MAX_LENGTH)));
    }

    @Test
    public void equals() {
        WhereMet whereMet = new WhereMet("Career fair 2026");
        assertTrue(whereMet.equals(new WhereMet("Career fair 2026")));
        assertTrue(whereMet.equals(whereMet));
        assertFalse(whereMet.equals(null));
        assertFalse(whereMet.equals(5.0f));
        assertFalse(whereMet.equals(new WhereMet("LinkedIn")));
    }
}
