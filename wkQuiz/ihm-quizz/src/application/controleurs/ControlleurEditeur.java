package application.controleurs;

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
	  
	@FXML 
	private void importer() {
		System.out.println("Importer des question");
	}
	
	@FXML 
	private void creer() {
		System.out.println("Creer une categorie ou une question ");
	}
	
	@FXML 
	private void editerQuestion() {
		System.out.println("Editer une question ");
	}
	
	@FXML 
	private void editerCategorie() {
		System.out.println("Editer une Catégorie ");
	}
	
	@FXML 
	private void accueil() {
		System.out.println("Retour a l'accueil");
	}
}
