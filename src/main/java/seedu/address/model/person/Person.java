package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final HomeCountry country;
    private final Company company;
    private final Set<Tag> tags = new HashSet<>();
    private final boolean isFlagged;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, HomeCountry country, Company company, Set<Tag> tags,
                  boolean isFlagged) {
        requireAllNonNull(name, phone, email, country, company, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.company = company;
        this.tags.addAll(tags);
        this.isFlagged = isFlagged;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public HomeCountry getCountry() {
        return country;
    }

    public Company getCompany() {
        return company;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    /**
     * Returns true if both persons have the same phone or email.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && (otherPerson.getEmail().equals(getEmail()) || otherPerson.getPhone().equals(getPhone()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && country.equals(otherPerson.country)
                && company.equals(otherPerson.company)
                && tags.equals(otherPerson.tags)
                && isFlagged == otherPerson.isFlagged;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, country, company, tags, isFlagged);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("country", country)
                .add("company", company)
                .add("tags", tags)
                .add("isFlagged", isFlagged)
                .toString();
    }

}
