package seedu.address.commons.time;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class TimeFormatterTest {

    @Test
    public void getFormattedTimeFromZone_shouldReturnValidFormat() {
        String time = TimeFormatter.getFormattedCurrentTime();
        assertNotNull(time, "Formatted time should not be null");
        assertTrue(time.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"),
                "Formatted time should match pattern 'dd MMM HH:mm', got: " + time);
    }

    @Test
    public void getFormattedTimeFromZone_withZone_shouldReturnValidFormat() {
        ZoneId zone = ZoneId.of("Asia/Singapore");
        String time = TimeFormatter.getFormattedTimeFromZone(zone);
        assertNotNull(time, "Formatted time with zone should not be null");
        assertTrue(time.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"),
                "Formatted time with zone should match pattern 'dd MMM HH:mm', got: " + time);
    }

    @Test
    public void getFormattedTimeFromZone_withDifferentZone_shouldReturnDifferentTimes() {
        ZoneId sg = ZoneId.of("Asia/Singapore");
        ZoneId ny = ZoneId.of("America/New_York");

        String timeSG = TimeFormatter.getFormattedTimeFromZone(sg);
        String timeNY = TimeFormatter.getFormattedTimeFromZone(ny);

        assertNotEquals(timeSG, timeNY, "Times in different zones should not be equal (most of the time)");
    }

    @Test
    public void getFormattedTimeFromZone_atMidnight_shouldFormatCorrectly() {
        ZoneId zone = ZoneId.of("UTC");
        String formatted = TimeFormatter.getFormattedTimeFromZone(zone);

        assertNotNull(formatted);
        assertTrue(formatted.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"));
    }
}
