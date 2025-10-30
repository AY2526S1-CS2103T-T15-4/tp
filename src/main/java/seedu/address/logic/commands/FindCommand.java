package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.MultiFieldContainsKeywordsPredicate;
import seedu.address.model.Model;

/**
 * Finds and lists all persons in address book whose specified parameter contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all people whose selected parameter contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Multiple fields are combined with AND logic. Keywords within the same field use OR logic.\n"
            + "Parameters: PREFIX KEYWORD [MORE_KEYWORDS]...\n"
            + "Examples:\n"
            + "• " + COMMAND_WORD + " " + PREFIX_NAME + "john " + PREFIX_COUNTRY + "singapore\n"
            + "   (Finds people named 'john' AND from 'singapore')\n"
            + "• " + COMMAND_WORD + " " + PREFIX_COMPANY + "tech " + PREFIX_EMAIL + "test1@gmail.com\n"
            + "   (Finds people working in 'tech' companies AND with 'test1@gmail.com' emails)\n";

    private final MultiFieldContainsKeywordsPredicate predicate;

    public FindCommand(MultiFieldContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        assert model != null : "model cannot be null.";
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

