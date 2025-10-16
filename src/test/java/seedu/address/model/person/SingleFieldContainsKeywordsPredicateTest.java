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

public class SingleFieldContainsKeywordsPredicateTest {

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
    public void test_phoneFieldIsMatching_returnsTrue() {
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.PHONE, Arrays.asList("345"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_phoneFieldIsNotMatching_returnsFalse() {
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.PHONE, Arrays.asList("999"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_emailFieldIsMatching_returnsTrue() {
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.EMAIL,
                        Arrays.asList("ALICE@EXAMPLE.COM"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_emailFieldIsNotMatching_returnsFalse() {
        // containsWordIgnoreCase requires whole-word match; substring "alice" should NOT match the email
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.EMAIL, Arrays.asList("alice"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@example.com").build()));
    }

    @Test
    public void test_countryFieldIsMatching_returnsTrue() {
        // word match on "States" should match "United States"
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.COUNTRY, Arrays.asList("States"));
        assertTrue(predicate.test(new PersonBuilder().withCountry("United States").build()));
    }

    @Test
    public void test_countryFieldIsNotMatching_returnsFalse() {
        // substring "Sing" should NOT match "Singapore" (word-only matching)
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.COUNTRY, Arrays.asList("Sing"));
        assertFalse(predicate.test(new PersonBuilder().withCountry("Singapore").build()));
    }

    @Test
    public void test_companyFieldIsMatching_returnsTrue() {
        // word match, case-insensitive
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.COMPANY, Arrays.asList("hope"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("New Hope Ltd").build()));
    }

    @Test
    public void test_companyFieldIsNotMatching_returnsFalse() {
        // substring "pen" should NOT match "OpenAI" (single word)
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.COMPANY, Arrays.asList("pen"));
        assertFalse(predicate.test(new PersonBuilder().withCompany("OpenAI").build()));
    }

    @Test
    public void test_tagFieldIsMatchingAnyTag_returnsTrue() {
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.TAG, Arrays.asList("FRIENDS"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));
    }

    @Test
    public void test_tagFieldIsNotMatching_returnsFalse() {
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.TAG, Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends", "colleagues").build()));
    }

    @Test
    public void test_tagFieldMatchesAllTag_returnsTrue() {
        SingleFieldContainsKeywordsPredicate predicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.TAG, Arrays.asList("family", "volunteer"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "volunteer").build()));
    }

    @Test
    public void equals_differentField_returnsFalse() {
        List<String> keywords = Collections.singletonList("Alice");
        SingleFieldContainsKeywordsPredicate namePredicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.NAME, keywords);
        SingleFieldContainsKeywordsPredicate emailPredicate =
                new SingleFieldContainsKeywordsPredicate(TargetField.EMAIL, keywords);
        assertFalse(namePredicate.equals(emailPredicate));
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
