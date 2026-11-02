package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;

/**
 * Reusable application fixtures for command and integration tests.
 */
public class TypicalApplications {
    public static final Application SOFTWARE_ENGINEER_INTERN = new Application(
            new ApplicationName("Software Engineer Intern"), new Company("Tech Corp"),
            new Description("Applied through the company careers page."), new ApplicationDate("2026-10-07"));
    public static final Application PRODUCT_MANAGER_INTERN = new Application(
            new ApplicationName("Product Manager Intern"), new Company("Other Corp"),
            new Description("Applied through a university careers portal."), new ApplicationDate("2026-10-08"));

    private TypicalApplications() {}

    /**
     * Returns an address book with two applications in a known order.
     */
    public static AddressBook getTypicalApplicationAddressBook() {
        AddressBook addressBook = new AddressBook();
        addressBook.addApplication(SOFTWARE_ENGINEER_INTERN);
        addressBook.addApplication(PRODUCT_MANAGER_INTERN);
        return addressBook;
    }
}
