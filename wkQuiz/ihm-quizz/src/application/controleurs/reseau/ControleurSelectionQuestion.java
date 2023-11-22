/*
 * ControleurSelectionCategorie.java                                    20 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.util.ArrayList;

import application.Quiz;
import application.controleurs.factories.CheckBoxCategorieCellFactory;
import application.controleurs.factories.CheckBoxQuestionCellFactory;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Question;
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
public class ControleurSelectionQuestion {
    
    @FXML TableView<LigneSelectionQuestion> tableView;
    
    @FXML TableColumn<LigneSelectionQuestion, String> libelleQuestion;
    @FXML TableColumn<LigneSelectionQuestion, String> categorieQuestion;
    @FXML TableColumn<LigneSelectionQuestion, CheckBox> selection;
    
    /**
  	  * Fonction liée au group Retour et son image
  	  * Et qui permet de retourner a la page précédente 
  	  */
  	@FXML
      public void retour() {
          Quiz.changerVue("ChoixEnvoie.fxml");
      }
       

    @FXML
    public void initialize() {
        
        libelleQuestion.setCellValueFactory(
                new PropertyValueFactory<LigneSelectionQuestion, String>
                ("libelleQuestion"));
        
        libelleQuestion.setCellFactory(tc -> {
            TableCell<LigneSelectionQuestion, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            cell.setWrapText(true);
            return cell;
        });
        
        
        categorieQuestion.setCellValueFactory(
                new PropertyValueFactory<LigneSelectionQuestion, String>
                ("categorieQuestion"));
        
        categorieQuestion.setCellFactory(tc->{
            TableCell<LigneSelectionQuestion, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            cell.setWrapText(true);
            return cell;
        });
        
        
        selection.setCellFactory(new CheckBoxQuestionCellFactory());
        
        miseAJourTableau();
    }
    
    /** 
     * Modifie le tableau des catégories
     */
    private void miseAJourTableau() {
        ModelePrincipal modele = ModelePrincipal.getInstance();

        ObservableList<LigneSelectionQuestion> data = tableView.getItems();

        ArrayList<Categorie> categories = modele.getBanqueCategorie().getCategories();
        ArrayList<Question> questions = modele.getBanqueQuestion().getQuestions();

        for (Question question : questions) {
            data.add(new LigneSelectionQuestion(question.getLibelle(), 
                                                question.getCategorie()));

        }
    }

    /**
     * Représente une ligne de la tableView
     * @author François de Saint Palais
     */
    public static class LigneSelectionQuestion {
        
        String libelleQuestion;
        String categorieQuestion;
        CheckBox selection;
        
        /** 
         * TODO comment initial state properties
         * @param libelleQuestion
         * @param categorieQuestion
         * @param selection
         */
        public LigneSelectionQuestion(String libelleQuestion, String categorieQuestion) {
            super();
            this.libelleQuestion = libelleQuestion;
            this.categorieQuestion = categorieQuestion;
            this.selection = new CheckBox();
        }

        /** @return valeur de libelleQuestion */
        public String getLibelleQuestion() {
            return libelleQuestion;
        }

        /** @return valeur de categorieQuestion */
        public String getCategorieQuestion() {
            return categorieQuestion;
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
            return libelleQuestion + " -> " + categorieQuestion;
        }
        
       
    }
}
