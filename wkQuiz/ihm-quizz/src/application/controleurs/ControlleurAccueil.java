package application.controleurs;

import application.Quiz;
import application.modele.ModelePrincipal;
import javafx.fxml.FXML;

/**
 * Controlleur de la page d'accueil.
 * Celui-ci instancie des méthodes liées aux boutons de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */


public class ControlleurAccueil {
	
	/**
	 * Méthode liée au bouton jouer 
	 * qui devra renvoyer vers la page ParametrePartie.fxml 
	 */
	@FXML 
	private void jouer() {
		Quiz.chargerEtChangerVue("ParametrePartie.fxml");
	}
	
	
	/**
	 * Methode liée au bouton éditer 
	 * qui devra envoyer vers la page Editeur.fxml 
	 */
	@FXML 
	private void editer() {
		Quiz.changerVue("Editeur.fxml");
	}
	
	/**
	 * Méthode liée au bouton en ligne
	 * qui devra envoyer vers la page ModeEnligne.fxml
	 */
	@FXML 
	private void online() {
		System.out.println("Button en ligne");
	}
	
	/**
	 * Méthode liée au groupe quitter,
	 * qui devra fermer l'application
	 */
	@FXML
	private void quitter() {
		Quiz.quitter();
	}
	
	/**
	 * Méthode liée au groupe aider,
	 * qui devra envoyer vers la page Aide.fxml
	 */
	@FXML
	private void aider() {
		System.out.println("Aider");
		// TODO : lancer une alertBox
	}

}
