package application.controleurs.reseau;

import application.Quiz;
import javafx.fxml.FXML;


public class ControleurModeEnLigne {


	
	@FXML
    void retour() {
    	Quiz.changerVue("Accueil.fxml");
        System.out.println("Retour");
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
