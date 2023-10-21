/*
 * Quiz.java 				                     18 octobre 2023
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application;

import java.io.IOException;

import application.vue.GestionVues;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * Classe principale de l'application permettant d'instancier
 * les contrôleurs, les vues et modèles.
 * 
 * TODO A enlever à la fin
 * Mettre dans les "VM argument" : 
 * --module-path /path/to/javafx-sdk-20/lib --add-modules javafx.controls,javafx.fxml
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD 
 */
public class Quiz extends Application {

	/** Diverses scènes de l'application contenant les vues. */
	private static Scene sceneAcceuil;
	private static Scene sceneAide;
	private static Scene sceneParametrePartie;
	private static Scene sceneQuestion;
	private static Scene sceneResultat;
	private static Scene sceneSolution;	
	private static Scene sceneEdition;
	private static Scene sceneImporterQuestion;	
	private static Scene sceneCreerQuestion;
	private static Scene sceneCreerCategorie;
	private static Scene sceneEditerCategorie;
	private static Scene sceneEditerQuestion;
	private static Scene sceneModeEnLigne;
	private static Scene scenePartagerQuestions;
	private static Scene sceneChoixPartageQuestions;
	private static Scene sceneRecevoirQuestions;


	//TODO Utiliser un HashMap ?
	public static Scene[] scenes = {
			sceneAcceuil, sceneAide, sceneParametrePartie, sceneQuestion, sceneResultat,
			sceneSolution, sceneEdition, sceneImporterQuestion, sceneCreerQuestion,
			sceneCreerCategorie, sceneEditerCategorie, sceneEditerQuestion,
			sceneModeEnLigne, scenePartagerQuestions, sceneChoixPartageQuestions,
			sceneRecevoirQuestions
	};
		
	private static String[] ressources = {
			"Acceuil.fxml", "Aide.fxml", "ParametrePartie.fxml",
			"Question.fxml", "Resultat.fxml", "Solution.fxml",
			"Editeur.fxml", "ImporterQuestion.fxml", "CreationQuestion.fxml",
			"CreationCategorie.fxml", "EditerCategorie.fxml", "EditerQuestion.fxml",
			"ModeEnLigne.fxml", "PartagerQuestions.fxml", "ChoixPartagerQuestions.fxml",
			"RecevoirQuestions.fxml","CreationQuestionetCatégorie.fxml"
	};

	/**
	 * Fenêtre principale de l'application
	 * La scène qui lui est associée sera modifiée
	 * par la classe héritée GestionVues en fonction
	 * des clics de l'utilisateur.
	 */
	public static Stage fenetrePrincipale;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		for (int indiceScene = 0; indiceScene < scenes.length; indiceScene++) {
        	try {
                /*
                 * Chargement de la vue et création
                 * de la scène associée à cette vue
                 */
                FXMLLoader chargeurFXMLCourant = new FXMLLoader();
                chargeurFXMLCourant.setLocation(
                		GestionVues.class.getResource(ressources[indiceScene]));
                Parent conteneur = chargeurFXMLCourant.load();
                
                /* Création de la scène correspondante à la vue chargée */
                scenes[indiceScene] = new Scene(conteneur); // TODO: Vérifier que ca va pas ne poser de pb avec les scenes qui ont une taille spécialle
            } catch (Exception e) {
                System.err.println("Nous n'avons pas pu loadé : " + ressources[indiceScene]);
            }
        }
        
        // on définit le titre, la hauteur et la largeur de la fenêtre principale
        primaryStage.setTitle("Quizéo - Menu principal");
        primaryStage.setHeight(600);
        primaryStage.setWidth(1000);

        //primaryStage.getIcons().add(new Image("vues/images/IconePrincipale.png")); // TODO faudra changer l'architecture de fichier de fond en comble pour que ca puisse marcher

        /*
         * on associe la scène principale à la fenêtre principale
         * Cette dernière est stockée en tant qu'attribut afin d'être accessible
         * dans les méthodes activer... Celles qui permettent de rendre active
         * l'une des 3 scènes
         */
        primaryStage.setScene(scenes[0]);
        fenetrePrincipale = primaryStage;
        fenetrePrincipale.setResizable(false);
        primaryStage.show();
	}
	
	/**
	 * Programme principal
	 * @param args non utilisé
	 */
	public static void main(String args[]) {	
		launch(args);
		// new ControleurPrincipal();	FIXME
	}
}
