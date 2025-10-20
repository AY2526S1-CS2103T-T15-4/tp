package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Meeting;

/**
 * Parses input arguments and creates a new {@link AddMeetingCommand} object.
 * <p>
 * Expected format: {@code addm INDEX m/MEETING_TIME [DESCRIPTION]}
 * <br>
 * Example: {@code addm 1 m/20-10-2025 14:30 Project discussion}
 */
public class AddMeetingCommandParser implements Parser<AddMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments and returns an {@code AddMeetingCommand} object.
     *
     * @param args Full user input string.
     * @return An {@code AddMeetingCommand} representing the parsed input.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public AddMeetingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MEETING);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEETING) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEETING);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE), pe);
        }
        String meetingInput = argMultimap.getValue(PREFIX_MEETING).get().trim();
        String[] parts = meetingInput.split(" ", 3); // Split on first two spaces (date, time, description)
        if (parts.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        String timePart = parts[0] + " " + parts[1]; // Combine date and time (DD-MM-YYYY HH:MM)
        String description = parts.length > 2 ? parts[2] : null; // Description is optional

        LocalDateTime meetingTime;
        try {
            meetingTime = ParserUtil.parseMeetingTime(timePart);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE), pe);
        }

        Meeting meeting = new Meeting(meetingTime, description);

        return new AddMeetingCommand(index, meeting);
    }

    /**
     * Returns true if all specified prefixes are present in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
