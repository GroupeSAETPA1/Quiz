package application.vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertBoxTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Test des Alertes");

        Button confirmationButton = new Button("Afficher Confirmation");
        Button successButton = new Button("Afficher Succès");
        Button errorButton = new Button("Afficher Erreur");
        Button warningButton = new Button("Afficher Warning");

        confirmationButton.setOnAction(e -> {
            String message = "Voulez-vous continuer ?";
            boolean result = AlertBox.showConfirmationBox(message);
            System.out.println("Confirmation résultat : " + result);
        });

        successButton.setOnAction(e -> {
            String message = "L'opération a été effectuée avec succès.";
            AlertBox.showSuccessBox(message);
        });

        errorButton.setOnAction(e -> {
            String message = "Une erreur s'est produite.";
            AlertBox.showErrorBox(message);
        });

        warningButton.setOnAction(e -> {
            String message = "Ceci est un warning ";
            AlertBox.showWarningBox(message);
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(confirmationButton, successButton, errorButton,warningButton);
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("mgoeh");
        launch(args);
    }
}
