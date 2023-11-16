/*
 * ControllerAide.java                                     
 * IUT de Rodez, pas de copyright ni de "copyleft"
 */

package application.controleurs;

import application.Quiz;
import application.modele.ModelePrincipal;
import javafx.fxml.FXML;

/**
 * Le controlleur de la page Aide
 * @author Néo BECOGNE
 */
public class ControllerAide {
	
	 private ModelePrincipal model = ModelePrincipal.getInstance();
	 
	@FXML
    public void retourPagePrecedente() {
		String pagePrecedente = model.getPagePrecendente();
		System.out.println("controlleur "+pagePrecedente);
        Quiz.changerVue(pagePrecedente);
    }
}
