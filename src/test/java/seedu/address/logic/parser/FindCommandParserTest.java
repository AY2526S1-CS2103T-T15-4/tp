package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.MultiFieldContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_validNamePrefix_returnsFindCommand() {
        String userInput = " n/Alice ";
        ArgumentMultimap expectedMap = ArgumentTokenizer.tokenize(
                userInput, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COUNTRY,
                PREFIX_COMPANY, PREFIX_TAG, PREFIX_MEETING, PREFIX_LINK);
        FindCommand expected = new FindCommand(new MultiFieldContainsKeywordsPredicate(expectedMap));
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_multipleValidPrefixes_returnsFindCommand() {
        String userInput = " n/Alice p/9123 e/@ex r/pore co/Open t/owes ";
        ArgumentMultimap expectedMap = ArgumentTokenizer.tokenize(
                userInput, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COUNTRY,
                PREFIX_COMPANY, PREFIX_TAG, PREFIX_MEETING, PREFIX_LINK);
        FindCommand expected = new FindCommand(new MultiFieldContainsKeywordsPredicate(expectedMap));
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_repeatedPrefix_returnsFindCommand() {
        String userInput = " t/friend t/owes ";
        ArgumentMultimap expectedMap = ArgumentTokenizer.tokenize(
                userInput, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_COUNTRY,
                PREFIX_COMPANY, PREFIX_TAG, PREFIX_MEETING, PREFIX_LINK);
        FindCommand expected = new FindCommand(new MultiFieldContainsKeywordsPredicate(expectedMap));
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
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

    @Test
    public void parse_allSpecifiedPrefixesHaveKeywordsAllEmpty_throwsParseException() {
        String userInput = " n/ e/ ";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
