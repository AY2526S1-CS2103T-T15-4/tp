package seedu.address.ui.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the Time of Person's country time zone.
 * The time displayed currently in this version is only
 * of the OS of the user opening the application.
 */
public class TimeFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM HH:mm");

    private TimeFormatter() {}

    public static String getFormattedTime() {
        return LocalDateTime.now().format(FORMATTER);
    }

    public static String getFormattedTime(ZoneId zone) {
        assert zone != null : "Zone cannot be null.";
        return ZonedDateTime.now(zone).format(FORMATTER);
    }
}
