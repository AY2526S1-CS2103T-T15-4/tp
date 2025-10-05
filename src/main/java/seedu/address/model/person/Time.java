package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM HH:mm:ss");

    private boolean running = true;

    public void start() {
        Thread clockThread = new Thread(() -> {
            while (running) {
                LocalDateTime now = LocalDateTime.now();
                displayTime(now);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        clockThread.setDaemon(true); // runs in background
        clockThread.start();
    }

    private void displayTime(LocalDateTime time) {
        //System.out.println("Time: " + time.format(FORMATTER));
    }

    public String getFormattedTime() {
        return LocalDateTime.now().format(FORMATTER);
    }

    public void stop() {
        running = false;
    }

}