package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seedu.address.model.application.Application;
import seedu.address.model.application.ApplicationStatus;
import seedu.address.model.person.Person;

/**
 * A live overview of applications and contacts in InternTrack.
 */
public class DashboardPanel extends VBox {
    private final ObservableList<Application> applications;
    private final ObservableList<Person> persons;
    private final Label activeApplicationCount = new Label();
    private final Label interviewingCount = new Label();
    private final HBox contactCards = new HBox(10);
    private final ScrollPane contactCardsScrollPane = new ScrollPane(contactCards);

    /**
     * Creates a dashboard that updates when the supplied application or contact lists change.
     */
    public DashboardPanel(ObservableList<Application> applications, ObservableList<Person> persons) {
        this.applications = requireNonNull(applications);
        this.persons = requireNonNull(persons);

        getStyleClass().add("dashboard");
        setSpacing(16);
        setFillWidth(true);

        getChildren().addAll(createGreeting(), createSummaryCards(), createMainContent());
        applications.addListener((ListChangeListener<Application>) change -> refresh());
        persons.addListener((ListChangeListener<Person>) change -> refresh());
        refresh();
    }

    private VBox createGreeting() {
        Label title = new Label("Welcome to InternTrack");
        title.getStyleClass().add("dashboard-greeting");

        Label subtitle = new Label("Keep your internship search moving.");
        subtitle.getStyleClass().add("dashboard-subtitle");
        return new VBox(3, title, subtitle);
    }

    private HBox createSummaryCards() {
        HBox cards = new HBox(16,
                createSummaryCard("ACTIVE APPLICATIONS", activeApplicationCount, "active"),
                createSummaryCard("INTERVIEWING", interviewingCount, "interviewing"));
        cards.getStyleClass().add("summary-cards");
        return cards;
    }

    private VBox createSummaryCard(String heading, Label count, String styleSuffix) {
        Label headingLabel = new Label(heading);
        headingLabel.getStyleClass().add("summary-heading");
        count.getStyleClass().add("summary-count");

        Label suffix = new Label("saved records");
        suffix.getStyleClass().add("summary-suffix");
        HBox countRow = new HBox(12, count, suffix);
        countRow.setAlignment(Pos.BASELINE_LEFT);

        VBox card = new VBox(8, headingLabel, countRow);
        card.getStyleClass().addAll("summary-card", "summary-card-" + styleSuffix);
        card.setPadding(new Insets(16));
        HBox.setHgrow(card, Priority.ALWAYS);
        card.setMaxWidth(Double.MAX_VALUE);
        return card;
    }

    private VBox createMainContent() {
        VBox applicationsPanel = createApplicationsPanel();
        VBox contactsPanel = createContactsPanel();

        VBox content = new VBox(16, applicationsPanel, contactsPanel);
        content.getStyleClass().add("dashboard-main-content");
        VBox.setVgrow(content, Priority.ALWAYS);
        return content;
    }

    private VBox createApplicationsPanel() {
        Label title = new Label("Applications");
        title.getStyleClass().add("panel-title");
        Label subtitle = new Label("Your current internship and graduate role applications");
        subtitle.getStyleClass().add("panel-subtitle");

        TableView<Application> applicationTable = new TableView<>(applications);
        applicationTable.getStyleClass().add("dashboard-application-table");
        applicationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        applicationTable.addEventHandler(ScrollEvent.SCROLL, event -> event.consume());
        applicationTable.setPlaceholder(createPlaceholder("No applications yet. Use addapp to create one."));
        applicationTable.getColumns().add(createRoleColumn());
        applicationTable.getColumns().add(createCompanyColumn());
        applicationTable.getColumns().add(createStatusColumn());
        applicationTable.getColumns().add(createDateColumn());
        applicationTable.setMinHeight(210);
        applicationTable.setPrefHeight(260);
        VBox.setVgrow(applicationTable, Priority.ALWAYS);

        VBox panel = new VBox(4, title, subtitle, applicationTable);
        panel.getStyleClass().add("dashboard-applications-panel");
        panel.setPadding(new Insets(18));
        panel.setMaxWidth(Double.MAX_VALUE);
        return panel;
    }

    private VBox createContactsPanel() {
        Label title = new Label("Contacts");
        title.getStyleClass().add("panel-title");
        Label subtitle = new Label("People in your internship network");
        subtitle.getStyleClass().add("panel-subtitle");

        contactCards.getStyleClass().add("dashboard-contact-cards");
        contactCardsScrollPane.getStyleClass().add("dashboard-contact-scroll-pane");
        contactCardsScrollPane.setFitToHeight(false);
        contactCardsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        contactCardsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        contactCardsScrollPane.setPannable(true);
        contactCardsScrollPane.setMinViewportHeight(190);

        VBox panel = new VBox(7, title, subtitle, contactCardsScrollPane);
        panel.getStyleClass().add("dashboard-contacts-panel");
        panel.setPadding(new Insets(18));
        return panel;
    }

    private TableColumn<Application, String> createRoleColumn() {
        TableColumn<Application, String> column = new TableColumn<>("ROLE");
        column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName().value));
        column.setPrefWidth(210);
        return column;
    }

    private TableColumn<Application, String> createCompanyColumn() {
        TableColumn<Application, String> column = new TableColumn<>("COMPANY");
        column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCompany().value));
        column.setPrefWidth(150);
        return column;
    }

    private TableColumn<Application, String> createStatusColumn() {
        TableColumn<Application, String> column = new TableColumn<>("STATUS");
        column.setCellValueFactory(data -> new ReadOnlyStringWrapper(formatStatus(data.getValue().getStatus())));
        column.setCellFactory(ignored -> new TableCell<>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setGraphic(null);
                } else {
                    Label badge = new Label(status);
                    badge.getStyleClass().addAll("status-badge", "status-badge-"
                            + status.toLowerCase(Locale.ROOT).replace(' ', '-'));
                    setGraphic(badge);
                }
            }
        });
        column.setPrefWidth(130);
        return column;
    }

    private TableColumn<Application, String> createDateColumn() {
        TableColumn<Application, String> column = new TableColumn<>("APPLIED");
        column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDate().value));
        column.setPrefWidth(115);
        return column;
    }

    private void refresh() {
        activeApplicationCount.setText(String.valueOf(countActiveApplications()));
        interviewingCount.setText(String.valueOf(countWithStatus(ApplicationStatus.INTERVIEWING)));
        refreshContactCards();
    }

    private long countActiveApplications() {
        return applications.stream()
                .filter(application -> application.getStatus() != ApplicationStatus.REJECTED)
                .filter(application -> application.getStatus() != ApplicationStatus.WITHDRAWN)
                .count();
    }

    private long countWithStatus(ApplicationStatus status) {
        return applications.stream().filter(application -> application.getStatus() == status).count();
    }

    private void refreshContactCards() {
        List<Person> recentPersons = persons.reversed().stream().toList();
        contactCards.getChildren().setAll(recentPersons.stream()
                .map(person -> createContactCard(person, recentPersons.indexOf(person) + 1)).toList());
        if (recentPersons.isEmpty()) {
            contactCards.getChildren().setAll(createPlaceholder("No contacts yet. Use add to create one."));
        }
    }

    private VBox createContactCard(Person person, int displayedIndex) {
        Label index = new Label(displayedIndex + ". ");
        index.getStyleClass().add("contact-card-index");
        Label name = new Label(person.getName().fullName);
        name.getStyleClass().add("contact-card-name");

        HBox header = new HBox(index, name);
        header.getStyleClass().add("contact-card-header");

        FlowPane tags = new FlowPane();
        tags.getStyleClass().add("overview-contact-tags");
        person.getTags().stream().sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        VBox details = new VBox(5);
        person.getCompany().ifPresent(company -> details.getChildren()
                .add(createContactDetail("Company", company.value)));
        person.getRole().ifPresent(role -> details.getChildren().add(createContactDetail("Role", role.value)));
        details.getChildren().add(createContactDetail("Email", person.getEmail().value));
        details.getChildren().add(createContactDetail("Phone", person.getPhone().value));
        person.getWhereMet().ifPresent(whereMet -> details.getChildren()
                .add(createContactDetail("Where met", whereMet.value)));
        details.getChildren().add(createContactDetail("Address", person.getAddress().value));
        if (!person.getRemark().value.isBlank()) {
            details.getChildren().add(createContactDetail("Remark", person.getRemark().value));
        }

        VBox card = new VBox(7, header, tags, details);
        card.getStyleClass().add("contact-card");
        card.setPadding(new Insets(12, 12, 48, 12));
        return card;
    }

    private static Label createContactDetail(String label, String value) {
        Label detail = new Label(label + ": " + value);
        detail.getStyleClass().add("contact-card-detail");
        detail.setMaxWidth(250);
        detail.setWrapText(true);
        return detail;
    }

    /** Moves the command panel below the summary cards. */
    public void showCommandPanel(javafx.scene.Node commandPanel) {
        moveToThisPanel(commandPanel, 2);
    }

    private void moveToThisPanel(javafx.scene.Node commandPanel, int index) {
        if (commandPanel.getParent() instanceof Pane parent) {
            parent.getChildren().remove(commandPanel);
        }
        getChildren().add(index, commandPanel);
    }

    private static Label createPlaceholder(String text) {
        Label placeholder = new Label(text);
        placeholder.getStyleClass().add("dashboard-placeholder");
        placeholder.setWrapText(true);
        return placeholder;
    }

    private static String formatStatus(ApplicationStatus status) {
        String normalized = status.name().toLowerCase(Locale.ROOT).replace('_', ' ');
        return Character.toUpperCase(normalized.charAt(0)) + normalized.substring(1);
    }

}
