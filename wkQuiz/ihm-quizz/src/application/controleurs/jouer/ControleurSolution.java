/*
 * ControleurSolution.java                               
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application.controleurs.jouer;

import java.util.HashMap;
import java.util.Set;

import application.Quiz;
import application.controleurs.factories.TextCellFactory;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.modele.Question;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Controlleur de la page Solution. La page affiche les réponses du dernier Quiz
 * avec :
 * <ul>
 * <li>La bonne réponse</li>
 * <li>Le feedback</li>
 * <li>Une indication si l'utilisateur à répondu correctement ou non</li>
 * </ul>
 *
 * @author Tom DOUAUD
 * @author François de Saint Palais
 */
public class ControleurSolution {

    private static final Image BONNE_REPONSE = new Image("application/vue/images/IconeValider.png");

    private static final Image MAUVAISE_REPONSE = new Image("application/vue/images/IconeAnnuler.png");

    private static final String TEXT_SCORE = "Score = %s/%s";

    private ModelePrincipal modele = ModelePrincipal.getInstance();
    private Partie partie = modele.getPartie();

    /** Le texte du score réalisé par le joueur */
    @FXML private Text score;

    /**
     * Le tableau de la solution du quiz, avec une indication si le joueur à eu bon
     */
    @FXML private TableView<LigneQuestionSolution> tableauSolution;
    
    @FXML private TableColumn<LigneQuestionSolution, String> colonneLibelle;
    @FXML private TableColumn<LigneQuestionSolution, String> colonneCategorie;
    @FXML private TableColumn<LigneQuestionSolution, String> colonneReponseJuste;
    @FXML private TableColumn<LigneQuestionSolution, String> colonneFeedBack;
    @FXML private TableColumn<LigneQuestionSolution, ImageView> colonneIndication;

    /**
     * Méthode exécutée au chargement de la page Solution pour récupérer le score et
     * le message
     */
    @FXML
    public void initialize() {
        
        tableauSolution.setRowFactory(tr->{
            TableRow<LigneQuestionSolution> row = new TableRow<ControleurSolution.LigneQuestionSolution>();
            row.setMinHeight(100);
            return row;
        });

        colonneLibelle.setCellValueFactory(
              new PropertyValueFactory<LigneQuestionSolution, String>("libelle")
              );

        colonneCategorie.setCellValueFactory(
            new PropertyValueFactory<LigneQuestionSolution, String>("categorie")
            );

        colonneReponseJuste.setCellValueFactory(
                new PropertyValueFactory<LigneQuestionSolution, String>
                ("reponseJuste")
                );

        colonneFeedBack.setCellValueFactory(
             new PropertyValueFactory<LigneQuestionSolution, String>("feedback")
             );

        colonneIndication.setCellValueFactory(
                new PropertyValueFactory<LigneQuestionSolution, ImageView>
                ("indicationReponse"));
        
        colonneLibelle.setCellFactory(new TextCellFactory<LigneQuestionSolution>());
        colonneCategorie.setCellFactory(new TextCellFactory<LigneQuestionSolution>());
        colonneReponseJuste.setCellFactory(new TextCellFactory<LigneQuestionSolution>());
        colonneFeedBack.setCellFactory(new TextCellFactory<LigneQuestionSolution>());

        colonneIndication.setCellFactory(new ImageViewCellFactory());
        
        if (modele.getPartie() != null) {

            HashMap<Question, String> resultatQuestionnaire 
            = partie.getReponseDonnees();
            int nombreTotalQuestion = resultatQuestionnaire.size();

            int nombreBonneReponse = partie.getNbBonneReponse();

            score.setText(String.format(TEXT_SCORE, 
                    nombreBonneReponse, nombreTotalQuestion));

            miseAJourTableau();
        }
        
        double tableViewWith = tableauSolution.getPrefWidth();
        
        colonneLibelle.setPrefWidth(tableViewWith * 0.42);
        colonneCategorie.setPrefWidth(tableViewWith * 0.14);
        colonneReponseJuste.setPrefWidth(tableViewWith * 0.20);
        colonneFeedBack.setPrefWidth(tableViewWith * 0.18);
        colonneIndication.setPrefWidth(tableViewWith * 0.10);
    }

    /**
     * Met à jour le tableau des questions
     */
    private void miseAJourTableau() {

        HashMap<Question, String> resultatQuestionnaire 
        = partie.getReponseDonnees();

        Set<Question> questionsQuestionnaire = resultatQuestionnaire.keySet();
        for (Question question : questionsQuestionnaire) {

            String reponseSelectionner = resultatQuestionnaire.get(question);
            String reponseAttendu = question.getReponseJuste();

            boolean reponseEstJuste 
            = reponseAttendu.equals(reponseSelectionner);

            ajouterReponse(question, reponseEstJuste);
        }
    }

    /**
     * Ajoute une ligne à la GridPane.
     * 
     * @param question     La question à ajouter
     * @param bonneReponse true si l'utilisateur à sélectionné la bonne réponse, false
     *                     sinon
     */
    private void ajouterReponse(Question question, boolean bonneReponse) {
        LigneQuestionSolution ligne 
        = new LigneQuestionSolution(question, bonneReponse);

        tableauSolution.getItems().add(ligne);

    }

    /**
     * Méthode liée au bouton retourner à l'acceuil qui devra envoyer vers la page
     * Accueil.fxml
     */
    @FXML
    private void retour() {
        Quiz.changerVue("Resultat.fxml");
    }

    /**
     * Représente une ligne de la tableView
     * @author François de Saint Palais
     */
    public static class LigneQuestionSolution {

        private String categorie;
        private String feedback;
        private String libelle;
        private String reponseJuste;
        private ImageView indicationReponse;

        /**
         * Construction de la ligne
         * @param question La question de la ligne
         * @param reponseEstJuste true si l'utilisateur à bien répondu false sinon
         */
        public LigneQuestionSolution(Question question, boolean reponseEstJuste) {
            this.categorie = question.getCategorie();
            this.feedback = question.getFeedback();
            this.libelle = question.getLibelle();
            this.reponseJuste = question.getReponseJuste();
            indicationReponse = getImage(reponseEstJuste);
        }

        /**
         * Renvoie l'image correspondant à la réponse de l'utilisateur
         * Une coche verte s'il a bien répondu.
         * Une croix rouge sinon.
         * @param reponseEstJuste true si l'utilisateur a bien répondu false sinon
         * @return L'image correspondant à la réponse de l'utilisateur
         */
        private static ImageView getImage(boolean reponseEstJuste) {
            return new ImageView(reponseEstJuste ? 
                    BONNE_REPONSE : MAUVAISE_REPONSE);
        }

        /** @return valeur de catégorie */
        public String getCategorie() {
            return categorie;
        }

        /** @return valeur de feedback */
        public String getFeedback() {
            return feedback;
        }

        /** @return valeur de libellé */
        public String getLibelle() {
            return libelle;
        }

        /** @return valeur de réponseJuste */
        public String getReponseJuste() {
            return reponseJuste;
        }

        /** @return valeur de indicationRéponse */
        public ImageView getIndicationReponse() {
            return indicationReponse;
        }

    }
    
    /**
     * Créer une cellule qui contient une image centrée
     * @author François de Saint Palais
     */
    public class ImageViewCellFactory implements 
    Callback<TableColumn<LigneQuestionSolution, ImageView>, 
             TableCell<LigneQuestionSolution, ImageView>> {

        @Override
        public TableCell<LigneQuestionSolution, ImageView> call(
                TableColumn<LigneQuestionSolution, ImageView> param) {
            
            // Cellule à return
            TableCell<LigneQuestionSolution, ImageView> cell 
            = new TableCell<LigneQuestionSolution, ImageView>() {
                @Override
                protected void updateItem(ImageView item, boolean empty) {
                    super.updateItem(item, empty);

                    setGraphic((empty || item == null) ? null : item);
                }
            };
            cell.setAlignment(Pos.CENTER);
            return cell;
        }
    }
}