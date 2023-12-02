/*
 * ControleurEditerQuestions.java                                    novembre 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */
package application.controleurs.editeur;

import java.util.ArrayList;

import application.Quiz;
import application.controleurs.factories.TextCellFactory;
import application.modele.ModelePrincipal;
import application.modele.Question;
import application.vue.AlertBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class ControleurEditerQuestions {

	
	@FXML
	private TableView<LigneQuestion> table;
		
	private ModelePrincipal modele = ModelePrincipal.getInstance();
	
	/**
	 * Méthode liée au group retour 
	 * qui devra renvoyer vers la page précédente
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
	    
	    table.setPlaceholder(new Label("Pas de Question"));
	    
		TableColumn<LigneQuestion, String> categorieColumn = new TableColumn<>("Categorie");
		categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
		categorieColumn.setCellFactory(new TextCellFactory<LigneQuestion>());
		
		TableColumn<LigneQuestion, String> libelleColumn = new TableColumn<>("Libellé");
		libelleColumn.setCellValueFactory(new PropertyValueFactory<>("nomQuestion"));
		libelleColumn.setCellFactory(new TextCellFactory<LigneQuestion>());

        TableColumn<LigneQuestion, String> modifColumn = new TableColumn<>("Modifier");
        modifColumn.setCellFactory(new EditerQuestionButtonCellFactory());

        TableColumn<LigneQuestion, String> supColumn = new TableColumn<>("Supprimer");
        supColumn.setCellFactory(new SupprimerQuestionButtonCellFactory());

        /** Style de la table */
        double tableWidth = 1280;
        categorieColumn.setPrefWidth(tableWidth * 0.40);
        libelleColumn.setPrefWidth(tableWidth * 0.35);
        modifColumn.setMinWidth(tableWidth * 0.09);
        supColumn.setMinWidth(tableWidth * 0.12);

        table.getColumns().add(categorieColumn);
        table.getColumns().add(libelleColumn);
        table.getColumns().add(modifColumn);
        table.getColumns().add(supColumn);
//        table.getColumns().addAll(categorieColumn, libelleColumn, modifColumn, supColumn);
        
        miseAJourTableau();
	}
	
	/** 
     * Modifie le tableau des question
     */
    private void miseAJourTableau() {
    	ObservableList<LigneQuestion> data = table.getItems();
        ArrayList<Question> questions = modele.getBanqueQuestion().getQuestions(); 
        
        for (Question question : questions) {
        	LigneQuestion ligne = new LigneQuestion(question);
        	data.add(ligne);
        }
        
    }
    
    /**
     * Classe permettant l'affichage des lignes dans la tableView de la vue EditerQuestions
     * @author quentin COSTES
     */
    public class LigneQuestion {
        
        private Question question;
        
        private final Button editerButton;
        private final Button supprimerButton;
        
        
        public LigneQuestion(Question question) {
            this.question = question;
            
            this.editerButton = new Button("Éditer");
            this.supprimerButton = new Button("Supprimer");
            editerButton.setOnAction(event -> editerQuestion());
            supprimerButton.setOnAction(event -> supprimerQuestion());
        }

        public String getCategorie() {
            return question.getCategorie();
        }


        public String getNomQuestion() {
            return question.getLibelle();
        }
        
        public Button getEditerButton() {
            return editerButton;
        }


        public Button getSupprimerButton() {
            return supprimerButton;
        }


        public void editerQuestion(){
            modele.setQuestionAModifier(question);
            Quiz.chargerEtChangerVue("EditerQuestion.fxml");
        }

        public void supprimerQuestion() {
            // Méthode appelée lors de l'appui sur le bouton de suppression de la catégorie

            // On demande confirmation à l'utilisateur
            boolean result = AlertBox.showConfirmationBox("Êtes-vous sûr de vouloir supprimer la question " + this.getNomQuestion() + " ?");
            if (result) {
                // Si l'utilisateur confirme
                // on supprime la question
                if (modele.supprimerQuestion(question)) {
                    // Si la suppression a réussie
                    AlertBox.showSuccessBox("Suppression effectuée");
                    Quiz.chargerEtChangerVue("EditerQuestions.fxml");
                } else {
                    // Si la suppression a échoué
                    AlertBox.showErrorBox("Suppression échouée");
                }
            } else {
                // Si l'utilisateur annule la suppression
                AlertBox.showErrorBox("Suppression annulée");
            }
            
        }
    }
    
    /**
     * Construit un bouton Editer
     * @author Quentin Costes
     * @author François de Saint Palais
     */
    public class EditerQuestionButtonCellFactory implements 
    Callback<TableColumn<LigneQuestion, String>, 
             TableCell<LigneQuestion, String>> {

        @Override
        public TableCell<LigneQuestion, String> call(
                TableColumn<LigneQuestion, String> param) {
            
            return new TableCell<LigneQuestion, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    super.setAlignment(Pos.CENTER);

                    if (empty || getTableRow() == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Button editerButton = new Button();
                        Image image 
                        = new Image("/application/vue/images/IconeEdition.png");
                        
                        editerButton.setGraphic(new ImageView(image));
                        editerButton.setStyle("-fx-background-color: transparent;");
                        editerButton.setOnAction(event -> {
                            getTableView().getItems()
                            .get(getIndex()).editerQuestion();
                        });

                        setGraphic(editerButton);
                    }
                }
            };
        }
    }
    
    /**
     * Construit un bouton Supprimer
     * @author Quentin Costes
     * @author François de Saint Palais
     */
    public class SupprimerQuestionButtonCellFactory implements 
    Callback<TableColumn<LigneQuestion, String>, 
             TableCell<LigneQuestion, String>> {

        @Override
        public TableCell<LigneQuestion, String> call(
                TableColumn<LigneQuestion, String> param) {
            
            return new TableCell<LigneQuestion, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    super.setAlignment(Pos.CENTER);

                    if (empty || getTableRow() == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Crée le bouton de suppression pour chaque ligne et associie une action
                        Button supprimerButton = new Button();
                        
                        Image image 
                        = new Image("/application/vue/images/IconeSupprimer.png");
                        supprimerButton.setGraphic(new ImageView(image));
                        
                        supprimerButton.setStyle("-fx-background-color: transparent;");
                        
                        supprimerButton.setOnAction(event -> {
                            getTableView().getItems()
                            .get(getIndex()).supprimerQuestion();
                        });

                        setGraphic(supprimerButton);
                    }
                }
            };
        }
    }
}