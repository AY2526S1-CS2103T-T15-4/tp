package seedu.address.logic.commands;

/**
 * A command that requires confirmation when encountering duplicate contacts.
 */
public abstract class ConfirmableCommand extends Command {
    public abstract void confirm();
}
