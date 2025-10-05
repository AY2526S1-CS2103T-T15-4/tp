package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM HH:mm:ss");

    public String getFormattedTime() {
        return LocalDateTime.now().format(FORMATTER);
    }

}
