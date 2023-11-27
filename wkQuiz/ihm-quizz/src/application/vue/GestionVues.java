/*
 * GestionVues.java 			                     18 octobre 2023
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application.vue;

import java.io.IOException;
import java.util.HashMap;

import application.Quiz;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Gère le chargement et le changement de Vue
 * @author François de Saint Palais
 */
public class GestionVues {
	
    /** Associe le nom du fichier fxml à sa Scene charger avec FXMLLoader */
    private static HashMap<String, Scene> scenes;
	
    /**
     * Charge la vue indiquer
     * @param vue Le nom de la vue à charger. (garder l'extension .fxml)
     */
    public static void charger(String vue) {
        Quiz quiz = Quiz.getInstance();
        try {
            Parent racine 
            = FXMLLoader.load(quiz.getClass().getResource("vue/" + vue));
            
            scenes.put(vue, new Scene(racine));
            
        } catch (IOException e) {
            System.err.println("Chargement impossible de : " + vue);
            e.printStackTrace(); //TODO Enlever
        } catch (NullPointerException e) {
            System.err.println("Page introuvale : " + vue);
            e.printStackTrace();
        }
    }

    /** @return valeur de scenes */
    public static Scene getScene(String vue) {
        return scenes.get(vue);
    }

    /** 
     * TODO comment method role
     */
    public static void initialiserScene() {
        scenes = new HashMap<String, Scene>();
    }
    
    /**
     * Gestion du changement de fenêtre
     * @param fenetre (String) le nom de la fenêtre a charger en .fxml
     */
    public static void changerVue(String fenetre) {
        System.out.println(fenetre);
        Quiz.fenetrePrincipale.setTitle("Quizéo - " + fenetre.split(".fxml")[0]);
        Quiz.fenetrePrincipale.setScene(GestionVues.getScene(fenetre));
        
        Quiz.fenetrePrincipale.show();
    }

}
