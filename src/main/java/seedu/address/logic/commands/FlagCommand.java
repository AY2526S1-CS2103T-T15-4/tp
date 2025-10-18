package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Flag a person identified using its displayed index from the address book.
 */
public class FlagCommand extends Command {

    public static final String COMMAND_WORD = "flag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Flags the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FLAG_PERSON_SUCCESS = "Flagged person: %1$s";
    public static final String MESSAGE_ALREADY_FLAGGED = "This person is already flagged.";

    private final Index targetIndex;

    public FlagCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToFlag = lastShownList.get(targetIndex.getZeroBased());

        if (personToFlag.isFlagged()) {
            throw new CommandException(MESSAGE_ALREADY_FLAGGED);
        }

        Person flaggedPerson = new Person(
                personToFlag.getName(),
                personToFlag.getPhone(),
                personToFlag.getEmail(),
                personToFlag.getCountry(),
                personToFlag.getCompany(),
                personToFlag.getTags(),
                true
        );

        model.setPerson(personToFlag, flaggedPerson);
        return new CommandResult(String.format(MESSAGE_FLAG_PERSON_SUCCESS, Messages.format(personToFlag)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FlagCommand)) {
            return false;
        }

        FlagCommand otherFlagCommand = (FlagCommand) other;
        return targetIndex.equals(otherFlagCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
