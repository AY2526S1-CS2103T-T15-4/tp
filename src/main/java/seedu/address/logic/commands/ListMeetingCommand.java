package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;

/**
 * Lists all past meetings of a contact to the user.
 */
public class ListMeetingCommand extends Command {
    public static final String COMMAND_WORD = "listm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all past meetings with the contact. "
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_NO_PAST_MEETINGS = "There are no past meetings.";

    private final Index index;

    public ListMeetingCommand(Index targetIndex) {
        this.index = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToList = lastShownList.get(index.getZeroBased());
        assert personToList != null;

        LocalDateTime now = LocalDateTime.now();
        List<Meeting> pastMeetings = personToList.getMeetings().stream()
                .filter(meeting -> meeting.getMeetingTime().isBefore(now))
                .sorted(Comparator.comparing(Meeting::getMeetingTime).reversed())
                .toList();

        if (pastMeetings.isEmpty()) {
            return new CommandResult(MESSAGE_NO_PAST_MEETINGS);
        }

        List<String> lines = new ArrayList<>();
        lines.add("Past meetings: ");
        for (int i = 0; i < pastMeetings.size(); i++) {
            lines.add((i + 1) + ". " + pastMeetings.get(i));
        }

        return new CommandResult(String.join("\n", lines));
    }

    /**
     * Returns true if both commands have the same index and meeting.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListMeetingCommand)) {
            return false;
        }
        ListMeetingCommand otherCommand = (ListMeetingCommand) other;
        return index.equals(otherCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
