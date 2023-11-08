package application.controleurs;

import application.Quiz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import java.util.ArrayList;
import application.controleurs.factories.EditerQuestionButtonCellFactory;
import application.controleurs.factories.SupprimerQuestionButtonCellFactory;


import application.controleurs.lignes.LigneCategorie;
import application.controleurs.lignes.LigneQuestion;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;

public class ControlleurEditerQuestions {
	
	@FXML
	private TableView<LigneCategorie> table;
	
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
	
	public void initialize() {
		TableColumn<LigneQuestion, String> categorieColumn = new TableColumn<>("categorie");
		categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
		categorieColumn.setCellFactory(tc -> {
	        TableCell<LigneQuestion, String> cell = new TableCell<>();
	        cell.setAlignment(Pos.CENTER);
	        cell.textProperty().bind(cell.itemProperty());
	        cell.setStyle("-fx-font-size: 30px");
	        return cell;
	    });
		
		TableColumn<LigneQuestion, String> libelleColumn = new TableColumn<>("libellé");
		libelleColumn.setCellValueFactory(new PropertyValueFactory<>("nomQuestion"));
		libelleColumn.setCellFactory(tc -> {
	        TableCell<LigneQuestion, String> cell = new TableCell<>();
	        cell.setAlignment(Pos.CENTER);
	        cell.textProperty().bind(cell.itemProperty());
	        cell.setStyle("-fx-font-size: 30px");
	        return cell;
	    });

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

        TableColumn<LigneQuestion, String> modifColumn = new TableColumn<>("Modifier la question");
        modifColumn.setCellFactory(new EditerQuestionButtonCellFactory());

        TableColumn<LigneQuestion, String> supColumn = new TableColumn<>("Supprimer la question");
        supColumn.setCellFactory(new SupprimerQuestionButtonCellFactory());

        /** style de la table */
        double tableWidth = 1272;
        categorieColumn.setPrefWidth(tableWidth * 0.15);
        libelleColumn.setPrefWidth(tableWidth * 0.15);
        reponseJusteColumn.setPrefWidth(tableWidth * 0.15);
        reponsesFaussesColumn.setPrefWidth(tableWidth * 0.15);
        feedbackColumn.setPrefWidth(tableWidth * 0.15);
        modifColumn.setPrefWidth(tableWidth * 0.1);
        supColumn.setPrefWidth(tableWidth * 0.1);

        // table.getColumns().addAll
	}

}