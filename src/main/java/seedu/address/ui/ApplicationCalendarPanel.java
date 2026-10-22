package seedu.address.ui;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import seedu.address.model.application.Application;

/** A weekly calendar that displays applications on their recorded application dates. */
public class ApplicationCalendarPanel extends VBox {
    private static final DateTimeFormatter DAY_MONTH_FORMAT = DateTimeFormatter.ofPattern("dd/MM");
    private static final DateTimeFormatter FULL_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String MUTED_TEXT_STYLE = "-fx-text-fill: #a0a0a0;";
    private static final String DAY_COLUMN_STYLE = "-fx-background-color: #292929;"
            + "-fx-border-color: #4d4d4d; -fx-border-width: 1;";

    private final ObservableList<Application> applications;
    private final Label dateRangeLabel = new Label();
    private final HBox weekColumns = new HBox(8);
    private LocalDate weekStart;

    /** Creates a calendar panel backed by the application's live application list. */
    public ApplicationCalendarPanel(ObservableList<Application> applications) {
        this.applications = applications;
        weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        setSpacing(10);
        setPadding(new Insets(12));
        setStyle("-fx-background-color: #1f1f1f;");

        Button previousWeekButton = new Button("‹");
        previousWeekButton.setAccessibleText("Previous week");
        previousWeekButton.setOnAction(event -> changeWeek(-1));

        Button nextWeekButton = new Button("›");
        nextWeekButton.setAccessibleText("Next week");
        nextWeekButton.setOnAction(event -> changeWeek(1));

        dateRangeLabel.setStyle("-fx-font-size: 20pt; -fx-text-fill: white;");
        HBox header = new HBox(16, previousWeekButton, dateRangeLabel, nextWeekButton);
        header.setStyle("-fx-alignment: center;");

        weekColumns.setFillHeight(true);
        ScrollPane weekScrollPane = new ScrollPane(weekColumns);
        weekScrollPane.setFitToHeight(true);
        weekScrollPane.setFitToWidth(false);
        weekScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        weekScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(weekScrollPane, Priority.ALWAYS);

        getChildren().addAll(header, weekScrollPane);
        applications.addListener((ListChangeListener<Application>) change -> renderWeek());
        renderWeek();
    }

    private void changeWeek(int weekOffset) {
        weekStart = weekStart.plusWeeks(weekOffset);
        renderWeek();
    }

    private void renderWeek() {
        LocalDate weekEnd = weekStart.plusDays(6);
        String range = weekStart.getYear() == weekEnd.getYear()
                ? DAY_MONTH_FORMAT.format(weekStart) + " – " + DAY_MONTH_FORMAT.format(weekEnd)
                : FULL_DATE_FORMAT.format(weekStart) + " – " + FULL_DATE_FORMAT.format(weekEnd);
        dateRangeLabel.setText(range);

        weekColumns.getChildren().clear();
        for (int dayOffset = 0; dayOffset < 7; dayOffset++) {
            LocalDate date = weekStart.plusDays(dayOffset);
            weekColumns.getChildren().add(createDayColumn(date));
        }
    }

    private VBox createDayColumn(LocalDate date) {
        String weekday = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
        Label dayHeading = new Label(weekday.toUpperCase(Locale.getDefault()) + "  "
                + DAY_MONTH_FORMAT.format(date));
        dayHeading.setStyle("-fx-font-size: 13pt; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox dayColumn = new VBox(8);
        dayColumn.setPadding(new Insets(10));
        dayColumn.setStyle(DAY_COLUMN_STYLE);
        dayColumn.setMinWidth(135);
        dayColumn.setPrefWidth(170);
        HBox.setHgrow(dayColumn, Priority.ALWAYS);
        dayColumn.getChildren().add(dayHeading);

        boolean hasApplication = false;
        for (Application application : applications) {
            if (LocalDate.parse(application.getDate().value).equals(date)) {
                dayColumn.getChildren().add(createApplicationCard(application));
                hasApplication = true;
            }
        }
        if (!hasApplication) {
            Label emptyDay = new Label("No applications");
            emptyDay.setStyle(MUTED_TEXT_STYLE);
            dayColumn.getChildren().add(emptyDay);
        }
        return dayColumn;
    }

    private VBox createApplicationCard(Application application) {
        Label role = new Label(application.getName().value);
        role.setStyle("-fx-font-weight: bold; -fx-text-fill: #202020;");
        role.setWrapText(true);

        Label company = new Label(application.getCompany().value);
        company.setStyle("-fx-text-fill: #303030;");
        company.setWrapText(true);

        VBox card = new VBox(4, role, company);
        card.setPadding(new Insets(9));
        card.setStyle("-fx-background-color: #8cb9e8; -fx-border-color: #6394c7;"
                + " -fx-border-width: 0 0 3 0; -fx-background-radius: 4;");
        return card;
    }
}
