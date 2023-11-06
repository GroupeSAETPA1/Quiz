package application.controleurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import application.modele.Categorie;
import application.modele.ModelePrincipal;

import java.util.ArrayList;

import application.Quiz;
import application.exception.InvalidNameException;

/**
 * Controlleur de la page d'édition des catégories.
 * Celui-ci instance  des methodes liée au bouton de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */

public class ControlleurEditerCategories {
	
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
	/**
	 * Méthodes liée au bouton Créer Categorie 
	 * qui devra envoyer vers la page CreationQuestionEtCategorie.fxml
	 */
	@FXML 
	private void versCreerCategorie() {
		Quiz.changerVue("CreationQuestionEtCategorie.fxml");
	}
	
	@FXML
	public void initialize() throws InvalidNameException {
		
		TableColumn<LigneCategorie, String> nomColumn = new TableColumn<>("Nom de la categorie");
		nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomProperty"));

		TableColumn<LigneCategorie, Integer> nbColumn = new TableColumn<>("Nombre de questions contenues");
		nbColumn.setCellValueFactory(new PropertyValueFactory<>("nbProperty"));

		TableColumn<LigneCategorie, String> modifColumn = new TableColumn<>("Modifier la categorie");
		modifColumn.setCellFactory(new EditerButtonCellFactory());

		TableColumn<LigneCategorie, String> supColumn = new TableColumn<>("Supprimer la categorie");
		supColumn.setCellFactory(new SupprimerButtonCellFactory());

		table.getColumns().addAll(nomColumn, nbColumn, modifColumn, supColumn);
		
        ObservableList<LigneCategorie> data = table.getItems();
        LigneCategorie nouvelleLigne = new LigneCategorie("test 1", 0);
        data.add(nouvelleLigne);
        nouvelleLigne = new LigneCategorie("test 2", 1);
        data.add(nouvelleLigne);
        // TODO modifier pour que ça soit les vrais carégories affichées
	}
}