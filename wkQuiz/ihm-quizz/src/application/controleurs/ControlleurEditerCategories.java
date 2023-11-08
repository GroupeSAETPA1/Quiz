package application.controleurs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import application.modele.Categorie;
import application.modele.ModelePrincipal;

import java.util.ArrayList;

import application.Quiz;
import application.controleurs.factories.EditerButtonCellFactory;
import application.controleurs.factories.SupprimerButtonCellFactory;
import application.controleurs.lignes.LigneCategorie;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;

/**
 * Controlleur de la page d'édition des catégories.
 * Celui-ci instance  des methodes liée au bouton de la page 
 * 
 * @author Quentin COSTES
 */

public class ControlleurEditerCategories {
	
	@FXML
	private TableView<LigneCategorie> table;

	@FXML
	private TextField barreRecherche;
	
	/**
	 * Méthodes liée au group retour 
	 * qui devra renvoyer vers la page precedente
	 */
	
	@FXML 
	private void retour() {
		Quiz.changerVue("Editeur.fxml");
	}
	
	static boolean filtre = false ;
	/**
	 * Méthodes liée au bouton Créer Categorie 
	 * qui devra envoyer vers la page CreationQuestionEtCategorie.fxml
	 */
	@FXML 
	private void versCreerCategorie() {
		ModelePrincipal.getInstance().setDisplayCategoriePane(true);
		Quiz.chargerEtChangerVue("CreationQuestionEtCategorie.fxml");
	}
	
	public void initialize() throws InvalidNameException, HomonymeException {
	    TableColumn<LigneCategorie, String> nomColumn = new TableColumn<>("Nom de la categorie");
	    nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomProperty"));
	    nomColumn.setCellFactory(tc -> {
	        TableCell<LigneCategorie, String> cell = new TableCell<>();
	        cell.setAlignment(Pos.CENTER);
	        cell.textProperty().bind(cell.itemProperty());
	        cell.setStyle("-fx-font-size: 30px");
	        return cell;
	    });

	    TableColumn<LigneCategorie, Integer> nbColumn = new TableColumn<>("Nombre de questions contenues");
	    nbColumn.setCellValueFactory(new PropertyValueFactory<>("nbProperty"));
	    nbColumn.setCellFactory(tc -> {
	        TableCell<LigneCategorie, Integer> cell = new TableCell<>();
	        cell.setAlignment(Pos.CENTER);
		    cell.textProperty().bind(cell.itemProperty().asString());
		    cell.setStyle("-fx-font-size: 30px");
	        return cell;
	    });

	    TableColumn<LigneCategorie, String> modifColumn = new TableColumn<>("Modifier la categorie");
	    modifColumn.setCellFactory(new EditerButtonCellFactory());

	    TableColumn<LigneCategorie, String> supColumn = new TableColumn<>("Supprimer la categorie");
	    supColumn.setCellFactory(new SupprimerButtonCellFactory());

	    /** style de la table */
	    double tableWidth = 1272;
	    nomColumn.setPrefWidth(tableWidth * 0.45);  
	    nbColumn.setPrefWidth(tableWidth * 0.25);
	    modifColumn.setPrefWidth(tableWidth * 0.13);
	    supColumn.setPrefWidth(tableWidth * 0.14);

	    table.getColumns().addAll(nomColumn, nbColumn, modifColumn, supColumn);
	    
	    miseAJourTableau();
	    
	    //if (filtre) {
	        //System.out.println(data);
	      //  data.removeLast();
	        //Quiz.charger("EditerCategories.fxml");
	        
	    //}
	    
	}

    /** 
     * TODO comment method role
     */
    private void miseAJourTableau() {
        ObservableList<LigneCategorie> data = table.getItems();
	    ArrayList<Categorie> categories ;
	    if (filtre) {
	        data.clear();
	        categories = ModelePrincipal.getInstance().getBanqueCategorie().getCategoriesLibelle(barreRecherche.getText().strip());
	    } else {
	        categories = ModelePrincipal.getInstance().getBanqueCategorie().getCategories();
	    } 
	    for (Categorie categorie : categories) {
			data.add(new LigneCategorie(categorie.getNom()
					, ModelePrincipal.getInstance().getBanqueQuestion().getQuestions(categorie).size()));
		}
    }
	
	public void filtrer() {
	    filtre = true ;
	    miseAJourTableau();
	}
}