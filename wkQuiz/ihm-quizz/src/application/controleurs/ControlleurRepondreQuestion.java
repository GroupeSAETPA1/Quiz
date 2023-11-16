package application.controleurs;

import java.util.ArrayList;
import java.util.Collections;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.modele.Question;
import application.vue.AlertBox;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class ControlleurRepondreQuestion {	
	
	@FXML
	private Label categorie;
	
	@FXML
	private Label intitule;
	
	@FXML
	private Label difficulte;
	
	@FXML
	private Label numero;
	
    
    @FXML
    private ToggleGroup reponses ;
	
	@FXML 
	public void initialize() {
		afficherQuestion(ModelePrincipal.getInstance().getBanqueQuestion().getQuestions().get(2));
		afficherChoixPossible(ModelePrincipal.getInstance().getBanqueQuestion().getQuestions().get(2));
		// if déja répondu, on affiche son choix;
	}

	private void afficherChoixPossible(Question question) {
		ArrayList<String> reponsePossibles = new ArrayList<>();
		
		reponsePossibles.add(question.getReponseJuste());
		reponsePossibles.addAll(question.getMauvaisesReponses());
		int nbQuestion = reponsePossibles.size();
		
		Collections.shuffle(reponsePossibles);
		
		//for ( int i = 0 ; i <= reponsePossibles.size() ; 
		
	}

	private void afficherQuestion(Question question) {
		
		categorie.setText(question.getCategorie());
		
		String libelle = question.getLibelle();
		String libelleFormater = "";
		
		
		if (libelle.length() > 179) {
			intitule.setStyle("-fx-font-size: 22px;");
			libelleFormater = formaterLIbelle(question.getLibelle(), 80);
		} else {
			libelleFormater = formaterLIbelle(question.getLibelle(), 60);
		}
		
		intitule.setText(libelleFormater);
		difficulte.setText("Difficultée : " + question.getDifficulte());
		numero.setText("Question n°");// ModelePrincipal.getInstance().getPartie().getNumeroQuestion());
	

		}
	
	private String formaterLIbelle(String chaine, int a) {
		String libelleFormater = "";

    	for (int i = 0; i <= chaine.length() - 1; i ++) {
    		libelleFormater += chaine.charAt(i);
    		if (i % a == 0 && i != 0) {
    			System.out.println(i);
    			libelleFormater += "\n";
    		}
    	}
    	
    	return libelleFormater;   	
	}

    
    
    @FXML
    private void validerReponse() {
        boolean reponseAlertBox = true ; 
        Partie partie = ModelePrincipal.getInstance().getPartie();
        String reponseChoisie; 
        if (reponses.getSelectedToggle() == null) {
           reponseAlertBox =  AlertBox.showConfirmationBox("Vous n'avez choisis "
                   + "aucunes reponses.\nPar défaut cette réponse sera compté "
                   + "comme fausse");
        }
        if (reponseAlertBox) {
            if (reponses.getSelectedToggle() == null) {
               reponseChoisie = ""; 
            } else {
                reponseChoisie = ((RadioButton) 
                        reponses.getSelectedToggle()).getText();
            }
            partie.setReponseDonnee(partie.getActuelle(), reponseChoisie);
            Quiz.chargerEtChangerVue("RepondreQuestion.fxml");
        }
    }
}