/*
 * ControleurSolution.java                               
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application.controleurs.jouer;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.modele.Question;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Controlleur de la page Solution. La page affiche les réponse du dernier Quiz
 * avec,
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

    private static final Image BONNE_REPONSE = new Image("application\\vue\\images\\IconeValider.png");

    private static final Image MAUVAISE_REPONSE = new Image("application\\vue\\images\\IconeAnnuler.png");

    private static final String TEXT_SCORE = "Score = %s/%s";

    private ModelePrincipal modele = ModelePrincipal.getInstance();
    private Partie partie = modele.getPartie();

    /** Le texte du score réalisé par le joueur */
    @FXML private Text score;

    /**
     * Le tableau de la solution du quiz, avec une indication si le joueur a eu bon
     */
    @FXML private TableView<LigneQuestionSolution> tableauSolution;

    /**
     * Méthode exécutée au chargement de la page Solution pour récupérer le score et
     * le message
     */
    @FXML
    public void initialize() {

        TableColumn<LigneQuestionSolution, String> libelle 
        = new TableColumn<LigneQuestionSolution, String>("Libelle");
        libelle.setCellValueFactory(
              new PropertyValueFactory<LigneQuestionSolution, String>("libelle")
              );

        TableColumn<LigneQuestionSolution, String> categorie 
        = new TableColumn<LigneQuestionSolution, String>("Categorie");
        categorie.setCellValueFactory(
            new PropertyValueFactory<LigneQuestionSolution, String>("categorie")
            );

        TableColumn<LigneQuestionSolution, String> reponseJuste 
        = new TableColumn<LigneQuestionSolution, String>("Reponse Juste");
        reponseJuste.setCellValueFactory(
                new PropertyValueFactory<LigneQuestionSolution, String>
                ("reponseJuste")
                );

        TableColumn<LigneQuestionSolution, String> feedback 
        = new TableColumn<LigneQuestionSolution, String>("feedback");
        feedback.setCellValueFactory(
             new PropertyValueFactory<LigneQuestionSolution, String>("feedback")
             );

        TableColumn<LigneQuestionSolution, ImageView> indication = 
        new TableColumn<LigneQuestionSolution, ImageView>("indicationReponse");
        indication.setCellValueFactory(
                new PropertyValueFactory<LigneQuestionSolution, ImageView>
                ("indicationReponse")
                );

        tableauSolution.getColumns().clear();
        tableauSolution.getColumns()
        .addAll(libelle, categorie, reponseJuste, feedback, indication);

        if (modele.getPartie() != null) {

            HashMap<Question, String> resultatQuestionnaire 
            = partie.getReponseDonnees();
            int nombreTotalQuestion = resultatQuestionnaire.size();

            int nombreBonneReponse = partie.getNbBonneReponse();

            score.setText(String.format(TEXT_SCORE, 
                    nombreBonneReponse, nombreTotalQuestion));

            miseAJourTableau();
        }
    }

    /**
     * Met à jour le tableau des questions
     */
    private void miseAJourTableau() {

        HashMap<Question, String> resultatQuestionnaire 
        = partie.getReponseDonnees();

        System.out.println(resultatQuestionnaire);

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
     * @param question     La question a ajouter
     * @param bonneReponse true l'utilisateur a sélectionner la bonne réponse, false
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
         * @param reponseEstJuste true si l'utilisateur a bien répondu false sinon
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

        /** @return valeur de categorie */
        public String getCategorie() {
            return categorie;
        }

        /** @return valeur de feedback */
        public String getFeedback() {
            return feedback;
        }

        /** @return valeur de libelle */
        public String getLibelle() {
            return libelle;
        }

        /** @return valeur de reponseJuste */
        public String getReponseJuste() {
            return reponseJuste;
        }

        /** @return valeur de indicationReponse */
        public ImageView getIndicationReponse() {
            return indicationReponse;
        }

    }
}