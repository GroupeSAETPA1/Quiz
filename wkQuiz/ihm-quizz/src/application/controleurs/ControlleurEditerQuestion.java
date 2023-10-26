package application.controleurs;

import application.Quiz;
import javafx.fxml.FXML;

/**
 * Controlleur de la page d'édition des questions.
 * Celui-ci instance  des methodes liée au bouton de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */

public class ControlleurEditerQuestion {
	
	/**
	 * Méthodes liée au group retour 
	 * qui devra renvoyer vers la page précedentes 
	 */
	@FXML 
	private void retour() {
		System.out.println("Retour en arriere ");
		//Quiz.changerVue(" Editeur.fxml");
	}
	/**
	 * Méthodes liée au button Créer Question
	 * qui devra renvoyer vers la page CreationQuestionEtCategorie.fxml
	 */
	@FXML 
	private void versCreerQuestion() {
		System.out.println("swalalala nous sommes partie pour créer");
		//Quiz.changerVue("CreationQuestionEtCategorie.fxml");
	}

}
