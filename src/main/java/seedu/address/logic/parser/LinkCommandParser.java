package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Link;

/**
 * Parses input arguments and creates a new LinkCommand object
 */
public class LinkCommandParser implements Parser<LinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LinkCommand
     * and returns an LinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmedArgs.split("\\s+", 2);

        try {
            Index index = ParserUtil.parseIndex(parts[0]);

            if (parts.length == 1) {
                return new LinkCommand(index, null);
            }

            String linkString = parts[1];
            Link link = new Link(linkString);
            return new LinkCommand(index, link);

        } catch (IllegalArgumentException e) {
            throw new ParseException(Link.MESSAGE_CONSTRAINTS);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE), pe);
        }
    }
}
