package application.controleurs;

import java.util.ArrayList;

import application.modele.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

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
<<<<<<< HEAD
	
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
=======

	private ArrayList<Categorie> categories;

	@FXML
	private ComboBox selectCategorie;

	@FXML
	private void retourAcceuil() {
		System.out.println("Button retour à l'acceuil");
	}
	@FXML
	private void annuler() {
		System.out.println("Annuler");
	}

	@FXML
>>>>>>> 607820f20bf3d9880b5ac7d192aea37eee11e44d
	private void valider() {
		System.out.println("Valider");
	}

	@FXML
	public void initialize(){
		// TODO: changer categorie pour qu'elle contiene les vraie categories
		// BanqueCategorie.getCategories();
        categories = new ArrayList<>();
        categories.add(new Categorie("test 1"));
        categories.add(new Categorie("test 2"));
        categories.add(new Categorie("test 2"));

        for (Categorie categorie : categories) {
        	 selectCategorie.getItems().add(categorie);
		}

    }


}
