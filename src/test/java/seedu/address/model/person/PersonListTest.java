package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COUNTRY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class PersonListTest {

    private final PersonList personList = new PersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(personList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        personList.add(ALICE);
        assertTrue(personList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        personList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withCountry(VALID_COUNTRY_BOB).withTags(VALID_TAG_HUSBAND)
                .withCompany(VALID_COMPANY_BOB).build();
        assertTrue(personList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.add(null));
    }

    @Test
    public void add_duplicatePhone_allowsAdd() {
        Person p1 = new PersonBuilder().withPhone("12345678").withEmail("a@test.com").build();
        Person p2 = new PersonBuilder().withPhone("12345678").withEmail("b@test.com").build();

        personList.add(p1);
        personList.add(p2);
        assertEquals(2, personList.asUnmodifiableObservableList().size());
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> personList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        personList.add(ALICE);
        personList.setPerson(ALICE, ALICE);
        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(ALICE);
        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        personList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withCountry(VALID_COUNTRY_BOB).withCompany(VALID_COMPANY_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        personList.setPerson(ALICE, editedAlice);
        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(editedAlice);
        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        personList.add(ALICE);
        personList.setPerson(ALICE, BOB);
        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(BOB);
        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.removeReference(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> personList.removeReference(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        personList.add(ALICE);
        personList.removeReference(ALICE);
        PersonList expectedPersonList = new PersonList();
        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.setPersons((PersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        personList.add(ALICE);
        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(BOB);
        personList.setPersons(expectedPersonList);
        assertEquals(expectedPersonList, personList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> personList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        personList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        this.personList.setPersons(personList);
        PersonList expectedPersonList = new PersonList();
        expectedPersonList.add(BOB);
        assertEquals(expectedPersonList, this.personList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> personList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(personList.asUnmodifiableObservableList().toString(), personList.toString());
    }

    @Test
    public void equals() {
        PersonList list1 = new PersonList();
        list1.add(ALICE);
        PersonList list2 = new PersonList();
        list2.add(ALICE);

        // same object -> returns true
        assertTrue(list1.equals(list1));

        // same contents -> returns true
        assertTrue(list1.equals(list2));

        // different contents -> returns false
        list2.add(BOB);
        assertFalse(list1.equals(list2));

        // null -> returns false
        assertFalse(list1.equals(null));

        // different type -> returns false
        assertFalse(list1.equals("string"));

        // hashCode consistent with equals
        PersonList list3 = new PersonList();
        list3.add(ALICE);
        assertEquals(list1.hashCode(), list3.hashCode());
    }

    @Test
    public void iterator_returnsExpectedElements() {
        personList.add(ALICE);
        personList.add(BOB);

        Iterator<Person> iterator = personList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(ALICE, iterator.next());
        assertEquals(BOB, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void setPersonByReference_sameReference_replacesCorrectInstance() {
        PersonList personList = new PersonList();
        Person alice = new PersonBuilder().withName("Alice").build();

        personList.add(alice);
        // Replace using the same object reference
        personList.setPerson(alice, alice);

        // Still contains the same instance
        assertTrue(personList.asUnmodifiableObservableList().contains(alice));
    }

    @Test
    public void setPersonByReference_nonExistingPerson_throwsException() {
        PersonList personList = new PersonList();
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();

        assertThrows(PersonNotFoundException.class, () -> personList.setPerson(alice, bob));
    }

    @Test
    public void removeReferenceByReference_sameReference_removesCorrectInstance() {
        PersonList personList = new PersonList();
        Person alice = new PersonBuilder().withName("Alice").build();

        personList.add(alice);
        // Remove using the same object reference
        personList.removeReference(alice);

        assertFalse(personList.asUnmodifiableObservableList().contains(alice));
    }

    @Test
    public void removeReferenceByReference_nonExistingPerson_throwsException() {
        PersonList personList = new PersonList();
        Person alice = new PersonBuilder().withName("Alice").build();

        assertThrows(PersonNotFoundException.class, () -> personList.removeReference(alice));
    }
}
