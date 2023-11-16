package application.controleurs.jouer;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


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
	
	@FXML
	private Label messagePrivee;
	

	/**
	 * Méthode liée au bouton éditer 
	 * qui devra envoyer vers la page Solution.fxml 
	 */
	@FXML 
	private void voirReponses() {
		Quiz.changerVue("Solution.fxml");
		System.out.println("Button voirReponses");
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
			int nbReponseBonne = 0; // STUB
			score.setText(String.format(TEXT_SCORE,nbReponseBonne,nbReponse));
		}else {
			score.setText("Il n'y a pas de partie");
		}
		messagePersonnaliser();
	}

	public void messagePersonnaliser() {
		String pseudo="Neo"; // STUB
		
		if(model.getPartie() != null) {
			Partie partie = model.getPartie();
			
			int pourcentage=partie.pourcentageBonneRep();
			if(pourcentage<0) {
				messagePrivee.setText("C'est ratée pour cette fois ci, mieux vos réessayer "  +pseudo);
			}else if(pourcentage>0 && pourcentage<25) {
				messagePrivee.setText("Il va falloir revoir un peu "  +pseudo);
			}else if(pourcentage>25 && pourcentage<50) {
				messagePrivee.setText("Les bases sont la mais faut revoir encore un peu "  +pseudo);
			}else if(pourcentage>50 && pourcentage<75) {
				messagePrivee.setText("Il y a le niveau du niveau mais c'est pas encore parfait "  +pseudo);
			}else if(pourcentage>75 && pourcentage<100) {
				messagePrivee.setText(" Vous maîtriser vos acquis "  +pseudo);
			}else {
				messagePrivee.setText("  Mais vous savez tous, bien joué "  +pseudo);
			}
			
			
		}else {
			messagePrivee.setText("Il n'y a pas de partie en cours "+pseudo);
			
		}
	}
			
}
	
	
	
	
	

