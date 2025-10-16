package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.SingleFieldContainsKeywordsPredicate;
import seedu.address.model.person.SingleFieldContainsKeywordsPredicate.TargetField;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new SingleFieldContainsKeywordsPredicate(
                        TargetField.NAME, Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_namePrefix_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new SingleFieldContainsKeywordsPredicate(
                        TargetField.NAME, Arrays.asList("Alice")));
        assertParseSuccess(parser, "n/Alice", expected);
    }

    @Test
    public void parse_phonePrefix_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new SingleFieldContainsKeywordsPredicate(
                        TargetField.PHONE, Arrays.asList("12345678", "23456789")));
        assertParseSuccess(parser, "p/   12345678  23456789", expected);
    }

    @Test
    public void parse_emailPrefix_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new SingleFieldContainsKeywordsPredicate(
                        TargetField.EMAIL, Arrays.asList("alice@example.com")));
        assertParseSuccess(parser, "e/alice@example.com", expected);
    }

    @Test
    public void parse_countryPrefix_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new SingleFieldContainsKeywordsPredicate(
                        TargetField.COUNTRY, Arrays.asList("Singapore", "SG")));
        assertParseSuccess(parser, "c/Singapore SG", expected);
    }

    @Test
    public void parse_companyPrefix_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new SingleFieldContainsKeywordsPredicate(
                        TargetField.COMPANY, Arrays.asList("OpenAI")));
        assertParseSuccess(parser, "com/OpenAI", expected);
    }

    @Test
    public void parse_tagPrefix_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new SingleFieldContainsKeywordsPredicate(
                        TargetField.TAG, Arrays.asList("friends")));
        assertParseSuccess(parser, "t/friends", expected);
    }

    @Test
    public void parse_collectsAllKeywords_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new SingleFieldContainsKeywordsPredicate(
                        TargetField.NAME, Arrays.asList("Alice", "Bob", "Charlie")));
        assertParseSuccess(parser, "n/   Alice\tBob\nCharlie  ", expected);
    }

    @Test
    public void parse_noPrefix_throwsParseException() {
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_unknownPrefix_throwsParseException() {
        assertParseFailure(parser, "x/whatever",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_prefixOnly_throwsParseException() {
        assertParseFailure(parser, "n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "n/    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_prefixCaseIsSensitive_throwsParseException() {
        assertParseFailure(parser, "N/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
