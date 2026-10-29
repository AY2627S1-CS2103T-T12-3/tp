package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WHERE_MET_DESC_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.PRODUCT_MANAGER_INTERN;
import static seedu.address.testutil.TypicalApplications.SOFTWARE_ENGINEER_INTERN;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddApplicationCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResult.ContactSearchAction;
import seedu.address.logic.commands.CommandResult.DisplayedList;
import seedu.address.logic.commands.EditApplicationCommand;
import seedu.address.logic.commands.ListApplicationCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationDate;
import seedu.address.model.application.ApplicationName;
import seedu.address.model.application.ApplicationStatus;
import seedu.address.model.application.Description;
import seedu.address.model.person.Company;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;
    private Path addressBookFilePath;

    @BeforeEach
    public void setUp() {
        addressBookFilePath = temporaryFolder.resolve("addressBook.json");
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(addressBookFilePath);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_EMPTY, model);
    }

    @Test
    public void execute_addApplicationCommand_success() throws Exception {
        Application application = new Application(new ApplicationName("Software Engineer Intern"),
                new Company("Tech Corp"), new Description("Applied through the company careers page."),
                new ApplicationDate("2026-10-07"));
        Model expectedModel = new ModelManager();
        expectedModel.addApplication(application);
        String command = AddApplicationCommand.COMMAND_WORD + " n/Software Engineer Intern c/Tech Corp "
                + "d/Applied through the company careers page. dt/2026-10-07";
        String expectedMessage = String.format(AddApplicationCommand.MESSAGE_SUCCESS, Messages.format(application));

        assertCommandSuccess(command, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addApplicationCommandWithStatus_success() throws Exception {
        Application application = new Application(new ApplicationName("Software Engineer Intern"),
                new Company("Tech Corp"), new Description("Applied through the company careers page."),
                new ApplicationDate("2026-10-07"), ApplicationStatus.INTERVIEWING);
        Model expectedModel = new ModelManager();
        expectedModel.addApplication(application);
        String command = AddApplicationCommand.COMMAND_WORD + " n/Software Engineer Intern c/Tech Corp "
                + "d/Applied through the company careers page. dt/2026-10-07 s/interviewing";
        String expectedMessage = String.format(AddApplicationCommand.MESSAGE_SUCCESS, Messages.format(application));

        assertCommandSuccess(command, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listApplicationCommand_success() throws Exception {
        Application application = new Application(new ApplicationName("Software Engineer Intern"),
                new Company("Tech Corp"), new Description("Applied through the company careers page."),
                new ApplicationDate("2026-10-07"));
        model.addApplication(application);

        String expectedMessage = String.format(ListApplicationCommand.MESSAGE_SUCCESS, 1);
        assertCommandSuccess(ListApplicationCommand.COMMAND_WORD, expectedMessage, model);
    }

    @Test
    public void execute_findFromContactList_showsMatchingContacts() throws Exception {
        model.addPerson(AMY);
        CommandResult listResult = logic.execute(ListCommand.COMMAND_WORD);
        assertEquals(DisplayedList.CONTACTS, listResult.getDisplayedList());

        CommandResult findResult = logic.execute("find Amy");

        assertEquals(DisplayedList.CONTACTS, findResult.getDisplayedList());
        assertEquals(List.of(AMY), logic.getFilteredPersonList());
    }

    @Test
    public void execute_editFilteredContact_nextIndexCommandTargetsSameContact() throws Exception {
        model.addPerson(AMY);
        logic.execute("find Amy");

        CommandResult editResult = logic.execute("edit 1 a/42 Alpha Street");

        assertEquals(ContactSearchAction.UNCHANGED, editResult.getContactSearchAction());
        assertEquals(1, logic.getFilteredPersonList().size());
        logic.execute("remark 1 r/Follow up");

        Person editedPerson = logic.getPersonList().get(0);
        assertEquals("42 Alpha Street", editedPerson.getAddress().value);
        assertEquals("Follow up", editedPerson.getRemark().value);
    }

    @Test
    public void execute_findFromApplicationList_switchesToMatchingContacts() throws Exception {
        model.addPerson(AMY);
        CommandResult listApplicationResult = logic.execute(ListApplicationCommand.COMMAND_WORD);
        assertEquals(DisplayedList.APPLICATIONS, listApplicationResult.getDisplayedList());

        CommandResult findResult = logic.execute("find Amy");

        assertEquals(DisplayedList.CONTACTS, findResult.getDisplayedList());
        assertEquals(List.of(AMY), logic.getFilteredPersonList());
    }

    @Test
    public void execute_findWithNoResultsFromApplicationList_switchesToEmptyContactList() throws Exception {
        model.addPerson(AMY);
        CommandResult listApplicationResult = logic.execute(ListApplicationCommand.COMMAND_WORD);
        assertEquals(DisplayedList.APPLICATIONS, listApplicationResult.getDisplayedList());

        CommandResult findResult = logic.execute("find Missing");

        assertEquals(DisplayedList.CONTACTS, findResult.getDisplayedList());
        assertEquals(List.of(), logic.getFilteredPersonList());
    }

    @Test
    public void execute_addApplicationThenReloadAndList_applicationPreserved() throws Exception {
        Application application = new Application(new ApplicationName("Software Engineer Intern"),
                new Company("Tech Corp"), new Description("Applied through the company careers page."),
                new ApplicationDate("2026-10-07"));
        String addCommand = AddApplicationCommand.COMMAND_WORD + " n/Software Engineer Intern c/Tech Corp "
                + "d/Applied through the company careers page. dt/2026-10-07";
        logic.execute(addCommand);

        JsonAddressBookStorage reloadedAddressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
        ReadOnlyAddressBook reloadedAddressBook = reloadedAddressBookStorage.readAddressBook().orElseThrow();
        Model reloadedModel = new ModelManager(reloadedAddressBook, new UserPrefs());
        StorageManager reloadedStorage = new StorageManager(reloadedAddressBookStorage,
                new JsonUserPrefsStorage(temporaryFolder.resolve("reloadedUserPrefs.json")));
        Logic reloadedLogic = new LogicManager(reloadedModel, reloadedStorage);

        assertEquals(application, reloadedLogic.getApplicationList().get(0));
        assertEquals(String.format(ListApplicationCommand.MESSAGE_SUCCESS, 1),
                reloadedLogic.execute(ListApplicationCommand.COMMAND_WORD).getFeedbackToUser());
    }

    @Test
    public void execute_addApplicationCommandWithInvalidDate_throwsParseException() {
        String command = AddApplicationCommand.COMMAND_WORD + " n/Software Engineer Intern c/Tech Corp "
                + "d/Applied through the company careers page. dt/2026-02-30";

        assertParseException(command, ApplicationDate.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_readOnlyCommands_succeedWithoutSaving() throws Exception {
        model.addPerson(AMY);
        JsonAddressBookStorage readOnlyStorage = new JsonAddressBookStorage(addressBookFilePath) {
            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
                throw DUMMY_AD_EXCEPTION;
            }
        };
        logic = new LogicManager(model, new StorageManager(readOnlyStorage,
                new JsonUserPrefsStorage(temporaryFolder.resolve("ReadOnlyUserPrefs.json"))));

        assertEquals(DisplayedList.CONTACTS, logic.execute("list").getDisplayedList());
        assertEquals(DisplayedList.CONTACTS, logic.execute("find Amy").getDisplayedList());
        assertEquals(List.of(AMY), logic.getFilteredPersonList());
    }

    @Test
    public void execute_failedSave_restoresModelBeforeLaterSuccessfulSave() throws Exception {
        AtomicInteger saveAttempts = new AtomicInteger();
        JsonAddressBookStorage failingOnceStorage = new JsonAddressBookStorage(addressBookFilePath) {
            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
                if (saveAttempts.getAndIncrement() == 0) {
                    throw DUMMY_AD_EXCEPTION;
                }
                super.saveAddressBook(addressBook);
            }
        };
        logic = new LogicManager(model, new StorageManager(failingOnceStorage,
                new JsonUserPrefsStorage(temporaryFolder.resolve("FailingOnceUserPrefs.json"))));
        String failedAdd = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + COMPANY_DESC_AMY + ROLE_DESC_AMY + WHERE_MET_DESC_AMY;

        assertThrows(CommandException.class, () -> logic.execute(failedAdd));
        assertEquals(List.of(), logic.getPersonList());

        logic.execute("add n/Bob Example c/Example r/Recruiter e/bob@example.com p/91234567");
        ReadOnlyAddressBook saved = new JsonAddressBookStorage(addressBookFilePath).readAddressBook().orElseThrow();
        assertEquals(1, saved.getPersonList().size());
        assertEquals("Bob Example", saved.getPersonList().get(0).getName().fullName);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    @Test
    public void getApplicationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getApplicationList().add(null));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with a JsonAddressBookStorage that throws the IOException e when saving
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(prefPath) {
            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveAddressBook method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + COMPANY_DESC_AMY + ROLE_DESC_AMY + WHERE_MET_DESC_AMY;
        ModelManager expectedModel = new ModelManager();
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void execute_editAndDeleteApplicationsFromContacts_persistsAndRefreshesList() throws Exception {
        model.addPerson(AMY);
        model.addApplication(SOFTWARE_ENGINEER_INTERN);
        model.addApplication(PRODUCT_MANAGER_INTERN);
        ObservableList<Application> applications = logic.getApplicationList();
        logic.execute("list");

        CommandResult editResult = logic.execute("editapp 2 d/Interview scheduled. dt/2026-10-15 s/interviewing");
        Application edited = new Application(PRODUCT_MANAGER_INTERN.getName(), PRODUCT_MANAGER_INTERN.getCompany(),
                new Description("Interview scheduled."), new ApplicationDate("2026-10-15"),
                ApplicationStatus.INTERVIEWING);
        assertEquals(DisplayedList.APPLICATIONS, editResult.getDisplayedList());
        assertEquals(List.of(SOFTWARE_ENGINEER_INTERN, edited), applications);
        JsonAddressBookStorage storage = new JsonAddressBookStorage(addressBookFilePath);
        ReadOnlyAddressBook afterEdit = storage.readAddressBook().orElseThrow();
        assertEquals(List.of(SOFTWARE_ENGINEER_INTERN, edited), afterEdit.getApplicationList());
        assertEquals(List.of(AMY), afterEdit.getPersonList());

        logic.execute("list");
        CommandResult deleteResult = logic.execute("deleteapp 1");
        assertEquals(DisplayedList.APPLICATIONS, deleteResult.getDisplayedList());
        assertEquals(List.of(edited), applications);
        ReadOnlyAddressBook afterDelete = storage.readAddressBook().orElseThrow();
        Model reloadedModel = new ModelManager(afterDelete, new UserPrefs());
        assertEquals(List.of(edited), reloadedModel.getAddressBook().getApplicationList());
        assertEquals(List.of(AMY), reloadedModel.getAddressBook().getPersonList());

        logic.execute("deleteapp 1");
        assertEquals(List.of(), applications);
        assertEquals(List.of(), storage.readAddressBook().orElseThrow().getApplicationList());
    }

    @Test
    public void execute_editAndDeleteContacts_persistsAndRefreshesList() throws Exception {
        model.addPerson(AMY);
        model.addApplication(SOFTWARE_ENGINEER_INTERN);
        ObservableList<Person> contacts = logic.getFilteredPersonList();
        logic.execute("listapp");

        CommandResult editResult = logic.execute("edit 1 c/Meta, Inc. r/Software Engineer w/LinkedIn");
        Person edited = new PersonBuilder(AMY).withCompany("Meta, Inc.").withRole("Software Engineer")
                .withWhereMet("LinkedIn").build();
        assertEquals(DisplayedList.CONTACTS, editResult.getDisplayedList());
        assertEquals(List.of(edited), contacts);

        JsonAddressBookStorage storage = new JsonAddressBookStorage(addressBookFilePath);
        assertEquals(List.of(edited), storage.readAddressBook().orElseThrow().getPersonList());
        assertEquals(List.of(SOFTWARE_ENGINEER_INTERN), storage.readAddressBook().orElseThrow().getApplicationList());

        logic.execute("listapp");
        CommandResult deleteResult = logic.execute("delete 1");
        assertEquals(DisplayedList.CONTACTS, deleteResult.getDisplayedList());
        assertEquals(List.of(), contacts);
        assertEquals(List.of(), storage.readAddressBook().orElseThrow().getPersonList());
        assertEquals(List.of(SOFTWARE_ENGINEER_INTERN), storage.readAddressBook().orElseThrow().getApplicationList());
    }

    @Test
    public void execute_invalidApplicationCommands_preserveMemoryAndSavedFile() throws Exception {
        model.addApplication(SOFTWARE_ENGINEER_INTERN);
        model.addApplication(PRODUCT_MANAGER_INTERN);
        new JsonAddressBookStorage(addressBookFilePath).saveAddressBook(model.getAddressBook());
        logic.execute("listapp");
        String savedData = Files.readString(addressBookFilePath);
        for (String command : List.of("editapp 0 d/Updated", "editapp 1", "editapp 1 dt/2026-02-30",
                "editapp 1 n/", "editapp 1 s/pending", "deleteapp 1 2", "deleteapp -1")) {
            assertThrows(ParseException.class, () -> logic.execute(command));
            assertEquals(savedData, Files.readString(addressBookFilePath));
            assertEquals(List.of(SOFTWARE_ENGINEER_INTERN, PRODUCT_MANAGER_INTERN), logic.getApplicationList());
        }
        for (String command : List.of("editapp 3 d/Updated", "deleteapp 3")) {
            assertCommandException(command, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
            assertEquals(savedData, Files.readString(addressBookFilePath));
        }
        assertCommandException("editapp 1 n/product manager intern c/other corp",
                EditApplicationCommand.MESSAGE_DUPLICATE_APPLICATION);
        assertEquals(savedData, Files.readString(addressBookFilePath));
    }
}
