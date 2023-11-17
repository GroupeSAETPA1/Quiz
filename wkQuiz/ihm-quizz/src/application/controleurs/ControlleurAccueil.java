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
	
	private ModelePrincipal model = ModelePrincipal.getInstance();
	
	/**
	 * Méthode liée au bouton jouer 
	 * envoie vers la page ParametrePartie.fxml 
	 */
	@FXML 
	private void jouer() {
		Quiz.chargerEtChangerVue("ParametrePartie.fxml");
	}
	
	
	/**
	 * Methode liée au bouton éditer 
	 * envoie vers la page Editeur.fxml 
	 */
	@FXML 
	private void editer() {
		Quiz.changerVue("Editeur.fxml");
	}
	
	
	/**
	 * Méthode liée au bouton en ligne
	 * envoie vers la page ModeEnligne.fxml
	 */
	@FXML 
	private void online() {
		//TODO
	}
	
	/**
	 * Méthode liée au groupe quitter,
	 * ferme l'application
	 */
	@FXML
	private void quitter() {
		Quiz.quitter();
	}
	
	/**
	 * Méthode liée au groupe aider,
	 * envoie vers la page Aide.fxml
	 */
	@FXML
	private void aider() {
		model.setPagePrecedente("Accueil.fxml");
		System.out.println("Aider");
		Quiz.chargerEtChangerVue("Aide.fxml");

		// TODO : lancer une alertBox
	}

}
