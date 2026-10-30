package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that any field of a {@code Person} matches any of the given keywords.
 */
public class ContactContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Creates a predicate that matches contacts against the given keywords.
     *
     * @param keywords search terms to match; must not be null or contain null values
     */
    public ContactContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = List.copyOf(requireNonNull(keywords));
    }

    /**
     * Returns the immutable keywords used by this predicate.
     */
    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        return keywords.stream().anyMatch(keyword -> containsKeyword(person, keyword));
    }

    private boolean containsKeyword(Person person, String keyword) {
        return StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                || StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword)
                || StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword)
                || person.getCompany().map(company -> StringUtil.containsWordIgnoreCase(company.value, keyword))
                        .orElse(false)
                || person.getRole().map(role -> StringUtil.containsWordIgnoreCase(role.value, keyword)).orElse(false)
                || person.getWhereMet().map(whereMet -> StringUtil.containsWordIgnoreCase(whereMet.value, keyword))
                        .orElse(false)
                || person.getAddressOptional()
                        .map(address -> StringUtil.containsWordIgnoreCase(address.value, keyword)).orElse(false)
                || StringUtil.containsWordIgnoreCase(person.getRemark().value, keyword)
                || person.getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword));
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
    public int hashCode() {
        return keywords.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
