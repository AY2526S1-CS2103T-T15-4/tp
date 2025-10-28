package seedu.address.commons.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for formatting date and time values.
 * <p>
 * Provides methods to obtain formatted time strings based on the current system time,
 * a specified time zone, or a given {@link LocalDateTime} instance.
 * The formatted output follows the pattern {@code "dd MMM HH:mm"} (e.g., {@code "28 Oct 17:45"}).
 * <p>
 * Note: The current version formats time according to the operating system's default time zone.
 */
public class TimeFormatter {

    /** Formatter used to produce output in the pattern "dd MMM HH:mm". */
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd MMM HH:mm");

    private static final DateTimeFormatter OUTPUT_FORMATTER_WITH_YEARS = DateTimeFormatter.ofPattern("dd MMM "
            + "YYYY HH:mm");

    /** Private constructor to prevent instantiation of this utility class. */
    private TimeFormatter() {}

    /**
     * Returns the current system time formatted using the {@link #OUTPUT_FORMATTER}.
     * <p>
     * The time displayed is based on the operating system's default time zone.
     *
     * @return a formatted string representing the current system time,
     *         e.g., {@code "28 Oct 17:45"}.
     */
    public static String getFormattedCurrentTime() {
        return LocalDateTime.now().format(OUTPUT_FORMATTER);
    }

    /**
     * Returns the current time in the specified time zone, formatted using the {@link #OUTPUT_FORMATTER}.
     *
     * @param zone the {@link ZoneId} representing the desired time zone; must not be {@code null}.
     * @return a formatted string representing the current time in the specified zone,
     *         e.g., {@code "28 Oct 10:45"} for {@code ZoneId.of("Europe/London")}.
     * @throws AssertionError if {@code zone} is {@code null}.
     */
    public static String getFormattedTimeFromZone(ZoneId zone) {
        assert zone != null : "Zone cannot be null.";
        return ZonedDateTime.now(zone).format(OUTPUT_FORMATTER);
    }

    /**
     * Formats the given {@link LocalDateTime} using the {@link #OUTPUT_FORMATTER}.
     *
     * @param input the {@code LocalDateTime} to format; must not be {@code null}.
     * @return a formatted string representing the given time,
     *         e.g., {@code "28 Oct 2012 17:45"}.
     * @throws NullPointerException if {@code input} is {@code null}.
     */
    public static String getFormattedTimeWithYearsFromInput(LocalDateTime input) {
        return input.format(OUTPUT_FORMATTER_WITH_YEARS);
    }
}
