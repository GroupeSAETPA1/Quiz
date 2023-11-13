package application.controleurs;

import java.util.ArrayList;

import application.Quiz;
import application.exception.InvalidNameException;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Question;
<<<<<<< Updated upstream
=======
import javafx.collections.ObservableList;
>>>>>>> Stashed changes
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
		    
		    selectCategorie.getSelectionModel().select(modele.getBanqueCategorie().getExactCategoriesLibelle(questionAModifier.getCategorie()));
	    }

	    
	    
	    
	    

    }
	
	/**
     * TODO comment method role
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
		System.out.println("Retour en arriere ");
		Quiz.changerVue("EditerQuestions.fxml");
	}
	/**
	 * Méthodes liée au button Créer Question
	 * qui devra renvoyer vers la page CreationQuestionEtCategorie.fxml
	 */
	@FXML 
	private void versCreerQuestion() {
		System.out.println("swalalala nous sommes partie pour créer");
		Quiz.changerVue("CreationQuestionEtCategorie.fxml");
	}
<<<<<<< Updated upstream
	
	@FXML
	public void initialize() throws InvalidNameException {
		Question aModifier = ModelePrincipal.getInstance().getQuestionAModifier();
		
    }

=======
>>>>>>> Stashed changes
}
