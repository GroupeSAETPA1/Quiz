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
	 * Méthode liée au groupe retour 
	 * qui devra renvoyer sur EditerCategories.fxml
	 */
	@FXML 
	private void retour() {
		System.out.println("Groupe retour cliqué");
		Quiz.changerVue("EditerCategories.fxml");
	}
	
	/**
	 * Méthode pour valider et modifier le libellé de la catégorie
	 * @throws InvalidNameException si le libellé est invalide (voir Categorie.java)
	 * et qui renvoie sur EditerCategories.fxml
	 */
	@FXML
	private void valider() throws InvalidNameException {
		Categorie aModifier = ModelePrincipal.getInstance().getCategorieAModifier();
		ModelePrincipal.getInstance().getBanqueCategorie().getExactCategoriesLibelle(aModifier.getNom()).setNom(input.getText());
		AlertBox.showSuccessBox("Categorie modifiée avec succées");
		try {
			Quiz.getInstance().charger("EditerCategories.fxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Quiz.chargerEtChangerVue("EditerCategories.fxml");
	}
	
    /**
     * Méthode pour annuler la modification du libellé
     * et qui renvoie sur EditerCategories.fxml
     */
	@FXML
	private void annuler() {
		AlertBox.showErrorBox("Modification annulée");
		Quiz.changerVue("EditerCategories.fxml");
	}

	/**
	 * Méthode qui se lance au chargement de la page pour récuperer la catégorie à éditer
	 */
	@FXML
	public void initialize(){
		Categorie aModifier = ModelePrincipal.getInstance().getCategorieAModifier();
		if (aModifier != null) {
			input.setText(aModifier.getNom());
		}
	}
}