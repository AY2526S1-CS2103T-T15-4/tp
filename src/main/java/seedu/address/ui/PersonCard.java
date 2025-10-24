package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import seedu.address.model.person.Person;
import seedu.address.model.util.TimezoneMapper;
import seedu.address.ui.util.TimeFormatter;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label country;
    @FXML
    private Label company;
    @FXML
    private Label email;
    @FXML
    private Label time;
    @FXML
    private Label link;
    @FXML
    private VBox meetings;
    @FXML
    private FlowPane tags;
    @FXML
    private Label flagLabel;

    private Timeline clock;
    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        country.setText(person.getCountry().value);
        company.setText(person.getCompany().value);
        email.setText(person.getEmail().value);

        if (person.isFlagged()) {
            flagLabel.setText("🚩");
        } else {
            flagLabel.setText("");
        }

        if (person.getLink() != null) {
            link.setText(person.getLink().value);
        } else {
            link.setVisible(false);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        updateMeetings();
        showTime();
        cardPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null && clock != null) {
                clock.stop();
            }
        });
    }
    /**
     * Updates the meetings displayed in the VBox.
     */
    private void updateMeetings() {
        if (meetings != null) {
            meetings.getChildren().clear(); // Clear existing meeting labels
            int index = 1;
            for (var meeting : person.getMeetings()) {
                Label meetingLabel = new Label(index + ". " + meeting.toString());
                meetingLabel.getStyleClass().add("cell_small_label"); // keep font consistent
                meetingLabel.setStyle("-fx-padding: 0 0 0 10;"); // optional indent
                meetings.getChildren().add(meetingLabel);
                index++;
            }
        }
    }

    /**
     * Gets the time zone based on the user's input.
     * The input must be a valid ZoneId string (e.g., "Asia/Singapore").
     * If invalid, returns null (and OS time will be shown instead).
     *
     * @param country user-input time zone name
     * @return ZoneId if valid, else null
     */
    private ZoneId getZoneIdFromCountry(String country) {
        assert country != null : "Country cannot be null.";

        if (country == null || country.isBlank()) {
            return null;
        }

        try {
            return TimezoneMapper.getZoneIdFromCountry(country);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * ensures the updating of time on the UI is accurate and
     * follows the same seconds as OS.
     */
    private void showTime() {
        updateTime();

        LocalDateTime now = LocalDateTime.now();
        long delayMillis = 60_000 - (now.getSecond() * 1000 + now.getNano() / 1_000_000);

        clock = new Timeline(
                new KeyFrame(Duration.millis(delayMillis), e -> updateTime()),
                new KeyFrame(Duration.minutes(1), e -> updateTime())
        );
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    /**
     * Actual display of time on UI
     */
    private void updateTime() {
        ZoneId zone = getZoneIdFromCountry(person.getCountry().value);

        if (zone == null) {
            time.setVisible(false);
            return;
        }

        time.setVisible(true);
        String formattedTime = TimeFormatter.getFormattedTime(zone);
        time.setText("Local time: " + formattedTime);
    }
}
