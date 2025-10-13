package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.SingleFieldContainsKeywordsPredicate.TargetField;
import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        SingleFieldContainsKeywordsPredicate firstPredicate = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, firstPredicateKeywordList);
        SingleFieldContainsKeywordsPredicate secondPredicate = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SingleFieldContainsKeywordsPredicate firstPredicateCopy = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        SingleFieldContainsKeywordsPredicate predicate = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SingleFieldContainsKeywordsPredicate predicate = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email, company and country, but does not match name
        predicate = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, Arrays.asList("12345", "alice@email.com", "Singapore", "Shopee"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withCountry("Singapore").withCompany("Shopee").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        SingleFieldContainsKeywordsPredicate predicate = new SingleFieldContainsKeywordsPredicate(
                TargetField.NAME, keywords);

        String expected = SingleFieldContainsKeywordsPredicate.class.getCanonicalName()
                + "{field=NAME, keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
