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
    private RadioButton choix1;
	
    @FXML
    private RadioButton choix2;
    
    @FXML
    private RadioButton choix3;
    
    @FXML
    private RadioButton choix4;
    
    @FXML
    private RadioButton choix5;
   
    
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
   		
   		String reponsesAAfficher = "";
   		
   		for ( int i = 0 ; i < nbQuestion ; i ++ ) {
   			if (reponsePossibles.get(i).length() > 125) {
   				reponsesAAfficher = formaterLibelle(reponsePossibles.get(i), 125);
   			} else {
   				reponsesAAfficher = reponsePossibles.get(i);
   			}
   			System.out.println(i);
   			
   			
   			if (i == 0) {
   				choix1.setText(reponsesAAfficher);
   			} else if (i == 1) {
   				choix2.setText(reponsesAAfficher);
   			} else if (i == 2) {
   				choix3.setText(reponsesAAfficher);
   			} else if (i == 3) {
   				choix4.setText(reponsesAAfficher);
   			} else if (i == 4) {
   				choix5.setText(reponsesAAfficher);
   			} 
   		}
   		
   	}

   	private void afficherQuestion(Question question) {
   		
   		categorie.setText("Catégorie : " + question.getCategorie());
   		
   		String libelle = question.getLibelle();
   		String libelleFormater = "";
   		
   		
   		if (libelle.length() > 125) {
   			intitule.setStyle("-fx-font-size: 22px;");
   		}
   		libelleFormater = formaterLibelle(question.getLibelle(), 125);
   		
   		intitule.setText(libelleFormater);
   		ModelePrincipal.getInstance();
		difficulte.setText("Difficulté : " + ModelePrincipal.INT_DIFFICULTE_TO_LABEL.get(question.getDifficulte()));
   		numero.setText("Question n°");
   	

   		}
   	
   	private String formaterLibelle(String chaine, int a) {
   		String libelleFormater = "";

       	for (int i = 0; i <= chaine.length() - 1; i ++) {
       		libelleFormater += chaine.charAt(i);
       		if (i % a == 0 && i != 0) {
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
           	reponseChoisie = ((RadioButton) reponses.getSelectedToggle()).getText();
               // partie.setReponseDonnee(partie.getActuelle(), reponseChoisie);
               Quiz.chargerEtChangerVue("RepondreQuestion.fxml");
           }
       }
}