/*
 * ControleurRecapitulatif.java                               
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application.controleurs.reseau;


import java.util.ArrayList;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Question;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControleurRecapitulatif {

	
	@FXML TableView<LigneRecapitulatif> tableRecap;
	@FXML TableColumn<LigneRecapitulatif, String> libelleQuestion;
	@FXML TableColumn<LigneRecapitulatif, String> categorie;
	@FXML TableColumn<LigneRecapitulatif, String> difficulte;
	@FXML TableColumn<LigneRecapitulatif, String> repVrai;
	 
	@FXML
	public void initialize() {
		 
		 libelleQuestion.setCellValueFactory(
	                new PropertyValueFactory<LigneRecapitulatif, String>
	                ("libelleQuestion"));
		 
		 categorie.setCellValueFactory(
	                new PropertyValueFactory<LigneRecapitulatif, String>
	                ("categorie"));
		 
		 categorie.setCellFactory(tc -> {
	            TableCell<LigneRecapitulatif, String> cell = new TableCell<>();
	            cell.setAlignment(Pos.CENTER);
	            cell.textProperty().bind(cell.itemProperty());
	            cell.setWrapText(true);
	            return cell;
	        });
		 
		 difficulte.setCellValueFactory(
	                new PropertyValueFactory<LigneRecapitulatif, String>
	                ("difficulte"));
		 
		 difficulte.setCellFactory(tc -> {
	            TableCell<LigneRecapitulatif, String> cell = new TableCell<>();
	            cell.setAlignment(Pos.CENTER);
	            cell.textProperty().bind(cell.itemProperty());
	            cell.setWrapText(true);
	            return cell;
	        });
		 
		 repVrai.setCellValueFactory(
	                new PropertyValueFactory<LigneRecapitulatif, String>
	                ("repVrai"));
		 
		 repVrai.setCellFactory(tc -> {
	            TableCell<LigneRecapitulatif, String> cell = new TableCell<>();
	            cell.setAlignment(Pos.CENTER);
	            cell.textProperty().bind(cell.itemProperty());
	            cell.setWrapText(true);
	            return cell;
	        });
	
		 miseAjourRecap();
	}
	
    private void miseAjourRecap() {
		 
		ModelePrincipal modele = ModelePrincipal.getInstance();
		 
		ObservableList<LigneRecapitulatif> data = tableRecap.getItems();

	    ArrayList<Question> questions = modele.getQuestionAEnvoyer();

        for (Question question : questions) {
            data.add(new LigneRecapitulatif(question.getLibelle(), 
            		question.getCategorie(), ModelePrincipal
            		.INT_DIFFICULTE_TO_LABEL.get(question.getDifficulte()),
            		question.getReponseJuste()));
        }
	 }
	
	
	@FXML
	private void retour() {
		Quiz.changerVue("ChoixEnvoie.fxml");
	}
	
	@FXML
	private void valider() {
		Quiz.changerVue("EnvoieQuestion.fxml");
	}
	
	public  class LigneRecapitulatif {
	        
	    String libelleQuestion;
	    String categorie;
	    String difficulte;
	    String repVrai;

	        
	    public LigneRecapitulatif(String libelleQuestion, String categorie,  String difficulte, String repVrai ) {
	        super();
	        this.libelleQuestion = libelleQuestion;
	        this.categorie = categorie;
	        this.difficulte = difficulte;
	        this.repVrai = repVrai;

	    }
	        
	    public String getLibelleQuestion() {
	        return libelleQuestion;
	    }
	        
        public String getCategorie() {
            return categorie;
        }
        
        public String getDifficulte() {
            return difficulte;
        }
        
        public String getRepVrai() {
            return repVrai;
        }
    }     
}