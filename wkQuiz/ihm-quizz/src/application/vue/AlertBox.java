package application.vue;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * classe contenant les différents types d'alertbox
 * 
 * @author Quentin COSTES
 */
public abstract class AlertBox {

	/**
	 * alert box de confirmation 
	 * @param message
	 * @return
	 */
    public static boolean showConfirmationBox(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Confirmation");

        ButtonType buttonTypeYes = new ButtonType("Valider");
        ButtonType buttonTypeNo = new ButtonType("Annuler");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }
    
    /**
     * alert box pour afficher le succés d'une opération
     * @param message
     */
    public static void showSuccessBox(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Succès");

        alert.showAndWait();
    }

    /**
     * alert box pour afficher une erreur
     * @param message
     */
    public static void showErrorBox(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Erreur");

        alert.showAndWait();
    }

    /**
     * alert box pour afficher une erreur
     * @param message
     */
    public static void showWarningBox(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Attention");
        
        alert.showAndWait();
    }
    
    
}
