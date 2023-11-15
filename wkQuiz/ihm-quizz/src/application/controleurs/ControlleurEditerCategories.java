package application.controleurs;

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
import java.util.function.Predicate;

import application.Quiz;
import application.controleurs.factories.EditerCategorieButtonCellFactory;
import application.controleurs.factories.SupprimerCategorieButtonCellFactory;
import application.controleurs.lignes.LigneCategorie;
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
	private TextField barreRecherche ;
	
	
	private boolean filtre = false ;
		
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
		ModelePrincipal.getInstance().setDisplayCategoriePane(true);
		Quiz.chargerEtChangerVue("CreationQuestionEtCategorie.fxml");
	}
	
	public void initialize() {
	    TableColumn<LigneCategorie, String> nomColumn = new TableColumn<>("Nom de la categorie");
	    nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomProperty"));
	    nomColumn.setCellFactory(tc -> {
	        TableCell<LigneCategorie, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
	        cell.textProperty().bind(cell.itemProperty());
	        cell.setStyle("-fx-font-size: 30px");
	        return cell;
	    });

	    TableColumn<LigneCategorie, Integer> nbColumn = new TableColumn<>("Nombre de questions");
	    nbColumn.setCellValueFactory(new PropertyValueFactory<>("nbProperty"));
	    nbColumn.setCellFactory(tc -> {
	        TableCell<LigneCategorie, Integer> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
		    cell.textProperty().bind(cell.itemProperty().asString());
		    cell.setStyle("-fx-font-size: 30px");
	        return cell;
	    });

	    TableColumn<LigneCategorie, String> modifColumn = new TableColumn<>("Modifier");
	    modifColumn.setCellFactory(new EditerCategorieButtonCellFactory());

	    TableColumn<LigneCategorie, String> supColumn = new TableColumn<>("Supprimer");
	    supColumn.setCellFactory(new SupprimerCategorieButtonCellFactory());

	    /* style initial de la table (avec ajouts via mainStyle.css) */
	    double tableWidth = 1272;
	    nomColumn.setPrefWidth(tableWidth * 0.45);  
	    nomColumn.setResizable(false);
	    nbColumn.setPrefWidth(tableWidth * 0.25);
	    nbColumn.setResizable(false);
	    modifColumn.setPrefWidth(tableWidth * 0.15);
	    modifColumn.setResizable(false);
	    supColumn.setPrefWidth(tableWidth * 0.15);
	    supColumn.setResizable(false);

	    table.getColumns().addAll(nomColumn, nbColumn, modifColumn, supColumn);
	    
	    miseAJourTableau();
	    
	}
	
	public void filtrer() {
	    filtre = true ; 
	    miseAJourTableau ();
	}
    /** 
     * Modifie le tableau des categorie
     */
    private void miseAJourTableau() {
    	ModelePrincipal modele = ModelePrincipal.getInstance();
       
        ObservableList<LigneCategorie> data = table.getItems();
//        FilteredList<LigneCategorie> dataFiltre = new FilteredList<>(data);

        ArrayList<Categorie> categories ; 
        if (filtre) {
        	data.clear();
        	categories = modele.getCategoriesLibelle(barreRecherche.getText().strip());
        } else {
            categories = modele.getBanqueCategorie().getCategories();
        }
   
        for (Categorie categorie : categories) {
        	if (modele.getBanqueQuestion().getQuestions(categorie).size() == 0) {
        		data.add(new LigneCategorie(categorie.getNom()));
			} else {
				data.add(new LigneCategorie(categorie.getNom()
						, String.valueOf(modele.getBanqueQuestion().getQuestions(categorie).size())));
			}
    
        }
    }
}