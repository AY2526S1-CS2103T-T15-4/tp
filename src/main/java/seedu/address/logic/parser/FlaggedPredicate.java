package seedu.address.logic.parser;

import java.util.function.Predicate;
import seedu.address.model.person.Person;

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