import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DostawcyWindow {

    public static void show(Menu menu) {

        Stage stage = new Stage();

        ListView<String> suppliersList = new ListView<>();

        updateSuppliersList(suppliersList, menu);

        Button addButton = new Button("Dodaj");
        Button deleteButton = new Button("Usun");

        deleteButton.setOnAction(event -> deleteSupplier(
        menu,
        suppliersList
        ));
        Button sortButton = new Button("Sortuj nazwa");
        Button closeButton = new Button("Zamknij");

        addButton.setOnAction(event -> showAddWindow(menu, suppliersList));

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
                suppliersList,
                buttons
        );

        sortButton.setOnAction(event -> {
            menu.getDostawcy().sort(null);
            updateSuppliersList(suppliersList, menu);
        });

        closeButton.setOnAction(event -> stage.close());

        Scene scene = new Scene(layout, 700, 450);

        stage.setTitle("Dostawcy");
        stage.setScene(scene);
        stage.show();
    }

    private static void updateSuppliersList(
            ListView<String> suppliersList,
            Menu menu) {

        suppliersList.getItems().clear();

        for (Dostawca dostawca : menu.getDostawcy()) {
            suppliersList.getItems().add(dostawca.toString());
        }
    }

    private static void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Blad");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
    private static void showAddWindow(
        Menu menu,
        ListView<String> suppliersList) {

    Stage stage = new Stage();

    Label nameLabel = new Label("Nazwa:");
    TextField nameField = new TextField();

    Label typeLabel = new Label("Typ towaru:");
    ComboBox<TypTowaru> typeBox = new ComboBox<>();

    typeBox.getItems().addAll(TypTowaru.values());
    typeBox.setValue(TypTowaru.JEDZENIE);

    Label dateLabel = new Label("Data podpisania:");
    TextField dateField = new TextField();

    Button addButton = new Button("Dodaj");
    Button cancelButton = new Button("Anuluj");

    GridPane form = new GridPane();

    form.setHgap(10);
    form.setVgap(10);
    form.setPadding(new Insets(15));

    form.add(nameLabel, 0, 0);
    form.add(nameField, 1, 0);

    form.add(typeLabel, 0, 1);
    form.add(typeBox, 1, 1);

    form.add(dateLabel, 0, 2);
    form.add(dateField, 1, 2);

    HBox buttons = new HBox(10);

    buttons.getChildren().addAll(
            addButton,
            cancelButton
    );

    form.add(buttons, 1, 3);

    addButton.setOnAction(event -> {

        String name = nameField.getText();
        String date = dateField.getText();

        if (name.isBlank()) {
            showError("Nazwa dostawcy nie moze byc pusta.");
            return;
        }

        if (date.isBlank()) {
            showError("Data podpisania umowy nie moze byc pusta.");
            return;
        }

        Dostawca newDostawca = new Dostawca(
                name,
                typeBox.getValue(),
                date
        );

        menu.getDostawcy().add(newDostawca);

DostawcaStorage.save(menu.getDostawcy());

updateSuppliersList(suppliersList, menu);

stage.close();
    });

    cancelButton.setOnAction(event -> stage.close());

    Scene scene = new Scene(form, 450, 220);

    stage.setTitle("Dodaj Dostawce");
    stage.setScene(scene);
    stage.show()   ;
    }
    private static void deleteSupplier(
        Menu menu,
        ListView<String> suppliersList) {

    int selectedIndex = suppliersList.getSelectionModel()
            .getSelectedIndex();

    if (selectedIndex == -1) {
        showError("Wybierz dostawce do usuniecia.");
        return;
    }

    Dostawca selectedSupplier =
            menu.getDostawcy().get(selectedIndex);

    Alert confirmation = new Alert(
            Alert.AlertType.CONFIRMATION
    );

    confirmation.setTitle("Usuwanie dostawcy");
    confirmation.setHeaderText(null);
    confirmation.setContentText(
            "Czy na pewno chcesz usunac dostawce:\n"
                    + selectedSupplier
    );

    confirmation.showAndWait().ifPresent(response -> {

        if (response == javafx.scene.control.ButtonType.OK) {

            menu.getDostawcy().remove(selectedSupplier);

DostawcaStorage.save(menu.getDostawcy());

updateSuppliersList(
        suppliersList,
        menu
);
        }
    });
    }
}