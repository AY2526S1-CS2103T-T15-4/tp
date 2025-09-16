package seedu.address.logic.commands;
import seedu.address.model.Model;

/**
 * Says hello to the user.
 */
public class HelloCommand extends Command {
    public static final String COMMAND_WORD = "hi";
    public static final String MESSAGE_SUCCESS = "Hi! How are you?";


    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
