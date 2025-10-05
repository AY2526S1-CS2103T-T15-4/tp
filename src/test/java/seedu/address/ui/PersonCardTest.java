package seedu.address.ui;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeCountry;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class PersonCardTest {

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {
        });
    }

    @Test
    void constructor_shouldRunWithoutException() {
        // Create a local modifiable set
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

        // Just creating the PersonCard
        new PersonCard(person, 1);

    }

}
