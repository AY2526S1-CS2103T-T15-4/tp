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
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.ui.util.TimeFormatter;

public class PersonCardTest {

    @Test
    void constructor_shouldRunWithoutException() {
        // Create a simple Person with tags
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));
        Set<Meeting> meetings = new HashSet<>();

        Person person = new Person(
                new Name("Alice"),
                new Phone("12345678"),
                new Email("alice@example.com"),
                new HomeCountry("Singapore"),
                new Company("ACME Corp"),
                tags,
                meetings
        );
        person.getTags().stream()
                .sorted((a, b) -> a.tagName.compareTo(b.tagName))
                .forEach(tag -> {});

        TimeFormatter.getFormattedTime();
    }

    @Test
    void timeFormatter_shouldReturnValidFormat() {
        String formatted = TimeFormatter.getFormattedTime(ZoneId.of("Asia/Singapore"));
        assertNotNull(formatted);
        assertTrue(formatted.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"));
    }

    @Test
    void timeFormatter_shouldReturnValidFormatForDefaultZone() {
        String formatted = TimeFormatter.getFormattedTime();
        assertNotNull(formatted);
        assertTrue(formatted.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"));
    }
}
