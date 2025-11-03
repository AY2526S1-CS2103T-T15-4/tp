package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeCountry;
import seedu.address.model.person.Link;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String country;
    private final String company;
    private final String link;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final boolean isFlagged;
    private final List<JsonAdaptedMeeting> meetings = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("country") String country,
                             @JsonProperty("company") String company,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("isFlagged") boolean isFlagged,
                             @JsonProperty("meetings") List<JsonAdaptedMeeting> meetings,
                             @JsonProperty("link") String link) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.company = company;
        this.link = link;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.isFlagged = isFlagged;
        if (meetings != null) {
            this.meetings.addAll(meetings);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        country = source.getCountry().value;
        company = source.getCompany().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        isFlagged = source.isFlagged();
        meetings.addAll(source.getMeetings().stream()
                .map(JsonAdaptedMeeting::new)
                .collect(Collectors.toList()));
        if (source.getLink() != null) {
            link = source.getLink().value;
        } else {
            link = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (country == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    HomeCountry.class.getSimpleName()));
        }
        if (!HomeCountry.isValidCountry(country)) {
            throw new IllegalValueException(HomeCountry.MESSAGE_CONSTRAINTS);
        }
        final HomeCountry modelCountry = new HomeCountry(country);

        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(company);

        final List<Meeting> modelMeetings = new ArrayList<>();
        for (JsonAdaptedMeeting meeting : meetings) {
            modelMeetings.add(meeting.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);

        final Link modelLink;
        if (link != null) {
            if (!Link.isValidLink(link)) {
                throw new IllegalValueException(Link.MESSAGE_CONSTRAINTS);
            }
            modelLink = new Link(link);
        } else {
            modelLink = null;
        }

        return new Person(modelName, modelPhone, modelEmail, modelCountry, modelCompany, modelTags,
                          isFlagged, modelMeetings, modelLink);
    }

}
