/*
 * ControleurEditerCategorie.java                                     
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application.controleurs.editeur;

import application.Quiz;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controleur de la page d'édition des catégories.
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
		AlertBox.showSuccessBox("Modification annulée");
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
				
		if (modele.categorieExiste(input.getText()) 
			|| modele.getCategorieAModifier().getNom().equals(input.getText())) {
			
			AlertBox.showErrorBox("La catégorie est déjà existante ou vous " 
			+ "n'avez pas modifié le nom de la catégorie. \nVeuillez réessayer");
		} else {
			try {
				modele.modifierCategorie(input.getText());
				AlertBox.showSuccessBox("Catégorie modifiée avec succès !");

				Quiz.chargerEtChangerVue("EditerCategories.fxml");
			} catch (InvalidNameException | HomonymeException e) {
				AlertBox.showErrorBox(e.getMessage());
			}
			
		}
	}
	
    /**
     * Méthode pour annuler la modification du libellé
     * et qui renvoie sur EditerCategories.fxml
     */
	@FXML
	private void annuler() {
		AlertBox.showSuccessBox("Modification annulée");
		Quiz.changerVue("EditerCategories.fxml");
	}
}