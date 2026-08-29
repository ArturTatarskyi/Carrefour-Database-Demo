import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.Year;
import java.time.YearMonth;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class WynagrodzeniaWindow {

        public static void show(Menu menu) {

                Stage stage = new Stage();

                ListView<String> salariesList = new ListView<>();

                updateSalariesList(
                                salariesList,
                                menu);

                Button addButton = new Button("Dodaj");

                Button deleteButton = new Button("Usun");

                Button sortButton = new Button("Sortuj nazwisko");

                Button closeButton = new Button("Zamknij");

                addButton.setOnAction(
                                event -> showAddWindow(
                                                menu,
                                                salariesList));

                deleteButton.setOnAction(
                                event -> deleteSalary(
                                                menu,
                                                salariesList));

                sortButton.setOnAction(event -> {

                        menu.getWynagrodzenia()
                                        .sort(null);

                        updateSalariesList(
                                        salariesList,
                                        menu);
                });

                closeButton.setOnAction(
                                event -> stage.close());

                HBox buttons = new HBox(10);

                buttons.getChildren().addAll(
                                addButton,
                                deleteButton,
                                sortButton,
                                closeButton);

                VBox layout = new VBox(10);

                layout.setPadding(
                                new Insets(15));

                layout.getChildren().addAll(
                                salariesList,
                                buttons);

                Scene scene = new Scene(
                                layout,
                                750,
                                450);

                stage.setTitle(
                                "Wynagrodzenia");

                stage.setScene(scene);
                stage.show();
        }

        private static void updateSalariesList(
                        ListView<String> salariesList,
                        Menu menu) {

                salariesList.getItems().clear();

                for (Wynagrodzenie wynagrodzenie : menu.getWynagrodzenia()) {

                        salariesList
                                        .getItems()
                                        .add(
                                                        wynagrodzenie.toString());
                }
        }

        private static void showAddWindow(
                        Menu menu,
                        ListView<String> salariesList) {

                Stage stage = new Stage();

                Label employeeLabel = new Label("Pracownik:");

                ComboBox<Pracownik> employeeBox = new ComboBox<>();

                employeeBox.getItems().addAll(
                                menu.getPracownicy());

                if (!employeeBox
                                .getItems()
                                .isEmpty()) {

                        employeeBox.setValue(
                                        employeeBox
                                                        .getItems()
                                                        .get(0));
                }

                Label periodLabel = new Label("Okres:");

                ComboBox<String> monthBox = new ComboBox<>();

                monthBox.getItems().addAll(
                                "01",
                                "02",
                                "03",
                                "04",
                                "05",
                                "06",
                                "07",
                                "08",
                                "09",
                                "10",
                                "11",
                                "12");

                monthBox.setPromptText(
                                "Miesiac");

                ComboBox<Integer> yearBox = new ComboBox<>();

                int currentYear = Year.now().getValue();

                for (int year = 2000; year <= currentYear + 5; year++) {

                        yearBox
                                        .getItems()
                                        .add(year);
                }

                yearBox.setPromptText(
                                "Rok");

                HBox periodBox = new HBox(10);

                periodBox.getChildren().addAll(
                                monthBox,
                                yearBox);

                Label hoursLabel = new Label(
                                "Liczba godzin:");

                TextField hoursField = new TextField();

                Button addButton = new Button("Dodaj");

                Button cancelButton = new Button("Anuluj");

                GridPane form = new GridPane();

                form.setHgap(10);
                form.setVgap(10);

                form.setPadding(
                                new Insets(15));

                form.add(
                                employeeLabel,
                                0,
                                0);

                form.add(
                                employeeBox,
                                1,
                                0);

                form.add(
                                periodLabel,
                                0,
                                1);

                form.add(
                                periodBox,
                                1,
                                1);

                form.add(
                                hoursLabel,
                                0,
                                2);

                form.add(
                                hoursField,
                                1,
                                2);

                HBox buttons = new HBox(10);

                buttons.getChildren().addAll(
                                addButton,
                                cancelButton);

                form.add(
                                buttons,
                                1,
                                3);

                addButton.setOnAction(event -> {

                        if (employeeBox.getValue() == null) {

                                showError(
                                                "Wybierz pracownika.");

                                return;
                        }

                        if (monthBox.getValue() == null) {

                                showError(
                                                "Wybierz miesiac.");

                                return;
                        }

                        if (yearBox.getValue() == null) {

                                showError(
                                                "Wybierz rok.");

                                return;
                        }

                        String period = monthBox.getValue()
                                        + "."
                                        + yearBox.getValue();

                        String hoursText = hoursField.getText();

                        if (hoursText.isBlank()) {

                                showError(
                                                "Liczba godzin nie moze byc pusta.");

                                return;
                        }

                        int hours;

                        try {

                                hours = Integer.parseInt(
                                                hoursText);

                        } catch (NumberFormatException e) {

                                showError(
                                                "Liczba godzin musi byc liczba.");

                                return;
                        }

                        if (hours <= 0) {

                                showError(
                                                "Liczba godzin musi byc wieksza od 0.");

                                return;
                        }

                        Pracownik employee = employeeBox.getValue();

                        try {

                                DateTimeFormatter employmentFormatter = DateTimeFormatter.ofPattern(
                                                "dd.MM.yyyy");

                                LocalDate employmentDate = LocalDate.parse(
                                                employee.getDataZatrudnienia(),
                                                employmentFormatter);

                                YearMonth employmentMonth = YearMonth.from(
                                                employmentDate);

                                YearMonth salaryMonth = YearMonth.of(
                                                yearBox.getValue(),
                                                Integer.parseInt(
                                                                monthBox.getValue()));

                                if (salaryMonth.isBefore(
                                                employmentMonth)) {

                                        showError(
                                                        "Nie mozna dodac wynagrodzenia "
                                                                        + "za okres przed zatrudnieniem pracownika.");

                                        return;
                                }

                        } catch (DateTimeParseException e) {

                                showError(
                                                "Nieprawidlowa data zatrudnienia pracownika.");

                                return;
                        }

                        for (Wynagrodzenie wynagrodzenie : menu.getWynagrodzenia()) {

                                if (wynagrodzenie
                                                .getPracownikId() == employee.getId()
                                                &&
                                                wynagrodzenie
                                                                .getOkres()
                                                                .equals(period)) {

                                        showError(
                                                        "To wynagrodzenie juz istnieje!");

                                        return;
                                }
                        }

                        Wynagrodzenie newSalary = new Wynagrodzenie(
                                        employee,
                                        hours,
                                        period);

                        menu.getWynagrodzenia()
                                        .add(newSalary);

                        WynagrodzenieStorage.save(
                                        menu.getWynagrodzenia());

                        updateSalariesList(
                                        salariesList,
                                        menu);

                        stage.close();
                });

                cancelButton.setOnAction(
                                event -> stage.close());

                Scene scene = new Scene(
                                form,
                                500,
                                250);

                stage.setTitle(
                                "Dodaj Wynagrodzenie");

                stage.setScene(scene);
                stage.show();
        }

        private static void deleteSalary(
                        Menu menu,
                        ListView<String> salariesList) {

                int selectedIndex = salariesList
                                .getSelectionModel()
                                .getSelectedIndex();

                if (selectedIndex == -1) {

                        showError(
                                        "Wybierz wynagrodzenie do usuniecia.");

                        return;
                }

                Wynagrodzenie selectedSalary = menu.getWynagrodzenia()
                                .get(selectedIndex);

                Alert confirmation = new Alert(
                                Alert.AlertType.CONFIRMATION);

                confirmation.setTitle(
                                "Usuwanie wynagrodzenia");

                confirmation.setHeaderText(null);

                confirmation.setContentText(
                                "Czy na pewno chcesz usunac wynagrodzenie:\n"
                                                + selectedSalary);

                confirmation.showAndWait()
                                .ifPresent(response -> {

                                        if (response == javafx.scene.control.ButtonType.OK) {

                                                menu.getWynagrodzenia()
                                                                .remove(
                                                                                selectedSalary);

                                                WynagrodzenieStorage.save(
                                                                menu.getWynagrodzenia());

                                                updateSalariesList(
                                                                salariesList,
                                                                menu);
                                        }
                                });
        }

        private static void showError(
                        String message) {

                Alert alert = new Alert(
                                Alert.AlertType.ERROR);

                alert.setTitle(
                                "Blad");

                alert.setHeaderText(null);

                alert.setContentText(
                                message);

                alert.showAndWait();
        }
}