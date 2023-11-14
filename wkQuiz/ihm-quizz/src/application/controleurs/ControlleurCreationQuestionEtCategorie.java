package application.controleurs;

import java.util.ArrayList;

import application.Quiz;
import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * Controlleur de la page Creation de Question et de Catégorie.
 * Celui-ci instance  des méthodes liée au bouton de la page
 *
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */


public class ControlleurCreationQuestionEtCategorie {


    private ArrayList<Categorie> categories;

    private ModelePrincipal modele;
    
    @FXML private ComboBox<Categorie> selectCategorie;
    
    @FXML private ToggleGroup difficulte;

	@FXML private TextArea saisieFeedback;
	
	@FXML private TextField saisieLibeleQuestion;
	@FXML private TextField saisieReponseVrai;
	@FXML private TextField saisiePremiereReponseFausse;
	@FXML private TextField saisieSecondeReponseFausse;
	@FXML private TextField saisieTroisiemeReponseFausse;
	@FXML private TextField saisieQuatriemeReponseFausse;

	@FXML private TextField saisieNomCategorie;

	@FXML Tab tabCategorie;
	@FXML TabPane tabPane;

	/**
	 * Méthodes liée au bouton annuler,
	 * qui devra vider les champs 
	 */
	@FXML
	private void annulerQuestion() {
		System.out.println("AnnulerQuestion");
		viderChampsQuestion();
	}
	
	/**
	 * Méthodes liée au bouton annuler,
	 * qui devra vider les champs 
	 */
	@FXML
	private void annulerCategorie() {
		System.out.println("AnnulerCategorie");
		viderChampsCategorie();
	}


	@FXML
	public void initialize() {
        // On récupère l'instance du Modèle
	    modele = ModelePrincipal.getInstance();

	    miseAJourListeCategorie();

	    if (ModelePrincipal.getInstance().isDisplayCategoriePane()) {
	    	tabPane.getSelectionModel().select(tabCategorie);
	    	ModelePrincipal.getInstance().setDisplayCategoriePane(false);
	    }
    }

	/**
     * TODO comment method role
     * @throws InvalidNameException
     */
    private void miseAJourListeCategorie() {
        //Récupération puis ajout des nom de catégorie
        categories = modele.getCategories();
        selectCategorie.getItems().clear();
        selectCategorie.getItems().addAll(categories);
    }

    /**
	 * Méthodes liée au bouton Accueil
	 * qui devra renvoyer vers la page Accueil.fxml
	 */
	@FXML
	private void retourAcceuil() {
		Quiz.changerVue("Editeur.fxml");
	}

    /**
     * Méthodes liée au bouton valider,
     * qui devra enregistrer les champs  dans la banques de question
     */
	@FXML
	private void validerQuestion() {
	    //TODO Refactor pour avoir une fonction par action
		System.out.println("Valider");

		//Récupération de l'indice de la catégorie choisie
		int indiceCategorieChoisie = getIndiceCategorieChoisie();
//		System.out.println(  "Catégorie choisie : "
//		                   + (indiceCategorieChoisie >= 0
//		                      ? categories.get(indiceCategorieChoisie)
//		                      : "Invalide"));
			if (indiceCategorieChoisie <= 0) {
				throw new NullPointerException("Il n'y a pas de Catégorie choisie");
			}
			
			//Récupération du nom de la question
			String libeleQuestion = getLibeleQuestion();
			System.out.println("Nom de la question : " + libeleQuestion);
			
			//Récupération de la difficulté
			int valeurDifficulte = getDifficulte();
			System.out.println("Difficulté : " + valeurDifficulte);
			
			//Récupération du feedback
			String feedback = getFeedback();
			System.out.println("Feedback : " + feedback);
			
			//Récupération de la réponse vrai
			String reponseVrai = getReponseVrai();
			System.out.println("Réponse vrai : " + reponseVrai);
			
			//Récupération des réponses fausses
			ArrayList<String> mauvaiseReponses = getMauvaiseReponse();
			System.out.println("Mauvaise réponse(s) : " + mauvaiseReponses);
			
			
			creerEtGererQuestion(indiceCategorieChoisie, libeleQuestion, valeurDifficulte, feedback,
					reponseVrai, mauvaiseReponses);
			
		} catch (NullPointerException e) {
        	AlertBox.showErrorBox("Les champs requis pour une question ne sont pas tous remplis");
        }
		
		// Quiz.charger("EditerQuestions.fxml"); crash l'appli
		
	}



    /** 
     * @return La liste des mauvaise réponse choisie
     */
    private ArrayList<String> getMauvaiseReponse() {
        ArrayList<String> mauvaiseReponses = new ArrayList<>();
        if (!saisiePremiereReponseFausse.getText().isBlank()) {
            mauvaiseReponses.add(saisiePremiereReponseFausse.getText());
        }
        if (!saisieSecondeReponseFausse.getText().isBlank()) {
            mauvaiseReponses.add(saisieSecondeReponseFausse.getText());
        }
        if (!saisieTroisiemeReponseFausse.getText().isBlank()) {
            mauvaiseReponses.add(saisieTroisiemeReponseFausse.getText());
        }
        if (!saisieQuatriemeReponseFausse.getText().isBlank()) {
            mauvaiseReponses.add(saisieQuatriemeReponseFausse.getText());
        }
        return mauvaiseReponses;
    }

    /** 
     * @return La réponse vrai
     */
    private String getReponseVrai() {
        return saisieReponseVrai.getText();
    }

    /** 
     * @return Le feedback
     */
    private String getFeedback() {
        return saisieFeedback.getText();
    }

    /** 
     * @return La difficulté
     */
    private int getDifficulte() {
        int reponse = Integer.MAX_VALUE;
        if (difficulte.getSelectedToggle() != null) {
            reponse = ModelePrincipal.LABEL_DIFFICULTE_TO_INT.get(
                    ((RadioButton) difficulte.getSelectedToggle()).getText());
        }
        return reponse;
    }

    /** 
     * @return Le libelle de la question
     */
    private String getLibeleQuestion() {
        return saisieLibeleQuestion.getText();
    }
	
    /**
     * @return L'indice de la catégorie choisie.
     */
	private int getIndiceCategorieChoisie() {
	    return selectCategorie.getSelectionModel().getSelectedIndex();
	}
	
    /**
     * Créer une question dans le modèle et gère les possibles exceptions qui
     * peuvent apparaître
     * @param indiceCategorieChoisie L'indice de la catégorie choisie
     * @param libeleQuestion Le libelle de la question à créer
     * @param valeurDifficulte La difficulté de la question
     * @param feedback Le feedback de la question
     * @param reponseVrai La réponse vrai de la question
     * @param mauvaiseReponses La liste des mauvaise réponse de la question
     */
    private void creerEtGererQuestion(int indiceCategorieChoisie, String libeleQuestion,
            int valeurDifficulte, String feedback, String reponseVrai, ArrayList<String> mauvaiseReponses) {
        //Création de la question
        boolean questionCreer = false;
        try {
            questionCreer = modele.creerQuestion(libeleQuestion,
                    indiceCategorieChoisie, valeurDifficulte, reponseVrai,
                    mauvaiseReponses, feedback);

        } catch (InvalidFormatException e) {
            AlertBox.showErrorBox(e.getMessage());
        } catch (InvalidNameException e) {
            AlertBox.showErrorBox("Attention, veuillez saisir le nom de la "
                    + "question ET une réponse juste.");
        } catch (ReponseException e) {
            AlertBox.showErrorBox("Attention, les mauvaise réponse ne doivent "
                    + "pas être en double ET la bonne réponse ne peut pas être "
                    + "une mauvaise réponse");
        } catch (HomonymeException e) {
            AlertBox.showWarningBox("La question saisie existe déjà");
        }
        if (questionCreer) {
            AlertBox.showSuccessBox("Question crée !");
            viderChampsQuestion();
        }
    }

    /**
     * Méthodes liée au bouton valider,
     * qui devra enregistrer les champs  dans la banques Catégori
     */
	@FXML
    private void validerCategorie() {

		boolean categorieCreer = false;

        String nom = saisieNomCategorie.getText();
		System.out.println("Nom de la catégorie : " + nom);

		creerEtGererCategorie( categorieCreer, nom);
    }
	
	/**
     * Créer une catégorie dans le modèle et gère les possibles exceptions qui
     * peuvent apparaître
     * @param categorieCreer
     * @param nom
     */
    private void creerEtGererCategorie(boolean categorieCreer,String nom) {
        try {
            categorieCreer = modele.creerCategorie(nom);

        } catch (InvalidNameException e) {
            AlertBox.showErrorBox("Veuillez saisir une nom de catégorie valide "
                    + ": entre 1 et 30 caractère maximum ");
        } catch (HomonymeException e) {
            AlertBox.showWarningBox("La categorie saisie existe déjà");
        }
        
        if (categorieCreer) {
            AlertBox.showSuccessBox("Categorie crée !");
            miseAJourListeCategorie();
            Quiz.charger("EditerCategories.fxml");
            viderChampsCategorie();
        }
    }
	
	/**
	 * Vide les champs de la créationQuestion
	 */
	private void viderChampsQuestion() {
	    difficulte.selectToggle(null);
	    saisieFeedback.setText("");
	    saisieLibeleQuestion.setText("");
	    saisieReponseVrai.setText("");
	    saisiePremiereReponseFausse.setText("");
	    saisieSecondeReponseFausse.setText("");
	    saisieTroisiemeReponseFausse.setText("");
	    saisieQuatriemeReponseFausse.setText("");
	    /* 
	     * Nous sommes obligés de garder la value de la combobox 
	     * meme si ce n'est pas le plus optimal pour l'utilisateur
	     * car la combo box ne prends que des catégories et non une String
	     */
	}

	/**
	 * Vide les champs de la créationCategorie
	 */
	private void viderChampsCategorie() {
	    saisieNomCategorie.setText("");
	}
}
