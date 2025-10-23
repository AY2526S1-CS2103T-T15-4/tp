package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeCountry;
import seedu.address.model.person.Meeting;
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
    private Set<Meeting> meetings;

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
        meetings = new HashSet<>();
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
        meetings = new HashSet<>(personToCopy.getMeetings());
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
    public PersonBuilder withFlag(boolean isFlagged) {
        this.isFlagged = isFlagged;
        return this;
    }

    /**
     * Parses the {@code meetings} into a {@code Set<Meeting>} and set it to the {@code Person} that we are building.
     * Each meeting string should be in the format "DD-MM-YYYY HH:MM [description]".
     */
    public PersonBuilder withMeetings(String... meetings) {
        this.meetings = new HashSet<>();
        for (String meeting : meetings) {
            String[] parts = meeting.split(" ", 3); // Split on first two spaces (date, time, description)
            if (parts.length < 2) {
                throw new IllegalArgumentException("Meeting must include date and time: " + meeting);
            }
            String timePart = parts[0] + " " + parts[1]; // Combine date and time
            String description = parts.length > 2 ? parts[2] : null;
            try {
                LocalDateTime meetingTime = ParserUtil.parseMeetingTime(timePart);
                this.meetings.add(new Meeting(meetingTime, description));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid meeting time format: " + timePart, e);
            }
        }
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, country, company, tags, isFlagged, meetings);
    }
}
