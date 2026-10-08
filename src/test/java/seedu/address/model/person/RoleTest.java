package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Role(""));
    }

    @Test
    public void isValidRole() {
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        assertFalse(Role.isValidRole(""));
        assertFalse(Role.isValidRole(" "));
        assertFalse(Role.isValidRole(" Software Engineer"));
        assertFalse(Role.isValidRole("Engineer/Recruiter"));
        assertFalse(Role.isValidRole("Engineer*"));
        assertFalse(Role.isValidRole("a".repeat(Role.MAX_LENGTH + 1)));

        assertTrue(Role.isValidRole("SWE Recruiter"));
        assertTrue(Role.isValidRole("Engineer 2"));
        assertTrue(Role.isValidRole("R&D Engineer, Level-2."));
        assertTrue(Role.isValidRole("Founder's Associate"));
        assertTrue(Role.isValidRole("Ingénieur Logiciel"));
        assertTrue(Role.isValidRole("a".repeat(Role.MAX_LENGTH)));
    }

    @Test
    public void valueMethods() {
        Role role = new Role("SWE Recruiter");

        assertEquals("SWE Recruiter", role.toString());
        assertEquals(new Role("SWE Recruiter"), role);
        assertEquals(new Role("SWE Recruiter").hashCode(), role.hashCode());
        assertTrue(role.equals(role));
        assertFalse(role.equals(null));
        assertFalse(role.equals(5.0f));
        assertFalse(role.equals(new Role("Software Engineer")));
    }
}
