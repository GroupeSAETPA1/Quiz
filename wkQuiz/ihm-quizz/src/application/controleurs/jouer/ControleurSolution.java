package application.controleurs.jouer;

import application.Quiz;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Controleur de la page Solution
 * Celui-ci instancie des méthodes liées aux boutons de la page 
 * 
 * @author Tom DOUAUD
 */
public class ControleurSolution {
		
	/**
	 * Le texte du score réalisé par le joueur
	 */
	@FXML
	private Label score;
	
	/**
	 * Le tableau de la solution du quiz, avec une indication si le joueur a eu bon
	 */
	@FXML
	private GridPane tableauSolution;
	
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
	 * Méthode exécutée au chargement de la page Solution
	 * pour récuperer le score et le message
	 */
	@FXML
	public void initialize() {
		// TODO initialiser le tableau avec les valeurs de la partie
		score.setText("Score = TODO/TODO");
	}

}