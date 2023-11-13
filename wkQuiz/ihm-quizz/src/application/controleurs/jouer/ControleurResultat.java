package application.controleurs.jouer;

import application.Quiz;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controlleur de la page Résultat
 * Celui-ci instancie des méthodes liées aux boutons de la page 
 * 
 * @author Tom DOUAUD
 */


public class ControleurResultat {
		
	/**
	 * Le texte du score réalisé par le joueur
	 */
	@FXML
	private Label score;
	
	/**
	 * Le texte en relation avec la performance du joueur
	 */
	@FXML
	private Label messageNBReponse;
	
	/**
	 * Méthode liée au bouton éditer 
	 * qui devra envoyer vers la page Solution.fxml 
	 */
	@FXML 
	private void voirReponses() {
		System.out.println("Button voirReponses");
		// TODO rajouter le lien vers Quiz.changerVue("Solution.fxml");
	}

	/**
	 * Méthode liée au bouton retourner à l'acceuil
	 * qui devra envoyer vers la page Accueil.fxml
	 */
	@FXML
	private void retourAccueil() {
		System.out.println("Button retourAccueil");
		Quiz.changerVue("Accueil.fxml");
	}
	
	/**
	 * Méthode exécutée au chargement de la page Resultat
	 * pour récuperer le score et le message
	 */
	@FXML
	public void initialize() {
		// TODO lier le score au vrai score et charger le bon texte selon la performance
		messageNBReponse.setText("Message approprié a écrire et relier (et centrer aussi ca mange pas de pain");
		score.setText("Score = TODO/TODO");
	}

}