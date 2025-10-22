package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Link;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class LinkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLink_success() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String link = "https://example.com";
        Person editedPerson = new PersonBuilder(personToEdit).withLink(link).build();

        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_PERSON, new Link(link));

        String expectedMessage = String.format(LinkCommand.MESSAGE_ADD_LINK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_updateLink_success() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String oldLink = "https://old.com";
        personToEdit = new PersonBuilder(personToEdit).withLink(oldLink).build();
        model.setPerson(model.getFilteredPersonList().get(0), personToEdit);

        String newLink = "https://new.com";
        Person editedPerson = new PersonBuilder(personToEdit).withLink(newLink).build();

        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_PERSON, new Link(newLink));

        String expectedMessage = String.format(LinkCommand.MESSAGE_UPDATE_LINK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeLink_success() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String link = "https://example.com";
        personToEdit = new PersonBuilder(personToEdit).withLink(link).build();
        model.setPerson(model.getFilteredPersonList().get(0), personToEdit);

        Person editedPerson = new PersonBuilder(personToEdit).withLink(null).build();
        System.out.println(model.getFilteredPersonList().get(0).getLink());

        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_PERSON, null);

        String expectedMessage = String.format(LinkCommand.MESSAGE_REMOVE_LINK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noChange_failure() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Link link = personToEdit.getLink();
        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_PERSON, link);

        String expectedMessage = String.format(LinkCommand.MESSAGE_NO_CHANGE, Messages.format(personToEdit));

        assertCommandFailure(linkCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Link link = new Link("https://example.com");
        LinkCommand linkCommand = new LinkCommand(outOfBoundIndex, link);

        assertCommandFailure(linkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Link linkA = new Link("https://a.com");
        Link linkB = new Link("https://b.com");
        LinkCommand commandA = new LinkCommand(INDEX_FIRST_PERSON, linkA);
        LinkCommand commandB = new LinkCommand(INDEX_FIRST_PERSON, linkB);

        // same object -> true
        assertTrue(commandA.equals(commandA));

        // same values -> true
        LinkCommand commandACopy = new LinkCommand(INDEX_FIRST_PERSON, linkA);
        assertTrue(commandA.equals(commandACopy));

        // different types -> false
        assertFalse(commandA.equals("string"));

        // null -> false
        assertFalse(commandA.equals(null));

        // different link -> false
        assertFalse(commandA.equals(commandB));

        // different index -> false
        LinkCommand commandDifferentIndex = new LinkCommand(INDEX_SECOND_PERSON, linkA);
        assertFalse(commandA.equals(commandDifferentIndex));
    }

    @Test
    public void toStringMethod() {
        Link link = new Link("https://example.com");
        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_PERSON, link);
        String expected = LinkCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_PERSON + ", link=" + link + "}";
        assertEquals(expected, linkCommand.toString());
    }
}
