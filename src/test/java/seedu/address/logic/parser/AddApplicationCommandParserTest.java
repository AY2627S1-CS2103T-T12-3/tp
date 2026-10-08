package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddApplicationCommand;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;

public class AddApplicationCommandParserTest {

    private static final String NAME_DESC = " n/Software Engineer Intern";
    private static final String COMPANY_DESC = " c/Tech Corp";
    private static final String DESCRIPTION_DESC = " d/Applied through the company careers page.";
    private static final String DATE_DESC = " dt/2026-10-07";
    private static final String VALID_APPLICATION_STRING = NAME_DESC + COMPANY_DESC + DESCRIPTION_DESC + DATE_DESC;

    private final AddApplicationCommandParser parser = new AddApplicationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Application expectedApplication = new Application(new ApplicationName("Software Engineer Intern"),
                new Company("Tech Corp"), new Description("Applied through the company careers page."),
                new ApplicationDate("2026-10-07"));

        assertParseSuccess(parser, VALID_APPLICATION_STRING, new AddApplicationCommand(expectedApplication));
    }

    @Test
    public void parse_repeatedValue_failure() {
        assertParseFailure(parser, NAME_DESC + VALID_APPLICATION_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
        assertParseFailure(parser, COMPANY_DESC + VALID_APPLICATION_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));
        assertParseFailure(parser, DESCRIPTION_DESC + VALID_APPLICATION_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));
        assertParseFailure(parser, DATE_DESC + VALID_APPLICATION_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApplicationCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "Software Engineer Intern" + COMPANY_DESC + DESCRIPTION_DESC + DATE_DESC,
                expectedMessage);
        assertParseFailure(parser, NAME_DESC + "Tech Corp" + DESCRIPTION_DESC + DATE_DESC, expectedMessage);
        assertParseFailure(parser, NAME_DESC + COMPANY_DESC + "Applied through the company careers page." + DATE_DESC,
                expectedMessage);
        assertParseFailure(parser, NAME_DESC + COMPANY_DESC + DESCRIPTION_DESC + "2026-10-07", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " n/ " + COMPANY_DESC + DESCRIPTION_DESC + DATE_DESC,
                ApplicationName.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC + " c/Tech*Corp" + DESCRIPTION_DESC + DATE_DESC,
                Company.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC + COMPANY_DESC + " d/ " + DATE_DESC, Description.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC + COMPANY_DESC + DESCRIPTION_DESC + " dt/2026-02-30",
                ApplicationDate.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertParseFailure(parser, "unexpected" + VALID_APPLICATION_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApplicationCommand.MESSAGE_USAGE));
    }
}
