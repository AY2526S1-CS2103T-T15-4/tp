package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COUNTRY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void withAddedMeeting_addsMeetingAndReturnsNewPerson() {
        // Arrange
        Meeting meeting = new Meeting(LocalDateTime.of(2025, 10, 20, 14, 30));
        Person originalPerson = new PersonBuilder(ALICE).build();

        Person updatedPerson = originalPerson.withAddedMeeting(meeting);

        assertFalse(originalPerson.getMeetings().contains(meeting));

        assertTrue(updatedPerson.getMeetings().contains(meeting));

        assertNotEquals(originalPerson, updatedPerson);

        assertEquals(originalPerson.getName(), updatedPerson.getName());
        assertEquals(originalPerson.getEmail(), updatedPerson.getEmail());
        assertEquals(originalPerson.getPhone(), updatedPerson.getPhone());
        assertEquals(originalPerson.getTags(), updatedPerson.getTags());
    }

    @Test
    public void withAddedMeeting_duplicateMeeting_returnsSameMeetingsSet() {
        // Arrange
        Meeting meeting = new Meeting(LocalDateTime.of(2025, 10, 20, 14, 30));
        Person originalPerson = new PersonBuilder(ALICE)
                .build().withAddedMeeting(meeting);

        // Act
        Person updatedPerson = originalPerson.withAddedMeeting(meeting);

        // Assert
        // Should not add duplicates
        assertEquals(1, updatedPerson.getMeetings().size());
        assertTrue(updatedPerson.getMeetings().contains(meeting));
    }

    @Test
    public void withDeletedMeeting_removesMeetingAndReturnsNewPerson() {
        // Arrange
        Meeting meeting1 = new Meeting(LocalDateTime.of(2025, 10, 20, 14, 30));
        Meeting meeting2 = new Meeting(LocalDateTime.of(2025, 11, 1, 10, 0));
        Person originalPerson = new PersonBuilder(ALICE)
                .build().withAddedMeeting(meeting1).withAddedMeeting(meeting2);

        // Act
        Person updatedPerson = originalPerson.withDeletedMeeting(meeting1);

        // Assert
        // 1. The deleted meeting should no longer be present
        assertFalse(updatedPerson.getMeetings().contains(meeting1));

        // 2. Other meetings should remain
        assertTrue(updatedPerson.getMeetings().contains(meeting2));

        // 3. The original person should remain unchanged
        assertTrue(originalPerson.getMeetings().contains(meeting1));

        // 4. Other fields remain the same
        assertEquals(originalPerson.getName(), updatedPerson.getName());
        assertEquals(originalPerson.getEmail(), updatedPerson.getEmail());
    }


    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withCountry(VALID_COUNTRY_BOB).withCompany(VALID_COMPANY_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different country -> returns false
        editedAlice = new PersonBuilder(ALICE).withCountry(VALID_COUNTRY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different company -> returns false
        editedAlice = new PersonBuilder(ALICE).withCompany(VALID_COMPANY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different flagged status -> returns false
        editedAlice = new PersonBuilder(ALICE).withFlag(true).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", country=" + ALICE.getCountry() + ", company=" + ALICE.getCompany()
                + ", tags=" + ALICE.getTags() + ", isFlagged=" + ALICE.isFlagged() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hashCode_shouldBeConsistentWithFields() {
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());
    }

}
