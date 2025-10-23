package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeCountry;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = " ";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_COUNTRY = " ";
    private static final String INVALID_COMPANY = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_COUNTRY = BENSON.getCountry().toString();
    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final boolean VALID_IS_FLAGGED = BENSON.isFlagged();
    private static final List<JsonAdaptedMeeting> VALID_MEETINGS = BENSON.getMeetings().stream()
            .map(JsonAdaptedMeeting::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedMeeting> INVALID_MEETINGS_INVALID_TIME = List.of(
            new JsonAdaptedMeeting("2025/10/22 10:00", "Project discussion") // wrong format
    );
    private static final List<JsonAdaptedMeeting> INVALID_MEETINGS_NULL_TIME = List.of(
            new JsonAdaptedMeeting(null, "Missing time")
    );

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COUNTRY, VALID_COMPANY, VALID_TAGS,
                        VALID_IS_FLAGGED, VALID_MEETINGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_COUNTRY,
                VALID_COMPANY, VALID_TAGS, VALID_IS_FLAGGED, VALID_MEETINGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_COUNTRY, VALID_COMPANY, VALID_TAGS,
                        VALID_IS_FLAGGED, VALID_MEETINGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_COUNTRY,
                VALID_COMPANY, VALID_TAGS, VALID_IS_FLAGGED, VALID_MEETINGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_COUNTRY,
                        VALID_COMPANY, VALID_TAGS, VALID_IS_FLAGGED, VALID_MEETINGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_COUNTRY,
                VALID_COMPANY, VALID_TAGS, VALID_IS_FLAGGED, VALID_MEETINGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCountry_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_COUNTRY, VALID_COMPANY, VALID_TAGS,
                        VALID_IS_FLAGGED, VALID_MEETINGS);
        String expectedMessage = HomeCountry.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullCountry_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_COMPANY, VALID_TAGS, VALID_IS_FLAGGED, VALID_MEETINGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, HomeCountry.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COUNTRY, INVALID_COMPANY, VALID_TAGS,
                        VALID_IS_FLAGGED, VALID_MEETINGS);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COUNTRY,
                null, VALID_TAGS, VALID_IS_FLAGGED, VALID_MEETINGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COUNTRY, VALID_COMPANY, invalidTags,
                        VALID_IS_FLAGGED, VALID_MEETINGS);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidMeetingTime_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COUNTRY,
                VALID_COMPANY, VALID_TAGS, VALID_IS_FLAGGED, INVALID_MEETINGS_INVALID_TIME);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullMeetingTime_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COUNTRY,
                VALID_COMPANY, VALID_TAGS, VALID_IS_FLAGGED, INVALID_MEETINGS_NULL_TIME);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_validMeetings_returnsCorrectMeetingSet() throws Exception {
        // Create person with one valid meeting
        List<JsonAdaptedMeeting> validMeetings = List.of(
                new JsonAdaptedMeeting("22-10-2025 10:00", "Team sync")
        );
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_COUNTRY,
                VALID_COMPANY, VALID_TAGS, VALID_IS_FLAGGED, validMeetings);
        Person modelPerson = person.toModelType();

        // Check that the person has exactly one meeting with matching details
        assertEquals(1, modelPerson.getMeetings().size());
        Meeting meeting = modelPerson.getMeetings().iterator().next();
        assertEquals(LocalDateTime.of(2025, 10, 22, 10, 0), meeting.getMeetingTime());
        assertEquals("Team sync", meeting.getDescription().orElse(null));
    }

    @Test
    public void constructor_fromPerson_includesMeetings() {
        // Convert BENSON -> JsonAdaptedPerson -> Person
        JsonAdaptedPerson adapted = new JsonAdaptedPerson(BENSON);
        Person reconstructed = null;
        try {
            reconstructed = adapted.toModelType();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }

        // Verify meetings are preserved through conversion
        assertEquals(BENSON.getMeetings(), reconstructed.getMeetings());
    }

}
