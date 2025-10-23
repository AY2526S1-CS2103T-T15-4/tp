package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
 * {@code FlagCommand}.
 */
public class FlagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToFlag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FlagCommand flagCommand = new FlagCommand(INDEX_FIRST_PERSON);

        Person flaggedPerson = new PersonBuilder(personToFlag).withFlag(true).build();
        String expectedMessage = String.format(FlagCommand.MESSAGE_FLAG_PERSON_SUCCESS, Messages.format(flaggedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToFlag, flaggedPerson);

        assertCommandSuccess(flagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FlagCommand flagCommand = new FlagCommand(outOfBoundIndex);

        assertCommandFailure(flagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToFlag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FlagCommand flagCommand = new FlagCommand(INDEX_FIRST_PERSON);

        Person flaggedPerson = new PersonBuilder(personToFlag).withFlag(true).build();
        String expectedMessage = String.format(FlagCommand.MESSAGE_FLAG_PERSON_SUCCESS, Messages.format(flaggedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToFlag, flaggedPerson);

        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(flagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FlagCommand flagCommand = new FlagCommand(outOfBoundIndex);
        assertCommandFailure(flagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyFlaggedPerson_throwsCommandException() {
        Person personToFlag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // create a flagged person in the model
        Person flaggedPerson = new PersonBuilder(personToFlag).withFlag(true).build();
        model.setPerson(personToFlag, flaggedPerson);

        FlagCommand flagCommand = new FlagCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(flagCommand, model, FlagCommand.MESSAGE_ALREADY_FLAGGED);
    }

    @Test
    public void equals() {
        FlagCommand flagFirstCommand = new FlagCommand(INDEX_FIRST_PERSON);
        FlagCommand flagSecondCommand = new FlagCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(flagFirstCommand.equals(flagFirstCommand));

        // same values -> returns true
        FlagCommand flagFirstCommandCopy = new FlagCommand(INDEX_FIRST_PERSON);
        assertTrue(flagFirstCommand.equals(flagFirstCommandCopy));

        // different types -> returns false
        assertFalse(flagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(flagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(flagFirstCommand.equals(flagSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        FlagCommand flagCommand = new FlagCommand(targetIndex);
        String expected = FlagCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, flagCommand.toString());
    }
}
