package application.controleurs;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ControlleurRepondreQuestion {
    
    @FXML
    private ToggleGroup reponseChoisi ;
    
    
    @FXML
    private void validerReponse() {
        boolean reponseAlertBox = true ; 
        Partie partie = ModelePrincipal.getInstance().getPartie();
        String reponse; 
        if (reponseChoisi.getSelectedToggle() == null) {
           reponseAlertBox =  AlertBox.showConfirmationBox("Vous n'avez choisis "
                   + "aucunes reponses.\nPar défaut cette réponse sera compté "
                   + "comme fausse");
        }
        if (reponseAlertBox) {
            reponse = ((RadioButton) reponseChoisi.getSelectedToggle()).getText();
            partie.setReponseDonnee(partie.getActuelle(), reponse);
            Quiz.chargerEtChangerVue("RepondreQuestion.fxml");
        }
    }
}