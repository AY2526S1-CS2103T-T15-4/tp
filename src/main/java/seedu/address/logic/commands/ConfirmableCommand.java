package seedu.address.logic.commands;

/**
 * A command that requires confirmation when encountering duplicate contacts.
 */
public abstract class ConfirmableCommand extends Command {
    /**
     * Confirms the command by setting a boolean flag in the class to true
     */
    public abstract void confirm();
}
