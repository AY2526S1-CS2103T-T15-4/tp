package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
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
}
