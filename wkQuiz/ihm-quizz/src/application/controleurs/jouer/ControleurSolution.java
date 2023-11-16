package application.controleurs.jouer;

import java.util.HashMap;
import java.util.Set;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.modele.Question;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
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

    private static final Image BONNE_REPONSE
    = new Image("application\\vue\\images\\IconeValider.png");

    private static final Image MAUVAISE_REPONSE
    = new Image("application\\vue\\images\\IconeAnnuler.png");

    private static final String TEXT_SCORE = "Score = %s/%s";

    private ModelePrincipal modele = ModelePrincipal.getInstance();

    private Partie partie = modele.getPartie();

    /** Le texte du score réalisé par le joueur */
    @FXML private Text score;

    /**
     * Le tableau de la solution du quiz,
     * avec une indication si le joueur a eu bon
     */
    @FXML private GridPane tableauSolution;

    /**
     * Méthode exécutée au chargement de la page Solution pour récupérer le score et
     * le message
     */
    @FXML
    public void initialize() {
        if (modele.getPartie() != null) {
    
            int nombreBonneReponse = partie.getNbBonneReponse();
            int nombreTotalQuestion = partie.getNombreQuestion();
    
            score.setText(String.format(TEXT_SCORE, nombreBonneReponse, nombreTotalQuestion));
    
            miseAJourTableau();
        }
    }

    /**
     * Met à jour le tableau des questions
     */
    private void miseAJourTableau() {

        HashMap<Question, String> resultatQuestionnaire
        =  partie.getReponseDonnees();

        Set<Question> questionsQuestionnaire = resultatQuestionnaire.keySet();
        for (Question question : questionsQuestionnaire) {

            String reponseSelectionner = resultatQuestionnaire.get(question);
            String reponseAttendu = question.getReponseJuste();

            boolean reponseEstJuste = reponseAttendu.equals(reponseSelectionner);

            ajouterReponse(question, reponseEstJuste);
        }
    }

    /**
     * Ajoute une ligne à la GridPane.
     * @param question La question a ajouter
     * @param bonneReponse true l'utilisateur a sélectionner la bonne réponse,
     * false sinon
     */
    private void ajouterReponse(Question question, boolean bonneReponse) {
        // On récupère la dernière ligne du GridPane
        int ligneAAjoute = tableauSolution.getRowCount();
    
        Text libelle = new Text(question.getLibelle());
        Text reponseJuste = new Text(question.getReponseJuste());
        Text feedback = new Text(question.getFeedback());
        ImageView feedbackReponse = new ImageView(bonneReponse ? BONNE_REPONSE : MAUVAISE_REPONSE);
    
        //On centre les éléments dans le GridPane
        GridPane.setHalignment(libelle, HPos.CENTER);
        GridPane.setHalignment(reponseJuste, HPos.CENTER);
        GridPane.setHalignment(feedback, HPos.CENTER);
        GridPane.setHalignment(feedbackReponse, HPos.CENTER);
    
        //Ajout de la ligne avec les différente colonne
        tableauSolution.add(libelle,        0, ligneAAjoute);
        tableauSolution.add(reponseJuste,   1, ligneAAjoute);
        tableauSolution.add(feedback,       2, ligneAAjoute);
        tableauSolution.add(feedbackReponse,3, ligneAAjoute);
    }

    /**
     * Méthode liée au bouton retourner à l'acceuil qui devra 
     * envoyer vers la page Accueil.fxml
     */
    @FXML
    private void retour() {
        Quiz.changerVue("Resultat.fxml");
    }

}