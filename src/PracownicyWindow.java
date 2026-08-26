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

public class PracownicyWindow {

    public static void show(Menu menu) {

        Stage stage = new Stage();

        ListView<String> employeesList = new ListView<>();

        updateEmployeesList(employeesList, menu);

        Button addButton = new Button("Dodaj");
        Button deleteButton = new Button("Usun");
        Button sortButton = new Button("Sortuj nazwisko");
        Button closeButton = new Button("Zamknij");

        addButton.setOnAction(
                event -> showAddWindow(menu, employeesList)
        );

        deleteButton.setOnAction(
                event -> deleteEmployee(menu, employeesList)
        );

        sortButton.setOnAction(event -> {
            menu.getPracownicy().sort(null);
            updateEmployeesList(employeesList, menu);
        });

        closeButton.setOnAction(
                event -> stage.close()
        );

        HBox buttons = new HBox(10);

        buttons.getChildren().addAll(
                addButton,
                deleteButton,
                sortButton,
                closeButton
        );

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(15));

        layout.getChildren().addAll(
                employeesList,
                buttons
        );

        Scene scene = new Scene(layout, 750, 450);

        stage.setTitle("Pracownicy");
        stage.setScene(scene);
        stage.show();
    }

    private static void updateEmployeesList(
            ListView<String> employeesList,
            Menu menu) {

        employeesList.getItems().clear();

        for (Pracownik pracownik : menu.getPracownicy()) {
            employeesList.getItems().add(
                    pracownik.toString()
            );
        }
    }

    private static void showError(String message) {

        Alert alert = new Alert(
                Alert.AlertType.ERROR
        );

        alert.setTitle("Blad");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private static void showAddWindow(
            Menu menu,
            ListView<String> employeesList) {

        Stage stage = new Stage();

        Label nameLabel = new Label("Imie:");
        TextField nameField = new TextField();

        Label surnameLabel = new Label("Nazwisko:");
        TextField surnameField = new TextField();

        Label ageLabel = new Label("Wiek:");
        TextField ageField = new TextField();

        Label positionLabel = new Label("Stanowisko:");
        ComboBox<Stanowisko> positionBox = new ComboBox<>();

        positionBox.getItems().addAll(
                Stanowisko.values()
        );

        positionBox.setValue(
                Stanowisko.PRACOWNIK
        );

        Label dateLabel = new Label(
                "Data zatrudnienia:"
        );

        TextField dateField = new TextField();

        Button addButton = new Button("Dodaj");
        Button cancelButton = new Button("Anuluj");

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(15));

        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);

        form.add(surnameLabel, 0, 1);
        form.add(surnameField, 1, 1);

        form.add(ageLabel, 0, 2);
        form.add(ageField, 1, 2);

        form.add(positionLabel, 0, 3);
        form.add(positionBox, 1, 3);

        form.add(dateLabel, 0, 4);
        form.add(dateField, 1, 4);

        HBox buttons = new HBox(10);

        buttons.getChildren().addAll(
                addButton,
                cancelButton
        );

        form.add(buttons, 1, 5);

        addButton.setOnAction(event -> {

            String name = nameField.getText();
            String surname = surnameField.getText();
            String ageText = ageField.getText();
            String date = dateField.getText();

            if (name.isBlank()) {
                showError(
                        "Imie nie moze byc puste."
                );
                return;
            }

            if (surname.isBlank()) {
                showError(
                        "Nazwisko nie moze byc puste."
                );
                return;
            }

            if (ageText.isBlank()) {
                showError(
                        "Wiek nie moze byc pusty."
                );
                return;
            }

            if (date.isBlank()) {
                showError(
                        "Data zatrudnienia nie moze byc pusta."
                );
                return;
            }

            int age;

            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException e) {
                showError(
                        "Wiek musi byc liczba."
                );
                return;
            }

            Stanowisko position =
                    positionBox.getValue();

            Pracownik newEmployee;

            if (position == Stanowisko.KIEROWNIK) {

                newEmployee = new Kierownik(
                        name,
                        surname,
                        age,
                        date
                );

            } else {

                newEmployee = new Pracownik(
                        name,
                        surname,
                        age,
                        position,
                        date
                );
            }

            menu.getPracownicy().add(
                    newEmployee
            );

            updateEmployeesList(
                    employeesList,
                    menu
            );

            stage.close();
        });

        cancelButton.setOnAction(
                event -> stage.close()
        );

        Scene scene = new Scene(
                form,
                500,
                300
        );

        stage.setTitle("Dodaj Pracownika");
        stage.setScene(scene);
        stage.show();
    }

    private static void deleteEmployee(
            Menu menu,
            ListView<String> employeesList) {

        int selectedIndex =
                employeesList
                        .getSelectionModel()
                        .getSelectedIndex();

        if (selectedIndex == -1) {

            showError(
                    "Wybierz pracownika do usuniecia."
            );

            return;
        }

        Pracownik selectedEmployee =
                menu.getPracownicy()
                        .get(selectedIndex);

        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmation.setTitle(
                "Usuwanie pracownika"
        );

        confirmation.setHeaderText(null);

        confirmation.setContentText(
                "Czy na pewno chcesz usunac pracownika:\n"
                        + selectedEmployee
        );

        confirmation.showAndWait().ifPresent(
                response -> {

                    if (response ==
                            javafx.scene.control.ButtonType.OK) {

                        menu.getPracownicy().remove(
                                selectedEmployee
                        );

                        updateEmployeesList(
                                employeesList,
                                menu
                        );
                    }
                }
        );
    }
}