package application.controleurs;

import application.Quiz;
import javafx.fxml.FXML;

/**
 * Controlleur de la page d'Edition.
 * Celui-ci instancie des méthodes liées aux boutons de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */

public class ControlleurEditeur {
	 
	/**
	 * Méthode liée au bouton importer des question 
	 * qui devra envoyer sur la page ImporterQuestion.fxml
	 */
	@FXML 
	private void importer() {
		System.out.println("Importer des question");
//		Quiz.changerVue("ImporterQuestion.fxml");
	}
	
	/**
	 * Méthode liée au bouton créer
	 * qui devra envoyer sur la page CreationQuestionEtCategorie.fxml
	 */
	@FXML 
	private void creer() {
		System.out.println("Creer une categorie ou une question ");
		Quiz.changerVue("CreationQuestionEtCategorie.fxml");
	}
	
	/**
	 * Méthode liée au bouton editer une question, 
	 * qui devra envoyer sur la page EditerQuestion.fxml
	 */
	@FXML 
	private void editerQuestion() {
		System.out.println("Editer une question ");
		Quiz.changerVue("EditerQuestions.fxml");
	}
	
	/**
	 * Méthode liée au bouton editer une categorie
	 * qui devra envoyer sur la page EditerCategorie.fxml
	 */
	@FXML 
	private void editerCategorie() {
		System.out.println("Editer une Catégorie ");
		Quiz.changerVue("EditerCategories.fxml");
	}
	
	/**
	 * Méthode liée au groupe accueil 
	 * qui devra envoyer sur la page Accueil.fxml
	 */
	@FXML 
	private void accueil() {
		System.out.println("Retour a l'accueil");
		Quiz.changerVue("Accueil.fxml");
	}
}
