/*
 * ControleurAide.java                                     
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application.controleurs;

import application.Quiz;
import application.modele.ModelePrincipal;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


/**
 * Le controleur de la page Aide
 * @author Néo BECOGNE
 */
public class ControleurAide {
	
	 private ModelePrincipal modele = ModelePrincipal.getInstance();
	 
	 @FXML private Text text1;
	 
	 @FXML private Text text2;
	 
	 @FXML private Text text3;
	 
	 @FXML private Text text4;
	 
	 @FXML private Text text5;
	 
	 @FXML private ImageView exempleTableur;
	 
	 private Image image = new Image("application/vue/images/tableur.png");
	 	 
	 /**
	  * Fonction liée au group Retour et son image
	  * qui permet de retourner à la page précédente 
	  */
	@FXML
    public void retourPagePrecedente() {
		String pagePrecedente = modele.getPagePrecendente();
        Quiz.changerVue(pagePrecedente);
    }
	
	/**
	 * Méthode qui se lance au chargement de la page
	 * et qui change le texte en fonction de la page précédente  
	 */
	@FXML
	public void initialize() {
		// Récuperation de la page précédente 
		String pagePrecedente = modele.getPagePrecendente();
		
		// Comparaison de la page précedente, afin de savoir le message à afficher
		if (pagePrecedente != null && pagePrecedente.equals("Accueil.fxml")) {
			
			// Change le texte de text1 
			text1.setText("L'icône quitter vous permet de quitter "
					+ "l’application\n");
			
			// Change le style et la police d'ecriture de text1
			text1.setStyle("-fx-font-size: 20px");
			
			/*
			 * Déplace text1 sur l'axe Y afin de le descendre et de centrer le 
			 * texte en fonction du nombre de texte présent
			 */
			text1.setTranslateY(80);
			
			text2.setText("Le bouton \"Jouer\" vous permettra d'accéder à tout ce "
					+ "qui concerne le jeu, c'est à dire le paramétrage de la partie et "
					+ "le jeu.\n");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(70);
			
			text3.setText("Le bouton \"Éditeur de questions\", vous permettra "
					+ "d’accéder à l'import de csv, l'édition et la création de "
					+ "question et de catégorie.\n");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(80);
			
			text4.setText("Le bouton \"Mode en-ligne\" vous permettra d’accéder "
					+ "au partage et à la réception en réseau de questions\n");
			text4.setStyle("-fx-font-size: 20px");
			text4.setTranslateY(95);
			
		} else if(pagePrecedente != null 
				&& pagePrecedente.equals("ImporterQuestion.fxml")) {
			
			text1.setText("Sur cette page vous pouvez importer un fichier "
					+ "CSV\n");
			text1.setStyle("-fx-font-size: 20px");
			text1.setTranslateY(80);
			
			text2.setText("Il faut que ce fichier respecte un format spécifié"
					+ "(voir Image)\n");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(70);
			
			text3.setText("Ne pas oublier de ne pas prendre en compte la "
					+ "premiere ligne du tableur\n");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(70);
			
			text4.setText("Le délimateur du fichier .CSV est le caractère \"\t\" (la tabulation)\n");
			text4.setStyle("-fx-font-size: 20px");
			text4.setTranslateY(70);
			
			// Initialise l'image avec celle choisit 
			exempleTableur.setImage(image);
			
			/*
			 * Déplace l'image sur l'axe Y afin de la centrer avec le reste du 
			 * texte
			 */
			exempleTableur.setTranslateY(50);
			
			
		} else if (pagePrecedente != null 
				&& pagePrecedente.equals("ParametrePartie.fxml")) {
			
			text1.setText("Sur cette page vous pouvez paramétrer votre partie"
					+ "\n");
			text1.setStyle("-fx-font-size: 20px");
			text1.setTranslateY(80);
			
			text2.setText("Vous avez une catégorie à choisir, parmi de "
					+ "multiples catégories proposées de base, ou que vous "
					+ "avez créées.\n");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(70);
			
			text3.setText("Ensuite vous pouvez choisir le nombre de questions "
					+ "que vous souhaitez, si la catégorie choisie ne contient "
					+ "pas assez de question, un message vous préviendra qu’il "
					+ "n’y a pas assez de question. \n");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(80);
			
			text4.setText("Pour finir vous pouvez choisir votre difficultée, "
					+ "si celle-ci ne contient pas assez de questions, une pop-"
					+ "up viendras vous prévenir que ce n’est pas possible de "
					+ "faire ces choix\n");
			text4.setStyle("-fx-font-size: 20px");
			text4.setTranslateY(95);
			
			text5.setText("Une fois votre paramétrage terminé il ne vous reste "
					+ "plus qu'à cliquer sur commencer\n");
			text5.setStyle("-fx-font-size: 20px");
			text5.setTranslateY(115);
			
		} else if (pagePrecedente != null 
				&& pagePrecedente.equals("CreationQuestionEtCategorie.fxml")) {
			
			text1.setText("Sur cette page vous pouvez créer vos questions ou "
					+ "catégorie\n");
			text1.setStyle("-fx-font-size: 20px");
			text1.setTranslateY(80);
			
			text2.setText("Si vous souhaitez créer une catégorie, il faut que "
					+ "son libellé soit différent des catégories déjà existantes"
					+ ".\n");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(80);
			
			text3.setText("Si vous souhaitez créer une question, vous devez "
					+ "choisir sa catégorie, entrer un nom, une difficulté, "
					+ "et une réponse bonne et au moins une réponse fausse.\n");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(80);
			
			text4.setText("Le feedback n’est pas obligatoire\n");
			text4.setStyle("-fx-font-size: 20px");
			text4.setTranslateY(105);
			
			text5.setText("Vous pouvez entrer le nom d’une question déjà "
					+ "existante, mais ses réponses doivent "
					+ "être différente\n");
			text5.setStyle("-fx-font-size: 20px");
			text5.setTranslateY(110);
			
		} else if(pagePrecedente != null 
				&& pagePrecedente.equals("RepondreQuestion.fxml")) {
			
			text1.setText("Sur cette page vous pourrez jouer au jeu\n");
			text1.setStyle("-fx-font-size: 20px");
			text1.setTranslateY(80);
			
			text2.setText("Vous retrouverez en haut à gauche le rappel de votre"
					+ " catégorie et en haut à droite votre difficulté.\n");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(80);
			
			text3.setText("En dessous de cela il y a le libellé de la question "
					+ " qui vous est posée.\n");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(80);
			
			text4.setText("Ensuite vous retrouvez les multiples choix de "
					+ "réponses, une seule est bonne et si vous passez la question, " 
					+ "la réponse est considérée comme fausse\n");
			text4.setStyle("-fx-font-size: 20px");
			text4.setTranslateY(80);
			
			text5.setText("Pour finir en bas il y a trois boutons, un qui vous "
					+ "permet de revenir à la question précédente, un autre qui"
					+ " vous permet de valider. Et un dernier qui vous permet "
					+ "de passer la question en cas de doute.\n");
			text5.setStyle("-fx-font-size: 20px");
			text5.setTranslateY(100);
			
		} else if(pagePrecedente != null 
				&& pagePrecedente.equals("ChoixEnvoie.fxml")) {
			
			text1.setText("Sur cette page vous disposez de trois boutons\n");
			text1.setStyle("-fx-font-size: 20px");
			text1.setTranslateY(80);
			
			text2.setText("Le premier “Envoyer toutes les questions” vous "
					+ "permettra d’envoyer toutes les questions dont vous "
					+ "disposez.");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(90);
			
			text3.setText("\nLe second “Séléctionner les questions à l'unitée” vous"
					+ " permettra d’aller sélectionner des questions que vous"
					+ " souhaitez envoyer.");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(120);
			
			text4.setText("\n\nLe dernier “Choisir les questions par catégorie”"
					+ " vous permettra d’aller sélectionner des catégories"
					+ " que vous souhaitez envoyer. ");
			text4.setStyle("-fx-font-size: 20px");
			text4.setTranslateY(150);
			
		} else if(pagePrecedente != null 
				&& pagePrecedente.equals("RecevoirQuestions.fxml")) {
			
			text1.setText("Sur cette page vous allez pouvoir entrer les "
					+ "informations pour recevoir des questionsn\n");
			text1.setStyle("-fx-font-size: 20px");
			text1.setTranslateY(80);
			
			text2.setText("Vous devrez entrer l’adresse IP de la personne qui"
					+ " souhaite partager ses questions.");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(80);
			
			text3.setText("Vous devrez entrer aussi le port qui sera utilisé,"
					+ " lui aussi trouvable sur l’ordinateur de la personne"
					+ " qui souhaite partager ses questions.\n"
					+ "Quand celui qui veux partager ses question est prêt "
					+ "cliquer sur \"Connexion\", puis accepter les questions");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(80);
			
		} else if(pagePrecedente != null 
				&& pagePrecedente.equals("EnvoieQuestion.fxml")) {
			
			text1.setText(  "Sur cette page vous allez pouvoir partager "
			              + "vos questions");
			text1.setStyle("-fx-font-size: 20px");
			text1.setTranslateY(80);
			
			text2.setText("En premier vous avez le port utilisé.\n");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(80);
			
			text3.setText("Ensuite vous avez votre adresse IP.\n");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(80);
			
			text4.setText("Pour envoyer les questions a n'importe qui, cliquer "
			              + "sur l'unique bouton : \"Se connecter et envoyer\""
			              + "\nUne popup devrait vous demander si vous êtes "
			              + "prêt. Si le client est prêt cliquer valider.\n"
			              + "Après cela les questions seront envoyé au client");
			text4.setStyle("-fx-font-size: 20px");
			text4.setTranslateY(80);
			
		} 
	}
}
