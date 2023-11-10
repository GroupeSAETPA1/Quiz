package application.controleurs;

import java.util.ArrayList;

import application.Quiz;
import application.exception.InvalidNameException;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Question;
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
		Question aModifier = ModelePrincipal.getInstance().getQuestionAModifier();
		
    }

}
