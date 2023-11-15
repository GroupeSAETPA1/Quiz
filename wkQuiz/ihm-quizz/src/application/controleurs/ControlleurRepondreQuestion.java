package application.controleurs;

import application.modele.ModelePrincipal;
import application.modele.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
	public void initialize() {
		afficherQuestion(ModelePrincipal.getInstance().getBanqueQuestion().getQuestions().get(2));
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
}
