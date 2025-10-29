package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelStubs;
import seedu.address.testutil.PersonBuilder;

public class ListMeetingCommandTest {

    private static final Meeting PAST_MEETING = new Meeting(
            LocalDateTime.now().minusDays(3), "Past team meeting");
    private static final Meeting FUTURE_MEETING = new Meeting(
            LocalDateTime.now().plusDays(2), "Future client meeting");

    @Test
    void execute_validIndexWithPastMeetings_success() throws Exception {
        Person validPerson = new PersonBuilder().build();

        ModelStubs.ModelStubAcceptingMeetingAdded modelStub = new ModelStubs.ModelStubAcceptingMeetingAdded();
        modelStub.addPerson(validPerson);

        modelStub.addMeeting(validPerson, PAST_MEETING);
        modelStub.addMeeting(validPerson, FUTURE_MEETING);

        ListMeetingCommand command = new ListMeetingCommand(Index.fromOneBased(1));
        CommandResult result = command.execute(modelStub);

        String feedback = result.getFeedbackToUser();

        assertTrue(feedback.contains("Past meetings"));
        assertTrue(feedback.contains("Past team meeting"));
        assertFalse(feedback.contains("Future client meeting"));
    }

    @Test
    void execute_noPastMeetings_returnsNoPastMeetingsMessage() throws Exception {
        Person validPerson = new PersonBuilder().build();

        ModelStubs.ModelStubAcceptingMeetingAdded modelStub = new ModelStubs.ModelStubAcceptingMeetingAdded();
        modelStub.addPerson(validPerson);

        modelStub.addMeeting(validPerson, FUTURE_MEETING);

        ListMeetingCommand command = new ListMeetingCommand(Index.fromOneBased(1));
        CommandResult result = command.execute(modelStub);

        assertEquals(ListMeetingCommand.MESSAGE_NO_PAST_MEETINGS, result.getFeedbackToUser());
    }

    @Test
    void execute_invalidIndex_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();

        ModelStubs.ModelStubAcceptingMeetingAdded modelStub = new ModelStubs.ModelStubAcceptingMeetingAdded();
        modelStub.addPerson(validPerson);

        ListMeetingCommand command = new ListMeetingCommand(Index.fromOneBased(2));

        CommandException e = assertThrows(CommandException.class, () -> command.execute(modelStub));
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, e.getMessage());
    }

    @Test
    void execute_pastMeetingsSortedDescending_success() throws Exception {
        Meeting oldMeeting = new Meeting(LocalDateTime.now().minusDays(5), "Old meeting");
        Meeting recentMeeting = new Meeting(LocalDateTime.now().minusDays(1), "Recent meeting");

        Person validPerson = new PersonBuilder().build();

        ModelStubs.ModelStubAcceptingMeetingAdded modelStub = new ModelStubs.ModelStubAcceptingMeetingAdded();
        modelStub.addPerson(validPerson);

        modelStub.addMeeting(validPerson, oldMeeting);
        modelStub.addMeeting(validPerson, recentMeeting);

        ListMeetingCommand command = new ListMeetingCommand(Index.fromOneBased(1));
        CommandResult result = command.execute(modelStub);

        String feedback = result.getFeedbackToUser();
        int indexRecent = feedback.indexOf("Recent meeting");
        int indexOld = feedback.indexOf("Old meeting");

        assertTrue(indexRecent < indexOld, "Most recent meeting should appear first");
    }

}
