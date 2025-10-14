package seedu.address.logic.commands;

/**
 * A Command that requires confirmation in the event of a duplicate contact.
 */
public interface ConfirmableCommand {
    Command confirm();
}
