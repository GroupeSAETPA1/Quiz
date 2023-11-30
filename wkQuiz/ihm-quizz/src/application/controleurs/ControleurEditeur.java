package application.controleurs;

import application.Quiz;
import javafx.fxml.FXML;

/**
 * Controleur de la page d'Edition.
 * Celui-ci instancie des méthodes liées aux boutons de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */

public class ControleurEditeur {
	 
	/**
	 * Méthode liée au bouton importer des questions
	 * qui envoie sur la page ImporterQuestion.fxml
	 */
	@FXML 
	private void importer() {
		Quiz.changerVue("ImporterQuestion.fxml");
	}
	
	/**
	 * Méthode liée au bouton créer
	 * qui envoie sur la page CreationQuestionEtCategorie.fxml
	 */
	@FXML 
	private void creer() {
		Quiz.changerVue("CreationQuestionEtCategorie.fxml");
	}
	
	/**
	 * Méthode liée au bouton éditer une question, 
	 * qui envoie sur la page EditerQuestion.fxml
	 */
	@FXML 
	private void editerQuestion() {
		Quiz.chargerEtChangerVue("EditerQuestions.fxml");
	}
	
	/**
	 * Méthode liée au bouton éditer une catégorie
	 * qui envoie sur la page EditerCategorie.fxml
	 */
	@FXML 
	private void editerCategorie() {
		Quiz.chargerEtChangerVue("EditerCategories.fxml");
	}
	
	/**
	 * Méthode liée au groupe accueil 
	 * qui envoie sur la page Accueil.fxml
	 */
	@FXML 
	private void accueil() {
		Quiz.changerVue("Accueil.fxml");
	}
}