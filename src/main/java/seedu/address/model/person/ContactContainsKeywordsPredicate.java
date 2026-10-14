package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s name, company, or role matches any of the given keywords.
 */
public class ContactContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ContactContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword -> containsKeyword(person, keyword));
    }

    private boolean containsKeyword(Person person, String keyword) {
        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                || person.getCompany().map(company -> StringUtil.containsWordIgnoreCase(company.value, keyword))
                        .orElse(false)
                || person.getRole().map(role -> StringUtil.containsWordIgnoreCase(role.value, keyword)).orElse(false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactContainsKeywordsPredicate otherContactContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherContactContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
