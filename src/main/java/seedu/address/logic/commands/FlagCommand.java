package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.FlaggedPredicate;
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
            + "or lists all flagged contacts if no index is given.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example (show flagged contacts): " + COMMAND_WORD;

    public static final String MESSAGE_FLAG_PERSON_SUCCESS = "Flagged person: %1$s";
    public static final String MESSAGE_ALREADY_FLAGGED = "This person is already flagged.";
    public static final String MESSAGE_SHOW_FLAGGED_SUCCESS = "Listed all flagged contacts.";

    private final Index targetIndex;

    public FlagCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public FlagCommand() {
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex == null) {
            model.updateFilteredPersonList(new FlaggedPredicate());
            return new CommandResult(MESSAGE_SHOW_FLAGGED_SUCCESS);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToFlag = lastShownList.get(targetIndex.getZeroBased());

        if (personToFlag.isFlagged()) {
            throw new CommandException(MESSAGE_ALREADY_FLAGGED);
        }

        Person flaggedPerson = personToFlag.withFlag();

        assert flaggedPerson.isFlagged() : "Flagged person should have isFlagged=true";
        model.setPerson(personToFlag, flaggedPerson);

        return new CommandResult(String.format(MESSAGE_FLAG_PERSON_SUCCESS, Messages.format(flaggedPerson)));
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
        if (targetIndex == null && otherFlagCommand.targetIndex == null) {
            return true;
        }
        return targetIndex != null && targetIndex.equals(otherFlagCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
