package seedu.address.ui;

import java.awt.Desktop;
import java.net.URI;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel that displays detailed information of a selected {@code Person}.
 */
public class PersonInfoPanel extends UiPart<Region> {
    private static final String FXML = "PersonInfoPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private VBox infoBox;
    @FXML
    private Label name;
    @FXML
    private Hyperlink phone;
    @FXML
    private Hyperlink telegram;
    @FXML
    private Label address;
    @FXML
    private Hyperlink email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label note;
    @FXML
    private VBox logContainer;

    private String telegramHandle;

    /**
     * Creates a {@code PersonInfoPanel} and initializes with placeholder text.
     */
    public PersonInfoPanel() {
        super(FXML);
        displayPerson(null);
    }

    /**
     * Displays the details of the given {@code Person} in the panel.
     * If {@code person} is {@code null}, shows placeholder text.
     *
     * @param person The person whose details to display, or {@code null} for default text.
     */
    public void displayPerson(Person person) {
        if (person == null) {
            name.setText("No person selected");
            phone.setText("-");
            telegram.setText("-");
            address.setText("-");
            email.setText("-");
            tags.getChildren().clear();
            note.setText("-");
        } else {
            name.setText(person.getName().fullName);
            phone.setText(person.getPhone().value);
            boolean validTelegram = person.getTelegramHandle().isValid;
            telegramHandle = person.getTelegramHandle().value;
            if (telegramHandle.isEmpty()) {
                telegramHandle = "-";
            }
            telegram.setText(telegramHandle);
            telegram.setDisable(!validTelegram);
            address.setText(person.getAddress().value);
            email.setText(person.getEmail().value);
            tags.getChildren().clear();
            person.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> {
                        Label tagLabel = new Label(tag.tagName);
                        tagLabel.getStyleClass().add("label");
                        tags.getChildren().add(tagLabel);
                    });
            note.setText(person.getNote().value);
            note.setWrapText(true);
            // subtract container padding (12 left + 12 right = 24) so text wraps correctly to visible width
            note.maxWidthProperty().bind(infoBox.widthProperty().subtract(24));

            // Display logs
            logContainer.getChildren().clear();
            if (!person.getLogs().isEmpty()) {
                person.getLogs().getLogs().forEach(log -> {
                    Label logLabel = new Label(log.toString());
                    logLabel.setWrapText(true);
                    logLabel.maxWidthProperty().bind(infoBox.widthProperty().subtract(24));
                    logLabel.getStyleClass().add("log-entry");
                    logContainer.getChildren().add(logLabel);
                });
            }
        }
    }

    private void openUri(String uri) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(uri));
                logger.warning("Opened link: " + uri);
            }
        } catch (Exception e) {
            logger.warning("Could not open link: " + uri + " (" + e.getMessage() + ")");
        }
    }

    @FXML
    private void onPhoneClick() {
        String phoneText = phone.getText();
        if (!phoneText.isEmpty()) {
            // tel: might be handled differently on different OSes...
            String digits = phoneText.replaceAll("\\s+", "");
            openUri("tel:" + digits);
        }
    }

    @FXML
    private void onTelegramClick() {
        String handle = telegram.getText();
        if (!telegramHandle.isEmpty()) {
            String path = telegramHandle.trim().replaceFirst("^@", "");
            openUri("https://t.me/" + path);
        }
    }

    @FXML
    private void onEmailClick() {
        String emailText = email.getText();
        if (!emailText.isEmpty()) {
            openUri("mailto:" + emailText.trim());
        }
    }
}
