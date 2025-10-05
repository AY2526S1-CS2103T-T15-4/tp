package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Time of Person's country time zone.
 * The time displayed currently in this version is only
 * of the OS of the user opening the application.
 */
public class Time {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM HH:mm:ss");

    public String getFormattedTime() {
        return LocalDateTime.now().format(FORMATTER);
    }

}
