package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.model.person.Link;

public class LinkCommandParserTest {

    private static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    private static final Index INDEX_SECOND_PERSON = Index.fromOneBased(2);
    private static final String INVALID_LINK = "invalid_link";
    private static final String VALID_LINK_GOOGLE = "https://google.com";
    private static final String VALID_LINK_YOUTUBE = "https://youtube.com";

    private final LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse_validArgsWithoutLink_success() {
        // Only index, no link
        LinkCommand expectedCommand = new LinkCommand(INDEX_FIRST_PERSON, null);
        assertParseSuccess(parser, "1", expectedCommand);
    }

    @Test
    public void parse_validArgsWithLink_success() {
        // Index + valid link
        LinkCommand expectedCommand = new LinkCommand(INDEX_SECOND_PERSON, new Link(VALID_LINK_GOOGLE));
        assertParseSuccess(parser, "2 " + VALID_LINK_GOOGLE, expectedCommand);

        expectedCommand = new LinkCommand(INDEX_SECOND_PERSON, new Link(VALID_LINK_YOUTUBE));
        assertParseSuccess(parser, "2 " + VALID_LINK_YOUTUBE, expectedCommand);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // Non-integer index
        assertParseFailure(parser, "a " + VALID_LINK_GOOGLE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));

        // Zero or negative index
        assertParseFailure(parser, "0 " + VALID_LINK_GOOGLE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1 " + VALID_LINK_GOOGLE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidLink_failure() {
        // Invalid link format
        assertParseFailure(parser, "1 " + INVALID_LINK, Link.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingParts_failure() {
        // No arguments
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));

        // Only whitespace
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }
}
