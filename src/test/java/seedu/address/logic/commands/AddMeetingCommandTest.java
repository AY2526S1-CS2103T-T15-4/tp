package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelStubs;
import seedu.address.testutil.PersonBuilder;


public class AddMeetingCommandTest {

    private static final Meeting VALID_MEETING = new Meeting(LocalDateTime.of(2025, 10, 22, 14, 0), "Team meeting");

    @Test
    public void execute_validInput_success() {
        Person validPerson = new PersonBuilder().build();
        ModelStubs.ModelStubAcceptingMeetingAdded modelStub = new ModelStubs.ModelStubAcceptingMeetingAdded();
        modelStub.addPerson(validPerson); // Add person to model
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(Index.fromOneBased(1), VALID_MEETING);


        CommandResult commandResult = null;
        try {
            commandResult = addMeetingCommand.execute(modelStub);

            assertEquals(String.format(AddMeetingCommand.MESSAGE_SUCCESS, validPerson.getName(), VALID_MEETING),
                    commandResult.getFeedbackToUser());
            assertEquals(Collections.singletonList(VALID_MEETING), modelStub.meetingsAdded);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        ModelStubs.ModelStubWithMeeting modelStub = new ModelStubs.ModelStubWithMeeting(validPerson, VALID_MEETING);
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(Index.fromOneBased(1), VALID_MEETING);

        assertThrows(CommandException.class, AddMeetingCommand.MESSAGE_DUPLICATE_MEETING, () ->
                addMeetingCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        ModelStubs.ModelStubWithMeeting modelStub = new ModelStubs.ModelStubWithMeeting(validPerson, VALID_MEETING);
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(Index.fromOneBased(2), VALID_MEETING);

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                addMeetingCommand.execute(modelStub));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(Index.fromOneBased(1), VALID_MEETING);
        assertThrows(NullPointerException.class, () -> addMeetingCommand.execute(null));
    }
}
