package application.controleurs.lignes;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * classe permettant l'affichage des lignes dans la tableView de la vue EditerQuestions
 * @author quentin COSTES
 */
public class LigneQuestion {
	
    private final SimpleStringProperty categorie;
    private final SimpleStringProperty nomQuestion;
    private final SimpleStringProperty reponseJuste;
    private final SimpleStringProperty reponsesFausses;
    private final SimpleStringProperty feedback;
    private final Button editerButton;
    private final Button supprimerButton;

    public LigneQuestion(String nom, String nomQuestion, String reponseJuste, ArrayList<String> reponsesFausses, String feedback) {
        this.categorie = new SimpleStringProperty(nom);
        this.nomQuestion = new SimpleStringProperty(nomQuestion);
        this.reponseJuste = new SimpleStringProperty(reponseJuste);
        this.feedback = new SimpleStringProperty(feedback);
        
        // on baillaie les reponses fausses pour les concatener dans une seule chaine de caractere
        String reponsesFaussesString = "";
        for (String reponse : reponsesFausses) {
        	reponsesFaussesString += reponse + "\n";
        }
        this.reponsesFausses = new SimpleStringProperty(reponsesFaussesString);
        
        this.editerButton = new Button("Éditer");
        this.supprimerButton = new Button("Supprimer");
        editerButton.setOnAction(event -> editerQuestion());
        supprimerButton.setOnAction(event -> supprimerQuestion()));
    }

    public void editerQuestion(){
    	// méthode appelée lors de l'appuie surle bouton d'edition de la categorie
    	
    	
    }

    public void supprimerQuesion() {
    	// méthode appelée lors de l'appuie sur le bouton de suppression de la categorie
    	
    }
}
