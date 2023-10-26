package application.controleurs;

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
	
	
	@FXML 
	private void jouer() {
		System.out.println("Button jouer");
	}
	
	@FXML 
	private void editer() {
		System.out.println("Button editer");
	}
	
	@FXML 
	private void online() {
		System.out.println("Button en ligne");
	}
	
	@FXML
	private void quitter() {
		System.out.println("Quitter");
	}
	
	@FXML
	private void aider() {
		System.out.println("Aider");
	}

}
