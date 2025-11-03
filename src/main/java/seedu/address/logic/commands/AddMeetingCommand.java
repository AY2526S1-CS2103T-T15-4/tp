package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;

/**
 * Adds a {@link Meeting} to a {@link Person} in the address book.
 */
public class AddMeetingCommand extends ConfirmableCommand {
    public static final String COMMAND_WORD = "addm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting time to a person in the address book.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_MEETING + "MEETING_TIME (format: DD-MM-YYYY HH:MM) Description(optional and less than 50 "
            + "characters)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MEETING + "20-10-2025 14:30 Project LinkUp";

    public static final String MESSAGE_SUCCESS = "New meeting added for %1$s: %2$s";
    public static final String MESSAGE_DUPLICATE_MEETING_WARNING =
            """
            Warning: A duplicate meeting already exists in the address book.
            Please confirm if this meeting should still be added.
            Enter 'y' to confirm or enter any other input to cancel AND continue normally.
            """;

    public static final String MESSAGE_PAST_MEETING_WARNING =
            """
            Warning: You are adding a meeting scheduled in the past.
            Please confirm if this meeting should still be added.
            Enter 'y' to confirm or enter any other input to cancel AND continue normally.
            """;

    private final Index index;
    private final Meeting meeting;

    /**
     * Creates an {@code AddMeetingCommand} to add the specified {@code Meeting}
     * to the person at the provided {@code Index}.
     *
     * @param targetIndex Index of the person in the filtered person list.
     * @param meeting Meeting to be added.
     */
    public AddMeetingCommand(Index targetIndex, Meeting meeting) {
        super(false);
        requireNonNull(targetIndex);
        requireNonNull(meeting);
        this.index = targetIndex;
        this.meeting = meeting;
    }

    private AddMeetingCommand(Index targetIndex, Meeting meeting, boolean confirmed) {
        super(confirmed);
        requireNonNull(targetIndex);
        requireNonNull(meeting);
        this.index = targetIndex;
        this.meeting = meeting;
    }

    @Override
    public ConfirmableCommand withConfirmed() {
        return new AddMeetingCommand(this.index, this.meeting, true);
    }

    /**
     * Executes the command to add a meeting to a person in the model.
     *
     * @param model The model containing the address book and filtered person list.
     * @return A {@code CommandResult} with feedback of the operation.
     * @throws CommandException If the index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        assert personToUpdate != null;
        if (model.getAddressBook().getPersonList().stream()
                .anyMatch(person-> person.getMeetings().stream()
                        .anyMatch(currentMeeting -> currentMeeting.equals(meeting))) && !this.isConfirmed()) {
            return new CommandResult(MESSAGE_DUPLICATE_MEETING_WARNING, this.withConfirmed());
        }

        if (meeting.getMeetingTime().isBefore(LocalDateTime.now()) && !this.isConfirmed()) {
            return new CommandResult(MESSAGE_PAST_MEETING_WARNING, this.withConfirmed());
        }

        model.addMeeting(personToUpdate, meeting);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToUpdate.getName(), meeting));
    }

    /**
     * Returns true if both commands have the same index and meeting.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddMeetingCommand)) {
            return false;
        }
        AddMeetingCommand otherCommand = (AddMeetingCommand) other;
        return index.equals(otherCommand.index)
                && meeting.equals(otherCommand.meeting);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .add("meetingTime", meeting)
                .toString();
    }
}
