/*
 * ControleurSelectionCategorie.java                                    20 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.util.ArrayList;

import application.controleurs.factories.CheckBoxCategorieCellFactory;
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

/** 
 * Controleur de la page SelectionQuestion.fxml
 * @author François de Saint Palais
 */
public class ControleurSelectionCategorie {
    
    @FXML TableView<LigneSelectionCategorie> tableView;
    
    @FXML TableColumn<LigneSelectionCategorie, String> nomCategorie;
    @FXML TableColumn<LigneSelectionCategorie, String> nombreQuestion;
    @FXML TableColumn<LigneSelectionCategorie, CheckBox> selection;

    @FXML
    public void initialize() {
        
        nomCategorie.setCellValueFactory(
                new PropertyValueFactory<LigneSelectionCategorie, String>
                ("nomCategorie"));
        
        nomCategorie.setCellFactory(tc -> {
            TableCell<LigneSelectionCategorie, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            cell.setWrapText(true);
            return cell;
        });
        
        
        nombreQuestion.setCellValueFactory(
                new PropertyValueFactory<LigneSelectionCategorie, String>
                ("nombreQuestion"));
        
        nombreQuestion.setCellFactory(tc->{
            TableCell<LigneSelectionCategorie, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            return cell;
        });
        
        
        selection.setCellFactory(new CheckBoxCategorieCellFactory());
        
        miseAJourTableau();
    }
    
    /** 
     * Modifie le tableau des catégories
     */
    private void miseAJourTableau() {
        ModelePrincipal modele = ModelePrincipal.getInstance();

        ObservableList<LigneSelectionCategorie> data = tableView.getItems();

        ArrayList<Categorie> categories = modele.getBanqueCategorie().getCategories();

        for (Categorie categorie : categories) {
            data.add(new LigneSelectionCategorie(categorie.getNom(), 
                    modele.getNombreQuestionCategorie(categorie)));

        }
    }

    /**
     * Représente une ligne de la tableView
     * @author François de Saint Palais
     */
    public static class LigneSelectionCategorie {
        
        String nomCategorie;
        int nombreQuestion;
        CheckBox selection;
        
        /** 
         * TODO comment initial state properties
         * @param nomCategorie
         * @param nombreQuestion
         * @param selection
         */
        public LigneSelectionCategorie(String nomCategorie, int nombreQuestion) {
            super();
            this.nomCategorie = nomCategorie;
            this.nombreQuestion = nombreQuestion;
            this.selection = new CheckBox();
        }

        /** @return valeur de nomCategorie */
        public String getNomCategorie() {
            return nomCategorie;
        }

        /** @return valeur de nombreQuestion */
        public String getNombreQuestion() {
            return nombreQuestion + "";
        }

        /** @return valeur de selection */
        public CheckBox getSelection() {
            return selection;
        }
        
        public void ajouterALaSelection() {
            // TODO Auto-generated method stub
            System.out.println(this + " selectionner");
        }

        /** 
         * TODO comment method role
         */
        public void retirerALaSelection() {
            // TODO Auto-generated method stub
            System.out.println(this + " deselectionner");            
        }

        /* non javadoc - @see java.lang.Object#toString() */
        @Override
        public String toString() {
            return nomCategorie + " -> " + nombreQuestion;
        }
        
        
    }
}
