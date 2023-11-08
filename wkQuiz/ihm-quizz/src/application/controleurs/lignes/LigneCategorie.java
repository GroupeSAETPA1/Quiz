package application.controleurs.lignes;

import javafx.beans.property.SimpleStringProperty;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * classe permettant l'affichage des lignes dans la tableView de la vue EditerCategories
 * @author quentin COSTES
 */
public class LigneCategorie {
	
    private final SimpleStringProperty nomProperty;
    private final SimpleIntegerProperty nbProperty;
    private final Button editerButton;
    private final Button supprimerButton;

    public LigneCategorie(String nom, int nb) {
        this.nomProperty = new SimpleStringProperty(nom);
        this.nbProperty = new SimpleIntegerProperty(nb);
        this.editerButton = new Button("Éditer");
        this.supprimerButton = new Button("Supprimer");

        editerButton.setOnAction(event -> editerCategorie());
        supprimerButton.setOnAction(event -> supprimerCategorie());
    }

    public String getNomProperty() {
        return nomProperty.get();
    }

    public SimpleStringProperty nomPropertyProperty() {
        return nomProperty;
    }

    public int getNbProperty() {
        return nbProperty.get();
    }

    public SimpleIntegerProperty nbPropertyProperty() {
        return nbProperty;
    }

    public Button getEditerButton() {
        return editerButton;
    }

    public Button getSupprimerButton() {
        return supprimerButton;
    }

    public void editerCategorie(){
    	// méthode appelée lors de l'appuie surle bouton d'edition de la categorie
    	if (this.getNomProperty() != "Général") {
    		// si la quesiotn n'est pas général
    		ModelePrincipal.getInstance().setCategorieAModifier(ModelePrincipal.getInstance().getBanqueCategorie().getExactCategoriesLibelle(getNomProperty()));
    		Quiz.chargerEtChangerVue("EditerCategorie.fxml");	
    	} else {
    		AlertBox.showErrorBox("la catégorie générale ne peut pas etre modifiée");
    	}
    	
    }

    public void supprimerCategorie() {
    	if (this.getNomProperty() != "Général") {
    		boolean result = AlertBox.showConfirmationBox("supprimer la categorie : " + nomProperty.get());
	    	if (result) {
	    		if (ModelePrincipal.getInstance().supprimerCategorie(ModelePrincipal.getInstance().getBanqueCategorie().getExactCategoriesLibelle(getNomProperty()))) {
	    			AlertBox.showSuccessBox("suppresion effectuée");
	        		Quiz.chargerEtChangerVue("EditerCategories.fxml");
	    		} else { 
	    			AlertBox.showErrorBox("suppression échouée de " + getNomProperty());
	    		}
	    	} else {
	    		AlertBox.showErrorBox("suppression annulée");
	    	}
    	} else {	
	    	AlertBox.showErrorBox("Vous ne pouvez modifier la catégorie principale");
    	}
    }
}
