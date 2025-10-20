package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.Meeting;

/**
 * Jackson-friendly version of {@link Meeting}.
 */
public class JsonAdaptedMeeting {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meeting's %s field is missing!";
    public static final String INVALID_MEETING_TIME_MESSAGE = "Meeting time must be in the format DD-MM-YYYY HH:MM";

    private final String meetingTime;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given meeting details.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("meetingTime") String meetingTime,
                              @JsonProperty("description") String description) {
        this.meetingTime = meetingTime;
        this.description = description;
    }

    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        this.meetingTime = source.getMeetingTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.description = source.getDescription().orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted meeting object into the model's {@code Meeting} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meeting.
     */
    public Meeting toModelType() throws IllegalValueException {
        if (meetingTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDateTime.class.getSimpleName()));
        }
        LocalDateTime modelMeetingTime;
        try {
            modelMeetingTime = ParserUtil.parseMeetingTime(meetingTime);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(INVALID_MEETING_TIME_MESSAGE, e);
        }
        // Description can be null
        return new Meeting(modelMeetingTime, description);
    }
}
