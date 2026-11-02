package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps commands entered in the current session and the draft being edited.
 */
class CommandHistory {
    private static final int MAX_COMMANDS = 100;

    private final List<String> commands = new ArrayList<>();
    private int cursor;
    private String draft = "";

    void record(String command) {
        commands.add(command);
        if (commands.size() > MAX_COMMANDS) {
            commands.remove(0);
        }
        cursor = commands.size();
        draft = "";
    }

    String previous(String currentText) {
        if (commands.isEmpty() || cursor == 0) {
            return currentText;
        }
        if (cursor == commands.size()) {
            draft = currentText;
        }
        cursor--;
        return commands.get(cursor);
    }

    String next(String currentText) {
        if (cursor == commands.size()) {
            return currentText;
        }
        cursor++;
        return cursor == commands.size() ? draft : commands.get(cursor);
    }
}
