package application.controleurs;

import java.util.ArrayList;

import application.Quiz;
import application.controleurs.factories.EditerQuestionButtonCellFactory;
import application.controleurs.factories.SupprimerQuestionButtonCellFactory;
import application.controleurs.lignes.LigneQuestion;
import application.modele.ModelePrincipal;
import application.modele.Question;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControleurEditerQuestions {

	
	@FXML
	private TableView<LigneQuestion> table;
	
	private boolean filtre = false;
	
	/**
	 * Méthodes liée au group retour 
	 * qui devra renvoyer vers la page precedente
	 */
	
	@FXML 
	private void retour() {
		Quiz.changerVue("Editeur.fxml");
	}
	
	@FXML
	private void versCreerQuestion() {
		ModelePrincipal.getInstance().setDisplayCategoriePane(false);
		Quiz.chargerEtChangerVue("CreationQuestionEtCategorie.fxml");
	}
	
	@FXML
	public void initialize() {
		TableColumn<LigneQuestion, String> categorieColumn = new TableColumn<>("categorie");
		categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
		categorieColumn.setCellFactory(tc -> {
	        TableCell<LigneQuestion, String> cell = new TableCell<>();
	        cell.setAlignment(Pos.CENTER);
	        cell.textProperty().bind(cell.itemProperty());
	        cell.setStyle("-fx-font-size: 20px");
	        return cell;
	    });
		
		TableColumn<LigneQuestion, String> libelleColumn = new TableColumn<>("libellé");
		libelleColumn.setCellValueFactory(new PropertyValueFactory<>("nomQuestion"));
		libelleColumn.setCellFactory(tc -> {
	        TableCell<LigneQuestion, String> cell = new TableCell<>();
	        cell.setAlignment(Pos.CENTER);
	        cell.textProperty().bind(cell.itemProperty());
	        cell.setStyle("-fx-font-size: 20px");
	        return cell;
	    });

		/* On remet si la prof veut a tout prix tout les colonnes
        TableColumn<LigneQuestion, String> reponseJusteColumn = new TableColumn<>("réponse juste");
        reponseJusteColumn.setCellValueFactory(new PropertyValueFactory<>("reponseJuste"));
        reponseJusteColumn.setCellFactory(tc -> {
            TableCell<LigneQuestion, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            cell.setStyle("-fx-font-size: 30px");
            return cell;
        });

        TableColumn<LigneQuestion, String> reponsesFaussesColumn = new TableColumn<>("réponses fausses");
        reponsesFaussesColumn.setCellValueFactory(new PropertyValueFactory<>("reponsesFausses"));
        reponsesFaussesColumn.setCellFactory(tc -> {
            TableCell<LigneQuestion, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            cell.setStyle("-fx-font-size: 30px");
            return cell;
        });

        TableColumn<LigneQuestion, String> feedbackColumn = new TableColumn<>("feedback");
        feedbackColumn.setCellValueFactory(new PropertyValueFactory<>("feedback"));
        feedbackColumn.setCellFactory(tc -> {
            TableCell<LigneQuestion, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            cell.setStyle("-fx-font-size: 30px");
            return cell;
        });
		*/
        TableColumn<LigneQuestion, String> modifColumn = new TableColumn<>("Modifier");
        modifColumn.setCellFactory(new EditerQuestionButtonCellFactory());

        TableColumn<LigneQuestion, String> supColumn = new TableColumn<>("Supprimer");
        supColumn.setCellFactory(new SupprimerQuestionButtonCellFactory());

        /** style de la table */
        double tableWidth = 1280;
        categorieColumn.setPrefWidth(tableWidth * 0.40);
        libelleColumn.setPrefWidth(tableWidth * 0.35);
        modifColumn.setMinWidth(tableWidth * 0.09);
        supColumn.setMinWidth(tableWidth * 0.12);

        table.getColumns().addAll(categorieColumn, libelleColumn, modifColumn, supColumn);
        
        miseAJourTableau();
	}
	
	public void filtrer() {
	    filtre = true ; 
	    // miseAJourTableau ();
	}
	
	/** 
     * Modifie le tableau des question
     */
    private void miseAJourTableau() {
    	ObservableList<LigneQuestion> data = table.getItems();
        ArrayList<Question> questions = ModelePrincipal.getInstance().getBanqueQuestion().getQuestions(); 
        
        for (Question question : questions) {
        	LigneQuestion ligne = new LigneQuestion(ModelePrincipal.getInstance().getBanqueCategorie().getCategorieLibelleExact(question.getCategorie())
        			                              , question.getLibelle(), question.getReponseJuste(), question.getMauvaisesReponses(), question.getFeedback());
        	data.add(ligne);
        }
        
    }
}