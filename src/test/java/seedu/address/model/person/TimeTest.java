package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    void getFormattedTime_shouldReturnCorrectFormat() {
        Time time = new Time();
        String formatted = time.getFormattedTime();

        // Regex: two digits day, hyphen, three letters month, space, HH:mm:ss
        String pattern = "\\d{2}-[A-Za-z]{3} \\d{2}:\\d{2}:\\d{2}";

        assertTrue(formatted.matches(pattern),
                "Time string should match DD-MMM HH:mm:ss format");
    }

    @Test
    void time_shouldChangeAfterOneSecond() throws InterruptedException {
        Time time = new Time();
        String t1 = time.getFormattedTime();

        Thread.sleep(1100); // wait a little over 1 second
        String t2 = time.getFormattedTime();

        assertNotEquals(t1, t2, "Time should update after one second");
    }
}
