package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.time.TimeFormatter;

/**
 * Represents a meeting associated with a person in the address book.
 * A meeting consists of a {@link LocalDateTime} indicating when it occurs,
 * and an optional description.
 */
public class Meeting {
    /**
     * The date and time of the meeting.
     */
    public final LocalDateTime meetingTime;
    /**
     * An optional description of the meeting.
     */
    public final Optional<String> description;
    /**
     * Constructs a {@code Meeting} with the specified time and optional description.
     *
     * @param meetingTime The {@link LocalDateTime} of the meeting. Must not be {@code null}.
     * @param description The description of the meeting. May be {@code null}.
     */
    public Meeting(LocalDateTime meetingTime, String description) {
        requireNonNull(meetingTime);
        this.meetingTime = meetingTime;
        this.description = Optional.ofNullable(description);
    }
    /**
     * Constructs a {@code Meeting} with no description.
     *
     * @param meetingTime The {@link LocalDateTime} of the meeting. Must not be {@code null}.
     */
    public Meeting(LocalDateTime meetingTime) {
        this(meetingTime, null);
    }

    public LocalDateTime getMeetingTime() {
        return meetingTime;
    }

    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Meeting)) {
            return false;
        }

        Meeting otherMeeting = (Meeting) other;
        return otherMeeting.meetingTime.equals(this.meetingTime);
    }

    @Override
    public String toString() {
        return TimeFormatter.getFormattedTimeFromInput(meetingTime) + " " + description.orElse("");
    }
}
