package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FlaggedPredicateTest {

    @Test
    public void test_personIsFlagged_returnsTrue() {
        FlaggedPredicate predicate = new FlaggedPredicate();
        Person flaggedPerson = new PersonBuilder().withFlag(true).build();
        assertTrue(predicate.test(flaggedPerson));
    }

    @Test
    public void test_personIsNotFlagged_returnsFalse() {
        FlaggedPredicate predicate = new FlaggedPredicate();
        Person unflaggedPerson = new PersonBuilder().withFlag(false).build();
        assertFalse(predicate.test(unflaggedPerson));
    }
}
