package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.WhereMet;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String company;
    private final String role;
    private final String whereMet;
    private final String address;
    private final String remark;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("company") String company,
            @JsonProperty("role") String role,
            @JsonProperty("whereMet") String whereMet,
            @JsonProperty("address") String address, @JsonProperty("remark") String remark,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.role = role;
        this.whereMet = whereMet;
        this.address = address;
        this.remark = remark == null ? "" : remark;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /** Constructs a person adapter without Role or WhereMet for compatibility with older callers. */
    public JsonAdaptedPerson(String name, String phone, String email, String company, String address, String remark,
            List<JsonAdaptedTag> tags) {
        this(name, phone, email, company, null, null, address, remark, tags);
    }

    /**
     * Constructs a {@code JsonAdaptedPerson} without a company, WhereMet, or remark for compatibility with older
     * callers and data.
     */
    public JsonAdaptedPerson(String name, String phone, String email, String address, List<JsonAdaptedTag> tags) {
        this(name, phone, email, null, null, null, address, "", tags);
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        company = source.getCompany().map(c -> c.value).orElse(null);
        role = source.getRole().map(r -> r.value).orElse(null);
        whereMet = source.getWhereMet().map(w -> w.value).orElse(null);
        address = source.getAddress().value;
        remark = source.getRemark().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (company != null && !Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Optional<Company> modelCompany = Optional.ofNullable(company).map(Company::new);

        if (role != null && !Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Optional<Role> modelRole = Optional.ofNullable(role).map(Role::new);

        if (whereMet != null && !WhereMet.isValidWhereMet(whereMet)) {
            throw new IllegalValueException(WhereMet.MESSAGE_CONSTRAINTS);
        }
        final Optional<WhereMet> modelWhereMet = Optional.ofNullable(whereMet).map(WhereMet::new);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);
        final Remark modelRemark = new Remark(remark);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPhone, modelEmail, modelCompany, modelRole, modelWhereMet, modelAddress,
                modelRemark, modelTags);
    }

}
