package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {

    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_validArguments_returnsRemarkCommand() throws Exception {
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_SECOND_PERSON, new Remark("Likes baseball"));
        assertEquals(expectedCommand, parser.parse("2 r/Likes baseball"));
    }

    @Test
    public void parse_emptyRemark_returnsRemarkCommand() throws Exception {
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_SECOND_PERSON, new Remark(""));
        assertEquals(expectedCommand, parser.parse("2 r/"));
    }

    @Test
    public void parse_missingRemarkPrefix_returnsRemarkCommandWithEmptyRemark() throws Exception {
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_SECOND_PERSON, new Remark(""));
        assertEquals(expectedCommand, parser.parse("2"));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        assertThrows(ParseException.class, expectedMessage, () -> parser.parse("zero r/remark"));
    }
}
