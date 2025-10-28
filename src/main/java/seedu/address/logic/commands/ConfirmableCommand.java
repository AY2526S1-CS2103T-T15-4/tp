package seedu.address.logic.commands;

/**
 * A command that requires confirmation when encountering duplicate contacts.
 */
public abstract class ConfirmableCommand extends Command {

    private final boolean confirmed;

    protected ConfirmableCommand() {
        this(false);
    }

    protected ConfirmableCommand(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isConfirmed() {
        return this.confirmed;
    }
    /**
     * Return a new ConfirmableCommand instance that is identical except marked as confirmed.
     * Subclasses must implement and return the appropriate concrete confirmed instance.
     */
    public abstract ConfirmableCommand withConfirmed();
}
