package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.ApplicationStatus;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;

/**
 * Jackson-friendly version of {@link Application}.
 */
class JsonAdaptedApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Application's %s field is missing!";

    private final String name;
    private final String company;
    private final String description;
    private final String date;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedApplication} with the given application details.
     */
    @JsonCreator
    public JsonAdaptedApplication(@JsonProperty("name") String name, @JsonProperty("company") String company,
            @JsonProperty("description") String description, @JsonProperty("date") String date,
            @JsonProperty("status") String status) {
        this.name = name;
        this.company = company;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    /**
     * Converts a given {@code Application} into this class for Jackson use.
     */
    public JsonAdaptedApplication(Application source) {
        name = source.getName().value;
        company = source.getCompany().value;
        description = source.getDescription().value;
        date = source.getDate().value;
        status = source.getStatus().name();
    }

    /**
     * Converts this Jackson-friendly adapted application object into the model's {@code Application} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted application.
     */
    public Application toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ApplicationName.class.getSimpleName()));
        }
        if (!ApplicationName.isValidName(name)) {
            throw new IllegalValueException(ApplicationName.MESSAGE_CONSTRAINTS);
        }
        final ApplicationName modelName = new ApplicationName(name);

        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(Company.normalize(company))) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(company);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ApplicationDate.class.getSimpleName()));
        }
        if (!ApplicationDate.isValidDate(date)) {
            throw new IllegalValueException(ApplicationDate.MESSAGE_CONSTRAINTS);
        }
        final ApplicationDate modelDate = new ApplicationDate(date);

        final ApplicationStatus modelStatus;
        if (status == null) {
            modelStatus = ApplicationStatus.APPLIED;
        } else {
            try {
                modelStatus = ApplicationStatus.fromString(status);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(ApplicationStatus.MESSAGE_CONSTRAINTS);
            }
        }

        return new Application(modelName, modelCompany, modelDescription, modelDate, modelStatus);
    }
}
