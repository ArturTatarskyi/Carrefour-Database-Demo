import javafx.geometry.Insets;
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

public class TowaryWindow {

    public static void show(Menu menu) {

        Stage stage = new Stage();

        ListView<String> productsList = new ListView<>();

        updateProductsList(productsList, menu);

        Button addButton = new Button("Dodaj");
        Button deleteButton = new Button("Usun");
        Button sortButton = new Button("Sortuj nazwa");
        Button closeButton = new Button("Zamknij");

        addButton.setOnAction(event -> showAddWindow(menu, productsList));

        deleteButton.setOnAction(event -> {

    int selectedIndex = productsList.getSelectionModel()
            .getSelectedIndex();

    if (selectedIndex == -1) {
        showError("Wybierz towar, ktory chcesz usunac.");
        return;
    }

    Alert confirmation = new Alert(
            Alert.AlertType.CONFIRMATION
    );

    confirmation.setTitle("Usuwanie towaru");
    confirmation.setHeaderText(null);
    confirmation.setContentText(
            "Czy na pewno chcesz usunac wybrany towar?"
    );

    confirmation.showAndWait().ifPresent(response -> {

        if (response == ButtonType.OK) {

            menu.getTowary().remove(selectedIndex);

            updateProductsList(productsList, menu);
        }
    });
});

        closeButton.setOnAction(event -> stage.close());

        sortButton.setOnAction(event -> {
            menu.getTowary().sort(null);
            updateProductsList(productsList, menu);
        });

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
                productsList,
                buttons
        );

        Scene scene = new Scene(layout, 700, 450);

        stage.setTitle("Towary");
        stage.setScene(scene);
        stage.show();
    }

    private static void updateProductsList(
            ListView<String> productsList,
            Menu menu) {

        productsList.getItems().clear();

        for (Towar towar : menu.getTowary()) {
            productsList.getItems().add(towar.toString());
        }
    }

    private static void showAddWindow(
            Menu menu,
            ListView<String> productsList) {

        Stage stage = new Stage();

        Label nameLabel = new Label("Nazwa:");
        TextField nameField = new TextField();

        Label typeLabel = new Label("Typ:");
        ComboBox<TypTowaru> typeBox = new ComboBox<>();
        typeBox.getItems().addAll(TypTowaru.values());
        typeBox.setValue(TypTowaru.JEDZENIE);

        Label priceLabel = new Label("Cena:");
        TextField priceField = new TextField();

        Label quantityLabel = new Label("Ilosc:");
        TextField quantityField = new TextField();

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

        form.add(priceLabel, 0, 2);
        form.add(priceField, 1, 2);

        form.add(quantityLabel, 0, 3);
        form.add(quantityField, 1, 3);

        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(
                addButton,
                cancelButton
        );

        form.add(buttons, 1, 4);

        addButton.setOnAction(event -> {

            String name = nameField.getText();

            if (name.isBlank()) {
                showError("Nazwa towaru nie moze byc pusta.");
                return;
            }

            try {
                double price = Double.parseDouble(
                        priceField.getText().replace(",", ".")
                );

                int quantity = Integer.parseInt(
                        quantityField.getText()
                );

                if (price < 0 || quantity < 0) {
                    showError("Cena i ilosc nie moga byc ujemne.");
                    return;
                }

                Towar newTowar = new Towar(
                        name,
                        typeBox.getValue(),
                        price,
                        quantity
                );

                menu.getTowary().add(newTowar);

                updateProductsList(productsList, menu);

                stage.close();

            } catch (NumberFormatException e) {
                showError("Cena musi byc liczba, a ilosc liczba calkowita.");
            }
        });

        cancelButton.setOnAction(event -> stage.close());

        Scene scene = new Scene(form, 450, 250);

        stage.setTitle("Dodaj Towar");
        stage.setScene(scene);
        stage.show();
    }

    private static void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Blad");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}