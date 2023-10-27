package application.controleurs;

import application.Quiz;
import javafx.fxml.FXML;

/**
 * Controlleur de la page d'accueil.
 * Celui-ci instance  des methodes liée au bouton de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */


public class ControlleurAccueil {
	
	/**
	 * Methodes liée au bouton jouer , 
	 * qui devra renvoyer vers la page ParametrePartie.fxml 
	 */
	@FXML 
	private void jouer() {
		System.out.println("Button jouer");
	}
	
	
	/**
	 * Methodes liée au bouton éditer, 
	 * qui devra envoyer vers la page Editeur.fxml 
	 */
	@FXML 
	private void editer() {
		System.out.println("Button editer");
		Quiz.changerVue("Editeur.fxml");
	}
	
	/**
	 * Méthodes liée au boutons online,
	 * qui devra envoyer vers la page ModeEnligne.fxml
	 */
	@FXML 
	private void online() {
		System.out.println("Button en ligne");
	}
	
	/**
	 * Méthodes liée au group quitter,
	 * qui devra fermer l'application
	 */
	@FXML
	private void quitter() {
		System.out.println("Quitter");
		Quiz.quitter();
	}
	
	/**
	 * Méthodes liée au groupe aider,
	 * qui devra envoyer vers la page Aide.fxml
	 */
	@FXML
	private void aider() {
		System.out.println("Aider");
	}

}
