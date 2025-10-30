package seedu.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeCountry;
import seedu.address.model.person.Link;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new HomeCountry("Singapore"),
                new Company("Shopee"),
                getTagSet("friends"), false,
                getMeetingSet("01-11-2025 10:00 Project Kickoff", "02-11-2025 14:30", "02-10-2025 20:30"), null),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new HomeCountry("South Korea"),
                new Company("Meta"),
                getTagSet("colleagues", "friends"), false,
                getMeetingSet("03-11-2025 09:00 Team Sync"), new Link("https://example.com")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new HomeCountry("Sweden"),
                new Company("Google"),
                getTagSet("neighbours"), false,
                getMeetingSet(), null),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new HomeCountry("China"),
                new Company("Amazon"),
                getTagSet("family"), false,
                getMeetingSet("04-11-2025 15:00 Family Call"), new Link("http://david.com/mycompany")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new HomeCountry("Australia"),
                new Company("Apple"),
                getTagSet("classmates"), false,
                getMeetingSet("02-03-2025 04:30"), null),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new HomeCountry("Germany"),
                new Company("Microsoft"),
                getTagSet("colleagues"), false,
                getMeetingSet(), null)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a meeting set containing the list of meeting strings given.
     * Each string should be in the format "DD-MM-YYYY HH:MM [description]".
     * If no description is provided, the meeting will have no description.
     */
    public static Set<Meeting> getMeetingSet(String... strings) {
        return Arrays.stream(strings)
                .map(str -> {
                    String[] parts = str.split(" ", 3); // Split on first two spaces (for time and optional description)
                    String timePart = parts[0] + " " + parts[1]; // Combine date and time
                    String description = parts.length > 2 ? parts[2] : null;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    try {
                        return new Meeting(LocalDateTime.parse(timePart.trim(), formatter), description);
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Invalid meeting time format: " + timePart, e);
                    }
                })
                .collect(Collectors.toCollection(HashSet::new));
    }

}
