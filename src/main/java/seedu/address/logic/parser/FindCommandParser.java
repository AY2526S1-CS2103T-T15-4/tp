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

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */

public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_COUNTRY, PREFIX_COMPANY, PREFIX_TAG, PREFIX_MEETING, PREFIX_LINK);

        // Check if any prefixes are present
        if (!anyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_COUNTRY, PREFIX_COMPANY, PREFIX_TAG, PREFIX_MEETING, PREFIX_LINK)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Validate that all specified prefixes have at least one keyword
        if (!allSpecifiedPrefixesHaveKeywords(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new MultiFieldContainsKeywordsPredicate(argMultimap));
    }

    /**
     * Returns true if any of the prefixes is present in the given {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if all specified prefixes in the ArgumentMultimap have at least one keyword.
     */
    private static boolean allSpecifiedPrefixesHaveKeywords(ArgumentMultimap argMultimap) {
        for (Prefix prefix : Arrays.asList(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_COUNTRY, PREFIX_COMPANY, PREFIX_TAG, PREFIX_MEETING, PREFIX_LINK)) {
            if (argMultimap.getValue(prefix).isPresent()) {
                List<String> values = argMultimap.getAllValues(prefix);
                // Check if all values for this prefix are empty strings
                if (values.stream().allMatch(String::isEmpty)) {
                    return false;
                }
            }
        }
        return true;
    }
}
