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
import javafx.util.Duration;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * An UI component that displays information of a {@code Person}.
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
    Label time;
    @FXML
    private FlowPane tags;

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
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        showTime();
    }

    /**
     * Gets the country from Person to find its relevant time
     * zone to be displayed, else displays OS time.
     * @param country country of Person
     * @return Time zone of country of Person
     */
    private ZoneId getZoneIdFromCountry(String country) {
        if (country == null) {
            return null;
        }

        switch (country.toLowerCase()) {
        case "singapore": return ZoneId.of("Asia/Singapore");
        case "india": return ZoneId.of("Asia/Kolkata");
        case "france": return ZoneId.of("Europe/Paris");
        case "united states": return ZoneId.of("America/New_York");
        case "japan": return ZoneId.of("Asia/Tokyo");
        case "china": return ZoneId.of("Asia/Shanghai");
        case "australia": return ZoneId.of("Australia/Sydney");
        case "canada": return ZoneId.of("America/Toronto");
        case "brazil": return ZoneId.of("America/Sao_Paulo");
        case "germany": return ZoneId.of("Europe/Berlin");
        case "united kingdom": return ZoneId.of("Europe/London");
        case "south korea": return ZoneId.of("Asia/Seoul");
        case "uae": return ZoneId.of("Asia/Dubai");
        case "south africa": return ZoneId.of("Africa/Johannesburg");
        case "mexico": return ZoneId.of("America/Mexico_City");
        case "argentina": return ZoneId.of("America/Argentina/Buenos_Aires");
        case "spain": return ZoneId.of("Europe/Madrid");
        case "italy": return ZoneId.of("Europe/Rome");
        case "netherlands": return ZoneId.of("Europe/Amsterdam");
        case "sweden": return ZoneId.of("Europe/Stockholm");
        case "norway": return ZoneId.of("Europe/Oslo");
        case "denmark": return ZoneId.of("Europe/Copenhagen");
        case "finland": return ZoneId.of("Europe/Helsinki");
        case "poland": return ZoneId.of("Europe/Warsaw");
        case "belgium": return ZoneId.of("Europe/Brussels");
        case "switzerland": return ZoneId.of("Europe/Zurich");
        case "austria": return ZoneId.of("Europe/Vienna");
        case "portugal": return ZoneId.of("Europe/Lisbon");
        case "greece": return ZoneId.of("Europe/Athens");
        case "turkey": return ZoneId.of("Europe/Istanbul");
        case "egypt": return ZoneId.of("Africa/Cairo");
        case "kenya": return ZoneId.of("Africa/Nairobi");
        case "nigeria": return ZoneId.of("Africa/Lagos");
        default: return null;
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

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(delayMillis), e -> {
            updateTime();

            Timeline repeating = new Timeline(new KeyFrame(Duration.minutes(1), ev -> updateTime()));
            repeating.setCycleCount(Timeline.INDEFINITE);
            repeating.play();
        }));
        timeline.play();
    }

    /**
     * Actual display of time on UI
     */
    private void updateTime() {
        Time timeObj = person.getTime();
        ZoneId zone = getZoneIdFromCountry(person.getCountry().value);

        String formattedTime = (zone != null)
                ? timeObj.getFormattedTime(zone)
                : timeObj.getFormattedTime();

        time.setText("Local time: " + formattedTime);
    }
}
