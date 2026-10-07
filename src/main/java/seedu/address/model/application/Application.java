package seedu.address.model.application;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Company;

/**
 * Represents a job application in InternTrack.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Application {

    private final ApplicationName name;
    private final Company company;
    private final Description description;
    private final ApplicationDate date;

    /**
     * Every field must be present and not null.
     */
    public Application(ApplicationName name, Company company, Description description, ApplicationDate date) {
        requireAllNonNull(name, company, description, date);
        this.name = name;
        this.company = company;
        this.description = description;
        this.date = date;
    }

    public ApplicationName getName() {
        return name;
    }

    public Company getCompany() {
        return company;
    }

    public Description getDescription() {
        return description;
    }

    public ApplicationDate getDate() {
        return date;
    }

    /**
     * Returns true if both applications have the same name and company, ignoring letter case.
     * This defines a weaker notion of equality between two applications.
     */
    public boolean isSameApplication(Application otherApplication) {
        if (otherApplication == this) {
            return true;
        }

        return otherApplication != null
                && name.value.equalsIgnoreCase(otherApplication.name.value)
                && company.value.equalsIgnoreCase(otherApplication.company.value);
    }

    /**
     * Returns true if both applications have the same identity and data fields.
     * This defines a stronger notion of equality between two applications.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Application otherApplication)) {
            return false;
        }

        return name.equals(otherApplication.name)
                && company.equals(otherApplication.company)
                && description.equals(otherApplication.description)
                && date.equals(otherApplication.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, company, description, date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("company", company)
                .add("description", description)
                .add("date", date)
                .toString();
    }
}
