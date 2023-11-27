package application.controleurs;

import application.Quiz;
import application.exception.InvalidNameException;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controlleur de la page d'édition des catégories.
 * Celui-ci instance  des méthodes liée au bouton de la page 
 * 
 * @author Quentin COSTES
 */

public class ControleurEditerCategorie {
	
	@FXML
	private TextField input;
			
	/**
     * Méthode qui se lance au chargement de la page 
     * pour récupérer la catégorie à éditer
     */
    @FXML
    public void initialize(){
    	Categorie aModifier 
    	= ModelePrincipal.getInstance().getCategorieAModifier();
    	
    	if (aModifier != null) {
    		input.setText(aModifier.getNom());
    	}
    }

    /**
	 * Méthode liée au groupe retour 
	 * qui devra renvoyer sur EditerCategories.fxml
	 */
	@FXML 
	private void retour() {
		Quiz.changerVue("EditerCategories.fxml");
	}
	
	/**
	 * Méthode pour valider et modifier le libellé de la catégorie
	 * @throws InvalidNameException si le libellé est invalide 
	 * (voir {@link application.modele.Categorie} ) 
	 * et qui renvoie sur EditerCategories.fxml
	 */
	@FXML
	private void valider() throws InvalidNameException {
		ModelePrincipal modele = ModelePrincipal.getInstance();
		Categorie aModifier = modele.getCategorieAModifier();
		
		//TODO utiliser la fonction modifierCategorie de ModelePrincipal
		
		
		if( modele.categorieExiste(input.getText()) 
			||  modele.getCategorieAModifier().getNom().equals(input.getText())) {
			
			AlertBox.showErrorBox("La Catégorie est déjà existante ");
		}else {
			modele.getCategoriesLibelleExact(aModifier.getNom()).setNom(input.getText());
			AlertBox.showSuccessBox("Catégorie modifiée avec succées");

			Quiz.chargerEtChangerVue("EditerCategories.fxml");
		}
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
}