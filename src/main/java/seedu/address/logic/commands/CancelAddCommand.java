package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * A command that tells the user adding of a duplicate person is cancelled.
 */
public class CancelAddCommand extends Command {
    private static final String MESSAGE_CANCELLATION = "Add Command cancelled.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_CANCELLATION);
    }

    @Override
    public boolean equals(Object other) {
        // All CancelAddCommands are the same
        return other instanceof CancelAddCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
