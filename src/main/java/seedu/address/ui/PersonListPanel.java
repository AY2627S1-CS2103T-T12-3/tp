package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of contacts.
 */
public class PersonListPanel extends UiPart<Region> {
    public static final String EMPTY_LIST_MESSAGE = "No contacts to show. Use the add command to add a contact.";

    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    @FXML
    private Label activeSearchLabel;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.setPlaceholder(createPlaceholder());
    }

    private static Label createPlaceholder() {
        Label placeholder = new Label(EMPTY_LIST_MESSAGE);
        placeholder.getStyleClass().add("empty-list-placeholder");
        return placeholder;
    }

    /** Updates the persistent indicator for the currently active contact search. */
    public void setActiveSearchTerm(String searchTerm) {
        boolean hasSearchTerm = !searchTerm.isEmpty();
        activeSearchLabel.setText(hasSearchTerm ? "Filtered by: " + searchTerm : "");
        activeSearchLabel.setVisible(hasSearchTerm);
        activeSearchLabel.setManaged(hasSearchTerm);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
