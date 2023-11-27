package application.controleurs;


import java.io.IOException;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Controlleur de la page d'accueil.
 * Celui-ci instancie des méthodes liées aux boutons de la page 
 * 
 * @author Néo BECOGNE
 * @author Quentin COSTES
 * @author François DE SAINT PALAIS
 * @author Lucas DESCRIAUD
 * @author Tom DOUAUD
 */


public class ControleurAccueil {
	
	private ModelePrincipal model = ModelePrincipal.getInstance();
	
	@FXML
	private Text labelPseudo;
	
	private Partie partie = ModelePrincipal.getInstance().getPartie();
	
	/**
	 * Méthode liée au bouton jouer 
	 * envoie vers la page ParametrePartie.fxml 
	 */
	@FXML 
	private void jouer() {
		Quiz.chargerEtChangerVue("ParametrePartie.fxml");
	}
	
	@FXML
	private void profil() {
		System.out.println("PARAMETRE !!!!!!!!!!!!!!!!!!");
		
		TextInputDialog saisiePseudo = new TextInputDialog();
		
		saisiePseudo.setContentText("Votre Pseudo");
		saisiePseudo.setHeaderText("Entrez ici votre pseudo");
		saisiePseudo.showAndWait();
		
	    String pseudoSaisie = saisiePseudo.getEditor().getText();
	    
		if(!pseudoSaisie.isBlank() && pseudoSaisie.length() <= 20) {
			
			labelPseudo.setText(pseudoSaisie);
			labelPseudo.setTextAlignment(TextAlignment.CENTER);
			partie.setPseudo(pseudoSaisie);
		} else {
			AlertBox.showErrorBox("Pseudo trop long, pas plus de 20 caractère.\nOu pseudo vide");
		}
	}
	
	
	/**
	 * Methode liée au bouton éditer 
	 * envoie vers la page Editeur.fxml 
	 */
	@FXML 
	private void editer() {
		Quiz.changerVue("Editeur.fxml");
	}
	
	
	/**
	 * Méthode liée au bouton en ligne
	 * envoie vers la page ModeEnligne.fxml
	 */
	@FXML 
	private void online() {
		Quiz.changerVue("ModeEnLigne.fxml");
	}
	
	/**
	 * Méthode liée au groupe quitter,
	 * ferme l'application
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 */
	@FXML
	private void quitter() throws ClassNotFoundException, InternalError, IOException {
		Quiz.quitter();
	}
	
	/**
	 * Méthode liée au groupe aider,
	 * envoie vers la page Aide.fxml
	 */
	@FXML
	private void aider() {
		model.setPagePrecedente("Accueil.fxml");
		System.out.println("Aider");
		Quiz.chargerEtChangerVue("Aide.fxml");
	}

}
