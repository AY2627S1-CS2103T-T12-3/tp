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

        // Phone number
        predicate = new ContactContainsKeywordsPredicate(List.of("91999999"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("91999999").build()));

        // Email address
        predicate = new ContactContainsKeywordsPredicate(List.of("referrer@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("referrer@example.com").build()));

        // Where-met
        predicate = new ContactContainsKeywordsPredicate(List.of("Career"));
        assertTrue(predicate.test(new PersonBuilder().withWhereMet("Career Fair").build()));

        // Words next to punctuation
        predicate = new ContactContainsKeywordsPredicate(List.of("Google"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Google, Inc.").build()));
        predicate = new ContactContainsKeywordsPredicate(List.of("Fair"));
        assertTrue(predicate.test(new PersonBuilder().withWhereMet("NUS Career Fair, 2026").build()));

        // Address
        predicate = new ContactContainsKeywordsPredicate(List.of("Market"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("55 Market Street").build()));

        // Remark
        predicate = new ContactContainsKeywordsPredicate(List.of("Conference"));
        assertTrue(predicate.test(new PersonBuilder().withRemark("Met at conference").build()));

        // Any tag
        predicate = new ContactContainsKeywordsPredicate(List.of("rEFERRAL"));
        assertTrue(predicate.test(new PersonBuilder().withTags("classmate", "referral").build()));
    }

    @Test
    public void test_contactDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate(List.of());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ContactContainsKeywordsPredicate(List.of("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // A contact without optional fields or tags is handled safely
        predicate = new ContactContainsKeywordsPredicate(List.of("Google", "Recruiter"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // An absent address is not searchable, but an explicitly supplied legacy-placeholder value is searchable.
        predicate = new ContactContainsKeywordsPredicate(List.of("provided"));
        assertFalse(predicate.test(new PersonBuilder().withoutAddress().build()));
        assertTrue(predicate.test(new PersonBuilder().withAddress(Address.LEGACY_DEFAULT_VALUE).build()));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Provided by the candidate").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ContactContainsKeywordsPredicate predicate = new ContactContainsKeywordsPredicate(keywords);

        String expected = ContactContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
