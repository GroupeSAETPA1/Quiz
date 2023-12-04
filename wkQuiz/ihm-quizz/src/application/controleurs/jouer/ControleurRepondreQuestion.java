/*
 * ControleurRepondreQuestion.java                                    novembre 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */
package application.controleurs.jouer;

import java.util.ArrayList;
import java.util.Collections;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.modele.Question;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ControleurRepondreQuestion {
    
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
    private ToggleGroup reponses;
	
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
    
    ModelePrincipal modele = ModelePrincipal.getInstance();
    
	@FXML 
	public void initialize() {
	    /*
	     * Évite l'IndexOutOfBoundsException au premier chargement
	     */	
	    if (partie.getQuestionPossible().size() != 0) {
	        Question questionEnCour = partie.getQuestionPossible().get(partie.getIndiceQuestion());
	        afficherQuestion(questionEnCour);
	        afficherChoixPossible(questionEnCour);	
//	        afficherQuestion(questionEnCour);	
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
		if (choix1.getText().isBlank()) {			
   			choix1.setVisible(false);
   		}
   		if (choix2.getText().isBlank()) { 			
   			choix2.setVisible(false);
   		} 
   		if (choix3.getText().isBlank()) {			
   			choix3.setVisible(false);
   		} 
   		if (choix4.getText().isBlank()) {		
   			choix4.setVisible(false);
   		}
   		if (choix5.getText().isBlank()) {	
   			choix5.setVisible(false);
   		}
		
	}

	/**
	 * Modification de la page si c'est la dernière quesiton
	 */
	private void afficherDernierPage() {
		valider.setText("Terminer le questionnaire");
		valider.setPrefWidth(333);
		valider.setTranslateX(-75);
		
	}

	/**
	 * Affiche sur la page le numéro de la question courante
	 */
	private void afficherNumeroQuestion() {
		Integer nb = partie.getIndiceQuestion() + 1;
		numero.setText("Question n°" + nb);
	}

	/** 
     * Change le style du bouton précédent en fonction de si il doit être
     * activé ou non
     */
    private void couleurBoutonPrecedent() {
        String couleur = 
            partie.getIndiceQuestion() != 0 ?
            "#0900FF" : "#848484";
        boutonPrecedent.setStyle("-fx-background-radius: 60 ; "
                + "-fx-background-color: " + couleur);
        
    }


    /**
     * Permet de rajouter des \n a tous les "a" caractères
     * @param chaine (String) la chaine de caractères à formatter
     * @param a (int) le nombre de caractères max avant le \n
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
    
    @FXML
    private void validerReponse() {
        boolean reponseAlertBox = true ; 
        String reponseChoisie;
        int actuelle = partie.getIndiceQuestion();
        if (reponses.getSelectedToggle() == null) {
           reponseAlertBox =  AlertBox.showConfirmationBox("Vous avez choisi "
                   + "aucune réponse.\nPar défaut la réponse sera comptée "
                   + "comme fausse");
        }
        if (reponseAlertBox) {
            if (reponses.getSelectedToggle() == null) {
               reponseChoisie = "vide"; 
            } else {
                reponseChoisie = ((RadioButton) 
                        reponses.getSelectedToggle()).getText();
            }
            partie.setReponseDonnees(
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
    
    @FXML
	public void aider() {
    	modele.setPagePrecedente("RepondreQuestion.fxml");
		Quiz.chargerEtChangerVue("Aide.fxml");
	}

    /**
     * Affiche les différentes réponses possibles
     * @param question la question dont on devra afficher les choix possibles
     */
   	private void afficherChoixPossible(Question question) {
   		ArrayList<String> reponsePossibles = new ArrayList<>();
   		
   		reponsePossibles.add(question.getReponseJuste());
   		reponsePossibles.addAll(question.getMauvaisesReponses());
        int nbQuestion = reponsePossibles.size();

        Collections.shuffle(reponsePossibles);

        String reponsesAAfficher = "";

        for (int i = 0; i < nbQuestion; i++) {
        	reponsesAAfficher = formaterLibelle(reponsePossibles.get(i), 125);
            if (i == 0) {
                choix1.setVisible(true);
                choix1.setText(reponsesAAfficher);
            } else if (i == 1) {
                choix2.setVisible(true);
                choix2.setText(reponsesAAfficher);
            } else if (i == 2) {
                choix3.setVisible(true);
                choix3.setText(reponsesAAfficher);
            } else if (i == 3) {
                choix4.setVisible(true);
                choix4.setText(reponsesAAfficher);
            } else if (i == 4) {
                choix5.setVisible(true);
                choix5.setText(reponsesAAfficher);
            }
        }
   		
   	}

   	/** 
   	 * Affiche la question sur la page
   	 * @param question la question à afficher
   	 */
   	private void afficherQuestion(Question question) {
   		
   		categorie.setText("Catégorie : " + question.getCategorie());
   		
   		String libelle = question.getLibelle();
   		String libelleFormater = "";
   		
   		
   		if (libelle.length() > 80) {
   			intitule.setStyle("-fx-font-size: 22px;");
   		}
   		libelleFormater = formaterLibelle(question.getLibelle(), 80);
   		
   		intitule.setText(libelleFormater);
   		ModelePrincipal.getInstance();
		difficulte.setText("Difficultée : " + ModelePrincipal.INT_DIFFICULTE_TO_LABEL.get(question.getDifficulte()));
   		numero.setText("Question n°");

   	}
   	
   	
    /** 
     * Sélectionne la réponse de l'utilisateur si il avait déjà répondu et 
     * qu'il revient sur cette question 
     * @param questionEnCour question dont ou souhaite vérifier l'existence 
     * d'une réponse
     */
    private void questionDejaRepondu(Question questionEnCour) {
        
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
        int actuelle = partie.getIndiceQuestion();
        
        boolean reponseAlertBox =  AlertBox.showConfirmationBox(
                "Vous avez choisi aucune réponse.\n"
                + "Par défaut la réponse sera comptée comme fausse");

        if (reponseAlertBox) {

            partie.setReponseDonnees(partie.getQuestionPossible()
                    .get(actuelle), "");

            partie.setIndiceQuestion(actuelle + 1);
        }

        //Si la question actuel est la dernière question du questionnaire
        if (partie.getQuestionPossible().size()-1 == actuelle
                || partie.getNombreQuestion() == actuelle + 1){
            
            confirmationPasserDerniereQuestion();
       } else {
           Quiz.chargerEtChangerVue("RepondreQuestion.fxml");           
       }
    }
    
    @FXML
    private void quitterQuiz() {
    	boolean quitterQuiz = AlertBox.showConfirmationBox("Voulez-vous " 
    						  + "renvenir au menu principal ?\nToute "
    						  + "votre progression sur le quiz sera perdue.");
    	if (quitterQuiz) {
    		Quiz.chargerEtChangerVue("Accueil.fxml");
    	}
    }

    /** 
     * Affiche un pop-up qui prévient l'utilisateur 
     * qu'il va passer la dernière question du quiz
     */
    private void confirmationPasserDerniereQuestion() {
        boolean finir = AlertBox.showConfirmationBox(
                "Vous allez passer la dernière question et finir le quiz. "
                + "Voulez vous vraiment passer la question et terminer le quiz ?");
        if (finir) {
            Quiz.chargerEtChangerVue("Resultat.fxml");
        }
        
    }
}