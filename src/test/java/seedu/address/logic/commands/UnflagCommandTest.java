package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnflagCommand}.
 */
public class UnflagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnflag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person flaggedPerson = new PersonBuilder(personToUnflag).withFlag(true).build();
        model.setPerson(personToUnflag, flaggedPerson); // Ensure the person is flagged first

        UnflagCommand unflagCommand = new UnflagCommand(INDEX_FIRST_PERSON);
        Person unflaggedPerson = new PersonBuilder(flaggedPerson).withFlag(false).build();

        String expectedMessage = String.format(UnflagCommand.MESSAGE_UNFLAG_PERSON_SUCCESS,
                Messages.format(unflaggedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(flaggedPerson, unflaggedPerson);

        assertCommandSuccess(unflagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnflagCommand unflagCommand = new UnflagCommand(outOfBoundIndex);

        assertCommandFailure(unflagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToUnflag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person flaggedPerson = new PersonBuilder(personToUnflag).withFlag(true).build();
        model.setPerson(personToUnflag, flaggedPerson);

        UnflagCommand unflagCommand = new UnflagCommand(INDEX_FIRST_PERSON);
        Person unflaggedPerson = new PersonBuilder(flaggedPerson).withFlag(false).build();

        String expectedMessage = String.format(UnflagCommand.MESSAGE_UNFLAG_PERSON_SUCCESS,
                Messages.format(unflaggedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(flaggedPerson, unflaggedPerson);

        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(unflagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UnflagCommand unflagCommand = new UnflagCommand(outOfBoundIndex);

        assertCommandFailure(unflagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyUnflaggedPerson_throwsCommandException() {
        Person personToUnflag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // Ensure the person is unflagged in the model
        Person unflaggedPerson = new PersonBuilder(personToUnflag).withFlag(false).build();
        model.setPerson(personToUnflag, unflaggedPerson);

        UnflagCommand unflagCommand = new UnflagCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(unflagCommand, model, UnflagCommand.MESSAGE_ALREADY_UNFLAGGED);
    }

    @Test
    public void equals() {
        UnflagCommand unflagFirstCommand = new UnflagCommand(INDEX_FIRST_PERSON);
        UnflagCommand unflagSecondCommand = new UnflagCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unflagFirstCommand.equals(unflagFirstCommand));

        // same values -> returns true
        UnflagCommand unflagFirstCommandCopy = new UnflagCommand(INDEX_FIRST_PERSON);
        assertTrue(unflagFirstCommand.equals(unflagFirstCommandCopy));

        // different types -> returns false
        assertFalse(unflagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unflagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unflagFirstCommand.equals(unflagSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnflagCommand unflagCommand = new UnflagCommand(targetIndex);
        String expected = UnflagCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertTrue(unflagCommand.toString().equals(expected));
    }
}
