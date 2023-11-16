package application.controleurs;

import java.util.ArrayList;
import java.util.Collections;import javax.naming.PartialResultException;

import org.junit.jupiter.params.provider.EnumSource.Mode;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.modele.Question;
import application.vue.AlertBox;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class ControlleurRepondreQuestion {
    
    private Partie partie = ModelePrincipal.getInstance().getPartie();
	
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
    private Button boutonPrecedent;
	@FXML 
	public void initialize() {
	    /*
	     * Evite IndexOutOfBoundsException au premier chargement
	     */
	    if (partie.getQuestionPossible().size() != 0) {
	        afficherQuestion(partie.getQuestionPossible().get(partie.getIndiceQuestion()));
	        afficherChoixPossible(partie.getQuestionPossible().get(partie.getIndiceQuestion()));	        
	    }
		couleurBoutonPrecedent();
		// if déja répondu, on affiche son choix;
		//questionPossible();
	}

	/** 
     * Change le style du bouton precedent en fonction de si il doit etre
     * active ou non
     */
    private void couleurBoutonPrecedent() {
        String couleur = 
            partie.getIndiceQuestion() != 0 ?
            "#0900FF" : "#848484";
        boutonPrecedent.setStyle("-fx-background-radius: 60 ; "
                + "-fx-background-color: " + couleur);
        
    }


    private String formaterLIbelle(String chaine, int a) {
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
        String reponseChoisie;
        int actuelle = partie.getIndiceQuestion();
        if (reponses.getSelectedToggle() == null) {
           reponseAlertBox =  AlertBox.showConfirmationBox("Vous n'avez choisis "
                   + "aucunes reponses.\nPar défaut cette réponse sera compté "
                   + "comme fausse");
        }
        if (reponseAlertBox) {
            if (reponses.getSelectedToggle() == null) {
               reponseChoisie = "vide"; 
            } else {
                reponseChoisie = ((RadioButton) 
                        reponses.getSelectedToggle()).getText();
            }
            System.out.println(partie.getQuestionPossible().get(actuelle));
            System.out.println(partie.getQuestionPossible());
            partie.setReponseDonnee(
                    partie.getQuestionPossible().get(actuelle), reponseChoisie);
            // TODO verifier ou on en est par rapport au parametrage
            partie.setIndiceQuestion(partie.getIndiceQuestion()+1);
            //System.out.println(partie.getIndiceQuestion());
            System.out.println(partie.getReponseDonnees());
            Quiz.chargerEtChangerVue("RepondreQuestion.fxml");
        }
    }
    
    @FXML
    public void precedent() {
        if (partie.getIndiceQuestion() != 0) {
            partie.setIndiceQuestion(partie.getIndiceQuestion()-1);
            System.out.println(partie.getIndiceQuestion());
            Quiz.chargerEtChangerVue("RepondreQuestion.fxml");
            
        }
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
}