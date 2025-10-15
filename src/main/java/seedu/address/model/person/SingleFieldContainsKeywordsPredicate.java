package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class SingleFieldContainsKeywordsPredicate implements Predicate<Person> {

    /** Represents which field of the Person this predicate targets. */
    public enum TargetField {
        NAME, PHONE, EMAIL, COUNTRY, COMPANY, TAG
    }

    private final TargetField field;
    private final List<String> keywords;

    /** Constructor of this class*/
    public SingleFieldContainsKeywordsPredicate(TargetField field, List<String> keywords) {
        this.field = field;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        switch (field) {
        case NAME:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        case PHONE:
            return keywords.stream()
                    .anyMatch(keyword -> person.getPhone().value.contains(keyword));

        case EMAIL:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));

        case COUNTRY:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getCountry().value, keyword));

        case COMPANY:
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getCompany().value, keyword));

        case TAG:
            Set<Tag> tags = person.getTags();
            return keywords.stream().anyMatch(keyword ->
                    tags.stream().anyMatch(tag ->
                            StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));

        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SingleFieldContainsKeywordsPredicate)) {
            return false;
        }

        SingleFieldContainsKeywordsPredicate o = (SingleFieldContainsKeywordsPredicate) other;
        return field.equals(o.field) && keywords.equals(o.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("field", field)
                .add("keywords", keywords)
                .toString();
    }
}
