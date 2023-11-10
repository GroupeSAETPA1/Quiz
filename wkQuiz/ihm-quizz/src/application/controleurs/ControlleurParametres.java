/*
 * ControlleurParametres.java                                    10 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.controleurs;

import application.Quiz;
import javafx.fxml.FXML;

/** 
 * Controller de la page fxml parametre partie
 * @author Lucas
 */
public class ControlleurParametres {

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
        System.out.println("non implémenté");
    }
}
