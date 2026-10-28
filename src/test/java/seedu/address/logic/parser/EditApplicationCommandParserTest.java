package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalApplications.PRODUCT_MANAGER_INTERN;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditApplicationCommand;
import seedu.address.logic.commands.EditApplicationCommand.EditApplicationDescriptor;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.ApplicationStatus;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;

public class EditApplicationCommandParserTest {
    private final EditApplicationCommandParser parser = new EditApplicationCommandParser();

    @Test
    public void parse_allFieldsInAnyOrder_success() {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        descriptor.setName(PRODUCT_MANAGER_INTERN.getName());
        descriptor.setCompany(PRODUCT_MANAGER_INTERN.getCompany());
        descriptor.setDescription(PRODUCT_MANAGER_INTERN.getDescription());
        descriptor.setDate(PRODUCT_MANAGER_INTERN.getDate());
        descriptor.setStatus(ApplicationStatus.INTERVIEWING);
        assertParseSuccess(parser, " 2 dt/2026-10-08 d/Applied through a university careers portal. "
                + "c/Other Corp s/interviewing n/Product Manager Intern",
                new EditApplicationCommand(Index.fromOneBased(2), descriptor));
    }

    @Test
    public void parse_eachFieldAlone_success() {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        descriptor.setName(PRODUCT_MANAGER_INTERN.getName());
        assertParseSuccess(parser, " 1 n/Product Manager Intern ",
                new EditApplicationCommand(Index.fromOneBased(1), descriptor));
        descriptor = new EditApplicationDescriptor();
        descriptor.setCompany(PRODUCT_MANAGER_INTERN.getCompany());
        assertParseSuccess(parser, " 1 c/Other Corp ", new EditApplicationCommand(Index.fromOneBased(1), descriptor));
        descriptor = new EditApplicationDescriptor();
        descriptor.setDescription(PRODUCT_MANAGER_INTERN.getDescription());
        assertParseSuccess(parser, " 1 d/Applied through a university careers portal. ",
                new EditApplicationCommand(Index.fromOneBased(1), descriptor));
        descriptor = new EditApplicationDescriptor();
        descriptor.setDate(new ApplicationDate("2028-02-29"));
        assertParseSuccess(parser, " \t 1 dt/2028-02-29 ",
                new EditApplicationCommand(Index.fromOneBased(1), descriptor));
        descriptor = new EditApplicationDescriptor();
        descriptor.setStatus(ApplicationStatus.OFFERED);
        assertParseSuccess(parser, " 1 s/offered ", new EditApplicationCommand(Index.fromOneBased(1), descriptor));
    }

    @Test
    public void parse_missingOrInvalidIndex_failure() {
        for (String index : new String[]{"", "0", "-1", "+1", "1.5", "one", "2147483648", "1 2"}) {
            assertParseFailure(parser, index + " n/New Role",
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditApplicationCommand.MESSAGE_USAGE));
        }
    }

    @Test
    public void parse_noFields_failure() {
        assertParseFailure(parser, " 1 ", EditApplicationCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_blankOrInvalidField_failure() {
        assertParseFailure(parser, " 1 n/ ", ApplicationName.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " 1 n/" + "x".repeat(101), ApplicationName.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " 1 c/ ", Company.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " 1 c/Invalid*Company", Company.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " 1 d/ ", Description.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " 1 d/" + "x".repeat(501), Description.MESSAGE_CONSTRAINTS);
        for (String date : new String[]{"", "2026-02-29", "2026-02-30", "15-10-2026", "2026-1-01"}) {
            assertParseFailure(parser, " 1 dt/" + date, ApplicationDate.MESSAGE_CONSTRAINTS);
        }
        assertParseFailure(parser, " 1 s/", ApplicationStatus.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " 1 s/pending", ApplicationStatus.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_prefixLikeTextInDescription_failureWithHint() {
        assertParseFailure(parser, " 1 d/Recruiter said s/o needed",
                ApplicationStatus.MESSAGE_CONSTRAINTS + "\n" + ParserUtil.MESSAGE_DESCRIPTION_PREFIX_HINT);
    }

    @Test
    public void parse_repeatedPrefixes_failure() {
        Prefix[] prefixes = {PREFIX_NAME, PREFIX_COMPANY, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_STATUS};
        for (Prefix prefix : prefixes) {
            assertParseFailure(parser, " 1 " + prefix + "first " + prefix + "second",
                    Messages.getErrorMessageForDuplicatePrefixes(prefix));
        }
    }
}
