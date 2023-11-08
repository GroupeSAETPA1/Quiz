package application.controleurs;

import java.util.ArrayList;

import application.Quiz;
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
 * Controlleur de la page Creation de Quetion et de Categorie.
 * Celui-ci instance  des methodes liée au bouton de la page
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
    
    @FXML private ToggleGroup difficulte;

	@FXML private TextArea saisieFeedback;
	
	@FXML private TextField saisieLibeleQuestion;
	@FXML private TextField saisieNomCategorie;
	@FXML private TextField saisieReponseVrai;

	@FXML private TextField saisiePremiereReponseFausse;
	@FXML private TextField saisieSecondeReponseFausse;
	@FXML private TextField saisieTroisiemeReponseFausse;
	@FXML private TextField saisieQuatriemeReponseFausse;

	@FXML private ComboBox<Categorie> selectCategorie;

	@FXML Tab tabCategorie;
	@FXML TabPane tapPane;

	/**
	 * Méthodes liée au bouton annuler,
	 * qui devra vider les champs et renvoyer vers la page Accueil.fxml
	 */
	@FXML
	private void annuler() {
		System.out.println("Annuler");
		Quiz.changerVue("Acceuil.fxml");
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
            AlertBox.showErrorBox("Veuillez saisir une nom de catégorie valide ");
        } catch (HomonymeException e) {
            AlertBox.showWarningBox("La categorie saisie existe déjà");
        }
		if (categorieCreer) {
            AlertBox.showSuccessBox("Categorie créer !");
        }
    }

    /**
     * Créer une question dans le modèle et gère les possibles exceptions qui
     * peuvent apparaître
     * @param questionCreer
     * @param indiceCategorieChoisie
     * @param libeleQuestion
     * @param valeurDifficulte
     * @param feedback
     * @param reponseVrai
     * @param mauvaiseReponses
     */
    private void creerEtGererQuestion(boolean questionCreer, int indiceCategorieChoisie, String libeleQuestion,
            int valeurDifficulte, String feedback, String reponseVrai, ArrayList<String> mauvaiseReponses) {
        //Création de la question
		try {
            questionCreer = modele.creerQuestion(libeleQuestion,
                    indiceCategorieChoisie, valeurDifficulte, reponseVrai,
                    mauvaiseReponses, feedback);

        } catch (InvalidFormatException e) {
            AlertBox.showErrorBox("Veuillez saisir au minimum une réponse fausse.");
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
            AlertBox.showSuccessBox("Question créer !");
        }
    }


	@FXML
	public void initialize() {
	    System.out.println("Instanciation de Création");
        // On récupère l'instance du Modèle
	    modele = ModelePrincipal.getInstance();

	    miseAJourListeCategorie();

	    if (ModelePrincipal.getInstance().isDisplayCategoriePane()) {
	    	System.out.println("ici");
	    	tapPane.getSelectionModel().select(tabCategorie);
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
        selectCategorie.getItems().addAll(categories);
    }

    /**
	 * Méthodes liée au bouton Accueil
	 * qui devra renvoyer vers la page Accueil.fxml
	 */
	@FXML
	private void retourAcceuil() {
		System.out.println("Bouton retour à l'acceuil");
		Quiz.changerVue("Editeur.fxml");
	}

    /**
     * Méthodes liée au bouton valider,
     * qui devra enregistrer les champs  dans la banques de question
     */
	@FXML
	private void valider() {
	    //TODO Refactor pour avoir une fonction par action
		System.out.println("Valider");

		boolean questionCreer = false;

		//Récupération de l'indice de la catégorie choisie
		int indiceCategorieChoisie =
		        selectCategorie.getSelectionModel().getSelectedIndex();
		System.out.println(  "Catégorie choisie : "
		                   + (indiceCategorieChoisie >= 0
		                      ? categories.get(indiceCategorieChoisie)
		                      : "Invalide"));

		//Récupération du nom de la question
		String libeleQuestion = saisieLibeleQuestion.getText();
		System.out.println("Nom de la question : " + libeleQuestion);

		//Récupération de la difficulté
		int valeurDifficulte = ModelePrincipal.LABEL_DIFFICULTE_TO_INT.get(
		        ((RadioButton) difficulte.getSelectedToggle()).getText());
		System.out.println("Difficulté : " + valeurDifficulte);

		//Récupération du feedback
		String feedback = saisieFeedback.getText();
		System.out.println("Feedback : " + feedback);

		//Récupération de la réponse vrai
		String reponseVrai = saisieReponseVrai.getText();
		System.out.println("Réponse vrai : " + reponseVrai);

		//Récupération des réponses fausses
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
		System.out.println("Mauvaise réponse(s) : " + mauvaiseReponses);


		creerEtGererQuestion(questionCreer, indiceCategorieChoisie, libeleQuestion, valeurDifficulte, feedback,
                reponseVrai, mauvaiseReponses);
	}

    /**
     * Méthodes liée au bouton valider,
     * qui devra enregistrer les champs  dans la banques Catégori
     */
	@FXML
    private void validerCategorie() {

		System.out.println("Valider");

		boolean categorieCreer = false;

        String nom = saisieNomCategorie.getText();
		System.out.println("Nom de la catégorie : " + nom);

		creerEtGererCategorie( categorieCreer, nom);
    }
	
	/**
	 * VIde les champs de la créationQuestion
	 */
	private void viderChampsQuestion() {
	    //TODO
	}

	/**
	 * VIde les champs de la créationCategorie
	 */
	private void viderChampsCategorie() {
	    //TODO
	}
}
