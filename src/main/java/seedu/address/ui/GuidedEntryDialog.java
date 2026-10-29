package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A keyboard-friendly, one-field-at-a-time way to create a contact or application.
 */
class GuidedEntryDialog {
    private static final String[] CONTACT_LABELS = {
        "Name", "Company", "Role", "Email", "Phone", "Where met (optional)"
    };
    private static final String[] CONTACT_PREFIXES = {"n/", "c/", "r/", "e/", "p/", "w/"};
    private static final String[] APPLICATION_LABELS = {
        "Role or application name", "Company", "Description", "Date (YYYY-MM-DD)", "Status"
    };
    private static final String[] APPLICATION_PREFIXES = {"n/", "c/", "d/", "dt/", "s/"};
    private static final ButtonType BACK = new ButtonType("Back", ButtonData.BACK_PREVIOUS);
    private static final ButtonType NEXT = new ButtonType("Next", ButtonData.NEXT_FORWARD);

    private final CommandBox.CommandExecutor executor;
    private final Dialog<Void> dialog = new Dialog<>();
    private final ComboBox<String> kind = new ComboBox<>();
    private final ComboBox<String> status = new ComboBox<>();
    private final TextField input = new TextField();
    private final Label prompt = new Label();
    private final Label error = new Label();
    private final List<String> answers = new ArrayList<>();
    private int step = -1;

    GuidedEntryDialog(CommandBox.CommandExecutor executor, Node owner) {
        this.executor = executor;
        dialog.setTitle("Guided entry");
        dialog.initOwner(owner.getScene().getWindow());
        kind.getItems().addAll("Contact", "Application");
        kind.getSelectionModel().selectFirst();
        status.getItems().addAll("APPLIED", "INTERVIEWING", "OFFERED", "REJECTED", "WITHDRAWN");
        status.getSelectionModel().selectFirst();

        dialog.getDialogPane().getButtonTypes().addAll(BACK, NEXT, ButtonType.CANCEL);
        Button back = (Button) dialog.getDialogPane().lookupButton(BACK);
        Button next = (Button) dialog.getDialogPane().lookupButton(NEXT);
        back.addEventFilter(ActionEvent.ACTION, event -> {
            showPrevious();
            event.consume();
        });
        next.addEventFilter(ActionEvent.ACTION, event -> {
            showNext();
            event.consume();
        });
        input.setOnAction(event -> showNext());
        kind.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                showNext();
                event.consume();
            }
        });
        status.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                showNext();
                event.consume();
            }
        });
        kind.setOnAction(event -> {
            answers.clear();
            render();
        });

        VBox contents = new VBox(10, prompt, kind, input, status, error);
        contents.setPrefWidth(420);
        dialog.getDialogPane().setContent(contents);
        dialog.setOnShown(event -> kind.requestFocus());
        render();
    }

    void show() {
        dialog.showAndWait();
    }

    private boolean isApplication() {
        return "Application".equals(kind.getValue());
    }

    private String[] labels() {
        return isApplication() ? APPLICATION_LABELS : CONTACT_LABELS;
    }

    private void render() {
        kind.setVisible(step == -1);
        kind.setManaged(step == -1);
        input.setVisible(step >= 0 && !isStatusStep());
        input.setManaged(step >= 0 && !isStatusStep());
        status.setVisible(isStatusStep());
        status.setManaged(isStatusStep());
        prompt.setText(step == -1 ? "Choose what to add. Use arrow keys, then Enter."
                : (step + 1) + " / " + labels().length + ": " + labels()[step]);
        error.setText("");
        Button back = (Button) dialog.getDialogPane().lookupButton(BACK);
        Button next = (Button) dialog.getDialogPane().lookupButton(NEXT);
        back.setDisable(step == -1);
        next.setText(step == labels().length - 1 ? "Add" : "Next");
        if (step == -1) {
            kind.requestFocus();
        } else if (isStatusStep()) {
            status.requestFocus();
        } else {
            input.setText(step < answers.size() ? answers.get(step) : "");
            input.requestFocus();
        }
    }

    private boolean isStatusStep() {
        return isApplication() && step == APPLICATION_LABELS.length - 1;
    }

    private void saveAnswer() {
        if (step < 0) {
            return;
        }
        String value = isStatusStep() ? status.getValue() : input.getText().trim();
        while (answers.size() <= step) {
            answers.add("");
        }
        answers.set(step, value);
    }

    private void showPrevious() {
        saveAnswer();
        step--;
        render();
    }

    private void showNext() {
        saveAnswer();
        if (step < labels().length - 1) {
            step++;
            render();
            return;
        }

        String command = buildCommand(isApplication(), answers);
        try {
            executor.execute(command);
            dialog.close();
        } catch (CommandException | ParseException exception) {
            error.setText(exception.getMessage());
        }
    }

    /** Builds a regular CLI command so the normal parser validates all fields. */
    static String buildCommand(boolean application, List<String> values) {
        String[] prefixes = application ? APPLICATION_PREFIXES : CONTACT_PREFIXES;
        StringBuilder command = new StringBuilder(application ? "addapp" : "add");
        for (int i = 0; i < prefixes.length && i < values.size(); i++) {
            if (!values.get(i).isBlank()) {
                command.append(' ').append(prefixes[i]).append(values.get(i).trim());
            }
        }
        return command.toString();
    }
}
