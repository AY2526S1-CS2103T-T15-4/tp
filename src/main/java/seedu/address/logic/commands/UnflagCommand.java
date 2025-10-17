package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Unflag a person identified using its displayed index from the address book.
 */
public class UnflagCommand extends Command {

    public static final String COMMAND_WORD = "unflag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unflags the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNFLAG_PERSON_SUCCESS = "Unflagged person: %1$s";
    public static final String MESSAGE_ALREADY_UNFLAGGED = "This person is already unflagged.";

    private final Index targetIndex;

    public UnflagCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnflag = lastShownList.get(targetIndex.getZeroBased());

        if (!personToUnflag.isFlagged()) {
            throw new CommandException(MESSAGE_ALREADY_UNFLAGGED);
        }

        Person unflaggedPerson = new Person(
                personToUnflag.getName(),
                personToUnflag.getPhone(),
                personToUnflag.getEmail(),
                personToUnflag.getCountry(),
                personToUnflag.getCompany(),
                personToUnflag.getTags(),
                false 
        );

        model.setPerson(personToUnflag, unflaggedPerson);
        return new CommandResult(String.format(MESSAGE_UNFLAG_PERSON_SUCCESS, unflaggedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnflagCommand)) {
            return false;
        }

        UnflagCommand otherUnflagCommand = (UnflagCommand) other;
        return targetIndex.equals(otherUnflagCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
