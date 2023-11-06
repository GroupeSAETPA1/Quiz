package application.controleurs;

import java.util.ArrayList;

import application.Quiz;
import application.exception.InvalidNameException;
import application.modele.Categorie;
import javafx.fxml.FXML;

/**
 * Controlleur de la page d'édition des questions.
 * Celui-ci instance  des methodes liée au bouton de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */

public class ControlleurEditerQuestion {
	
	/**
	 * Méthodes liée au bouton valider,
	 * qui devra enregister les champs  dans la banques de question 
	 */
	@FXML 
	private ArrayList<Categorie> categories;
	
	/**
	 * Méthodes liée au group retour 
	 * qui devra renvoyer vers la page précedentes 
	 */
	@FXML 
	private void retour() {
		System.out.println("Retour en arriere ");
		Quiz.changerVue("Editeur.fxml");
	}
	/**
	 * Méthodes liée au button Créer Question
	 * qui devra renvoyer vers la page CreationQuestionEtCategorie.fxml
	 */
	@FXML 
	private void versCreerQuestion() {
		System.out.println("swalalala nous sommes partie pour créer");
		Quiz.changerVue("CreationQuestionEtCategorie.fxml");
	}
	
	@FXML
	public void initialize() throws InvalidNameException {
		// TODO: changer categorie pour qu'elle contiene les vraie categories
		// BanqueCategorie.getCategories();
	    // STUB
        categories = new ArrayList<>();
        categories.add(new Categorie("test 1"));
        categories.add(new Categorie("test 2"));
        categories.add(new Categorie("test 3"));

        for (Categorie categorie : categories) {
        	 selectCategorie.getItems().add(categorie);
		}

    }

}
