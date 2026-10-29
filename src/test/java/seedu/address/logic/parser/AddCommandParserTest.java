package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WHERE_MET_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WHERE_MET_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WHERE_MET_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WHERE_MET;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.WhereMet;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = getExpectedPerson();
        String validContact = NAME_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB
                + WHERE_MET_DESC_BOB;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + validContact, new AddCommand(expectedPerson));

        // fields may be supplied in any order
        assertParseSuccess(parser,
                WHERE_MET_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ROLE_DESC_BOB + COMPANY_DESC_BOB
                        + NAME_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + WHERE_MET_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple companies
        assertParseFailure(parser, COMPANY_DESC_AMY + COMPANY_DESC_BOB + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));

        // multiple roles
        assertParseFailure(parser, ROLE_DESC_AMY + ROLE_DESC_BOB + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // multiple where-met values
        assertParseFailure(parser, WHERE_MET_DESC_BOB + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WHERE_MET));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + COMPANY_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_WHERE_MET, PREFIX_EMAIL,
                        PREFIX_COMPANY, PREFIX_ROLE, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, COMPANY_DESC_BOB + ROLE_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB
                + WHERE_MET_DESC_BOB, expectedMessage);

        // missing company prefix
        assertParseFailure(parser, NAME_DESC_BOB + ROLE_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB
                + WHERE_MET_DESC_BOB, expectedMessage);

        // missing role prefix
        assertParseFailure(parser, NAME_DESC_BOB + COMPANY_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB
                + WHERE_MET_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + PHONE_DESC_BOB
                + WHERE_MET_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + EMAIL_DESC_BOB
                + WHERE_MET_DESC_BOB, expectedMessage);

    }

    @Test
    public void parse_whereMetMissing_success() {
        Person expectedPerson = new PersonBuilder(getExpectedPerson()).withoutWhereMet().build();
        assertParseSuccess(parser, NAME_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_invalidValue_failure() {
        String validContact = COMPANY_DESC_BOB + ROLE_DESC_BOB + EMAIL_DESC_BOB + PHONE_DESC_BOB
                + WHERE_MET_DESC_BOB;

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validContact, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_PHONE_DESC + WHERE_MET_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + INVALID_EMAIL_DESC
                + PHONE_DESC_BOB + WHERE_MET_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_COMPANY_DESC + ROLE_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + WHERE_MET_DESC_BOB, Company.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOB + COMPANY_DESC_BOB + INVALID_ROLE_DESC + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + WHERE_MET_DESC_BOB, Role.MESSAGE_CONSTRAINTS);

        // invalid WhereMet
        assertParseFailure(parser, NAME_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + EMAIL_DESC_BOB
                + PHONE_DESC_BOB + INVALID_WHERE_MET_DESC, WhereMet.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + COMPANY_DESC_BOB + ROLE_DESC_BOB + INVALID_EMAIL_DESC
                + PHONE_DESC_BOB + WHERE_MET_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB
                + EMAIL_DESC_BOB + PHONE_DESC_BOB + WHERE_MET_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    private Person getExpectedPerson() {
        return new PersonBuilder(BOB).withCompany(VALID_COMPANY_BOB).withRole(VALID_ROLE_BOB)
                .withWhereMet(VALID_WHERE_MET_BOB).withoutAddress().withTags().build();
    }
}
