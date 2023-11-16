package application.controleurs;

import application.Quiz;
import application.modele.ModelePrincipal;
import javafx.fxml.FXML;
import application.Quiz;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


/**
 *
 */
public class ControllerAide {
	
	 private ModelePrincipal model = ModelePrincipal.getInstance();
	 
	 @FXML
	 private Text Text1;
	 
	 @FXML
	 private Text Text2;
	 
	 @FXML
	 private Text Text3;
	 
	 @FXML
	 private Text Text4;
	 
	 
	 
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
			Text1.setText("L'icône quitter vous permet de quitter l’application\n");
			Text1.setStyle("-fx-font-size: 20px");
			Text1.setTranslateY(10);
			
			Text2.setText("Le bouton jouer vous permettra d'accéder à tout ce qui concerne la jeux,donc le paramétrage de la partie et le jeu.\n");
			Text2.setStyle("-fx-font-size: 20px");
			Text2.setTranslateY(15);
			
			Text3.setText("Le bouton Editeur de question, vous permettra d’accéder au import  de csv, l'édition et création de catégorie.\n");
			Text3.setStyle("-fx-font-size: 20px");
			Text3.setTranslateY(35);
			
			Text4.setText("Le bouton Mode En Ligne vous permettra d’accéder  au partage en réseaux de question, et l’import en réseaux de question\n");
			Text4.setStyle("-fx-font-size: 20px");
			Text4.setTranslateY(50);
			
		}else if(pagePrecedente != null && pagePrecedente.equals("ImporterQuestion.fxml")) {
			Text1.setText("François le Beau gosse ");
		}else if(pagePrecedente != null && pagePrecedente.equals("ParametrePartie.fxml")) {
			Text1.setText("Néo le Beau gosse ");
		}
	}
}
