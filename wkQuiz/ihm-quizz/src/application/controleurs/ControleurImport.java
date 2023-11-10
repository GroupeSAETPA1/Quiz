/*
 * ControleurImport.java                                    10 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/** 
 * Controlleur de Import
 * Gère le fichier CSV et créer les nouvelles questions et catégorie
 * présente dans le fichier
 * @author François de Saint Palais
 */
public class ControleurImport {
    
    @FXML private TextField saisieCheminFichier;

    @FXML
    private void parcourirExplorer () {
        System.out.println("Parcourir Explorer");
    }
    
    @FXML
    private void aider() {
        System.out.println("Aider");
    }

    @FXML
    private void retour() {
        System.out.println("Retour");
    }
    @FXML
    private void valider() {
        System.out.println("Valider");
    }
}
