package seedu.address.ui;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

public class PersonCardTest {

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {});
    }

    @Test
    void constructor_shouldRunWithoutException() {
        Person person = SampleDataUtil.getSamplePersons()[0];
        new PersonCard(person, 1);
    }

}
