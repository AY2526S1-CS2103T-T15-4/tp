package seedu.address.ui.util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class TimeFormatterTest {

    @Test
    void getFormattedTime_shouldReturnValidFormat() {
        String time = TimeFormatter.getFormattedTime();
        assertNotNull(time, "Formatted time should not be null");
        assertTrue(time.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"),
                "Formatted time should match pattern 'dd MMM HH:mm', got: " + time);
    }

    @Test
    void getFormattedTime_withZone_shouldReturnValidFormat() {
        ZoneId zone = ZoneId.of("Asia/Singapore");
        String time = TimeFormatter.getFormattedTime(zone);
        assertNotNull(time, "Formatted time with zone should not be null");
        assertTrue(time.matches("\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}"),
                "Formatted time with zone should match pattern 'dd MMM HH:mm', got: " + time);
    }

    @Test
    void getFormattedTime_withDifferentZone_shouldReturnDifferentTimes() {
        ZoneId sg = ZoneId.of("Asia/Singapore");
        ZoneId ny = ZoneId.of("America/New_York");

        String timeSG = TimeFormatter.getFormattedTime(sg);
        String timeNY = TimeFormatter.getFormattedTime(ny);

        assertNotEquals(timeSG, timeNY, "Times in different zones should not be equal (most of the time)");
    }
}
