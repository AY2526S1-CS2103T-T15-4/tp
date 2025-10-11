package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Confirms the addition of a duplicate person i.e. same phone or email to the address book.
 */
public class ConfirmAddCommand extends Command {

    private static final String MESSAGE_SUCCESS = "New person added/edited: %1$s";
    private static final String MESSAGE_NO_PENDING_PERSON = "No pending person available.\n"
            + "Please use the 'add' command first.'";
    private Person pendingPerson = null;

    /**
     * Adds a person with an existing phone/email in the address book.
     * @param pendingPerson The duplicate person awaiting addition.
     */
    public ConfirmAddCommand(Person pendingPerson) {
        requireNonNull(pendingPerson);
        this.pendingPerson = pendingPerson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (pendingPerson == null) {
            throw new CommandException(MESSAGE_NO_PENDING_PERSON);
        }

        model.addPerson(pendingPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(pendingPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ConfirmAddCommand otherConfirmAddCommand)) {
            return false;
        }

        return pendingPerson.equals(otherConfirmAddCommand.pendingPerson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", pendingPerson)
                .toString();
    }
}
