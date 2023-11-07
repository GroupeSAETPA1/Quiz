package application.controleurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;

import java.util.ArrayList;

import application.Quiz;
import application.exception.InvalidNameException;

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
	
	@FXML
	private TextField input;
			
	/**
	 * Méthodes liée au group retour 
	 * qui devra renvoyer vers la page precedente
	 */
	
	@FXML 
	private void retour() {
		System.out.println("sortir");
		Quiz.changerVue("EditerCategories.fxml");
	}
	
	@FXML
	private void valider() throws InvalidNameException {
		System.out.println("pour l'instant ça ne marche pas car la categorie n'existe pas vraiment");
		ModelePrincipal.getInstance().getBanqueCategorie().getExactCategoriesLibelle(ModelePrincipal.getInstance().getCategorieAModifier().getNom()).setNom(input.getText());
		AlertBox.showSuccessBox("categorie modifiée avec succées");
		Quiz.changerVue("EditerCategories.fxml");
		// TODO je pense qu'il faut recharger la vue non ?
	}
	
	@FXML
	private void annuler() {
		AlertBox.showErrorBox("modification annulée");
		Quiz.changerVue("EditerCategories.fxml");
	}

	
	@FXML
	public void initialize(){
		if (ModelePrincipal.getInstance().getCategorieAModifier() != null) {
			input.setText(ModelePrincipal.getInstance().getCategorieAModifier().getNom());
		}	
	}
}