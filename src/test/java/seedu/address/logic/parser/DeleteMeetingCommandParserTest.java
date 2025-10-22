package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteMeetingCommandParserTest {

    private static final LocalDateTime VALID_MEETING_TIME = LocalDateTime.of(2025, 10, 20, 14, 30);
    private static final String VALID_MEETING_TIME_INPUT = "20-10-2025 14:30";

    private DeleteMeetingCommandParser parser = new DeleteMeetingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = Index.fromOneBased(1);
        String userInput = targetIndex.getOneBased() + " " + PREFIX_MEETING + VALID_MEETING_TIME_INPUT;

        assertParseSuccess(parser, userInput, new DeleteMeetingCommand(targetIndex, VALID_MEETING_TIME));
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE);

        // missing meeting prefix
        assertParseFailure(parser, "1", expectedMessage);

        // missing preamble
        assertParseFailure(parser, PREFIX_MEETING + VALID_MEETING_TIME_INPUT, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE);

        // invalid index
        assertParseFailure(parser, "a " + PREFIX_MEETING + VALID_MEETING_TIME_INPUT, expectedMessage);

        // negative index
        assertParseFailure(parser, "-1 " + PREFIX_MEETING + VALID_MEETING_TIME_INPUT, expectedMessage);

        // zero index
        assertParseFailure(parser, "0 " + PREFIX_MEETING + VALID_MEETING_TIME_INPUT, expectedMessage);
    }

    @Test
    public void parse_invalidMeetingTime_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMeetingCommand.MESSAGE_USAGE);

        // invalid date format
        assertParseFailure(parser, "1 " + PREFIX_MEETING + "invalid-date", expectedMessage);

        // empty meeting time
        assertParseFailure(parser, "1 " + PREFIX_MEETING, expectedMessage);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String expectedMessage = "Multiple values specified for the following single-valued field(s): m/";

        // duplicate meeting prefix
        String userInput = "1 " + PREFIX_MEETING + VALID_MEETING_TIME_INPUT + " " + PREFIX_MEETING + VALID_MEETING_TIME_INPUT;
        assertParseFailure(parser, userInput, expectedMessage);
    }
}