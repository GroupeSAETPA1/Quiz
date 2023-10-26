package application.controleurs;

import application.Quiz;
import javafx.fxml.FXML;

/**
 * Controlleur de la page d'Edition.
 * Celui-ci instance  des methodes liée au bouton de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */

public class ControlleurEditeur {
	 
	/**
	 * Méthode liée au bouton importer des question, 
	 * qui devra envoyer sur la page ImporterQuestion.fxml
	 */
	@FXML 
	private void importer() {
		System.out.println("Importer des question");
		//Quiz.changerVue("ImporterQuestion.fxml");
	}
	
	/**
	 *  Méthode liée au bouton Créer , 
	 * qui devra envoyer sur la page CreationQuestionetCategorie.fxml
	 */
	@FXML 
	private void creer() {
		System.out.println("Creer une categorie ou une question ");
		//Quiz.changerVue("CreationQuestionetCategorie.fxml");
	}
	
	/**
	 *  Méthode liée au bouton Editer une question, 
	 * qui devra envoyer sur la page EditerQuestion.fxml
	 */
	@FXML 
	private void editerQuestion() {
		System.out.println("Editer une question ");
		//Quiz.changerVue("EditerQuestion.fxml");
	}
	
	/**
	 *  Méthode liée au bouton editer une categorie , 
	 * qui devra envoyer sur la page EditerCategorie.fxml
	 */
	@FXML 
	private void editerCategorie() {
		System.out.println("Editer une Catégorie ");
		//Quiz.changerVue("EditerCategorie.fxml");
	}
	
	/**
	 * Méthode liée au group Accueil, 
	 * qui devra envoyer sur la page Accueile.fxml
	 */
	@FXML 
	private void accueil() {
		System.out.println("Retour a l'accueil");
		//Quiz.changerVue(" Accueile.fxml");
	}
}
