package seedu.address.ui;

import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResult.DisplayedList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final KeyCombination HELP_ACCELERATOR = KeyCombination.valueOf("F1");
    private static final KeyCombination QUICK_SEARCH_ACCELERATOR = new KeyCodeCombination(
            KeyCode.F, KeyCombination.SHORTCUT_DOWN);

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private Path dataFilePath;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ApplicationListPanel applicationListPanel;
    private ApplicationCalendarPanel applicationCalendarPanel;
    private DashboardPanel dashboardPanel;
    private ScrollPane dashboardScrollPane;
    private WorkspaceListPanel contactsPage;
    private WorkspaceListPanel applicationsPage;
    private WorkspaceListPanel calendarPage;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private CommandBox commandBox;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox commandPanel;

    @FXML
    private Button overviewButton;

    @FXML
    private Button applicationsButton;

    @FXML
    private Button contactsButton;

    @FXML
    private Button calendarButton;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage}, {@code Logic},
     * and the data file path to show in the status bar.
     */
    public MainWindow(Stage primaryStage, Logic logic, Path dataFilePath) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.dataFilePath = dataFilePath;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (HELP_ACCELERATOR.match(event)) {
                handleHelp();
                event.consume();
            } else if (isQuickSearchShortcut(event) && commandBox != null) {
                commandBox.focusForQuickSearch();
                event.consume();
            }
        });
    }

    /**
     * Returns whether {@code event} is the platform's quick-search shortcut.
     */
    static boolean isQuickSearchShortcut(KeyEvent event) {
        return QUICK_SEARCH_ACCELERATOR.match(event);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        applicationListPanel = new ApplicationListPanel(logic.getApplicationList());
        applicationCalendarPanel = new ApplicationCalendarPanel(logic.getApplicationList());
        dashboardPanel = new DashboardPanel(logic.getApplicationList(), logic.getPersonList());
        dashboardPanel.setMinHeight(Region.USE_PREF_SIZE);
        dashboardScrollPane = new ScrollPane(dashboardPanel);
        dashboardScrollPane.setFitToWidth(true);
        dashboardScrollPane.setFitToHeight(false);
        dashboardScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        dashboardScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        dashboardScrollPane.setPannable(true);
        dashboardScrollPane.getStyleClass().add("dashboard-scroll-pane");
        contactsPage = new WorkspaceListPanel("Contacts", "People and organisations in your internship network.",
                personListPanel.getRoot());
        applicationsPage = new WorkspaceListPanel("Applications",
                "Your current internship and graduate role applications.", applicationListPanel.getRoot());
        calendarPage = new WorkspaceListPanel("Application calendar",
                "Review application dates one week at a time.", applicationCalendarPanel);
        personListPanelPlaceholder.getChildren().add(dashboardScrollPane);

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(dataFilePath);
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        dashboardPanel.showCommandPanel(commandPanel);
        setActiveNavigation(overviewButton);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /** Shows the workspace overview. */
    @FXML
    public void showOverview() {
        dashboardPanel.showCommandPanel(commandPanel);
        showListPanel(dashboardScrollPane);
        setActiveNavigation(overviewButton);
    }

    /** Shows the contacts list. */
    @FXML
    public void showContacts() {
        contactsPage.showCommandPanel(commandPanel);
        showListPanel(contactsPage);
        setActiveNavigation(contactsButton);
    }

    /** Shows the applications list. */
    @FXML
    public void showApplications() {
        applicationsPage.showCommandPanel(commandPanel);
        showListPanel(applicationsPage);
        setActiveNavigation(applicationsButton);
    }

    /** Shows the weekly application calendar. */
    @FXML
    public void showApplicationCalendar() {
        calendarPage.showCommandPanel(commandPanel);
        showListPanel(calendarPage);
        setActiveNavigation(calendarButton);
    }

    /** Prepares the command box for adding an application. */
    @FXML
    public void prepareApplicationCreation() {
        commandBox.focusForApplicationCreation();
    }

    /** Prepares the command box for adding a contact. */
    @FXML
    public void prepareContactCreation() {
        commandBox.focusForContactCreation();
    }

    /** Focuses the command box and prepares a contact search. */
    @FXML
    public void prepareContactSearch() {
        commandBox.focusForQuickSearch();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.getDisplayedList() == DisplayedList.CONTACTS) {
                showContacts();
            } else if (commandResult.getDisplayedList() == DisplayedList.APPLICATIONS
                    && !(isCalendarVisible() && isApplicationMutation(commandText))) {
                showApplications();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    private void showListPanel(Node panel) {
        personListPanelPlaceholder.getChildren().setAll(panel);
    }

    private boolean isCalendarVisible() {
        return personListPanelPlaceholder.getChildren().contains(calendarPage);
    }

    private static boolean isApplicationMutation(String commandText) {
        return commandText.trim().matches("^(addapp|editapp|deleteapp)(\\s.*)?$");
    }

    private void setActiveNavigation(Button activeButton) {
        overviewButton.getStyleClass().remove("navigation-button-active");
        applicationsButton.getStyleClass().remove("navigation-button-active");
        contactsButton.getStyleClass().remove("navigation-button-active");
        calendarButton.getStyleClass().remove("navigation-button-active");
        activeButton.getStyleClass().add("navigation-button-active");
    }
}
