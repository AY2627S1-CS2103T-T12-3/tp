package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainWindowTest {

    @Test
    public void isQuickSearchShortcut_withPlatformModifier_matches() {
        boolean isMacOs = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac");
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "f", "f", KeyCode.F,
                false, !isMacOs, false, isMacOs);

        assertTrue(MainWindow.isQuickSearchShortcut(event));
    }

    @Test
    public void isQuickSearchShortcut_withoutModifier_doesNotMatch() {
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "f", "f", KeyCode.F,
                false, false, false, false);

        assertFalse(MainWindow.isQuickSearchShortcut(event));
    }

    @Test
    public void isQuickSearchShortcut_withDifferentKey_doesNotMatch() {
        boolean isMacOs = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac");
        KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "g", "g", KeyCode.G,
                false, !isMacOs, false, isMacOs);

        assertFalse(MainWindow.isQuickSearchShortcut(event));
    }
}
