package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import seedu.address.ui.util.TimeFormatter;

public class TimeTest {

    @Test
    void getFormattedTime_shouldReturnCorrectFormat() {
        String formatted = TimeFormatter.getFormattedTime();
        String pattern = "\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}";

        assertTrue(formatted.matches(pattern),
                "Time string should match DD-MMM HH:mm format");
    }

    @Test
    void getFormattedTime_withZone_shouldReturnCorrectFormat() {
        ZoneId zone = ZoneId.of("Asia/Singapore");
        String formatted = TimeFormatter.getFormattedTime(zone);
        String pattern = "\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}";

        assertTrue(formatted.matches(pattern),
                "Time string with zone should match DD-MMM HH:mm format");
    }

    @Test
    void time_shouldChangeAfterOneMinute() throws InterruptedException {
        String t1 = TimeFormatter.getFormattedTime();

        Thread.sleep(61_000);
        String t2 = TimeFormatter.getFormattedTime();

        assertNotEquals(t1, t2, "Time should update after one minute");
    }

}
