package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
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
    private final Boolean addressProvided;
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
            @JsonProperty("address") String address, @JsonProperty("addressProvided") Boolean addressProvided,
            @JsonProperty("remark") String remark,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.role = role;
        this.whereMet = whereMet;
        this.address = address;
        this.addressProvided = addressProvided;
        this.remark = remark == null ? "" : remark;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /** Constructs an adapter from legacy data that does not record whether the address was provided. */
    public JsonAdaptedPerson(String name, String phone, String email, String company, String role, String whereMet,
            String address, String remark, List<JsonAdaptedTag> tags) {
        this(name, phone, email, company, role, whereMet, address, null, remark, tags);
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
        addressProvided = source.getAddressOptional().isPresent();
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
        Set<Tag> modelTags = restoreTags();
        Name modelName = restoreRequired(name, Name.class, Name::isValidName, Name::new, Name.MESSAGE_CONSTRAINTS);
        Phone modelPhone = restoreRequired(phone, Phone.class, Phone::isValidPhone, Phone::new,
                Phone.MESSAGE_CONSTRAINTS);
        Email modelEmail = restoreRequired(email, Email.class, Email::isValidEmail, Email::new,
                Email.MESSAGE_CONSTRAINTS);
        Optional<Company> modelCompany = restoreOptional(company, value -> Company.isValidCompany(
                Company.normalize(value)), Company::new, Company.MESSAGE_CONSTRAINTS);
        Optional<Role> modelRole = restoreOptional(role, Role::isValidRole, Role::new, Role.MESSAGE_CONSTRAINTS);
        Optional<WhereMet> modelWhereMet = restoreOptional(whereMet, WhereMet::isValidWhereMet, WhereMet::new,
                WhereMet.MESSAGE_CONSTRAINTS);
        Optional<Address> modelAddress = restoreAddress();
        return new Person(modelName, modelPhone, modelEmail, modelCompany, modelRole, modelWhereMet,
                modelAddress, new Remark(remark), modelTags);
    }

    /** Restores tags and rejects missing tag entries. */
    private Set<Tag> restoreTags() throws IllegalValueException {
        List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            // OpenAI Codex assisted mao455 with this null-tag recovery fix and its regression test.
            if (tag == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Tag.class.getSimpleName()));
            }
            personTags.add(tag.toModelType());
        }
        return new HashSet<>(personTags);
    }

    /** Validates and restores a required field, reporting missing and invalid values separately. */
    private <T> T restoreRequired(String value, Class<T> type, Predicate<String> isValid,
            Function<String, T> create, String constraints) throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, type.getSimpleName()));
        }
        if (!isValid.test(value)) {
            throw new IllegalValueException(constraints);
        }
        return create.apply(value);
    }

    /** Validates and restores an optional field when it is present. */
    private <T> Optional<T> restoreOptional(String value, Predicate<String> isValid,
            Function<String, T> create, String constraints) throws IllegalValueException {
        if (value == null) {
            return Optional.empty();
        }
        if (!isValid.test(value)) {
            throw new IllegalValueException(constraints);
        }
        return Optional.of(create.apply(value));
    }

    /** Restores the address, interpreting the legacy placeholder when no presence flag was saved. */
    private Optional<Address> restoreAddress() throws IllegalValueException {
        Address modelAddress = restoreRequired(address, Address.class, Address::isValidAddress, Address::new,
                Address.MESSAGE_CONSTRAINTS);
        boolean hasAddress = addressProvided != null
                ? addressProvided
                : !Address.LEGACY_DEFAULT_VALUE.equals(address);
        return hasAddress ? Optional.of(modelAddress) : Optional.empty();
    }

}
