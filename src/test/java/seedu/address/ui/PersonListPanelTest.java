package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PersonListPanelTest {

    @Test
    public void getEmptyListMessage_noSearchTerm_suggestsAddingContact() {
        assertEquals(PersonListPanel.EMPTY_LIST_MESSAGE, PersonListPanel.getEmptyListMessage(false));
    }

    @Test
    public void getEmptyListMessage_activeSearchTerm_suggestsListingAllContacts() {
        assertEquals(PersonListPanel.NO_MATCHING_CONTACTS_MESSAGE, PersonListPanel.getEmptyListMessage(true));
    }
}
