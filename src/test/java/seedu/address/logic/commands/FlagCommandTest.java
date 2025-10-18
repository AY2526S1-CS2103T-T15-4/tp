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

public class FlagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToFlag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FlagCommand flagCommand = new FlagCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FlagCommand.MESSAGE_FLAG_PERSON_SUCCESS,
                Messages.format(personToFlag));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person flaggedPerson = new Person(
                personToFlag.getName(),
                personToFlag.getPhone(),
                personToFlag.getEmail(),
                personToFlag.getCountry(),
                personToFlag.getCompany(),
                personToFlag.getTags(),
                true // flagged
        );
        expectedModel.setPerson(personToFlag, flaggedPerson);

        assertCommandSuccess(flagCommand, model, expectedMessage, expectedModel);
    }
}
