package application.controleurs.jouer;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controlleur de la page Résultat
 * Celui-ci instancie des méthodes liées aux boutons de la page 
 * 
 * @author Tom DOUAUD
 */


public class ControleurResultat {
	
	 ModelePrincipal model = ModelePrincipal.getInstance();
	 
	 final String TEXT_SCORE="Score=%s/%s";
		
	/**
	 * Le texte du score réalisé par le joueur
	 */
	@FXML
	private Label score;
	
	/**
	 * Le texte en relation avec la performance du joueur
	 */
	@FXML
	private Label messageNBReponse;
	
	/**
	 * Méthode liée au bouton éditer 
	 * qui devra envoyer vers la page Solution.fxml 
	 */
	@FXML 
	private void voirReponses() {
		Quiz.changerVue("Solution.fxml");
		System.out.println("Button voirReponses");
		// TODO rajouter le lien vers Quiz.changerVue("Solution.fxml");
	}

	/**
	 * Méthode liée au bouton retourner à l'acceuil
	 * qui devra envoyer vers la page Accueil.fxml
	 */
	@FXML
	private void retourAccueil() {
		System.out.println("Button retourAccueil");
		Quiz.changerVue("Accueil.fxml");
	}
	
	@FXML
	private void nouveauQuiz() {
		Quiz.changerVue("ParametrePartie.fxml");
	}
	
	/**
	 * Méthode exécutée au chargement de la page Resultat
	 * pour récuperer le score et le message
	 */
	@FXML
	public void initialize() {
		
		if(model.getPartie() != null) {
			Partie partie = model.getPartie();
			
			int nbReponse = partie.getNombreQuestion();
			int nbReponseBonne = 0;
			// TODO lier le score au vrai score et charger le bon texte selon la performance
			score.setText(String.format(TEXT_SCORE,nbReponseBonne,nbReponse));
		}else {
			score.setText("Il n'y a pas de partie");
			
		}
	}
	
	
	

}