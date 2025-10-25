package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.DeleteMeetingCommand.MESSAGE_DELETE_MEETING_SUCCESS;
import static seedu.address.logic.commands.DeleteMeetingCommand.MESSAGE_MEETING_NOT_FOUND;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelStubs;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for {@link DeleteMeetingCommand}.
 */
public class DeleteMeetingCommandTest {

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteMeetingCommand(null, LocalDateTime.now()));
        assertThrows(NullPointerException.class, () -> new DeleteMeetingCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validMeeting_success() throws Exception {
        Person personWithMeeting = new PersonBuilder().build();
        LocalDateTime meetingTime = LocalDateTime.of(2025, 10, 20, 14, 30);
        Meeting meeting = new Meeting(meetingTime, "Project discussion");

        // person initially has this meeting
        personWithMeeting = personWithMeeting.withAddedMeeting(meeting);

        ModelStubs.ModelStubAcceptingMeetingDeleted modelStub = new ModelStubs.ModelStubAcceptingMeetingDeleted(
                personWithMeeting, meeting);
        DeleteMeetingCommand command = new DeleteMeetingCommand(Index.fromOneBased(1), meetingTime);

        String expectedMessage = String.format(MESSAGE_DELETE_MEETING_SUCCESS, meeting, personWithMeeting.getName());

        CommandResult result = command.execute(modelStub);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(modelStub.getMeetingsDeleted().contains(meeting));
    }

    @Test
    public void execute_meetingNotFound_throwsCommandException() {
        Person person = new PersonBuilder().build();
        LocalDateTime meetingTime = LocalDateTime.of(2025, 10, 20, 14, 30);

        // person has no meetings
        ModelStubs.ModelStubAcceptingMeetingDeleted modelStub = new ModelStubs
                .ModelStubAcceptingMeetingDeleted(person, null);
        DeleteMeetingCommand command = new DeleteMeetingCommand(Index.fromOneBased(1), meetingTime);

        assertThrows(CommandException.class, MESSAGE_MEETING_NOT_FOUND, () -> command.execute(modelStub));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Person person = new PersonBuilder().build();
        LocalDateTime meetingTime = LocalDateTime.of(2025, 10, 20, 14, 30);
        Meeting meeting = new Meeting(meetingTime, "Project discussion");

        ModelStubs.ModelStubAcceptingMeetingDeleted modelStub =
                new ModelStubs.ModelStubAcceptingMeetingDeleted(person, meeting);
        DeleteMeetingCommand command = new DeleteMeetingCommand(Index.fromOneBased(2), meetingTime);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                command.execute(modelStub));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        DeleteMeetingCommand command = new DeleteMeetingCommand(Index.fromOneBased(1),
                LocalDateTime.of(2025, 10, 20, 14, 30));
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void equals() {
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);
        LocalDateTime firstTime = LocalDateTime.of(2025, 10, 20, 14, 30);
        LocalDateTime secondTime = LocalDateTime.of(2025, 10, 21, 10, 0);

        DeleteMeetingCommand firstCommand = new DeleteMeetingCommand(firstIndex, firstTime);
        DeleteMeetingCommand sameCommand = new DeleteMeetingCommand(firstIndex, firstTime);
        DeleteMeetingCommand diffIndexCommand = new DeleteMeetingCommand(secondIndex, firstTime);
        DeleteMeetingCommand diffTimeCommand = new DeleteMeetingCommand(firstIndex, secondTime);

        // same object -> true
        assertEquals(firstCommand, firstCommand);

        // same values -> true
        assertEquals(firstCommand, sameCommand);

        // different types -> false
        assertNotEquals(1, firstCommand);

        // null -> false
        assertNotEquals(null, firstCommand);

        // different index -> false
        assertNotEquals(firstCommand, diffIndexCommand);

        // different meeting time -> false
        assertNotEquals(firstCommand, diffTimeCommand);
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        LocalDateTime meetingTime = LocalDateTime.of(2025, 10, 20, 14, 30);
        DeleteMeetingCommand command = new DeleteMeetingCommand(index, meetingTime);
        String expected = DeleteMeetingCommand.class.getCanonicalName()
                + "{targetIndex=" + index + ", meetingTime=" + meetingTime + "}";
        assertEquals(expected, command.toString());
    }
}
