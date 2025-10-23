package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.format;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;

/**
 * Deletes a meeting identified by the person's index and the meeting's datetime from the address book.
 */
public class DeleteMeetingCommand extends Command {

    public static final String COMMAND_WORD = "deletem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meeting identified by the person's index and the meeting's datetime.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_MEETING + "MEETING_TIME (format: DD-MM-YYYY HH:MM)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_MEETING + "20-10-2025 14:30";

    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Deleted Meeting: %1$s from Person: %2$s";
    public static final String MESSAGE_MEETING_NOT_FOUND = "No meeting found at the "
            + "specified datetime for this person.";
    private final Index targetIndex;
    private final LocalDateTime meetingTime;

    /**
     * Creates a DeleteMeetingCommand to delete a meeting at the specified index and datetime.
     *
     * @param targetIndex Index of the person in the displayed person list.
     * @param meetingTime Datetime of the meeting to delete.
     */
    public DeleteMeetingCommand(Index targetIndex, LocalDateTime meetingTime) {
        requireNonNull(targetIndex);
        requireNonNull(meetingTime);
        this.targetIndex = targetIndex;
        this.meetingTime = meetingTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        int zeroBased = targetIndex.getZeroBased();
        if (zeroBased >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(zeroBased);
        Meeting meetingToDelete = person.getMeetings().stream()
                .filter(meeting -> meeting.getMeetingTime().equals(meetingTime))
                .findFirst()
                .orElseThrow(() -> new CommandException(MESSAGE_MEETING_NOT_FOUND));

        model.deleteMeeting(person, meetingToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS, meetingToDelete, format(person)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteMeetingCommand)) {
            return false;
        }

        DeleteMeetingCommand otherCommand = (DeleteMeetingCommand) other;
        return targetIndex.equals(otherCommand.targetIndex)
                && meetingTime.equals(otherCommand.meetingTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("meetingTime", meetingTime)
                .toString();
    }
}
