package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * A light workspace page that presents an existing contacts or applications list.
 */
public class WorkspaceListPanel extends VBox {

    /**
     * Creates a consistently styled workspace page around a supplied list view.
     */
    public WorkspaceListPanel(String title, String subtitle, Node listView) {
        requireNonNull(title);
        requireNonNull(subtitle);
        requireNonNull(listView);

        getStyleClass().add("list-workspace-page");
        setSpacing(16);

        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("list-page-title");
        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.getStyleClass().add("list-page-subtitle");

        VBox records = new VBox(listView);
        records.getStyleClass().add("list-page-records");
        VBox.setVgrow(records, Priority.ALWAYS);
        VBox.setVgrow(listView, Priority.ALWAYS);

        getChildren().addAll(titleLabel, subtitleLabel, records);
    }

    /** Moves the command panel below this page's records. */
    public void showCommandPanel(Node commandPanel) {
        if (commandPanel.getParent() instanceof Pane parent) {
            parent.getChildren().remove(commandPanel);
        }
        getChildren().add(commandPanel);
    }
}
