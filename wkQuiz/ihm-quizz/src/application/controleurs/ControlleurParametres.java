/*
 * ControlleurParametres.java                                    10 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.controleurs;

import application.Quiz;
import application.modele.ModelePrincipal;
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
            modifierParametrePartie();
        } catch (Exception e) {
            AlertBox.showErrorBox(e.getMessage());
        }
        System.out.println("non implémenté");
        
    }
    
    /** 
     * Met a jour les paramètre de la partie
     */
    private void modifierParametrePartie() {
        if (selecteurCategorie.getValue() != null ) {
            ModelePrincipal.getInstance()
            .setCategoriePartie(selecteurCategorie.getValue());
        } else {
            throw new NullPointerException("Categorie non selectionné  ! ");
        }
        ModelePrincipal.getInstance().setNombreQuestion(getNbQuestion());
        /* Si null l'attribut de la classe reste a null 
         * et on choisis des questions de niveau aléatoire
         */
        if (difficulte.getSelectedToggle() != null) {
            ModelePrincipal.getInstance().setDifficultePartie(getDifficulte());
        }
        
    }

    /** 
     * @return La difficulte choisis
     */
    private Integer getDifficulte() {
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