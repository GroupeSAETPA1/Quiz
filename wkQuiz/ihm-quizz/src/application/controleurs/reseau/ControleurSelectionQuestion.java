/*
 * ControleurSelectionCategorie.java                                    20 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.util.ArrayList;

import application.Quiz;
import application.controleurs.factories.TextCellFactory;
import application.controleurs.jouer.ControleurSolution;
import application.controleurs.jouer.ControleurSolution.LigneQuestionSolution;
import application.modele.ModelePrincipal;
import application.modele.Question;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
  	  * Et qui permet de retourner à la page précédente 
  	  */
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
        
        libelleQuestion.setCellValueFactory(
                new PropertyValueFactory<LigneSelectionQuestion, String>
                ("libelleQuestion"));
        categorieQuestion.setCellValueFactory(
                new PropertyValueFactory<LigneSelectionQuestion, String>
                ("categorieQuestion"));
        
        libelleQuestion.setCellFactory(
                new TextCellFactory<LigneSelectionQuestion>());
        categorieQuestion.setCellFactory(
                new TextCellFactory<LigneSelectionQuestion>());
        
        selection.setCellFactory(new CheckBoxQuestionCellFactory());
        
        double tailleTableView = 1025;
        
        libelleQuestion.setPrefWidth(tailleTableView * 0.45);
        categorieQuestion.setPrefWidth(tailleTableView * 0.40);
        selection.setPrefWidth(tailleTableView * 0.15);

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
            data.add(new LigneSelectionQuestion(question));
        }
    }

    /**
     * Représente une ligne de la tableView
     * @author François de Saint Palais
     */
    public static class LigneSelectionQuestion {
        
        ModelePrincipal modele = ModelePrincipal.getInstance();
        
        private Question question;
        
        /** 
         * Récupere la question pour la tableView
         * @param question la question à récuperer
         */
        public LigneSelectionQuestion(Question question) {
            super();
            this.question = question;
        }

        /** @return valeur de libelleQuestion */
        public String getLibelleQuestion() {
            return question.getLibelle();
        }

        /** @return valeur de categorieQuestion */
        public String getCategorieQuestion() {
            return question.getCategorie();
        }
        
        public void ajouterALaSelection() {
            modele.ajouterALaSelectionDEnvoie(question);
        }

        /** 
         * Retire de la sélection d'envoi la question décochée
         */
        public void retirerALaSelection() {
            modele.supprimerALaSelectionDEnvoie(question);
        }

        /* non javadoc - @see java.lang.Object#toString() */
        @Override
        public String toString() {
            return question.toString();
        }
        
        /** @return true si la checkbox doit être sélectionner */
        public boolean estSelectionner() {
            return modele.estAEnvoyer(question); //STUB
        }

        /* non javadoc - @see java.lang.Object#equals(java.lang.Object) */
        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (question.getClass() != obj.getClass())
                return false;
            Question other = (Question) obj;
            return question.equals(other);
        }
   
    }

    /**
     * Crée et gère les actions sur les CheckBox
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
                    // On crée une CheckBox
                    CheckBox checkbox = new CheckBox();
                    
                    /* 
                     * Lors de la création des lignes, 
                     * la TableView commence à l'index -1 => Exception 
                     */
                    try {
                        checkbox.setSelected(getTableView().getItems()
                                .get( getIndex() ).estSelectionner());
                    } catch (IndexOutOfBoundsException e) { }

                    // On ajoute l'évenement lié au côchage/décochage
                    checkbox.setOnAction(event -> {
                        // La ligne de la checkbox
                        LigneSelectionQuestion ligne 
                        = getTableView().getItems().get(getIndex());
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
