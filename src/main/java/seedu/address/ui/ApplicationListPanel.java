package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.application.Application;

/**
 * Panel containing the list of applications.
 */
public class ApplicationListPanel extends UiPart<Region> {
    public static final String EMPTY_LIST_MESSAGE =
            "No applications to show. Use the addapp command to add an application.";

    private static final String FXML = "ApplicationListPanel.fxml";

    @FXML
    private ListView<Application> applicationListView;

    /**
     * Creates an {@code ApplicationListPanel} with the given {@code ObservableList}.
     */
    public ApplicationListPanel(ObservableList<Application> applicationList) {
        super(FXML);
        applicationListView.setItems(applicationList);
        applicationListView.setCellFactory(listView -> new ApplicationListViewCell());
        applicationListView.setPlaceholder(createPlaceholder());
    }

    private static Label createPlaceholder() {
        Label placeholder = new Label(EMPTY_LIST_MESSAGE);
        placeholder.getStyleClass().add("empty-list-placeholder");
        return placeholder;
    }

    /**
     * Custom {@code ListCell} that displays an {@code Application} using an {@code ApplicationCard}.
     */
    class ApplicationListViewCell extends ListCell<Application> {
        @Override
        protected void updateItem(Application application, boolean empty) {
            super.updateItem(application, empty);

            if (empty || application == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ApplicationCard(application, getIndex() + 1).getRoot());
            }
        }
    }
}
