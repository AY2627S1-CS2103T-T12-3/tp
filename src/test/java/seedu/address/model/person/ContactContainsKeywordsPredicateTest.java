package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ContactContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = List.of("first");
        List<String> secondPredicateKeywordList = List.of("first", "second");

        ContactContainsKeywordsPredicate firstPredicate =
                new ContactContainsKeywordsPredicate(firstPredicateKeywordList);
        ContactContainsKeywordsPredicate secondPredicate =
                new ContactContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContactContainsKeywordsPredicate firstPredicateCopy =
                new ContactContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_contactContainsKeywords_returnsTrue() {
        // One keyword
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate(List.of("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new ContactContainsKeywordsPredicate(List.of("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ContactContainsKeywordsPredicate(List.of("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ContactContainsKeywordsPredicate(List.of("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Company keyword
        predicate = new ContactContainsKeywordsPredicate(List.of("gOOgle"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").build()));

        // Role keyword
        predicate = new ContactContainsKeywordsPredicate(List.of("reCRuiter"));
        assertTrue(predicate.test(new PersonBuilder().withRole("SWE Recruiter").build()));

        // A keyword can match a different contact field from the other keywords
        predicate = new ContactContainsKeywordsPredicate(List.of("Google", "Recruiter"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google").withRole("SWE Recruiter").build()));
    }

    @Test
    public void test_contactDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate(List.of());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ContactContainsKeywordsPredicate(List.of("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but do not match searchable contact fields
        predicate = new ContactContainsKeywordsPredicate(List.of("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("1234567")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // A contact without company and role is handled safely
        predicate = new ContactContainsKeywordsPredicate(List.of("Google", "Recruiter"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate(keywords);

        String expected = ContactContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
