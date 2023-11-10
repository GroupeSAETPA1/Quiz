/*
 * ControlleurParametres.java                                    10 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.controleurs;

import application.Quiz;
import application.modele.ModelePrincipal;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/** 
 * 
 * Controller de la page fxml parametre partie
 * @author Lucas
 * TODO gerer les exception lie a la non selection de nombre de question
 *      gerer exception si aucune categorie choisi
 */
public class ControlleurParametres {

    @FXML
    private ComboBox<String> selecteurCategorie ;
    
    @FXML
    private ToggleGroup nombreQuestions ;
    
    @FXML
    private ToggleGroup difficulte ;
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
        modifierParametrePartie();
        System.out.println("non implémenté");
        
    }
    
    /** 
     * Met a jour les paramètre de la partie
     */
    private void modifierParametrePartie() {
        ModelePrincipal.getInstance()
        .setCategoriePartie(selecteurCategorie.getValue());
        //System.out.println(ModelePrincipal.getInstance().getCategoriePartie());
        ModelePrincipal.getInstance().setNombreQuestion(getNbQuestion());
        //System.out.println(ModelePrincipal.getInstance().getNombreQuestion());
        //System.out.println(ModelePrincipal.getInstance().getBanqueCategorie());
        ModelePrincipal.getInstance().setDifficultePartie(getDifficulte());
        System.out.println(ModelePrincipal.getInstance());
    }

    /** 
     * @return La difficulte choisis
     */
    private Integer getDifficulte() {
        if (difficulte.getSelectedToggle() == null) {
            return null;
        }
        return ModelePrincipal.LABEL_DIFFICULTE_TO_INT.get(
                ((RadioButton) difficulte.getSelectedToggle()).getText());
    }

    /**
     * @return la valeur choisis pour le nombre de question
     */
    private int getNbQuestion() {
        return Integer.parseInt((
                (RadioButton) nombreQuestions.getSelectedToggle()).getText()); 
    }

    @FXML
    public void initialize() {
        selecteurCategorie.getItems().add("Aléatoire");
        // Ajoute les categorie de banque categorie dans la combo box
        selecteurCategorie.getItems().
        addAll(ModelePrincipal.getInstance().getBanqueCategorie().getCategoriesNom());
    }
}
