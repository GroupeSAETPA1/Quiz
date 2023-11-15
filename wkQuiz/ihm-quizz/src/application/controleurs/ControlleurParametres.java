/*
 * ControlleurParametres.java                                    10 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.controleurs;

import application.modele.Partie;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Question;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/** 
 * Controller de la page fxml parametre partie
 * @author Lucas
 */
public class ControlleurParametres {

    @FXML
    private ComboBox<String> selecteurCategorie ;
    
    @FXML
    private ToggleGroup nombreQuestions ;
    
    @FXML
    private ToggleGroup difficulte ;
    
    @FXML
    public void initialize() {
        selecteurCategorie.getItems().add("Aléatoire");
        // Ajoute les categorie de banque categorie dans la combo box
        selecteurCategorie.getItems().
        addAll(ModelePrincipal.getInstance().getBanqueCategorie().getCategoriesNom());
    }
    
    
    /*
     * Fonction lié au bouton de retour a la page d'accueil
     */
    @FXML
    public void retourAccueil() {
        Quiz.changerVue("Accueil.fxml");
    }
    
    /*
     * Fonction lié au bouton de commencement d'une partie
     */
    @FXML
    public void commencerPartie() {
        try {
        	ModelePrincipal.getInstance().setPartie(new Partie());
            modifierParametrePartie();
            boolean lancer;
            int nombreQuestion = genererListeQuestionPossible();
            Partie partie = ModelePrincipal.getInstance().getPartie();
            if (nombreQuestion == 0) {
                throw new IllegalArgumentException("Impossible de lancer un "
                        + "quiz ! Aucunes questions trouver pour vos choix "
                        + "de parametres"); 
            } else if (nombreQuestion < partie.getNombreQuestion()) {
                lancer  = AlertBox.showConfirmationBox(
                        "Seulement " + nombreQuestion + 
                        " questions trouvé pour votre paramétrage \n "
                        + "voulez vous lance le quizz avec celui-ci ?");
            } else {
                lancer = AlertBox.showConfirmationBox(
                        "Voulez vous lancer le quizz avec ce paramétrage");
            }
            if (lancer) {
                Quiz.changerVue("RepondreQuestion.fxml");
            }
        } catch (Exception e) {
            AlertBox.showErrorBox(e.getMessage());
        }
    }
    
    /** 
     * Ajoute a la liste de questions dans laquelle seront tirés celle de la partie
     * toutes les questions correspondant au parametre
     * @return le nombre de question repondant au parametre
     */
    private int genererListeQuestionPossible() {
        Partie partie = ModelePrincipal.getInstance().getPartie();
        for (Question question : ModelePrincipal.getInstance().
             getBanqueQuestion().getQuestions()) {
            /*
             * Categorie partie est null si aléatoire choisis donc on prendras
             * les questions de toutes les categories
             * Difficulte partie = 0 si on a choisis Tous comme niveau donc on 
             * prendras les questions de tous les difficultes
             */
            if (partie.getCategoriePartie() == null ||
                (question.getCategorie().equals(
                 partie.getCategoriePartie().toString()))
               && (question.getDifficulte() == partie.getDifficulte().intValue()
                   || partie.getDifficulte().intValue() == 0)
               ){
                partie.getQuestionPossible().add(question);        
            }   
        }
        return partie.getQuestionPossible().size();
        
    }


    /** 
     * Met a jour les paramètre de la partie
     */
    private void modifierParametrePartie() {
        if (selecteurCategorie.getValue() != null ) {
            ModelePrincipal.getInstance().getPartie()
            .setCategoriePartie(selecteurCategorie.getValue());
        } else {
            throw new NullPointerException("Categorie non selectionné  ! ");
        }
        ModelePrincipal.getInstance().getPartie().setNombreQuestion(getNbQuestion());
        /* 
         * difficulte partie a 0 = tous type de questions 
         */
        ModelePrincipal.getInstance().getPartie().setDifficultePartie(getDifficulte());
    }

    /** 
     * @return La difficulte choisis
     */
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
        //else
        return Integer.parseInt((
                (RadioButton) nombreQuestions.getSelectedToggle()).getText()); 
    }
}