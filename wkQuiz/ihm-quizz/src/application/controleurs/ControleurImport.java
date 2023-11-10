/*
 * ControleurImport.java                                    10 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs;

import java.io.File;

import application.Quiz;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/** 
 * Controlleur de Import
 * Gère le fichier CSV et créer les nouvelles questions et catégorie
 * présente dans le fichier
 * @author François de Saint Palais
 */
public class ControleurImport {
    
    @FXML private TextField saisieCheminFichier;
    
    private File fichierCSVChoisie;

    @FXML
    private void parcourirExplorer () {
        System.out.println("Parcourir Explorer");
        FileChooser fichier = new FileChooser();
        fichier.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        fichier.setTitle("Selectionner le fichier contenant les nouvelles questions");
        
        fichierCSVChoisie = fichier.showOpenDialog(null);
        
        if (fichierCSVChoisie != null) {
            saisieCheminFichier.setText(fichierCSVChoisie.getAbsolutePath());
            System.out.println(fichierCSVChoisie.getAbsolutePath()); 
        }
        
    }
    
    @FXML
    private void aider() {
        System.out.println("Aider");
    }

    @FXML
    private void retour() {
        System.out.println("Retour");
        Quiz.changerVue("Editeur.fxml");
    }
    
    @FXML
    private void valider() {
        System.out.println("Valider");
        String cheminFichierCSV = saisieCheminFichier.getText();
        
        File fichier = new File(cheminFichierCSV);
        
        if (!fichier.exists()) {
            AlertBox.showErrorBox(cheminFichierCSV + ", n'existe pas.");
        } else if (!cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf(".") + 1).equals("csv")) {
            AlertBox.showErrorBox(
                  cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf("\\") + 1)
                + ", n'est pas un fichier CSV");     
        } else if (formatCSVValide(fichier)) {
            
        }
    }

    /** 
     * Un fichier CSV valide contient :
     * <ul>
     *     <li>Colonne 1 la catégorie</li>
     *     <li>Colonne 2 la difficulté</li>
     *     <li>Colonne 3 le libellé</li>
     *     <li>Colonne 4 la réponse juste</li>
     *     <li>Colonne 5 la première réponse fausse</li>
     *     <li>Colonne 6 la deuxième réponse fausse</li>
     *     <li>Colonne 7 la troisième réponse fausse</li>
     *     <li>Colonne 7 la quatrième réponse fausse</li>
     * </ul>
     * @param fichierAVerifier Le document à vérifier
     * @return true si le fichier est valide false sinon
     */
    private boolean formatCSVValide(File fichierAVerifier) {
        // TODO Auto-generated method stub
        return true; //STUB
    }
}
