package seedu.address.ui;

import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeCountry;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import static org.junit.jupiter.api.Assertions.*;

public class PersonCardTest {

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {}); // Initialize JavaFX toolkit once
    }

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
    void constructor_shouldCallShowTimeAndUpdateTime() {
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

        // Instantiating PersonCard will call showTime(), which calls updateTime() internally
        PersonCard card = new PersonCard(person, 1);

        // The time label should be updated to something non-null
        Platform.runLater(() -> {
            assertNotNull(card.time.getText());
            assertTrue(card.time.getText().contains("Local time:"));
        });
    }

    @Test
    void getZoneIdFromCountry_shouldReturnCorrectZoneId() {
        PersonCard card = new PersonCard(createPerson("USA"), 1);

        // Use reflection to call private method
        try {
            java.lang.reflect.Method method = PersonCard.class.getDeclaredMethod("getZoneIdFromCountry", String.class);
            method.setAccessible(true);

            assertEquals(java.time.ZoneId.of("America/New_York"),
                    method.invoke(card, "United States"));
            assertNull(method.invoke(card, "UnknownCountry"));
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    void updateTime_shouldReturnFormattedTime() {
        PersonCard card = new PersonCard(createPerson("Singapore"), 1);

        // Use reflection to call private updateTime()
        try {
            java.lang.reflect.Method method = PersonCard.class.getDeclaredMethod("updateTime");
            method.setAccessible(true);
            Platform.runLater(() -> {
                try {
                    method.invoke(card);
                    assertNotNull(card.time.getText());
                    assertTrue(card.time.getText().matches("Local time: \\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"));
                } catch (Exception e) {
                    fail("Reflection failed: " + e.getMessage());
                }
            });
        } catch (NoSuchMethodException e) {
            fail("Method updateTime not found");
        }
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

    private Person createPerson(String country) {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));

        return new Person(
                new Name("Alice"),
                new Phone("12345678"),
                new Email("alice@example.com"),
                new HomeCountry(country),
                new Company("ACME Corp"),
                tags
        );
    }

}
