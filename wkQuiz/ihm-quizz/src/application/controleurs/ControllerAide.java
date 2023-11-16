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
	 private Text aide;
	 
	 
	@FXML
    public void retourPagePrecedente() {
		String pagePrecedente = model.getPagePrecendente();
		System.out.println("controlleur "+pagePrecedente);
        Quiz.changerVue(pagePrecedente);
    }
	
	@FXML
	public void initialize() {
		String pagePrecedente = model.getPagePrecendente();
		if(pagePrecedente=="Accueil.fxml") {
			aide.setText("jjfsjfbsdkjs");
		}
	}
}
