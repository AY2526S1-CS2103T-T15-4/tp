package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.testutil.PersonBuilder;


public class MultiFieldContainsKeywordsPredicateTest {

    // phone
    @Test
    public void testPhone_validKeyword_matchesTrue() {
        Person person = new PersonBuilder().withPhone("91234567").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_PHONE, "1234"); // substring
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertTrue(p.test(person));
    }

    @Test
    public void testPhone_invalidKeyword_matchesFalse() {
        Person person = new PersonBuilder().withPhone("91234567").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_PHONE, "888"); // not a substring
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertFalse(p.test(person));
    }

    // email
    @Test
    public void testEmail_caseInsensitiveKeyword_matchesTrue() {
        Person person = new PersonBuilder().withEmail("Alice.PAULINE@example.com").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_EMAIL, "pauline"); // lower vs mixed case in value
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertTrue(p.test(person));
    }

    @Test
    public void testEmail_invalidKeyword_matchesFalse() {
        Person person = new PersonBuilder().withEmail("alice@example.com").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_EMAIL, "@yahoo"); // not contained
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertFalse(p.test(person));
    }

    // country
    @Test
    public void testCountry_validKeyword_matchesTrue() {
        Person person = new PersonBuilder().withCountry("Singapore").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_COUNTRY, "pore"); // substring
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertTrue(p.test(person));
    }

    @Test
    public void testCountry_invalidKeyword_matchesFalse() {
        Person person = new PersonBuilder().withCountry("Singapore").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_COUNTRY, "usa"); // not contained
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertFalse(p.test(person));
    }

    // company
    @Test
    public void testCompany_validKeyword_matchesTrue() {
        Person person = new PersonBuilder().withCompany("OpenAI Singapore").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_COMPANY, "open"); // case-insensitive substring
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertTrue(p.test(person));
    }

    @Test
    public void testCompany_invalidKeyword_matchesFalse() {
        Person person = new PersonBuilder().withCompany("OpenAI").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_COMPANY, "Google"); // not contained
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertFalse(p.test(person));
    }

    // tags
    @Test
    public void testTags_validKeyword_matchesTrue() {
        Person person = new PersonBuilder().withTags("Friends", "owesMoney").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_TAG, "friend"); // matches "Friends"
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertTrue(p.test(person));
    }

    @Test
    public void testTags_invalidKeyword_matchesFalse() {
        Person person = new PersonBuilder().withTags("Friends", "owesMoney").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_TAG, "colleague"); // not contained in any tag
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertFalse(p.test(person));
    }

    // multipleFields
    @Test
    public void testMultipleFields_allValidKeywords_matchesTrue() {
        Person person = new PersonBuilder()
                .withPhone("9123")
                .withEmail("a@x.com")
                .withCountry("Singapore")
                .build();

        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_PHONE, "912");
        map.put(PREFIX_EMAIL, "@x.");
        map.put(PREFIX_COUNTRY, "pore");
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);

        assertTrue(p.test(person));
    }

    @Test
    public void testMultipleFields_oneInvalidKeyword_matchesFalse() {
        Person person = new PersonBuilder()
                .withPhone("9123")
                .withEmail("a@x.com")
                .withCountry("Singapore")
                .build();

        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_PHONE, "912");
        map.put(PREFIX_EMAIL, "@x.");
        map.put(PREFIX_COUNTRY, "usa"); // fails here
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);

        assertFalse(p.test(person));
    }

    // empty/whitespace keyword
    @Test
    public void testEmail_whitespaceKeyword_matchesFalse() {
        Person person = new PersonBuilder().withEmail("alice@example.com").build();
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_EMAIL, "   "); // becomes empty after trimming -> returns false in matcher
        MultiFieldContainsKeywordsPredicate p = new MultiFieldContainsKeywordsPredicate(map);
        assertFalse(p.test(person));
    }

    @Test
    public void equals() {
        ArgumentMultimap map1 = new ArgumentMultimap();
        map1.put(PREFIX_NAME, "alice");
        ArgumentMultimap map2 = new ArgumentMultimap();
        map2.put(PREFIX_NAME, "alice");
        ArgumentMultimap map3 = new ArgumentMultimap();
        map3.put(PREFIX_NAME, "bob");

        MultiFieldContainsKeywordsPredicate p1 = new MultiFieldContainsKeywordsPredicate(map1);
        MultiFieldContainsKeywordsPredicate p2 = new MultiFieldContainsKeywordsPredicate(map2);
        MultiFieldContainsKeywordsPredicate p3 = new MultiFieldContainsKeywordsPredicate(map3);

        // same object -> true
        assertTrue(p1.equals(p1));

        // same map content -> true
        assertTrue(p1.equals(p2));

        // different map -> false
        assertFalse(p1.equals(p3));

        // different type -> false
        assertFalse(p1.equals("not a predicate"));

        // null -> false
        assertFalse(p1.equals(null));
    }
}
