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
import application.controleurs.factories.EditerButtonCellFactory;
import application.controleurs.factories.SupprimerButtonCellFactory;
import application.controleurs.lignes.LigneCategorie;
import application.controleurs.lignes.LigneQuestion;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;

public class ControlleurEditerQuestions {
	
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
		TableColumn<LigneQuestion, String> libelleColumn = new TableColumn<>("libellé de la question");
		libelleColumn.setCellFactory(tc -> {
	        TableCell<LigneQuestion, String> cell = new TableCell<>();
	        cell.setAlignment(Pos.CENTER);
	        cell.textProperty().bind(cell.itemProperty());
	        cell.setStyle("-fx-font-size: 30px");
	        return cell;
	    });
	}

}
