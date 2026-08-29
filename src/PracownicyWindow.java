import java.time.format.DateTimeFormatter;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PracownicyWindow {

    public static void show(Menu menu) {
        Stage stage = new Stage();

        Label title = new Label("Pracownicy");
        title.getStyleClass().add("section-title");

        ListView<String> employeesList = new ListView<>();
        employeesList.getStyleClass().add("data-list");

        updateEmployeesList(employeesList, menu);

        Button addButton = new Button("Dodaj");
        Button deleteButton = new Button("Usun");
        Button sortButton = new Button("Sortuj nazwisko");
        Button closeButton = new Button("Zamknij");

        addButton.getStyleClass().add("action-button");
        deleteButton.getStyleClass().add("action-button");
        sortButton.getStyleClass().add("action-button");
        closeButton.getStyleClass().addAll("action-button", "secondary-button");

        addButton.setOnAction(event -> showAddWindow(menu, employeesList));
        deleteButton.setOnAction(event -> deleteEmployee(menu, employeesList));

        sortButton.setOnAction(event -> {
            menu.getPracownicy().sort(null);
            updateEmployeesList(employeesList, menu);
        });

        closeButton.setOnAction(event -> stage.close());

        HBox buttons = new HBox(10);
        buttons.getStyleClass().add("button-bar");
        buttons.getChildren().addAll(
                addButton,
                deleteButton,
                sortButton,
                closeButton);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("window-container");
        layout.getChildren().addAll(
                title,
                employeesList,
                buttons);

        Scene scene = new Scene(
                layout,
                750,
                450);

        applyStylesheet(scene);

        stage.setTitle("Pracownicy");
        stage.setScene(scene);
        stage.show();
    }

    private static void updateEmployeesList(
            ListView<String> employeesList,
            Menu menu) {

        employeesList.getItems().clear();

        for (Pracownik pracownik : menu.getPracownicy()) {
            employeesList.getItems().add(pracownik.toString());
        }
    }

    private static void showAddWindow(
            Menu menu,
            ListView<String> employeesList) {

        Stage stage = new Stage();

        Label title = new Label("Dodaj pracownika");
        title.getStyleClass().add("section-title");

        Label nameLabel = new Label("Imie:");
        TextField nameField = new TextField();

        Label surnameLabel = new Label("Nazwisko:");
        TextField surnameField = new TextField();

        Label ageLabel = new Label("Wiek:");
        TextField ageField = new TextField();

        Label positionLabel = new Label("Stanowisko:");
        ComboBox<Stanowisko> positionBox = new ComboBox<>();

        refreshPositionBox(positionBox);
        positionBox.setValue(Stanowisko.PRACOWNIK);

        Button addPositionButton = new Button("Dodaj stanowisko");
        Button deletePositionButton = new Button("Usun stanowisko");

        Label dateLabel = new Label("Data zatrudnienia:");
        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);

        Button addButton = new Button("Dodaj");
        Button cancelButton = new Button("Anuluj");

        addPositionButton.getStyleClass().add("small-button");
        deletePositionButton.getStyleClass().add("small-button");

        addButton.getStyleClass().add("action-button");
        cancelButton.getStyleClass().addAll("action-button", "secondary-button");

        nameField.getStyleClass().add("form-control");
        surnameField.getStyleClass().add("form-control");
        ageField.getStyleClass().add("form-control");
        positionBox.getStyleClass().add("form-control");
        datePicker.getStyleClass().add("form-control");

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(12);
        form.getStyleClass().add("form-grid");

        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);

        form.add(surnameLabel, 0, 1);
        form.add(surnameField, 1, 1);

        form.add(ageLabel, 0, 2);
        form.add(ageField, 1, 2);

        form.add(positionLabel, 0, 3);
        form.add(positionBox, 1, 3);

        HBox positionButtons = new HBox(10);
        positionButtons.getStyleClass().add("button-bar");
        positionButtons.getChildren().addAll(
                addPositionButton,
                deletePositionButton);

        form.add(positionButtons, 2, 3);

        form.add(dateLabel, 0, 4);
        form.add(datePicker, 1, 4);

        HBox buttons = new HBox(10);
        buttons.getStyleClass().add("button-bar");
        buttons.getChildren().addAll(
                addButton,
                cancelButton);

        form.add(buttons, 1, 5);

        addPositionButton.setOnAction(
                event -> showAddPositionWindow(positionBox));

        deletePositionButton.setOnAction(
                event -> deletePosition(menu, positionBox));

        addButton.setOnAction(event -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String ageText = ageField.getText();

            if (name.isBlank()) {
                showError("Imie nie moze byc puste.");
                return;
            }

            if (!name.matches("[\\p{L}]+")) {
                showError("Imie moze zawierac tylko litery.");
                return;
            }

            if (surname.isBlank()) {
                showError("Nazwisko nie moze byc puste.");
                return;
            }

            if (!surname.matches("[\\p{L}]+")) {
                showError("Nazwisko moze zawierac tylko litery.");
                return;
            }

            if (ageText.isBlank()) {
                showError("Wiek nie moze byc pusty.");
                return;
            }

            if (datePicker.getValue() == null) {
                showError("Wybierz date zatrudnienia.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    "dd.MM.yyyy");

            String date = datePicker
                    .getValue()
                    .format(formatter);

            int age;

            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException e) {
                showError("Wiek musi byc liczba.");
                return;
            }

            Stanowisko position = positionBox.getValue();

            if (position == null) {
                showError("Wybierz stanowisko.");
                return;
            }

            Pracownik newEmployee;

            if (position.isKierownicze()) {
                newEmployee = new Kierownik(
                        name,
                        surname,
                        age,
                        position,
                        date);
            } else {
                newEmployee = new Pracownik(
                        name,
                        surname,
                        age,
                        position,
                        date);
            }

            menu.getPracownicy().add(newEmployee);

            PracownikStorage.save(
                    menu.getPracownicy());

            updateEmployeesList(
                    employeesList,
                    menu);

            stage.close();
        });

        cancelButton.setOnAction(event -> stage.close());

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("window-container");
        layout.getChildren().addAll(
                title,
                form);

        Scene scene = new Scene(
                layout,
                780,
                350);

        applyStylesheet(scene);

        stage.setTitle("Dodaj Pracownika");
        stage.setScene(scene);
        stage.show();
    }

    private static void showAddPositionWindow(
            ComboBox<Stanowisko> positionBox) {

        Stage stage = new Stage();

        Label title = new Label("Dodaj stanowisko");
        title.getStyleClass().add("section-title");

        Label nameLabel = new Label("Nazwa stanowiska:");
        TextField nameField = new TextField();
        nameField.getStyleClass().add("form-control");

        CheckBox managerCheckBox = new CheckBox(
                "Stanowisko kierownicze");

        Button addButton = new Button("Dodaj");
        Button cancelButton = new Button("Anuluj");

        addButton.getStyleClass().add("action-button");
        cancelButton.getStyleClass().addAll("action-button", "secondary-button");

        HBox buttons = new HBox(10);
        buttons.getStyleClass().add("button-bar");
        buttons.getChildren().addAll(
                addButton,
                cancelButton);

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("window-container");
        layout.getChildren().addAll(
                title,
                nameLabel,
                nameField,
                managerCheckBox,
                buttons);

        addButton.setOnAction(event -> {
            String positionName = nameField.getText();

            if (positionName.isBlank()) {
                showError("Nazwa stanowiska nie moze byc pusta.");
                return;
            }

            boolean managerial = managerCheckBox.isSelected();

            Stanowisko newPosition = Stanowisko.dodajStanowisko(
                    positionName,
                    managerial);

            StanowiskoStorage.save();

            refreshPositionBox(positionBox);
            positionBox.setValue(newPosition);

            stage.close();
        });

        cancelButton.setOnAction(event -> stage.close());

        Scene scene = new Scene(
                layout,
                400,
                230);

        applyStylesheet(scene);

        stage.setTitle("Dodaj stanowisko");
        stage.setScene(scene);
        stage.show();
    }

    private static void deletePosition(
            Menu menu,
            ComboBox<Stanowisko> positionBox) {

        Stanowisko selectedPosition = positionBox.getValue();

        if (selectedPosition == null) {
            showError(
                    "Wybierz stanowisko, ktore chcesz usunac.");
            return;
        }

        if (Stanowisko.czyPodstawowe(selectedPosition)) {
            showError(
                    "Nie mozna usunac podstawowego stanowiska.");
            return;
        }

        for (Pracownik pracownik : menu.getPracownicy()) {
            if (pracownik
                    .getStanowisko()
                    .equals(selectedPosition)) {

                showError(
                        "Nie mozna usunac tego stanowiska, "
                                + "poniewaz jest uzywane przez pracownika.");
                return;
            }
        }

        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION);

        confirmation.setTitle(
                "Usuwanie stanowiska");

        confirmation.setHeaderText(null);

        confirmation.setContentText(
                "Czy na pewno chcesz usunac stanowisko: "
                        + selectedPosition
                        + "?");

        confirmation.showAndWait()
                .ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        boolean deleted = Stanowisko.usunStanowisko(
                                selectedPosition);

                        if (deleted) {
                            StanowiskoStorage.save();
                            refreshPositionBox(positionBox);
                            positionBox.setValue(
                                    Stanowisko.PRACOWNIK);
                        }
                    }
                });
    }

    private static void refreshPositionBox(
            ComboBox<Stanowisko> positionBox) {

        positionBox.getItems().clear();
        positionBox.getItems().addAll(
                Stanowisko.values());
    }

    private static void deleteEmployee(
            Menu menu,
            ListView<String> employeesList) {

        int selectedIndex = employeesList
                .getSelectionModel()
                .getSelectedIndex();

        if (selectedIndex == -1) {
            showError(
                    "Wybierz pracownika do usuniecia.");
            return;
        }

        Pracownik selectedEmployee = menu
                .getPracownicy()
                .get(selectedIndex);

        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION);

        confirmation.setTitle(
                "Usuwanie pracownika");

        confirmation.setHeaderText(null);

        confirmation.setContentText(
                "Czy na pewno chcesz usunac pracownika:\n"
                        + selectedEmployee);

        confirmation.showAndWait()
                .ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        int employeeId = selectedEmployee.getId();

                        menu.getPracownicy().remove(
                                selectedEmployee);

                        menu.getWynagrodzenia().removeIf(
                                wyn -> wyn.getPracownikId() == employeeId);

                        PracownikStorage.save(
                                menu.getPracownicy());

                        WynagrodzenieStorage.save(
                                menu.getWynagrodzenia());

                        updateEmployeesList(
                                employeesList,
                                menu);
                    }
                });
    }

    private static void showError(
            String message) {

        Alert alert = new Alert(
                Alert.AlertType.ERROR);

        alert.setTitle("Blad");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private static void applyStylesheet(Scene scene) {
        scene.getStylesheets().add(
                PracownicyWindow.class
                        .getResource("style.css")
                        .toExternalForm());
    }
}
