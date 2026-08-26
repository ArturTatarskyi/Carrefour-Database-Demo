import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {

    private Menu menu = new Menu();

    @Override
    public void start(Stage stage) {
        Menu storeMenu = new Menu();

        Label title = new Label("CARREFOUR");

        Button productsButton = new Button("Towary");
        Button employeesButton = new Button("Pracownicy");
        Button suppliersButton = new Button("Dostawcy");
        suppliersButton.setOnAction(event -> DostawcyWindow.show(storeMenu));
        Button salariesButton = new Button("Wynagrodzenia");
        Button exitButton = new Button("Wyjście");

        productsButton.setOnAction(event -> TowaryWindow.show(storeMenu));

        employeesButton.setOnAction(event -> PracownicyWindow.show(menu));

        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);

        menu.getChildren().addAll(
                title,
                productsButton,
                employeesButton,
                suppliersButton,
                salariesButton,
                exitButton
        );

        exitButton.setOnAction(event -> stage.close());

        Scene scene = new Scene(menu, 500, 400);

        stage.setTitle("Carrefour Database");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}