package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedMeeting.INVALID_MEETING_TIME_MESSAGE;
import static seedu.address.storage.JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Meeting;

public class JsonAdaptedMeetingTest {

    private static final Meeting VALID_MEETING = new Meeting(
            LocalDateTime.of(2025, 11, 1, 10, 0),
            "Project Kickoff"
    );
    private static final String VALID_MEETING_TIME = "01-11-2025 10:00";
    private static final String VALID_DESCRIPTION = "Project Kickoff";
    private static final String INVALID_MEETING_TIME = "2025-11-01 10:00"; // Wrong format

    @Test
    public void toModelType_validMeetingDetails_returnsMeeting() throws Exception {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_MEETING);
        assertEquals(VALID_MEETING, meeting.toModelType());
    }

    @Test
    public void toModelType_validMeetingNoDescription_returnsMeeting() throws Exception {
        Meeting meetingNoDesc = new Meeting(LocalDateTime.of(2025, 11, 1, 10, 0));
        JsonAdaptedMeeting jsonMeeting = new JsonAdaptedMeeting(VALID_MEETING_TIME, null);
        assertEquals(meetingNoDesc, jsonMeeting.toModelType());
    }

    @Test
    public void toModelType_invalidMeetingTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(INVALID_MEETING_TIME, VALID_DESCRIPTION);
        assertThrows(IllegalValueException.class, INVALID_MEETING_TIME_MESSAGE, meeting::toModelType);
    }

    @Test
    public void toModelType_nullMeetingTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(null, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }
}