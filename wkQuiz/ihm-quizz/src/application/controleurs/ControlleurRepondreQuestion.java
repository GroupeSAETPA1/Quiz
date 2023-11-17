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
    private Button valider;
    
	@FXML 
	public void initialize() {
	    /*
	     * Evite IndexOutOfBoundsException au premier chargement
	     */	
	    if (partie.getQuestionPossible().size() != 0) {
	    	Question questionEnCour = partie.getQuestionPossible().get(partie.getIndiceQuestion());
	        afficherQuestion(questionEnCour);
	        afficherChoixPossible(questionEnCour);	
	        afficherQuestion(questionEnCour);	
	        retirerChoixVides();
	        afficherNumeroQuestion();	
	        couleurBoutonPrecedent();
	        questionDejaRepondu(questionEnCour);

	        if (   partie.getQuestionPossible().size()-1 == partie.getIndiceQuestion()
	        	|| partie.getNombreQuestion() == partie.getIndiceQuestion() + 1) {
	        	afficherDernierPage();
	        }
	    }
	}

	private void retirerChoixVides() {
		// on balai les choix possibles, si leur texte est vide on les retire
   		if (choix1.getText().isBlank()) {
   			choix1.setVisible(false);
   		} else if (choix2.getText().isBlank()) {
   			choix2.setVisible(false);
   		} else if (choix3.getText().isBlank()) {
   			choix3.setVisible(false);
   		} else if (choix4.getText().isBlank()) {
   			choix4.setVisible(false);
   		} else if (choix5.getText().isBlank()) {
   			choix5.setVisible(false);
   		}
		
	}

	/**
	 * modification de la page si c'est la dernier quesiton
	 */
	private void afficherDernierPage() {
		valider.setText("Termminer le questionnaire");
		valider.setPrefWidth(333);
		valider.setTranslateX(-75);
		
	}

	/**
	 * 
	 * TODO comment method role
	 */
	private void afficherNumeroQuestion() {
		Integer nb = partie.getIndiceQuestion() + 1;
		numero.setText("Question n°" + nb);
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


    /**
     * permet de rajouter des \n a tout les a caracteres
     * @param chaine 
     * @param a
     */
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
            partie.setReponseDonnee(
                    partie.getQuestionPossible().get(actuelle), reponseChoisie);
            
            if (   partie.getQuestionPossible().size()-1 == partie.getIndiceQuestion()
    	        || partie.getNombreQuestion() == partie.getIndiceQuestion() + 1) {
            	Quiz.chargerEtChangerVue("Resultat.fxml");
            } else {
                partie.setIndiceQuestion(partie.getIndiceQuestion()+1);
                Quiz.chargerEtChangerVue("RepondreQuestion.fxml");
            }
            
        }
    }
    
    @FXML
    public void precedent() {
        if (partie.getIndiceQuestion() != 0) {
            partie.setIndiceQuestion(partie.getIndiceQuestion()-1);
            Quiz.chargerEtChangerVue("RepondreQuestion.fxml");
            
        }
    }

    /**
     * 
     * TODO comment method role
     * @param question
     */
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

   	/**
   	 * 
   	 * TODO comment method role
   	 * @param question
   	 */
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
   	
   	/**
   	 * 
   	 * TODO comment method role
   	 * @param chaine
   	 * @param a TODO un meilleur nom pour a svp
   	 * @return
   	 */
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
   	
    /** 
     * Sélectionne la réponse de l'utilisateur si il avait déjà répondu et 
     * qu'il revient sur cette question 
     * @param questionEnCour question dont ou souhaite verifier l'existence 
     * d'une réponse
     */
    private void questionDejaRepondu(Question questionEnCour) {
//        Partie parti = ModelePrincipal.getInstance().getPartie();
        
        if (partie.getReponseDonnees().containsKey(questionEnCour)) {
        
            String reponseExistante = partie.getReponseDonnees().get(questionEnCour) ;
            RadioButton aSelectionner;
            
            if (choix1.getText().equals(reponseExistante)) {
                aSelectionner = choix1 ;
            } else if (choix2.getText().equals(reponseExistante)) {
                 aSelectionner = choix2 ;
            } else if (choix3.getText().equals(reponseExistante)) {
                 aSelectionner = choix3 ;
            } else if (choix4.getText().equals(reponseExistante)) {
                 aSelectionner = choix4 ;
            } else if (choix5.getText().equals(reponseExistante)) {
                 aSelectionner = choix5 ;
            } else {
                aSelectionner = null ;
            }
            reponses.selectToggle(aSelectionner);
        }
    }
    
    /**
     * Action lié au bouton Passer
     */
    @FXML
    private void passer() {
       boolean reponseAlertBox =  AlertBox.showConfirmationBox(
               "Vous n'avez choisis aucunes reponses.\n"
               + "Par défaut cette réponse sera compté comme fausse");
       int actuelle = partie.getIndiceQuestion();
       if (reponseAlertBox) {
           partie.setReponseDonnee(
                   partie.getQuestionPossible().get(actuelle), "");
           partie.setIndiceQuestion(partie.getIndiceQuestion() + 1 );
           Quiz.chargerEtChangerVue("RepondreQuestion.fxml");
           
           if (partie.getQuestionPossible().size()-1 == partie.getIndiceQuestion()) {
               
           }
           
       }
    }
}