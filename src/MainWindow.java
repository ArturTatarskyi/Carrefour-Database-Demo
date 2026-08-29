import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {

    @Override
    public void start(Stage stage) {

        Menu menu = new Menu();

        Label title = new Label("CARREFOUR");
        title.getStyleClass().add("main-title");

        Button productsButton = new Button("Towary");
        Button employeesButton = new Button("Pracownicy");
        Button suppliersButton = new Button("Dostawcy");
        Button salariesButton = new Button("Wynagrodzenia");
        Button exitButton = new Button("Wyjście");

        productsButton.getStyleClass().add("menu-button");
        employeesButton.getStyleClass().add("menu-button");
        suppliersButton.getStyleClass().add("menu-button");
        salariesButton.getStyleClass().add("menu-button");

        exitButton.getStyleClass().addAll(
                "menu-button",
                "exit-button"
        );

        productsButton.setOnAction(
                event -> TowaryWindow.show(menu)
        );

        employeesButton.setOnAction(
                event -> PracownicyWindow.show(menu)
        );

        suppliersButton.setOnAction(
                event -> DostawcyWindow.show(menu)
        );

        salariesButton.setOnAction(
                event -> WynagrodzeniaWindow.show(menu)
        );

        exitButton.setOnAction(
                event -> stage.close()
        );

        VBox menuBox = new VBox(15);

        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(30));
        menuBox.getStyleClass().add("main-container");

        menuBox.getChildren().addAll(
                title,
                productsButton,
                employeesButton,
                suppliersButton,
                salariesButton,
                exitButton
        );

        Scene scene = new Scene(
                menuBox,
                500,
                400
        );

        scene.getStylesheets().add(
                getClass().getResource("style.css").toExternalForm()
        );

        stage.setTitle("Carrefour Database");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}