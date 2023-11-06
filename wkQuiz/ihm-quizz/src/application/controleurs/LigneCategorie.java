package application.controleurs;

import javafx.beans.property.SimpleStringProperty;
import application.Quiz;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;

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

    public void editerCategorie() {
        // Action d'édition
    	System.out.println("edition de " + nomProperty.get());
    	ModelePrincipal.getInstance().setCategorieAModifier(ModelePrincipal.getInstance().getBanqueCategorie().getExactCategoriesLibelle(getNomProperty()));
    	Quiz.changerVue("EditerCategorie.fxml");
    }

    public void supprimerCategorie() {
        // Action de suppression
    	System.out.println("suppresion de " + nomProperty.get());
    	boolean result = AlertBox.showConfirmationBox("supprimer la categorie : " + nomProperty.get());
    	if (result) {
    		if (ModelePrincipal.getInstance().getBanqueCategorie().supprimerCategorieLibelle(getNomProperty())) {
    			AlertBox.showSuccessBox("suppresion effectuée");
    		} else {
    			AlertBox.showErrorBox("suppression échouée");
    		}
    	} else {
    		AlertBox.showErrorBox("suppression annulée");
    	}
    }
}
