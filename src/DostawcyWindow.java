import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import java.time.format.DateTimeFormatter;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DostawcyWindow {

    public static void show(Menu menu) {

        Stage stage = new Stage();

        ListView<String> suppliersList =
                new ListView<>();

        updateSuppliersList(
                suppliersList,
                menu
        );

        Button addButton =
                new Button("Dodaj");

        Button deleteButton =
                new Button("Usun");

        Button sortButton =
                new Button("Sortuj nazwa");

        Button closeButton =
                new Button("Zamknij");

        addButton.setOnAction(
                event -> showAddWindow(
                        menu,
                        suppliersList
                )
        );

        deleteButton.setOnAction(
                event -> deleteSupplier(
                        menu,
                        suppliersList
                )
        );

        sortButton.setOnAction(event -> {

            menu.getDostawcy().sort(null);

            updateSuppliersList(
                    suppliersList,
                    menu
            );
        });

        closeButton.setOnAction(
                event -> stage.close()
        );

        HBox buttons =
                new HBox(10);

        buttons.getChildren().addAll(
                addButton,
                deleteButton,
                sortButton,
                closeButton
        );

        VBox layout =
                new VBox(10);

        layout.setPadding(
                new Insets(15)
        );

        layout.getChildren().addAll(
                suppliersList,
                buttons
        );

        Scene scene =
                new Scene(
                        layout,
                        700,
                        450
                );

        stage.setTitle("Dostawcy");
        stage.setScene(scene);
        stage.show();
    }

    private static void updateSuppliersList(
            ListView<String> suppliersList,
            Menu menu) {

        suppliersList.getItems().clear();

        for (Dostawca dostawca :
                menu.getDostawcy()) {

            suppliersList
                    .getItems()
                    .add(
                            dostawca.toString()
                    );
        }
    }

    private static void showAddWindow(
            Menu menu,
            ListView<String> suppliersList) {

        Stage stage =
                new Stage();

        Label nameLabel =
                new Label("Nazwa:");

        TextField nameField =
                new TextField();

        Label typeLabel =
                new Label("Typ towaru:");

        ComboBox<TypTowaru> typeBox =
                new ComboBox<>();

        refreshTypeBox(
                typeBox
        );

        typeBox.setValue(
                TypTowaru.JEDZENIE
        );

        Button addTypeButton =
                new Button("Dodaj nowy typ");

        Button deleteTypeButton =
                new Button("Usun typ");

        Label dateLabel =
                new Label("Data podpisania:");

        DatePicker datePicker =
        new DatePicker();

datePicker.setEditable(false);

        Button addButton =
                new Button("Dodaj");

        Button cancelButton =
                new Button("Anuluj");

        GridPane form =
                new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.setPadding(
                new Insets(15)
        );

        form.add(
                nameLabel,
                0,
                0
        );

        form.add(
                nameField,
                1,
                0
        );

        form.add(
                typeLabel,
                0,
                1
        );

        form.add(
                typeBox,
                1,
                1
        );

        HBox typeButtons =
                new HBox(10);

        typeButtons.getChildren().addAll(
                addTypeButton,
                deleteTypeButton
        );

        form.add(
                typeButtons,
                2,
                1
        );

        form.add(
                dateLabel,
                0,
                2
        );

        form.add(
                datePicker,
                1,
                2
        );

        HBox buttons =
                new HBox(10);

        buttons.getChildren().addAll(
                addButton,
                cancelButton
        );

        form.add(
                buttons,
                1,
                3
        );

        addTypeButton.setOnAction(
                event -> showAddTypeWindow(
                        typeBox
                )
        );

        deleteTypeButton.setOnAction(
                event -> deleteType(
                        menu,
                        typeBox
                )
        );

        addButton.setOnAction(event -> {

            String name =
                    nameField.getText();

            TypTowaru selectedType =
                    typeBox.getValue();

            if (name.isBlank()) {

                showError(
                        "Nazwa dostawcy nie moze byc pusta."
                );

                return;
            }

            if (selectedType == null) {

                showError(
                        "Wybierz typ towaru."
                );

                return;
            }

            if (datePicker.getValue() == null) {

    showError(
            "Wybierz date podpisania umowy."
    );

    return;
}

DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern(
                "dd.MM.yyyy"
        );

String date =
        datePicker
                .getValue()
                .format(formatter);

            Dostawca newDostawca =
                    new Dostawca(
                            name,
                            selectedType,
                            date
                    );

            menu.getDostawcy()
                    .add(newDostawca);

            DostawcaStorage.save(
                    menu.getDostawcy()
            );

            updateSuppliersList(
                    suppliersList,
                    menu
            );

            stage.close();
        });

        cancelButton.setOnAction(
                event -> stage.close()
        );

        Scene scene =
                new Scene(
                        form,
                        700,
                        230
                );

        stage.setTitle(
                "Dodaj Dostawce"
        );

        stage.setScene(scene);
        stage.show();
    }

    private static void showAddTypeWindow(
            ComboBox<TypTowaru> typeBox) {

        Stage stage =
                new Stage();

        Label typeLabel =
                new Label(
                        "Nazwa nowego typu:"
                );

        TextField typeField =
                new TextField();

        Button addButton =
                new Button("Dodaj");

        Button cancelButton =
                new Button("Anuluj");

        HBox buttons =
                new HBox(10);

        buttons.getChildren().addAll(
                addButton,
                cancelButton
        );

        VBox layout =
                new VBox(10);

        layout.setPadding(
                new Insets(15)
        );

        layout.getChildren().addAll(
                typeLabel,
                typeField,
                buttons
        );

        addButton.setOnAction(event -> {

            String typeName =
                    typeField.getText();

            if (typeName.isBlank()) {

                showError(
                        "Nazwa typu nie moze byc pusta."
                );

                return;
            }

            TypTowaru newType =
                    TypTowaru.dodajTyp(
                            typeName
                    );

            TypTowaruStorage.save();

            refreshTypeBox(
                    typeBox
            );

            typeBox.setValue(
                    newType
            );

            stage.close();
        });

        cancelButton.setOnAction(
                event -> stage.close()
        );

        Scene scene =
                new Scene(
                        layout,
                        350,
                        150
                );

        stage.setTitle(
                "Dodaj typ towaru"
        );

        stage.setScene(scene);
        stage.show();
    }

    private static void deleteType(
            Menu menu,
            ComboBox<TypTowaru> typeBox) {

        TypTowaru selectedType =
                typeBox.getValue();

        if (selectedType == null) {

            showError(
                    "Wybierz typ, ktory chcesz usunac."
            );

            return;
        }

        if (TypTowaru.czyPodstawowy(
                selectedType)) {

            showError(
                    "Nie mozna usunac podstawowego typu towaru."
            );

            return;
        }

        for (Towar towar :
                menu.getTowary()) {

            if (towar.getTyp()
                    .equals(selectedType)) {

                showError(
                        "Nie mozna usunac tego typu, "
                                + "poniewaz jest uzywany przez towary."
                );

                return;
            }
        }

        for (Dostawca dostawca :
                menu.getDostawcy()) {

            if (dostawca
                    .getTypTowaru()
                    .equals(selectedType)) {

                showError(
                        "Nie mozna usunac tego typu, "
                                + "poniewaz jest uzywany przez dostawcow."
                );

                return;
            }
        }

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "Usuwanie typu"
        );

        confirmation.setHeaderText(null);

        confirmation.setContentText(
                "Czy na pewno chcesz usunac typ: "
                        + selectedType
                        + "?"
        );

        confirmation.showAndWait()
                .ifPresent(response -> {

                    if (response
                            == ButtonType.OK) {

                        boolean deleted =
                                TypTowaru.usunTyp(
                                        selectedType
                                );

                        if (deleted) {

                            TypTowaruStorage.save();

                            refreshTypeBox(
                                    typeBox
                            );

                            typeBox.setValue(
                                    TypTowaru.JEDZENIE
                            );
                        }
                    }
                });
    }

    private static void deleteSupplier(
            Menu menu,
            ListView<String> suppliersList) {

        int selectedIndex =
                suppliersList
                        .getSelectionModel()
                        .getSelectedIndex();

        if (selectedIndex == -1) {

            showError(
                    "Wybierz dostawce do usuniecia."
            );

            return;
        }

        Dostawca selectedSupplier =
                menu.getDostawcy()
                        .get(selectedIndex);

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "Usuwanie dostawcy"
        );

        confirmation.setHeaderText(null);

        confirmation.setContentText(
                "Czy na pewno chcesz usunac dostawce:\n"
                        + selectedSupplier
        );

        confirmation.showAndWait()
                .ifPresent(response -> {

                    if (response
                            == ButtonType.OK) {

                        menu.getDostawcy()
                                .remove(
                                        selectedSupplier
                                );

                        DostawcaStorage.save(
                                menu.getDostawcy()
                        );

                        updateSuppliersList(
                                suppliersList,
                                menu
                        );
                    }
                });
    }

    private static void refreshTypeBox(
            ComboBox<TypTowaru> typeBox) {

        typeBox.getItems().clear();

        typeBox.getItems().addAll(
                TypTowaru.values()
        );
    }

    private static void showError(
            String message) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle("Blad");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}