package application.controleurs;

import javafx.fxml.FXML;

/**
 * Controlleur de la page Creation de Quetion et de Categorie.
 * Celui-ci instance  des methodes liée au bouton de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */


public class ControlleurCreationQuestionEtCategorie {
	
	/**
	 * Méthodes liée au bouton Accueil
	 * qui devra renvoyer vers la page Accueil.fxml
	 */
	@FXML 
	private void retourAcceuil() {
		System.out.println("Button retour à l'acceuil");
	}
	
	/**
	 * Méthodes liée au bouton annuler,
	 * qui devra vider les champs et renvoyer vers la page Accueil.fxml
	 */
	@FXML 
	private void annuler() {
		System.out.println("Annuler");
	}
	
	/**
	 * Méthodes liée au bouton valider,
	 * qui devra enregister les champs  dans la banques de question 
	 */
	@FXML 
	private void valider() {
		System.out.println("Valider");
	}
	
	// TODO array pour liste deroulante 
	
	
	
}
