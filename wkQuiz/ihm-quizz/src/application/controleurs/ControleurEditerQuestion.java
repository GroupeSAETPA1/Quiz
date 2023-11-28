/*
 * ControleurEditerQuestion.java                                     
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */
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
 * Controleur de la page d'édition des questions.
 * Celui-ci instance des methodes liée au boutons de la page 
 * 
 * @author Quentin COSTES
 */

public class ControleurEditerQuestion {
	
    private ArrayList<Categorie> categories;
	private ModelePrincipal modele = ModelePrincipal.getInstance();
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
	
	@FXML
	public void initialize() {
        // On récupère l'instance du Modèle
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
	
	/**
     * Méthode liée au bouton valider,
     * qui devra enregistrer les champs dans la banque de questions 
     */
	@FXML
	private void valider() {
		Question aModifier = modele.getQuestionAModifier();
		
		//TODO utiliser la méthode modifierQuestion de ModelePrincipal
		
		try {
	    	
			aModifier.setCategorie(selectCategorie.getValue());
			if (ModelePrincipal.alphabetOk(saisieReponseVrai.getText())) {
				aModifier.setBonneReponse(saisieReponseVrai.getText());
			}
			if (ModelePrincipal.alphabetOk(saisieFeedback.getText())) {
				aModifier.setFeedback(saisieFeedback.getText());
			}
			if (ModelePrincipal.alphabetOk(saisieLibeleQuestion.getText())) {
				aModifier.setLibelle(saisieLibeleQuestion.getText());
			}
			
			ArrayList<String> reponseFausses = new ArrayList<>();
			if (!saisiePremiereReponseFausse.getText().isBlank()) {
				if (ModelePrincipal.alphabetOk(saisiePremiereReponseFausse.getText())) {
					reponseFausses.add(saisiePremiereReponseFausse.getText());
				} else {
					AlertBox.showErrorBox("Il ne doit pas y avoir d'accents dans la première réponse fausse");
				}
			}
			if (!saisieSecondeReponseFausse.getText().isBlank()) {
				if (ModelePrincipal.alphabetOk(saisieSecondeReponseFausse.getText())) {
					reponseFausses.add(saisieSecondeReponseFausse.getText());
				} else {
					AlertBox.showErrorBox("Il ne doit pas y avoir d'accents dans la seconde réponse fausse");
				}
			}
			if (!saisieTroisiemeReponseFausse.getText().isBlank()) {
				if (ModelePrincipal.alphabetOk(saisieTroisiemeReponseFausse.getText())) {
					reponseFausses.add(saisieTroisiemeReponseFausse.getText());
				} else {
					AlertBox.showErrorBox("Il ne doit pas y avoir d'accents dans la troisème réponse fausse");
				}
			}
			if (!saisieQuatriemeReponseFausse.getText().isBlank()) {
				if (ModelePrincipal.alphabetOk(saisieQuatriemeReponseFausse.getText())) {
					reponseFausses.add(saisieQuatriemeReponseFausse.getText());
				} else {
					AlertBox.showErrorBox("Il ne doit pas y avoir d'accents dans la quatrième réponse fausse");
				}
			}
			aModifier.setMauvaiseReponse(reponseFausses);
			
			int reponse = 0;
	        if (difficulte.getSelectedToggle() != null) {
	            reponse = ModelePrincipal.LABEL_DIFFICULTE_TO_INT.get(
	                    ((RadioButton) difficulte.getSelectedToggle()).getText());
	        }
	        
	        aModifier.setDifficulte(reponse);
			
			AlertBox.showSuccessBox("Question modifiée avec succès");
			Quiz.chargerEtChangerVue("EditerQuestions.fxml");
			
		} catch (ReponseException | InvalidNameException | InvalidFormatException e) {
			AlertBox.showErrorBox(e.getMessage());
		}
		
	}
	
	/**
     * Met à jour la liste des categories
     * @throws InvalidNameException
     */
    private void miseAJourListeCategorie() {
        //Récupération puis ajout des nom de catégorie
        categories = modele.getCategories();
        selectCategorie.getItems().addAll(categories);
    }
	
	/**
	 * Méthodes liée au group retour 
	 * qui devra renvoyer vers la page précédentes 
	 */
	@FXML 
	private void retour() {
		Quiz.changerVue("EditerQuestions.fxml");
	}
	
}
