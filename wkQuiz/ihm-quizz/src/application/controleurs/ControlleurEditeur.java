package application.controleurs;

import javafx.fxml.FXML;

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
