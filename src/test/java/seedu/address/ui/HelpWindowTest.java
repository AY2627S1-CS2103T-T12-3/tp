package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HelpWindowTest {

    @Test
    public void userGuideUrl_pointsToInternTrackUserGuide() {
        assertEquals("https://ay2627s1-cs2103t-t12-3.github.io/tp/UserGuide.html", HelpWindow.USERGUIDE_URL);
    }
}
