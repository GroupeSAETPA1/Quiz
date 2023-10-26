package application.controleurs;

import javafx.fxml.FXML;

/**
 * Controlleur de la page d'édition des catégories.
 * Celui-ci instance  des methodes liée au bouton de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */

public class ControlleurEditerCategorie {
	
	/**
	 * Méthodes liée au group retour 
	 * qui devra renvoyer vers la page precedente
	 */
	
	@FXML 
	private void retour() {
		System.out.println("Retour en arriere ");
	}
	/**
	 * Méthodes liée au bouton Créer Categorie 
	 * qui devra envoyer vers la page CreationQuestionEtCategorie.fxml
	 */
	@FXML 
	private void versCreerCategorie() {
		System.out.println("swalalala nous sommes partie pour créer");
	}
}
