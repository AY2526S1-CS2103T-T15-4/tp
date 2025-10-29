package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListMeetingCommand object
 */
public class ListMeetingParser implements Parser<ListMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListMeetingCommand
     * and returns an ListMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListMeetingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ListMeetingCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListMeetingCommand.MESSAGE_USAGE), pe);
        }
    }
}
