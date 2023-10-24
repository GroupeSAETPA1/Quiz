package application.controleurs;

import javafx.fxml.FXML;
import javafx.scene.Group;

public class ControlleurAccueil {
	
	@FXML 
	private void jouer() {
		System.out.println("Button jouer");
	}
	
	@FXML 
	private void editer() {
		System.out.println("Button editer");
	}
	
	@FXML 
	private void online() {
		System.out.println("Button en ligne");
	}
	
	@FXML
	private void quitter() {
		System.out.println("Quitter");
	}

}
