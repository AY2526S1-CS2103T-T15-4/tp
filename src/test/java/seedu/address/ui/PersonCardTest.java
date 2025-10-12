package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void timeFormatting_shouldBeValidForZone() {
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

        // Test Time formatting indirectly via Person
        String formatted = person.getTime().getFormattedTime(ZoneId.of("Asia/Singapore"));
        assertNotNull(formatted);
        assertTrue(formatted.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"),
                "Time string should match dd MMM HH:mm format");
    }

    @Test
    void timeFormatting_shouldBeValidForDefaultZone() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));

        Person person = new Person(
                new Name("Alice"),
                new Phone("12345678"),
                new Email("alice@example.com"),
                new HomeCountry("UnknownCountry"),
                new Company("ACME Corp"),
                tags
        );

        // If the country is unknown, should fallback to OS time
        String formatted = person.getTime().getFormattedTime();
        assertNotNull(formatted);
        assertTrue(formatted.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"),
                "Time string should match dd MMM HH:mm format for OS time");
    }

}
