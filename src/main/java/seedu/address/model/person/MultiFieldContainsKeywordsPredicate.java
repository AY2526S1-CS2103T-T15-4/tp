package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person} matches ALL the specified field criteria.
 * Each field uses OR logic within itself, but AND logic between different fields.
 * Matching is case-insensitive and uses partial/substring matching.
 */
public class MultiFieldContainsKeywordsPredicate implements Predicate<Person> {

    private final ArgumentMultimap fieldKeywordsMap;

    public MultiFieldContainsKeywordsPredicate(ArgumentMultimap fieldKeywordsMap) {
        this.fieldKeywordsMap = fieldKeywordsMap;
    }

    /**
     * Filters out any empty String "" or "  " from List<String></String>.
     */
    private static List<String> filterEmptyString(List<String> raw) {
        return raw.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    @Override
    public boolean test(Person person) {
        assert person != null;
        requireNonNull(person);

        // Start with always true and combine with AND logic for each specified field
        boolean matchesAllFields = true;

        // Check name field if specified
        if (fieldKeywordsMap.getValue(PREFIX_NAME).isPresent()) {
            List<String> nameKeywords = filterEmptyString(fieldKeywordsMap.getAllValues(PREFIX_NAME));
            matchesAllFields = matchesAllFields && matchesName(person, nameKeywords);
        }

        // Check phone field if specified
        if (fieldKeywordsMap.getValue(PREFIX_PHONE).isPresent()) {
            List<String> phoneKeywords = filterEmptyString(fieldKeywordsMap.getAllValues(PREFIX_PHONE));
            matchesAllFields = matchesAllFields && matchesPhone(person, phoneKeywords);
        }

        // Check email field if specified
        if (fieldKeywordsMap.getValue(PREFIX_EMAIL).isPresent()) {
            List<String> emailKeywords = filterEmptyString(fieldKeywordsMap.getAllValues(PREFIX_EMAIL));
            matchesAllFields = matchesAllFields && matchesEmail(person, emailKeywords);
        }

        // Check country field if specified
        if (fieldKeywordsMap.getValue(PREFIX_COUNTRY).isPresent()) {
            List<String> countryKeywords = filterEmptyString(fieldKeywordsMap.getAllValues(PREFIX_COUNTRY));
            matchesAllFields = matchesAllFields && matchesCountry(person, countryKeywords);
        }

        // Check company field if specified
        if (fieldKeywordsMap.getValue(PREFIX_COMPANY).isPresent()) {
            List<String> companyKeywords = filterEmptyString(fieldKeywordsMap.getAllValues(PREFIX_COMPANY));
            matchesAllFields = matchesAllFields && matchesCompany(person, companyKeywords);
        }

        // Check tag field if specified
        if (fieldKeywordsMap.getValue(PREFIX_TAG).isPresent()) {
            List<String> tagKeywords = filterEmptyString(fieldKeywordsMap.getAllValues(PREFIX_TAG));
            matchesAllFields = matchesAllFields && matchesTags(person, tagKeywords);
        }

        return matchesAllFields;
    }

    /**
     * Checks if the person's name contains ANY of the keywords (OR logic within name)
     * Uses partial matching (case-insensitive)
     */
    private boolean matchesName(Person person, List<String> keywords) {
        assert person != null;
        requireNonNull(person);
        assert !keywords.isEmpty();
        return keywords.stream()
                .anyMatch(keyword -> person.getName().fullName.toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if the person's phone contains ANY of the keywords (OR logic within phone)
     * Uses partial matching
     */
    private boolean matchesPhone(Person person, List<String> keywords) {
        assert person != null;
        requireNonNull(person);
        assert !keywords.isEmpty();
        return keywords.stream()
                .anyMatch(keyword -> person.getPhone().value.contains(keyword));
    }

    /**
     * Checks if the person's email contains ANY of the keywords (OR logic within email)
     * Uses partial matching (case-insensitive)
     */
    private boolean matchesEmail(Person person, List<String> keywords) {
        assert person != null;
        requireNonNull(person);
        assert !keywords.isEmpty();
        return keywords.stream()
                .anyMatch(keyword -> person.getEmail().value.toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if the person's country contains ANY of the keywords (OR logic within country)
     * Uses partial matching (case-insensitive)
     */
    private boolean matchesCountry(Person person, List<String> keywords) {
        assert person != null;
        requireNonNull(person);
        assert !keywords.isEmpty();
        return keywords.stream()
                .anyMatch(keyword -> person.getCountry().value.toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if the person's company contains ANY of the keywords (OR logic within company)
     * Uses partial matching (case-insensitive)
     */
    private boolean matchesCompany(Person person, List<String> keywords) {
        assert person != null;
        requireNonNull(person);
        assert !keywords.isEmpty();
        return keywords.stream()
                .anyMatch(keyword -> person.getCompany().value.toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if the person's tags contain ANY of the keywords (OR logic within tags)
     * Uses partial matching (case-insensitive)
     */
    private boolean matchesTags(Person person, List<String> keywords) {
        assert person != null;
        requireNonNull(person);
        assert !keywords.isEmpty();
        Set<Tag> tags = person.getTags();
        return keywords.stream().anyMatch(keyword ->
                tags.stream().anyMatch(tag ->
                        tag.tagName.toLowerCase().contains(keyword.toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MultiFieldContainsKeywordsPredicate)) {
            return false;
        }

        MultiFieldContainsKeywordsPredicate otherPredicate = (MultiFieldContainsKeywordsPredicate) other;
        return fieldKeywordsMap.equals(otherPredicate.fieldKeywordsMap);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("fieldKeywordsMap", fieldKeywordsMap)
                .toString();
    }
}
