/*
 * ControleurChoixEnvoie.java                               
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application.controleurs.reseau;

import application.Quiz;
import application.modele.ModelePrincipal;
import javafx.fxml.FXML;

public class ControleurChoixEnvoie {
	
	private ModelePrincipal modele = ModelePrincipal.getInstance();
	
	@FXML
    void retour() {
    	Quiz.changerVue("ModeEnLigne.fxml");
        System.out.println("Retour");
    }
    
    /**
	 * Méthode liée au groupe aider,
	 * envoie vers la page Aide.fxml
	 */
	@FXML
	private void aider() {
		modele.setPagePrecedente("ChoixEnvoie.fxml");
		System.out.println("Aider");
		Quiz.chargerEtChangerVue("Aide.fxml");
	}
	
	@FXML
	private void envoieTous() {
	    modele.toutEnvoyer();
		Quiz.chargerEtChangerVue("EnvoieQuestion.fxml");
	}
	
	@FXML
	private void envoieQuestion() {
		Quiz.chargerEtChangerVue("ChoixQuestionExport.fxml");
	}
	
	@FXML
	private void envoieCategorie() {
		Quiz.chargerEtChangerVue("ChoixQuestionCategorieExport.fxml");
	}
}
