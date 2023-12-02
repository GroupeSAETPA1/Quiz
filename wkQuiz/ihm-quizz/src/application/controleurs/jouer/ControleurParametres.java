/*
 * ControleurParametres.java                                    10 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.controleurs.jouer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import application.Quiz;
import application.exception.DifficulteException;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.modele.Question;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/** 
 * Controller de la page fxml parametre partie
 * @author Lucas Descriaud
 */
public class ControleurParametres {

    @FXML
    private ComboBox<String> selecteurCategorie ;
    
    @FXML
    private ToggleGroup nombreQuestions ;
    
    @FXML
    private ToggleGroup difficulte ;
    
    ModelePrincipal modele = ModelePrincipal.getInstance();
    
    @FXML
    public void initialize() {
        selecteurCategorie.getItems().add("Aléatoire");
        // Ajoute les catégorie de banque catégorie dans la combo box
        selecteurCategorie.getItems().
        addAll(ModelePrincipal.getInstance().getBanqueCategorie().getCategoriesNom());  
        modele.getPartie().getReponseDonnees().clear();
        modele.getPartie().getQuestionPossible().clear();
        modele.getPartie().setIndiceQuestion(0);
    }
    
    
    
    /*
     * Fonction liée au bouton de retour à la page d'accueil
     */
    @FXML
    public void retourAccueil() {
        Quiz.changerVue("Accueil.fxml");
    }

    @FXML
	private void aider() {
    	modele.setPagePrecedente("ParametrePartie.fxml");
		Quiz.chargerEtChangerVue("Aide.fxml");
	}
    
    /*
     * Fonction liée au bouton de commencement d'une partie
     */
    @FXML
    public void commencerPartie() {
        try {
            modifierParametrePartie();
            boolean lancer;
            int nombreQuestion = genererListeQuestionPossible();
            Partie partie = ModelePrincipal.getInstance().getPartie();
            if (nombreQuestion == 0) {
                throw new IllegalArgumentException("Impossible de lancer un "
                        + "quiz ! Aucune question trouvée pour vos choix "
                        + "de parametres"); 
            } else if (nombreQuestion < partie.getNombreQuestion()) {
                lancer  = AlertBox.showConfirmationBox(
                        "Seulement " + nombreQuestion + 
                        " questions trouvées pour votre paramétrage \n "
                        + "voulez-vous lancer le quiz avec celui-ci ?");
            } else {
                lancer = AlertBox.showConfirmationBox(
                        "Voulez-vous lancer le quiz avec ce paramétrage ?");
            }
            if (lancer) {
                ordreAleatoire();
          
                Quiz.chargerEtChangerVue("RepondreQuestion.fxml");;
            }
        } catch (Exception e) {
            AlertBox.showErrorBox(e.getMessage());
        }
    }
    
    /** 
     * Mélange de maniere aléatoire la liste des questions possibles
     */
    private void ordreAleatoire() {
        Partie partie = ModelePrincipal.getInstance().getPartie();
        ArrayList<Question> listeAMelanger = partie.getQuestionPossible();
        Collections.shuffle(listeAMelanger);
        partie.setQuestionPossible(listeAMelanger);
    }


    /** 
     * Ajoute à la liste de questions qui seront tirées dans la partie
     * toutes les questions qui correspondent au paramètre
     * @return le nombre de questions repondant au paramètre
     */
    private int genererListeQuestionPossible() {
        Partie partie = ModelePrincipal.getInstance().getPartie();
        // On réinitialise la liste des questions possibles
        partie.getQuestionPossible().clear();
        Categorie categoriePartie = partie.getCategoriePartie();
        for (Question question : ModelePrincipal.getInstance().
             getBanqueQuestion().getQuestions()) {
            /*
             * La catégorie de la partie est null si aléatoire est choisis donc on prendras
             * les questions de toutes les catégories.
             * Difficulte partie = 0 si on à choisi Tous comme niveau donc on 
             * prendras les questions de toutes les difficultes
             */
            if ((categoriePartie == null || question.getCategorie().equals
               (categoriePartie.getNom()))
               && (question.getDifficulte() == partie.getDifficultePartie()
               .intValue()
               || partie.getDifficultePartie().intValue() == 0)
               ){
                partie.getQuestionPossible().add(question); 
            }   
        }

        return partie.getQuestionPossible().size();
        
    }


    /** 
     * Met à jour les paramètres de la partie
     * @throws DifficulteException 
     */
    private void modifierParametrePartie() throws DifficulteException {
        if (selecteurCategorie.getValue() != null ) {
            try {
				ModelePrincipal.getInstance().getPartie()
				.setCategoriePartie(selecteurCategorie.getValue());
			} catch (ClassNotFoundException | InternalError | IOException e) {
				e.printStackTrace();
			}
        } else {
            throw new NullPointerException("Categorie non selectionnée  !");
        }
        ModelePrincipal.getInstance().getPartie().setNombreQuestion(getNbQuestion());
        /* 
         * Difficultée partie à 0 = tout type de questions 
         */
        ModelePrincipal.getInstance().getPartie().setDifficultePartie(getDifficulte());
    }

    /** 
     * Getter de la difficultée choisie
     * @return La difficultée choisie
     */
    private Integer getDifficulte() {
        if (difficulte.getSelectedToggle() == null) {
            throw new NullPointerException("Aucune difficultée selectionée !");
        }
        return ModelePrincipal.LABEL_DIFFICULTE_TO_INT.get(
               ((RadioButton) difficulte.getSelectedToggle()).getText());

    }

    /**
     * Getter du nombre de questions choisi
     * @return la valeur choisie pour le nombre de questions
     */
    private int getNbQuestion() {
        if (nombreQuestions.getSelectedToggle() == null) {
            throw new NullPointerException("Nombre de questiosn non selectionné !");
        }
        //else
        return Integer.parseInt((
                (RadioButton) nombreQuestions.getSelectedToggle()).getText()); 
    }
}