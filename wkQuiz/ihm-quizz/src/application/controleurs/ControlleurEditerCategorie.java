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
import application.exception.HomonymeException;
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
		Categorie aModifier = ModelePrincipal.getInstance().getCategorieAModifier();
		ModelePrincipal.getInstance().getBanqueCategorie().getExactCategoriesLibelle(aModifier.getNom()).setNom(input.getText());
		AlertBox.showSuccessBox("categorie modifiée avec succées");
		try {
			Quiz.getInstance().charger("EditerCategories.fxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		Categorie aModifier = ModelePrincipal.getInstance().getCategorieAModifier();
		if (aModifier != null) {
			input.setText(aModifier.getNom());
		}
	}
}