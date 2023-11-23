/*
 * Quiz.java 				                     18 octobre 2023
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application;

import java.io.IOException;
import java.util.ArrayList;

import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Question;
import application.vue.AlertBox;
import application.vue.GestionVues;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe principale de l'application permettant d'instancier
 * les contrôleurs, les vues et modèles.
 *
 * TODO A enlever à la fin
 * Mettre dans les "VM argument" :
 *  --module-path /path/to/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml
 *
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François de Saint Palais
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */
public class Quiz extends Application {

	/**
	 * Fenêtre principale de l'application
	 * La scène qui lui est associée sera modifiée
	 * par la classe héritée GestionVues en fonction
	 * des clics de l'utilisateur.
	 */
	public static Stage fenetrePrincipale;
	
	private static Quiz instance;

	/** Le nom de tout les fichier fxml de l'application */
	private ArrayList<String> ressources;

    /**
     * Fonction main qui lance la fenêtre JavaFX
     * @param args non utilisé
     */
    public static void main(String args[]) {
    	launch(args);
    }

    /**
     * Lance l'application
     * @throws InvalidNameException 
     * @throws HomonymeException 
     * @throws ReponseException 
     * @throws InvalidFormatException 
     * @throws DifficulteException 
     */
	@Override
	public void start(Stage primaryStage) throws IOException, HomonymeException, InvalidNameException, InvalidFormatException, ReponseException, DifficulteException {
        instance = this;
		ressources = new ArrayList<String>();
		GestionVues.initialiserScene();
		
//		{	// TODO c'est temporaire, c'est pour tester
    		ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test1"));
    	    ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test2"));
    	    ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test3"));
    	    ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test4"));
    	    ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test5"));
    	    ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test6"));
    	    ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test7"));
    	    ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test8"));
    	    ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test9"));
    	    ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
//    	    
    	    ArrayList<String> rep = new ArrayList<>();
    	    rep.add("coubeh");
    	    rep.add("je vais me prendre a cause des tableView");
//    	    
//    	   
//    	    
    	    String char250 = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium";
    	    String char350 = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate.";
    	    ArrayList<String> rep250 = new ArrayList<>();
    	    rep250.add(char250 + "1");
    	    rep250.add(char250 + "2");
    	    rep250.add(char250 + "3");
    	    rep250.add(char250 + "4");
    	    
    	    ModelePrincipal.getInstance().getBanqueQuestion().ajouter(new Question("quoi ?", ModelePrincipal.getInstance().getBanqueCategorie().getCategorieLibelleExact("test1"), 1, "feur", rep, ""));
    	    ModelePrincipal.getInstance().getBanqueQuestion().ajouter(new Question("qui ?", ModelePrincipal.getInstance().getBanqueCategorie().getCategorieLibelleExact("test2"), 2, "quette", rep, ""));
    	    ModelePrincipal.getInstance().getBanqueQuestion().ajouter(new Question(char250, ModelePrincipal.getInstance().getBanqueCategorie().getCategorieLibelleExact("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"), 3, char250, rep250, char250));
//		}
	    
	    ressources.add("Accueil.fxml");
		ressources.add("Editeur.fxml");
		ressources.add("CreationQuestionEtCategorie.fxml");
		ressources.add("EditerCategorie.fxml");
		ressources.add("EditerCategories.fxml");
	    ressources.add("EditerQuestion.fxml");
	    ressources.add("EditerQuestions.fxml");
	    ressources.add("Resultat.fxml");
	    ressources.add("Solution.fxml");
	    ressources.add("ImporterQuestion.fxml");
	    ressources.add("ParametrePartie.fxml");
	    ressources.add("RepondreQuestion.fxml");
	    ressources.add("Aide.fxml");
	    ressources.add("RecevoirQuestions.fxml");
	    ressources.add("EnvoieQuestion.fxml");
	    ressources.add("ChoixEnvoie.fxml");
	    ressources.add("ModeEnLigne.fxml");

		
		for (String element : ressources) {
			GestionVues.charger(element);
		}

		 try {
		    Image logo 
		    = new Image("application/vue/images/IconePrincipale.png");
		    
            primaryStage.getIcons().add(logo);
         } catch (Exception e) {
            System.err.println("Erreur : L'îcone est introuvables");
         }

		 
		primaryStage.setTitle("Quizéo - Accueil");
		fenetrePrincipale = primaryStage;
		primaryStage.setScene(GestionVues.getScene("Accueil.fxml"));
		fenetrePrincipale.setResizable(false);
		
		primaryStage.show();
	}
	
	public static Quiz getInstance() {
    	return instance;
    }
	
	public static void charger(String vue) {
	    GestionVues.charger(vue);
	}

    /**
	 * Gestion du changement de fenêtre
     * @param fenetre (String) le nom de la fenêtre a charger en .fxml
     */
	public static void changerVue(String fenetre) {
		GestionVues.changerVue(fenetre);
	}
	
	/**
     * Fonction appelée par les controlleurs permettant de quitter l'application
     */
	public static void quitter( ) {
	    if (AlertBox.showConfirmationBox("Êtes vous sur de vouloir quitter l'application")) {
	        Platform.exit();            
        }
	}
	
	/**
	 * Recharge une vue et l'affiche
	 * @param vue Le nom de la vue
	 */
	public static void chargerEtChangerVue(String vue) {
	    GestionVues.charger(vue);
	    GestionVues.changerVue(vue);
	}

}
