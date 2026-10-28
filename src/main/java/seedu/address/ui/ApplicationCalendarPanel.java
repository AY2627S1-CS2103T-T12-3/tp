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
import javafx.geometry.Pos;
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
    private final ObservableList<Application> applications;
    private final Label dateRangeLabel = new Label();
    private final HBox weekColumns = new HBox(8);
    private LocalDate weekStart;

    /** Creates a calendar panel backed by the application's live application list. */
    public ApplicationCalendarPanel(ObservableList<Application> applications) {
        this.applications = applications;
        weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        getStyleClass().add("application-calendar");
        setSpacing(10);
        setPadding(new Insets(12));

        Button previousWeekButton = new Button("‹");
        previousWeekButton.getStyleClass().add("calendar-navigation-button");
        previousWeekButton.setAccessibleText("Previous week");
        previousWeekButton.setOnAction(event -> changeWeek(-1));

        Button nextWeekButton = new Button("›");
        nextWeekButton.getStyleClass().add("calendar-navigation-button");
        nextWeekButton.setAccessibleText("Next week");
        nextWeekButton.setOnAction(event -> changeWeek(1));

        dateRangeLabel.getStyleClass().add("calendar-range-label");
        HBox header = new HBox(16, previousWeekButton, dateRangeLabel, nextWeekButton);
        header.getStyleClass().add("calendar-header");
        header.setAlignment(Pos.CENTER);

        weekColumns.setFillHeight(true);
        ScrollPane weekScrollPane = new ScrollPane(weekColumns);
        weekScrollPane.getStyleClass().add("calendar-scroll-pane");
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
        dayHeading.getStyleClass().add("calendar-day-heading");

        VBox dayColumn = new VBox(8);
        dayColumn.getStyleClass().add("calendar-day-column");
        dayColumn.setPadding(new Insets(10));
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
            emptyDay.getStyleClass().add("calendar-empty-day");
            dayColumn.getChildren().add(emptyDay);
        }
        return dayColumn;
    }

    private VBox createApplicationCard(Application application) {
        Label role = new Label(application.getName().value);
        role.getStyleClass().add("calendar-application-role");
        role.setWrapText(true);

        Label company = new Label(application.getCompany().value);
        company.getStyleClass().add("calendar-application-company");
        company.setWrapText(true);

        VBox card = new VBox(4, role, company);
        card.getStyleClass().add("calendar-application-card");
        card.setPadding(new Insets(9));
        return card;
    }
}
