package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.testutil.ModelStubs;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for ConfirmAddCommand.
 */
public class ConfirmAddCommandTest {

    @Test
    void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ConfirmAddCommand(null));
    }

    @Test
    void execute_validPerson_addsPersonAndReturnsExpectedMessage() throws Exception {
        ModelStubs.ModelStubAcceptingPersonAdded modelStub = new ModelStubs.ModelStubAcceptingPersonAdded();

        Person alice = new PersonBuilder()
                .withName("Alice Pauline")
                .withPhone("85355255")
                .withEmail("alice@example.com")
                .withCountry("Singapore")
                .withCompany("Shopee")
                .build();

        ConfirmAddCommand command = new ConfirmAddCommand(alice);
        CommandResult result = command.execute(modelStub);

        String expected = "New person added: " + Messages.format(alice);
        assertEquals(expected, result.getFeedbackToUser());
        assertTrue(modelStub.getPersonsAdded().contains(alice),
                "ModelStubAcceptingPersonAdded should contain the added person");
    }

    @Test
    void execute_nullModel_throwsNullPointerException() {
        Person bob = new PersonBuilder().withName("Bob").withPhone("22222222").withEmail("bob@example.com").build();
        ConfirmAddCommand cmd = new ConfirmAddCommand(bob);
        assertThrows(NullPointerException.class, () -> cmd.execute(null));
    }

    @Test
    void execute_pendingPersonNull_throwsCommandException() {
        ConfirmAddCommand cmd = new ConfirmAddCommand();
        ModelStubs.ModelStubAcceptingPersonAdded modelStub = new ModelStubs.ModelStubAcceptingPersonAdded();

        CommandException thrown = assertThrows(CommandException.class, () -> cmd.execute(modelStub));
        assertEquals("No pending person available.\nPlease use the 'add' command first.", thrown.getMessage());
    }

    @Test
    void equals_sameAndDifferentPersons_behaviour() {
        Person p1 = new PersonBuilder().withName("D").withPhone("4444").withEmail("d@ex.com").build();
        Person p1Copy = new PersonBuilder().withName("D").withPhone("4444").withEmail("d@ex.com").build();
        Person p2 = new PersonBuilder().withName("E").withPhone("5555").withEmail("e@ex.com").build();

        ConfirmAddCommand c1 = new ConfirmAddCommand(p1);
        ConfirmAddCommand c1Copy = new ConfirmAddCommand(p1Copy);
        ConfirmAddCommand c2 = new ConfirmAddCommand(p2);

        assertEquals(c1, c1);
        assertEquals(c1, c1Copy);
        assertNotEquals(c1, c2);
        assertNotEquals(c1, null);
        assertNotEquals(c1, "string");
    }

    @Test
    void toString_containsToAddAndPersonName() {
        Person p = new PersonBuilder().withName("Frank").withPhone("9999").withEmail("frank@ex.com").build();
        ConfirmAddCommand cmd = new ConfirmAddCommand(p);

        String s = cmd.toString();
        assertNotNull(s);
        assertTrue(s.contains("toAdd"));
        assertTrue(s.contains("Frank"));
    }
}
