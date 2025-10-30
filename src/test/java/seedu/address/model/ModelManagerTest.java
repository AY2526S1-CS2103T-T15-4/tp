package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.MultiFieldContainsKeywordsPredicate;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;


public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void addMeeting_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addMeeting(null, new Meeting(LocalDateTime.now())));
        assertThrows(NullPointerException.class, () -> modelManager.addMeeting(ALICE, null));
    }

    @Test
    public void addMeeting_validMeeting_meetingAddedToPerson() {
        // Prepare test data
        Person alice = new PersonBuilder(ALICE).build();
        modelManager.addPerson(alice);
        Meeting meeting = new Meeting(LocalDateTime.of(2025, 10, 22, 10, 0), "Project Discussion");

        // Add meeting
        modelManager.addMeeting(alice, meeting);

        // Fetch updated Alice
        Person updatedAlice = modelManager.getFilteredPersonList().get(0);

        // Verify meeting was added
        assertTrue(updatedAlice.getMeetings().contains(meeting));
    }

    @Test
    public void deleteMeeting_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteMeeting(null,
                new Meeting(LocalDateTime.now())));
        assertThrows(NullPointerException.class, () -> modelManager.deleteMeeting(ALICE, null));
    }

    @Test
    public void deleteMeeting_validMeeting_meetingRemovedFromPerson() {
        // Prepare test data
        Meeting meeting = new Meeting(LocalDateTime.of(2025, 10, 22, 10, 0), "Project Discussion");
        Person aliceWithMeeting = new PersonBuilder(ALICE).build().withAddedMeeting(meeting);
        modelManager.addPerson(aliceWithMeeting);

        // Delete meeting
        modelManager.deleteMeeting(aliceWithMeeting, meeting);

        // Fetch updated Alice
        Person updatedAlice = modelManager.getFilteredPersonList().get(0);

        // Verify meeting was removed
        assertFalse(updatedAlice.getMeetings().contains(meeting));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        for (String keyword : keywords) {
            argMultimap.put(PREFIX_NAME, keyword);
        }

        modelManager.updateFilteredPersonList(new MultiFieldContainsKeywordsPredicate(argMultimap));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
