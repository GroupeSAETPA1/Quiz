package application.controleurs;

import application.Quiz;
import application.modele.ModelePrincipal;
import javafx.fxml.FXML;
import application.Quiz;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 *
 */
public class ControllerAide {
	
	 private ModelePrincipal model = ModelePrincipal.getInstance();
	 
	 @FXML
	 private Text text1;
	 
	 @FXML
	 private Text text2;
	 
	 @FXML
	 private Text text3;
	 
	 @FXML
	 private Text text4;
	 
	 @FXML
	 private Text text5;
	 
	 @FXML
	 private ImageView exempleTableur;
	 
	 Image image = new Image("application\\vue\\images\\tableur.png");
	 
	 
	@FXML
    public void retourPagePrecedente() {
		String pagePrecedente = model.getPagePrecendente();
		System.out.println("controlleur "+pagePrecedente);
        Quiz.changerVue(pagePrecedente);
    }
	
	@FXML
	public void initialize() {
		String pagePrecedente = model.getPagePrecendente();
		//pagePrecedente="Accueil.fxml";
		if(pagePrecedente != null && pagePrecedente.equals("Accueil.fxml")) {
			
			text1.setText("L'icône quitter vous permet de quitter l’application\n");
			text1.setStyle("-fx-font-size: 20px");
			text1.setTranslateY(10);
			
			text2.setText("Le bouton jouer vous permettra d'accéder à tout ce qui concerne la jeux,donc le paramétrage de la partie et le jeu.\n");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(15);
			
			text3.setText("Le bouton Editeur de question, vous permettra d’accéder au import  de csv, l'édition et création de catégorie.\n");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(35);
			
			text4.setText("Le bouton Mode En Ligne vous permettra d’accéder  au partage en réseaux de question, et l’import en réseaux de question\n");
			text4.setStyle("-fx-font-size: 20px");
			text4.setTranslateY(50);
			
		}else if(pagePrecedente != null && pagePrecedente.equals("ImporterQuestion.fxml")) {
			
			text1.setText("Sur cette page vous pouvez importer un dossier CSV\n");
			text1.setStyle("-fx-font-size: 20px");
			text1.setTranslateY(10);
			
			text2.setText("Il faut qu’il respecte un format spécifié(voir Image) \n");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(15);
			
			text3.setText("Ne pas oublier de ne pas prendre en compte la premiere ligne du tableur\n");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(35);
			
			exempleTableur.setImage(image);
			exempleTableur.setTranslateY(50);
			
			
		}else if(pagePrecedente != null && pagePrecedente.equals("ParametrePartie.fxml")) {
			
			text1.setText("Sur cette page vous pouvez paramétrer votre partie\n");
			text1.setStyle("-fx-font-size: 20px");
			text1.setTranslateY(10);
			
			text2.setText("Vous avez une catégorie à choisir, parmi de multiples catégories, proposées de base, ou que vous avez créées.\n");
			text2.setStyle("-fx-font-size: 20px");
			text2.setTranslateY(15);
			
			text3.setText("Ensuite vous pouvez choisir le nombre de questions que vous souhaitez, si la catégorie choisie ne contient pas assez de question, un message vous préviendra qu’il n’y a pas assez de question. \n");
			text3.setStyle("-fx-font-size: 20px");
			text3.setTranslateY(35);
			
			text4.setText("Pour finir vous pouvez choisir votre difficultée, si celle ci ne contient pas assez de questions, une pop up viendras vous prévenir que ce n’est pas possible de faire ces choix\n");
			text4.setStyle("-fx-font-size: 20px");
			text4.setTranslateY(50);
			
			text5.setText("Une fois votre paramétrage terminé il ne vous reste plus qu'à cliquer sur commencer\n");
			text5.setStyle("-fx-font-size: 20px");
			text5.setTranslateY(65);
			
		}else if(pagePrecedente != null && pagePrecedente.equals("CreationQuestionEtCategorie.fxml")) {
			
			
		}
	}
}
