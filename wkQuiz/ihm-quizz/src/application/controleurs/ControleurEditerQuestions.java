package application.controleurs;

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
	
	private boolean filtre = false;
	
	private ModelePrincipal modele = ModelePrincipal.getInstance();
	
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
	    
	    table.setPlaceholder(new Label("Pas de Question"));
	    
		TableColumn<LigneQuestion, String> categorieColumn = new TableColumn<>("Categorie");
		categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
		categorieColumn.setCellFactory(new TextCellFactory<LigneQuestion>());
		
		TableColumn<LigneQuestion, String> libelleColumn = new TableColumn<>("Libellé");
		libelleColumn.setCellValueFactory(new PropertyValueFactory<>("nomQuestion"));
		libelleColumn.setCellFactory(new TextCellFactory<LigneQuestion>());

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
        ArrayList<Question> questions = modele.getBanqueQuestion().getQuestions(); 
        
        for (Question question : questions) {
        	LigneQuestion ligne = new LigneQuestion(question);
        	data.add(ligne);
        }
        
    }
    
    /**
     * classe permettant l'affichage des lignes dans la tableView de la vue EditerQuestions
     * @author quentin COSTES
     */
    public class LigneQuestion {
        
        private Question question;
        
        /*
        private final SimpleStringProperty reponseJuste;
        private final SimpleStringProperty reponsesFausses;
        private final SimpleStringProperty feedback;
        */
        
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

        /* On remet si la prof veut a tout prix tout les colonnes
        public String getReponseJuste() {
            return reponseJuste.get();
        }


        public String getReponsesFausses() {
            return reponsesFausses.get();
        }


        public String getFeedback() {
            return feedback.get();
        }

        */
        
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
            // méthode appelée lors de l'appuie sur le bouton de suppression de la categorie

            // on demande confirmation à l'utilisateur
            boolean result = AlertBox.showConfirmationBox("Êtes-vous sûr de vouloir supprimer la question " + this.getNomQuestion() + " ?");
            if (result) {
                // si l'utilisateur confirme
                // on supprime la question
                if (modele.supprimerQuestion(question)) {
                    // si la suppression a réussi
                    AlertBox.showSuccessBox("Suppression effectuée");
                    Quiz.chargerEtChangerVue("EditerQuestions.fxml");
                } else {
                    // si la suppression a échoué
                    AlertBox.showErrorBox("Suppression échouée");
                }
            } else {
                // si l'utilisateur annule
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
                        // Créez le bouton de suppression pour chaque ligne et associez une action
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