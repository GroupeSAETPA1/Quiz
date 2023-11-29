/*
 * ControleurModeEnLigne.java                               
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application.controleurs.reseau;

import application.Quiz;
import javafx.fxml.FXML;


public class ControleurModeEnLigne {

	@FXML
    void retour() {
    	Quiz.changerVue("Accueil.fxml");
    }
	
	@FXML
	void envoyer() {
		Quiz.changerVue("ChoixEnvoie.fxml");
	}
	
	@FXML
	void recevoir() {
		Quiz.changerVue("RecevoirQuestions.fxml");
	}
}
