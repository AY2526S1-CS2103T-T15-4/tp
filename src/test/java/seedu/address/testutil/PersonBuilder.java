package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeCountry;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_COUNTRY = "Singapore";
    public static final String DEFAULT_COMPANY = "Shopee";

    private Company company;
    private Name name;
    private Phone phone;
    private Email email;
    private HomeCountry country;
    private Set<Tag> tags;
    private boolean isFlagged;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        country = new HomeCountry(DEFAULT_COUNTRY);
        company = new Company(DEFAULT_COMPANY);
        tags = new HashSet<>();
        isFlagged = false;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        country = personToCopy.getCountry();
        company = personToCopy.getCompany();
        tags = new HashSet<>(personToCopy.getTags());
        isFlagged = personToCopy.isFlagged();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Country} of the {@code Person} that we are building.
     */
    public PersonBuilder withCountry(String country) {
        this.country = new HomeCountry(country);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Person} that we are building.
     */
    public PersonBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code isFlagged} status of the {@code Person} that we are building.
     */
    public PersonBuilder withIsFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, country, company, tags, isFlagged);
    }
}
