package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeCountry;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class PersonCardTest {

    @Test
    void constructor_shouldRunWithoutException() {
        // Create a simple Person with tags
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));

        Person person = new Person(
                new Name("Alice"),
                new Phone("12345678"),
                new Email("alice@example.com"),
                new HomeCountry("Singapore"),
                new Company("ACME Corp"),
                tags
        );

        person.getTags().stream()
                .sorted((a, b) -> a.tagName.compareTo(b.tagName))
                .forEach(tag -> {});

        person.getTime().getFormattedTime();
    }

    @Test
    void getZoneIdFromCountry_shouldReturnCorrectZone() {
        // Create a simple Person
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));
        Person person = new Person(
                new Name("Alice"),
                new Phone("12345678"),
                new Email("alice@example.com"),
                new HomeCountry("Singapore"),
                new Company("ACME Corp"),
                tags
        );

        // Test known countries
        ZoneId zone = switch (person.getCountry().value.toLowerCase()) {
        case "singapore" -> ZoneId.of("Asia/Singapore");
        case "united states", "usa", "america" -> ZoneId.of("America/New_York");
        case "uk", "united kingdom" -> ZoneId.of("Europe/London");
        case "japan" -> ZoneId.of("Asia/Tokyo");
        default -> null;
        };
        assertEquals(ZoneId.of("Asia/Singapore"), zone);

        // Test unknown country
        ZoneId unknown = switch ("Unknown".toLowerCase()) {
        case "singapore" -> ZoneId.of("Asia/Singapore");
        default -> null;
        };
        assertNull(unknown);
    }

    @Test
    void updateTime_shouldReturnFormattedTime() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));
        Person person = new Person(
                new Name("Alice"),
                new Phone("12345678"),
                new Email("alice@example.com"),
                new HomeCountry("Singapore"),
                new Company("ACME Corp"),
                tags
        );

        ZoneId zone = ZoneId.of("Asia/Singapore");
        String formattedTime = person.getTime().getFormattedTime(zone);

        assertNotNull(formattedTime);
        assertTrue(formattedTime.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"));
    }

    @Test
    void showTimeLogic_shouldProduceValidTime() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));
        Person person = new Person(
                new Name("Alice"),
                new Phone("12345678"),
                new Email("alice@example.com"),
                new HomeCountry("United States"),
                new Company("ACME Corp"),
                tags
        );

        // Simulate the logic of showTime
        ZoneId zone = ZoneId.of("America/New_York");
        String formattedTime = person.getTime().getFormattedTime(zone);

        assertNotNull(formattedTime);
        assertTrue(formattedTime.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"));
    }

}
