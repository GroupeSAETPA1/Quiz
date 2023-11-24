/*
 * Quiz.java 				                     18 octobre 2023
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application;

import java.beans.EventHandler;
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
import javafx.stage.WindowEvent;

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
		
		primaryStage.setOnCloseRequest((e) -> {
			try {
			    if (AlertBox.showConfirmationBox("Êtes vous sur de vouloir quitter l'application")) {
			    	ModelePrincipal.getInstance().serialiser();
			        Platform.exit();            
		        } else {
		        	// Si l'utilisateur a appuyé par erreur, 
		        	// il peut annuler le fait de quitter l'application
		        	e.consume();
		        }
			} catch (InternalError | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
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
	 * @throws IOException 
	 * @throws InternalError 
     */
	public static void quitter() throws InternalError, IOException {
	    if (AlertBox.showConfirmationBox("Êtes vous sur de vouloir quitter l'application")) {
	    	ModelePrincipal.getInstance().serialiser();
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
