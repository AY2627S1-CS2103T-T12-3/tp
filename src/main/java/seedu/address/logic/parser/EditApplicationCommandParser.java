package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditApplicationCommand;
import seedu.address.logic.commands.EditApplicationCommand.EditApplicationDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates an EditApplicationCommand.
 */
public class EditApplicationCommandParser implements Parser<EditApplicationCommand> {

    /**
     * Parses the application index and optional replacement fields.
     * @throws ParseException if the index or fields are invalid, or no fields are supplied.
     */
    public EditApplicationCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenizeWithFreeText(args, PREFIX_DESCRIPTION,
                PREFIX_NAME, PREFIX_COMPANY, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_STATUS);

        Index index = parseIndex(argMultimap.getPreamble());
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_COMPANY, PREFIX_DESCRIPTION, PREFIX_DATE,
                PREFIX_STATUS);
        EditApplicationDescriptor descriptor = parseDescriptor(argMultimap);
        if (!descriptor.isAnyFieldEdited()) {
            throw new ParseException(EditApplicationCommand.MESSAGE_NOT_EDITED);
        }
        return new EditApplicationCommand(index, descriptor);
    }

    /** Parses the index, reporting errors with the edit application command format. */
    private Index parseIndex(String preamble) throws ParseException {
        try {
            return ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditApplicationCommand.MESSAGE_USAGE), pe);
        }
    }

    /** Parses the supplied optional fields into an edit descriptor. */
    private EditApplicationDescriptor parseDescriptor(ArgumentMultimap argMultimap) throws ParseException {
        EditApplicationDescriptor descriptor = new EditApplicationDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            descriptor.setName(ParserUtil.parseApplicationName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            descriptor.setCompany(ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            descriptor.setDescription(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            descriptor.setDate(ParserUtil.parseApplicationDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            descriptor.setStatus(ParserUtil.parseApplicationStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }
        return descriptor;
    }
}
