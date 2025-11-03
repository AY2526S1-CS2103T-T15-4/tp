package seedu.address.logic.parser;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests whether a {@code Person} is flagged.
 * This predicate can be used to filter a list of persons to only those that are flagged.
 */
public class FlaggedPredicate implements Predicate<Person> {
    @Override
    public boolean test(Person person) {
        return person.isFlagged();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof FlaggedPredicate;
    }
}
