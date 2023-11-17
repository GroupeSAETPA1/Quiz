/*
 * ControlleurParametres.java                                    10 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.controleurs;

import application.modele.Partie;

import java.util.ArrayList;
import java.util.Collections;

import application.Quiz;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Question;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * Controller de la page fxml parametre partie
 * 
 * @author Lucas Descriaud
 */
public class ControlleurParametres {

    private ModelePrincipal modele = ModelePrincipal.getInstance();

    @FXML
    private ComboBox<String> selecteurCategorie;

    @FXML
    private ToggleGroup nombreQuestions;

    @FXML
    private ToggleGroup difficulte;

    @FXML
    public void initialize() {
        selecteurCategorie.getItems().add("Aléatoire");

        // Ajoute les catégories de banqueCategorie dans la ComboBox
        selecteurCategorie.getItems().addAll(modele.getBanqueCategorie().getCategoriesNom());
    }

    /*
     * Fonction lié au bouton de retour a la page d'accueil
     */
    @FXML
    public void retourAccueil() {
        Quiz.changerVue("Accueil.fxml");
    }

    @FXML
    private void aider() {
        modele.setPagePrecedente("ParametrePartie.fxml");
        Quiz.changerVue("Aide.fxml");
    }

    /*
     * Fonction lié au bouton de commencement d'une partie
     */
    @FXML
    public void commencerPartie() {
        // ModelePrincipal.getInstance().setPartie(new Partie());
        try {
            modifierParametrePartie();
            boolean lancer;
            int nombreQuestion = genererListeQuestionPossible();
            Partie partie = modele.getPartie();
            if (nombreQuestion == 0) {
                throw new IllegalArgumentException(
                        "Impossible de lancer un " + "quiz ! "
                        + "Aucunes questions trouver pour vos choix " + "de parametres");
                } else if (nombreQuestion < partie.getNombreQuestion()) {
                    lancer = AlertBox.showConfirmationBox("Seulement " 
                           + nombreQuestion
                           + " questions trouvé pour votre paramétrage \n " 
                           + "voulez vous lance le quizz avec celui-ci ?");
                } else {
                    lancer = AlertBox.showConfirmationBox("Voulez vous lancer le quizz avec ce paramétrage");
                }

            if (lancer) {
                ordreAleatoire();
                Quiz.chargerEtChangerVue("RepondreQuestion.fxml");
            }
                
            } catch (Exception e) {
                AlertBox.showErrorBox(e.getMessage());
            }
    }

    /**
     * Mélange de manière aléatoire la liste des questions possibles
     */
    private void ordreAleatoire() {
        Partie partie = modele.getPartie();
        ArrayList<Question> listeAMelanger = partie.getQuestionPossible();

        Collections.shuffle(listeAMelanger);

        partie.setQuestionPossible(listeAMelanger);
    }

    /**
     * Ajoute a la liste de questions dans laquelle seront tirés celle de la partie
     * toutes les questions correspondant au paramètre
     * 
     * @return le nombre de question répondant aux paramètres
     */
    private int genererListeQuestionPossible() {
        Partie partie = ModelePrincipal.getInstance().getPartie();

        // TODO créer une méthode dans le ModelePrincipal pour ne pas faire appel à une
        // méthode de banqueQuestion
        for (Question question : modele.getBanqueQuestion().getQuestions()) {

            Categorie categorieSelectionner = partie.getCategoriePartie();
            int difficulteSelectionner = partie.getDifficulte().intValue();

            // Si l'utilisateur a sélectionner 'Tous' la difficulté de la
            // question ne pas prise en compte dans la sélection des réponses
            difficulteSelectionner = difficulteSelectionner == 0 
                    ? question.getDifficulte() : difficulteSelectionner;

            // On n'a pas sélectionner de catégorie mais 'Aléatoire'
            if (categorieSelectionner == null) {
                if (difficulteSelectionner == question.getDifficulte()) {
                    partie.getQuestionPossible().add(question);
                }
                // On a sélectionner une catégorie
            } else if (question.getCategorie().equals(categorieSelectionner.getNom())
                    && question.getDifficulte() == difficulteSelectionner) {
                partie.getQuestionPossible().add(question);
            }
        }

        return partie.getQuestionPossible().size();
    }

    /**
     * Met a jour les paramètre de la partie
     */
    private void modifierParametrePartie() {
        if (selecteurCategorie.getValue() != null) {
            modele.getPartie().setCategoriePartie(selecteurCategorie.getValue());
        } else {
            throw new NullPointerException("Categorie non selectionné  ! ");
        }
        modele.getPartie().setNombreQuestion(getNbQuestion());
        /*
         * difficulte partie a 0 = tous type de questions
         */
        modele.getPartie().setDifficultePartie(getDifficulte());
    }

    /** @return La difficulté choisis */
    private Integer getDifficulte() {
        if (difficulte.getSelectedToggle() == null) {
            throw new NullPointerException("Aucunes difficulte selectionee");
        }
        return ModelePrincipal.LABEL_DIFFICULTE_TO_INT.get(
                ((RadioButton) difficulte.getSelectedToggle()).getText());

    }

    /**
     * @return la valeur choisis pour le nombre de question
     */
    private int getNbQuestion() {
        if (nombreQuestions.getSelectedToggle() == null) {
            throw new NullPointerException("Nombre de question non selectionné ! ");
        }
        // else
        return Integer.parseInt(((RadioButton) nombreQuestions.getSelectedToggle()).getText());
    }
}