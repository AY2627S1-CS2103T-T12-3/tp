package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidCompany = "";
        assertThrows(IllegalArgumentException.class, () -> new Company(invalidCompany));
    }

    @Test
    public void isValidCompany() {
        // null company
        assertThrows(NullPointerException.class, () -> Company.isValidCompany(null));

        // invalid companies
        assertFalse(Company.isValidCompany("")); // empty string
        assertFalse(Company.isValidCompany(" ")); // spaces only
        assertFalse(Company.isValidCompany(" Google")); // leading space
        assertFalse(Company.isValidCompany("Google/Alphabet")); // '/' not allowed
        assertFalse(Company.isValidCompany("Acme*")); // '*' not allowed
        assertFalse(Company.isValidCompany("a".repeat(Company.MAX_LENGTH + 1))); // too long

        // valid companies
        assertTrue(Company.isValidCompany("G")); // one character
        assertTrue(Company.isValidCompany("Google"));
        assertTrue(Company.isValidCompany("Bank of America")); // with spaces
        assertTrue(Company.isValidCompany("Meta, Inc.")); // with comma and full stop
        assertTrue(Company.isValidCompany("A&O Shearman")); // with ampersand
        assertTrue(Company.isValidCompany("McDonald's")); // with apostrophe
        assertTrue(Company.isValidCompany("Rolls-Royce")); // with hyphen
        assertTrue(Company.isValidCompany("Société Générale")); // non-ASCII letters
        assertTrue(Company.isValidCompany("a".repeat(Company.MAX_LENGTH))); // maximum length
    }

    @Test
    public void equals() {
        Company company = new Company("Valid Company");

        // same values -> returns true
        assertTrue(company.equals(new Company("Valid Company")));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(new Company("Other Valid Company")));
    }
}
