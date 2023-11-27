/*
 * ControleurSelectionCategorie.java                                    20 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.util.ArrayList;

import application.Quiz;
import application.controleurs.factories.TextCellFactory;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * Contrôleur de la page SelectionQuestion.fxml
 * 
 * @author François de Saint Palais
 */
public class ControleurSelectionCategorie {

    @FXML
    TableView<LigneSelectionCategorie> tableView;

    @FXML
    TableColumn<LigneSelectionCategorie, String> nomCategorie;
    @FXML
    TableColumn<LigneSelectionCategorie, String> nombreQuestion;
    @FXML
    TableColumn<LigneSelectionCategorie, CheckBox> selection;

    @FXML
    public void retour() {
        Quiz.chargerEtChangerVue("ChoixEnvoie.fxml");
    }
    
    @FXML
    public void valider() {
    	Quiz.chargerEtChangerVue("Recapitulatif.fxml");
    }

    @FXML
    public void initialize() {

        nomCategorie.setCellValueFactory(new PropertyValueFactory<LigneSelectionCategorie, String>("nomCategorie"));
        nombreQuestion.setCellValueFactory(new PropertyValueFactory<LigneSelectionCategorie, String>("nombreQuestion"));

        nomCategorie.setCellFactory(new TextCellFactory<LigneSelectionCategorie>());
        nombreQuestion.setCellFactory(new TextCellFactory<LigneSelectionCategorie>());

        selection.setCellFactory(new CheckBoxCategorieCellFactory());

        miseAJourTableau();
    }

    /**
     * Modifie le tableau des catégories
     */
    private void miseAJourTableau() {
        ModelePrincipal modele = ModelePrincipal.getInstance();

        ObservableList<LigneSelectionCategorie> data = tableView.getItems();

        ArrayList<Categorie> categories 
        = modele.getBanqueCategorie().getCategories();
        
        for (Categorie categorie : categories) {
            data.add(new LigneSelectionCategorie(categorie));
        }
    }

    /**
     * Représente une ligne dans la tableView
     * 
     * @author François de Saint Palais
     */

    public class LigneSelectionCategorie {

        ModelePrincipal modele = ModelePrincipal.getInstance();
        
        private Categorie categorie;

        /**
         * TODO comment initial state properties
         * 
         * @param nomCategorie
         * @param nombreQuestion
         * @param selection
         */
        public LigneSelectionCategorie(Categorie categorie) {
            super();
            this.categorie = categorie;
        }

        /** @return valeur de nomCategorie */
        public String getNomCategorie() {
            return categorie.getNom();
        }

        /** @return valeur de nombreQuestion */
        public String getNombreQuestion() {
            return modele.getNombreQuestionCategorie(categorie) + "";
        }

        public void ajouterALaSelection() {
            System.out.println(this + " selectionner");
            modele.ajouterALaSelectionDEnvoie(categorie);
        }

        /**
         * TODO comment method role
         */
        public void retirerALaSelection() {
            System.out.println(this + " deselectionner");
            modele.supprimerALaSelectionDEnvoie(categorie);
        }

        /* non javadoc - @see java.lang.Object#toString() */
        @Override
        public String toString() {
            return getNomCategorie() + " -> " + getNombreQuestion();
        }

        /** @return true si la checkbox doit être sélectionner */
        public boolean estSelectionner() {
            return modele.estAEnvoyer(categorie);
        }
    }

    /**
     * Construit et gère les actions sur les CheckBox
     * @author François de Saint Palais
     */
    public class CheckBoxCategorieCellFactory implements
            Callback<TableColumn<LigneSelectionCategorie, CheckBox>, TableCell<LigneSelectionCategorie, CheckBox>> {

        /* non javadoc - @see javafx.util.Callback#call(java.lang.Object) */
        @Override
        public TableCell<LigneSelectionCategorie, CheckBox> call(TableColumn<LigneSelectionCategorie, CheckBox> arg0) {
            return new TableCell<LigneSelectionCategorie, CheckBox>() {
                @Override
                protected void updateItem(CheckBox item, boolean empty) {
                    super.updateItem(item, empty);
                    super.setAlignment(Pos.CENTER);
                    // On créer une CheckBox
                    CheckBox checkbox = new CheckBox();
                    
                    /* Lors de la création des lignes, 
                     * la TableView commence à l'index -1 => Exception */
                    try {
                        checkbox.setSelected(getTableView().getItems()
                                .get( getIndex() ).estSelectionner());
                    } catch (IndexOutOfBoundsException e) { }

                    // On ajoute l'évenement lié au cochage/décochage
                    checkbox.setOnAction(event -> {
                        // La ligne de la checkbox
                        LigneSelectionCategorie ligne = getTableView().getItems().get(getIndex());
                        if (checkbox.isSelected()) {
                            ligne.ajouterALaSelection();
                        } else {
                            ligne.retirerALaSelection();
                        }
                    });

                    if (getIndex() >= getTableView().getItems().size()) {
                        setGraphic(null);                        
                    } else {
                        setGraphic(checkbox);
                    }
                }
            };
        }

    }
}

