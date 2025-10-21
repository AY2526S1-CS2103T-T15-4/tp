package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SingleFieldContainsKeywordsPredicate;
import seedu.address.model.person.SingleFieldContainsKeywordsPredicate.TargetField;

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

        final String[] tokens = trimmedArgs.split("\\s+");
        final String first = tokens[0];

        TargetField field = null;
        String firstKeyword = null;

        if (first.startsWith(PREFIX_NAME.toString())) { // n/
            field = TargetField.NAME;
            firstKeyword = first.substring(PREFIX_NAME.toString().length()).trim();
        } else if (first.startsWith(PREFIX_PHONE.toString())) { // p/
            field = TargetField.PHONE;
            firstKeyword = first.substring(PREFIX_PHONE.toString().length()).trim();
        } else if (first.startsWith(PREFIX_EMAIL.toString())) { // e/
            field = TargetField.EMAIL;
            firstKeyword = first.substring(PREFIX_EMAIL.toString().length()).trim();
        } else if (first.startsWith(PREFIX_COUNTRY.toString())) { // c/
            field = TargetField.COUNTRY;
            firstKeyword = first.substring(PREFIX_COUNTRY.toString().length()).trim();
        } else if (first.startsWith(PREFIX_COMPANY.toString())) { // com/
            field = TargetField.COMPANY;
            firstKeyword = first.substring(PREFIX_COMPANY.toString().length()).trim();
        } else if (first.startsWith(PREFIX_TAG.toString())) { // t/
            field = TargetField.TAG;
            firstKeyword = first.substring(PREFIX_TAG.toString().length()).trim();
        }

        if (field == null) {
            // no valid prefix at the start
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Build keywords list from the first token's suffix + the remaining tokens
        final List<String> keywords = new ArrayList<>();
        if (!firstKeyword.isEmpty()) {
            keywords.add(firstKeyword);
        }
        if (tokens.length > 1) {
            keywords.addAll(Arrays.asList(tokens).subList(1, tokens.length));
        }

        if (keywords.isEmpty()) {
            // prefix given but no keywords
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new SingleFieldContainsKeywordsPredicate(field, keywords));


    }
}


