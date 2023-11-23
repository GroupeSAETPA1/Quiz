/*
 * ControleurSelectionCategorie.java                                    20 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.util.ArrayList;

import application.Quiz;
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
import javafx.util.Callback;

/** 
 * Contrôleur de la page SelectionQuestion.fxml
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
        
        ModelePrincipal modele = ModelePrincipal.getInstance();
        
        private String libelleQuestion;
        private String categorieQuestion;
        private CheckBox selection;
        
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
        
        
        public boolean estSelectionner() {
            return false; //STUB
        }
    }

    /**
     * Créer et gère les actions sur les CheckBox
     * 
     * @author François de Saint Palais
     */
    public class CheckBoxQuestionCellFactory implements
            Callback<TableColumn<LigneSelectionQuestion, CheckBox>, TableCell<LigneSelectionQuestion, CheckBox>> {

        /* non javadoc - @see javafx.util.Callback#call(java.lang.Object) */
        @Override
        public TableCell<LigneSelectionQuestion, CheckBox> call(TableColumn<LigneSelectionQuestion, CheckBox> arg0) {
            return new TableCell<LigneSelectionQuestion, CheckBox>() {
                @Override
                protected void updateItem(CheckBox item, boolean empty) {
                    super.updateItem(item, empty);
                    super.setAlignment(Pos.CENTER);
                    // On créer une CheckBox
                    CheckBox checkbox = new CheckBox();

                    // On ajoute l'évenement lié au côchage/décochage
                    checkbox.setOnAction(event -> {
                        // La ligne de la checkbox
                        LigneSelectionQuestion ligne = getTableView().getItems().get(getIndex());
                        if (checkbox.isSelected()) {
                            ligne.ajouterALaSelection();
                        } else {
                            ligne.retirerALaSelection();
                        }
                    });

                    setGraphic(checkbox);
                }
            };
        }

    }
}
