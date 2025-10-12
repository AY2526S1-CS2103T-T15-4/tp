package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import seedu.address.model.util.SampleDataUtil;

public class TimeTest {

    @Test
    void getFormattedTime_shouldReturnCorrectFormat() {
        Time time = new Time();
        String formatted = time.getFormattedTime();

        // Regex: two digits day, space, three letters month, space, HH:mm
        String pattern = "\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}";

        assertTrue(formatted.matches(pattern),
                "Time string should match DD-MMM HH:mm format");
    }

    @Test
    void getFormattedTime_withZone_shouldReturnCorrectFormat() {
        Time time = new Time();
        ZoneId zone = ZoneId.of("Asia/Singapore");
        String formatted = time.getFormattedTime(zone);

        // Regex: two digits day, space, three letters month, space, HH:mm
        String pattern = "\\d{2} [A-Za-z]{3} \\d{2}:\\d{2}";

        assertTrue(formatted.matches(pattern),
                "Time string with zone should match DD-MMM HH:mm format");
    }

    @Test
    void time_shouldChangeAfterOneMinute() throws InterruptedException {
        Time time = new Time();
        String t1 = time.getFormattedTime();

        Thread.sleep(61_000);
        String t2 = time.getFormattedTime();

        assertNotEquals(t1, t2, "Time should update after one minute");
    }

    @Test
    void getTime_shouldReturnTimeObject() {
        Person person = SampleDataUtil.getSamplePersons()[0];

        Time time = person.getTime();

        assertNotNull(time, "getTime() should return a non-null Time object");
        assertTrue(time instanceof Time, "getTime() should return a Time instance");
    }
}
