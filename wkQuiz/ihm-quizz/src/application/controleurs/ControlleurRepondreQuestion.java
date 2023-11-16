package application.controleurs;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.modele.Question;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
    private ToggleGroup reponseChoisi ;
	
	@FXML 
	public void initialize() {
		afficherQuestion(ModelePrincipal.getInstance().getBanqueQuestion().getQuestions().get(10));
		afficherChoixPossible();
		// if déja répondu, on affiche son choix;
	}

	private void afficherChoixPossible() {
		// TODO Auto-generated method stub
		
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