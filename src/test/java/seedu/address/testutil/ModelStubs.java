package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;

/**
 * Collection of reusable Model stubs for unit tests.
 */
public class ModelStubs {

    private ModelStubs() {} // utility class

    /**
     * Fail-fast base stub which throws AssertionError for all methods by default.
     * Subclasses should override only the methods they expect to be used.
     */
    public abstract static class ModelStub implements Model {
        protected AssertionError fail() {
            return new AssertionError("This method should not be called");
        }

        /**
         * Replaces user prefs data with the data in {@code userPrefs}.
         */
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw fail();
        }

        /**
         * Returns the user prefs.
         */
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw fail();
        }

        /**
         * Returns the user prefs' GUI settings.
         */
        @Override
        public GuiSettings getGuiSettings() {
            throw fail();
        }

        /**
         * Sets the user prefs' GUI settings.
         */
        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw fail();
        }

        /**
         * Returns the user prefs' address book file path.
         */
        @Override
        public Path getAddressBookFilePath() {
            throw fail();
        }

        /**
         * Sets the user prefs' address book file path.
         */
        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw fail();
        }

        /**
         * Replaces address book data with the data in {@code addressBook}.
         */
        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw fail();
        }

        /** Returns the AddressBook */
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw fail();
        }

        /**
         * Returns true if a person with the same identity as {@code person} exists in the address book.
         */
        @Override
        public boolean hasPerson(Person person) {
            throw fail();
        }

        /**
         * Deletes the given person.
         * The person must exist in the address book.
         */
        @Override
        public void deletePerson(Person target) {
            throw fail();
        }

        /**
         * Adds the given person.
         * {@code person} must not already exist in the address book.
         */
        @Override
        public void addPerson(Person person) {
            throw fail();
        }

        /**
         * Replaces the given person {@code target} with {@code editedPerson}.
         * {@code target} must exist in the address book.
         * The person identity of {@code editedPerson} must not be the same as another
         * existing person in the address book.
         */
        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw fail();
        }

        /** Returns an unmodifiable view of the filtered person list */
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw fail();
        }

        /**
         * Updates the filter of the filtered person list to filter by the given {@code predicate}.
         * @throws NullPointerException if {@code predicate} is null.
         */
        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw fail();
        }

        @Override
        public void addMeeting(Person target, Meeting meeting) {
            throw fail();
        }

        @Override
        public void deleteMeeting(Person target, Meeting meeting) {
            throw fail();
        }
    }

    /**
     * A Model stub that always accepts added persons and records them for assertions.
     */
    public static class ModelStubAcceptingPersonAdded extends ModelStub {
        private final List<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        public List<Person> getPersonsAdded() {
            return new ArrayList<>(personsAdded);
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    public static class ModelStubWithPerson extends ModelStub {
        private final Person person;

        /**
         * A Model stub that contains a single person.
         */
        public ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A {@code ModelStub} that contains a single {@code Person} with one or more {@code Meeting}s.
     * This stub supports adding new meetings to the person and retrieving the updated state.
     */
    public static class ModelStubWithMeeting extends ModelStub {
        private final List<Person> persons = new ArrayList<>();
        private final List<Meeting> meetings = new ArrayList<>();

        /**
         * Constructs a {@code ModelStubWithMeeting} containing a person with a pre-added meeting.
         *
         * @param person  The person to associate with the meeting.
         * @param meeting The meeting to add to the person.
         */
        public ModelStubWithMeeting(Person person, Meeting meeting) {
            // Create a new Person with the meeting added
            persons.add(person.withAddedMeeting(meeting));
            meetings.add(meeting);
        }

        /**
         * Returns the list of persons stored in this stub.
         *
         * @return An observable list containing the persons.
         */
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(persons);
        }

        /**
         * Returns an {@code AddressBook} containing all persons stored in this stub.
         *
         * @return A read-only view of the address book.
         */
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            AddressBook addressBook = new AddressBook();
            addressBook.addPerson(persons.get(0));
            return addressBook;
        }

        /**
         * Adds a {@code Meeting} to the specified {@code Person}.
         * Updates the internal list of persons with the modified person.
         *
         * @param target  The person to add the meeting to.
         * @param meeting The meeting to add.
         */
        @Override
        public void addMeeting(Person target, Meeting meeting) {
            // Update the person's meetings by replacing with a new Person instance
            for (int i = 0; i < persons.size(); i++) {
                if (persons.get(i).isSamePerson(target)) {
                    persons.set(i, persons.get(i).withAddedMeeting(meeting));
                    break;
                }
            }
            meetings.add(meeting);
        }
    }

    /**
     * A {@code ModelStub} that accepts meeting additions and keeps track of added meetings.
     * Useful for verifying that meetings are correctly created and associated with persons in tests.
     */
    public static class ModelStubAcceptingMeetingAdded extends ModelStubs.ModelStub {
        public final List<Meeting> meetingsAdded = new ArrayList<>();
        public final List<Person> persons = new ArrayList<>();

        /**
         * Adds a {@code Person} to this model stub.
         *
         * @param person The person to add.
         */
        public void addPerson(Person person) {
            persons.add(person);
        }

        /**
         * Returns the list of persons stored in this stub.
         *
         * @return An observable list containing the persons.
         */
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(persons);
        }

        /**
         * Returns an {@code AddressBook} containing all persons stored in this stub.
         *
         * @return A read-only view of the address book.
         */
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            AddressBook addressBook = new AddressBook();
            persons.forEach(addressBook::addPerson);
            return addressBook;
        }

        /**
         * Adds a {@code Meeting} to the specified {@code Person} and records it in {@code meetingsAdded}.
         *
         * @param target  The person to add the meeting to.
         * @param meeting The meeting to add.
         */
        @Override
        public void addMeeting(Person target, Meeting meeting) {
            for (int i = 0; i < persons.size(); i++) {
                if (persons.get(i).isSamePerson(target)) {
                    persons.set(i, persons.get(i).withAddedMeeting(meeting));
                    break;
                }
            }
            meetingsAdded.add(meeting);
        }
    }

    /**
     * A Model stub that accepts meeting deletions and records them for assertions.
     */
    public static class ModelStubAcceptingMeetingDeleted extends ModelStubs.ModelStub {
        public final List<Person> persons = new ArrayList<>();
        public final List<Meeting> meetingsDeleted = new ArrayList<>();

        public ModelStubAcceptingMeetingDeleted(Person person, Meeting meeting) {
            if (meeting != null) {
                persons.add(person.withAddedMeeting(meeting));
            } else {
                persons.add(person);
            }
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(persons);
        }

        @Override
        public void deleteMeeting(Person target, Meeting meeting) {
            meetingsDeleted.add(meeting);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            AddressBook addressBook = new AddressBook();
            persons.forEach(addressBook::addPerson);
            return addressBook;
        }

        public List<Meeting> getMeetingsDeleted() {
            return meetingsDeleted;
        }
    }
}

