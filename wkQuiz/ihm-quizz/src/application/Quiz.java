/*
 * Quiz.java 				                     18 octobre 2023
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe principale de l'application permettant d'instancier
 * les contrôleurs, les vues et modèles.
 *
 * TODO A enlever à la fin
 * Mettre dans les "VM argument" :
 *  --module-path /path/to/javafx-sdk-20/lib --add-modules javafx.controls,javafx.fxml
 *
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
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

	private ArrayList<String> ressources;

	private static HashMap<String, Scene> scenes;


	@Override
	public void start(Stage primaryStage) throws IOException {

		ressources = new ArrayList<>();
		scenes = new HashMap<>();

		ressources.add("Acceuil.fxml");
		ressources.add("Editeur.fxml");

		for (String element : ressources) {
			try { 
				Parent racine = FXMLLoader.load(getClass().getResource("vue/" + element));
				scenes.put(element, new Scene(racine));
			} catch (IOException e) {
				System.err.println("Nous n'avons pas pu loader : " + element);
				e.printStackTrace();
			}
		}

		 try {
            primaryStage.getIcons().add(new Image("application/vue/images/IconePrincipale.png"));
         } catch (Exception e) {
            System.err.println("Erreur : L'îcone est introuvables");
         }
		primaryStage.setTitle("Quizéo - Acceuil");
		fenetrePrincipale = primaryStage;
		primaryStage.setScene(scenes.get("Acceuil.fxml"));
		fenetrePrincipale.setResizable(false);
		primaryStage.show();
	}
	
	public static void changerVue(String fenetre) {
		fenetrePrincipale.setTitle("Quizéo - " + fenetre.split(".fxml")[0]);
		fenetrePrincipale.setScene(scenes.get(fenetre));
		fenetrePrincipale.show();
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
