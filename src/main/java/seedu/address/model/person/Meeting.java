package seedu.address.model.person;

import java.time.LocalDateTime;
import java.util.Optional;

public class Meeting {
    public final LocalDateTime meetingTime;
    public final Optional<String> description;

    public Meeting(LocalDateTime meetingTime, String description) {
        this.meetingTime = meetingTime;
        this.description = Optional.ofNullable(description);
    }

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
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Meeting)) {
            return false;
        }

        Meeting otherMeeting = (Meeting) other;
        return otherMeeting.description.equals(this.description) && otherMeeting.meetingTime.equals(this.meetingTime);
    }

    @Override
    public String toString() {
        return this.meetingTime.toString() + " " + description.orElse("").toString();
    }
}
