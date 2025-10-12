package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Time of Person's country time zone.
 * The time displayed currently in this version is only
 * of the OS of the user opening the application.
 */
public class Time {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM HH:mm");

    public String getFormattedTime() {
        return LocalDateTime.now().format(FORMATTER);
    }

    public String getFormattedTime(ZoneId zone) {
        return LocalDateTime.now(zone).format(FORMATTER);
    }
}
