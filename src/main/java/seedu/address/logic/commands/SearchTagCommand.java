package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Finds and lists all persons in address book whose tags contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */

public class SearchTagCommand extends Command {

    public static final String COMMAND_WORD = "SearchTag";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("This is a SearchTag Command, not implemented yet.");
    }
}
