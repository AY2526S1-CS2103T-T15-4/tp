package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelStubs;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_duplicateConfirmed_addsPerson() throws Exception {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);

        ModelStubs.ModelStubAcceptingPersonAdded modelStub = new ModelStubs.ModelStubAcceptingPersonAdded();
        modelStub.addPerson(validPerson);

        CommandResult firstResult = addCommand.execute(modelStub);
        assertEquals(AddCommand.MESSAGE_DUPLICATE_PERSON_WARNING, firstResult.getFeedbackToUser());

        addCommand.confirm();

        CommandResult confirmedResult = addCommand.execute(modelStub);
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                confirmedResult.getFeedbackToUser());
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubs.ModelStubAcceptingPersonAdded modelStub = new ModelStubs.ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.getPersonsAdded());
    }

    @Test
    public void execute_duplicatePerson_givesWarning() throws Exception {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStubs.ModelStub modelStub = new ModelStubs.ModelStubWithPerson(validPerson);

        CommandResult result = addCommand.execute(modelStub);
        assertEquals(AddCommand.MESSAGE_DUPLICATE_PERSON_WARNING, result.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }
}
