package application.controleurs;

import java.util.ArrayList;

import application.Quiz;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Question;
import application.vue.AlertBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * Controlleur de la page d'édition des questions.
 * Celui-ci instance  des methodes liée au bouton de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */

public class ControlleurEditerQuestion {
	
    private ArrayList<Categorie> categories;
	private ModelePrincipal modele;
	private Question questionAModifier;
    
    @FXML private ToggleGroup difficulte;

	@FXML private TextArea saisieFeedback;
	@FXML private TextField saisieLibeleQuestion;
	@FXML private TextField saisieReponseVrai;
	@FXML private TextField saisiePremiereReponseFausse;
	@FXML private TextField saisieSecondeReponseFausse;
	@FXML private TextField saisieTroisiemeReponseFausse;
	@FXML private TextField saisieQuatriemeReponseFausse;

	@FXML private ComboBox<Categorie> selectCategorie;
	
	/**
	 * Méthodes liée au bouton valider,
	 * qui devra enregister les champs  dans la banques de question 
	 */
	
	@FXML
	public void initialize() {
        // On récupère l'instance du Modèle
	    modele = ModelePrincipal.getInstance();
	    questionAModifier = modele.getQuestionAModifier();
	    miseAJourListeCategorie();
	    
	    if (questionAModifier != null) {
	    	saisieLibeleQuestion.setText(questionAModifier.getLibelle());
		    saisieFeedback.setText(questionAModifier.getFeedback());
		    saisieReponseVrai.setText(questionAModifier.getReponseJuste());
		    
		    
		    saisiePremiereReponseFausse.setText(questionAModifier.getMauvaisesReponses().get(0)); 
		    if (questionAModifier.getMauvaisesReponses().size() >= 2) {
		    	saisieSecondeReponseFausse.setText(questionAModifier.getMauvaisesReponses().get(1));
			}
		    if (questionAModifier.getMauvaisesReponses().size() >= 3) {
		    	saisieTroisiemeReponseFausse.setText(questionAModifier.getMauvaisesReponses().get(2));
		    }
		    if (questionAModifier.getMauvaisesReponses().size() >= 4) {
		    	saisieQuatriemeReponseFausse.setText(questionAModifier.getMauvaisesReponses().get(3));
		    }
		    
		 	ObservableList<Toggle> toggles = difficulte.getToggles();
		    switch (questionAModifier.getDifficulte()) {
			case 1:
				difficulte.selectToggle(toggles.get(0));
				break;
			case 2:
				difficulte.selectToggle(toggles.get(1));
				break;
			case 3:
				difficulte.selectToggle(toggles.get(2));
				break;

			default:
				break;
			}
		    selectCategorie.getSelectionModel().select(modele.getBanqueCategorie().getCategorieLibelleExact(questionAModifier.getCategorie()));
	    }
    }
	
	@FXML
	private void valider() {
		Question aModifier = modele.getBanqueQuestion().getQuestionsLibelle(questionAModifier.getLibelle()).get(0);
		
		try {
			aModifier.setCatgorie(selectCategorie.getValue());
			aModifier.setBonneReponse(saisieReponseVrai.getText());
			aModifier.setFeedback(saisieFeedback.getText());
			aModifier.setLibelle(saisieLibeleQuestion.getText());
			
			ArrayList<String> reponseFausses = new ArrayList<>();
			if (!saisiePremiereReponseFausse.getText().isBlank()) {
				reponseFausses.add(saisiePremiereReponseFausse.getText());
			}
			if (!saisieSecondeReponseFausse.getText().isBlank()) {
				reponseFausses.add(saisieSecondeReponseFausse.getText());
			}
			if (!saisieTroisiemeReponseFausse.getText().isBlank()) {
				reponseFausses.add(saisieTroisiemeReponseFausse.getText());
			}
			if (!saisieQuatriemeReponseFausse.getText().isBlank()) {
				reponseFausses.add(saisieQuatriemeReponseFausse.getText());
			}
			aModifier.setMauvaiseReponse(reponseFausses);
			
			int reponse = 0;
	        if (difficulte.getSelectedToggle() != null) {
	            reponse = ModelePrincipal.LABEL_DIFFICULTE_TO_INT.get(
	                    ((RadioButton) difficulte.getSelectedToggle()).getText());
	        }
	        
	        aModifier.setDifficulte(reponse);
			
			AlertBox.showSuccessBox("catégorie modifiée avec succé");
			Quiz.chargerEtChangerVue("EditerQuestions.fxml");
			
		} catch (ReponseException | InvalidNameException | InvalidFormatException e) {
			AlertBox.showErrorBox(e.getMessage());
		}
		
	}
	
	/**
     * met a jour la liste des categories
     * @throws InvalidNameException
     */
    private void miseAJourListeCategorie() {
        //Récupération puis ajout des nom de catégorie
        categories = modele.getCategories();
        selectCategorie.getItems().addAll(categories);
    }
	
	/**
	 * Méthodes liée au group retour 
	 * qui devra renvoyer vers la page précedentes 
	 */
	@FXML 
	private void retour() {
		Quiz.changerVue("EditerQuestions.fxml");
	}
	
}
