package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.AddMeetingCommandParser.MESSAGE_INVALID_DESCRIPTION_LENGTH;
import static seedu.address.logic.parser.AddMeetingCommandParser.MESSAGE_INVALID_MEETING_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.model.person.Meeting;

public class AddMeetingCommandParserTest {

    private static final String INVALID_INDEX_ZERO = "0";
    private static final String INVALID_INDEX_NON_NUMERIC = "a";
    private static final String INVALID_MEETING_MISSING_TIME = " m/20-10-2025";
    private static final String INVALID_MEETING_INVALID_DATE = " m/32-13-2025 14:30";
    private static final String INVALID_MEETING_INVALID_DESCRIPTION = " m/30-10-2025 14:30 "
            + "afkasdnfsnfnsdjfnsajfjfjsnjfnsjnfsafnjasfsfnjsnfjsanjfsanjfnjsfnjsnfjnsjanfjasnfj"
            + "fsafadsfsdfdsafsafsdafasdfadsfasdfadsfsadfadsfadsfasdfdsafafdsfadsfsafsadfasdfasd";
    private static final String INVALID_MEETING_INVALID_FORMAT = " m/invalid";
    private static final String VALID_INDEX = "1";
    private static final String PREAMBLE_NON_EMPTY = "extra ";
    private static final String PREAMBLE_WHITESPACE = " ";
    private static final String VALID_MEETING_WITH_DESC = " m/20-10-2025 14:30 Project discussion";
    private static final String VALID_MEETING_WITHOUT_DESC = " m/20-10-2025 14:30";
    private static final LocalDateTime VALID_MEETING_TIME = LocalDateTime.of(2025, 10, 20, 14, 30);
    private static final String VALID_DESCRIPTION = "Project discussion";
    private final AddMeetingCommandParser parser = new AddMeetingCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = Index.fromOneBased(1);
        Meeting expectedMeetingWithDesc = new Meeting(VALID_MEETING_TIME, VALID_DESCRIPTION);
        Meeting expectedMeetingWithoutDesc = new Meeting(VALID_MEETING_TIME);

        // with description
        assertParseSuccess(parser, VALID_INDEX + VALID_MEETING_WITH_DESC,
                new AddMeetingCommand(targetIndex, expectedMeetingWithDesc));

        // without description
        assertParseSuccess(parser, VALID_INDEX + VALID_MEETING_WITHOUT_DESC,
                new AddMeetingCommand(targetIndex, expectedMeetingWithoutDesc));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_INDEX + VALID_MEETING_WITH_DESC,
                new AddMeetingCommand(targetIndex, expectedMeetingWithDesc));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validInput = VALID_INDEX + VALID_MEETING_WITH_DESC;

        // multiple meeting prefixes
        assertParseFailure(parser, validInput + VALID_MEETING_WITH_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING));

        // multiple fields repeated
        assertParseFailure(parser, validInput + VALID_MEETING_WITHOUT_DESC + VALID_MEETING_WITH_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING));

        // invalid value followed by valid value
        assertParseFailure(parser, VALID_INDEX + INVALID_MEETING_INVALID_FORMAT + VALID_MEETING_WITH_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING));

        // valid value followed by invalid value
        assertParseFailure(parser, validInput + INVALID_MEETING_INVALID_FORMAT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEETING));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // without description - already covered in allFieldsPresent, but reiterating
        Index targetIndex = Index.fromOneBased(1);
        Meeting expectedMeetingWithoutDesc = new Meeting(VALID_MEETING_TIME);
        assertParseSuccess(parser, VALID_INDEX + VALID_MEETING_WITHOUT_DESC,
                new AddMeetingCommand(targetIndex, expectedMeetingWithoutDesc));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);

        // missing index (preamble empty)
        assertParseFailure(parser, VALID_MEETING_WITH_DESC, expectedMessage);

        // missing meeting prefix
        assertParseFailure(parser, VALID_INDEX, expectedMessage);

        // all missing
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidIndexValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);

        // invalid index - zero
        assertParseFailure(parser, INVALID_INDEX_ZERO + VALID_MEETING_WITH_DESC, expectedMessage);

        // invalid index - non-numeric
        assertParseFailure(parser, INVALID_INDEX_NON_NUMERIC + VALID_MEETING_WITH_DESC, expectedMessage);

        // two invalid values, only first reported (but since wrapped, expects format message)
        assertParseFailure(parser, INVALID_INDEX_ZERO + INVALID_MEETING_INVALID_FORMAT, expectedMessage);

        // non-empty preamble with extra
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_INDEX + VALID_MEETING_WITH_DESC, expectedMessage);


    }

    @Test
    public void parse_invalidMeetingValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_MEETING_FORMAT);
        // invalid meeting - invalid date
        assertParseFailure(parser, VALID_INDEX + INVALID_MEETING_INVALID_DATE, expectedMessage);
        // invalid meeting - missing time part
        assertParseFailure(parser, VALID_INDEX + INVALID_MEETING_MISSING_TIME, expectedMessage);
        // invalid meeting - invalid format
        assertParseFailure(parser, VALID_INDEX + INVALID_MEETING_INVALID_FORMAT, expectedMessage);
    }

    @Test
    public void parse_invalidDescriptionLength_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_DESCRIPTION_LENGTH);
        // invalid meeting - invalid description
        assertParseFailure(parser, VALID_INDEX + INVALID_MEETING_INVALID_DESCRIPTION, expectedMessage);

    }

    @Test
    public void parse_emptyInput_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, "   ", expectedMessage);
    }
}
