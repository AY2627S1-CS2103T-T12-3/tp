package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.application.Application;

/**
 * A UI component that displays an {@code Application}.
 */
public class ApplicationCard extends UiPart<Region> {

    private static final String FXML = "ApplicationListCard.fxml";

    public final Application application;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label company;
    @FXML
    private Label description;
    @FXML
    private Label date;

    /**
     * Creates an {@code ApplicationCard} with the given {@code Application} and index to display.
     */
    public ApplicationCard(Application application, int displayedIndex) {
        super(FXML);
        this.application = application;
        id.setText(displayedIndex + ". ");
        name.setText(application.getName().value);
        company.setText("Company: " + application.getCompany().value);
        description.setText("Description: " + application.getDescription().value);
        date.setText("Date: " + application.getDate().value);
    }
}
