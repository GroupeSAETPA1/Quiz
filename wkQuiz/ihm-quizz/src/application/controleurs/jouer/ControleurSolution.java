package application.controleurs.jouer;

import javax.naming.PartialResultException;

import application.Quiz;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.modele.Question;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Controleur de la page Solution
 * Celui-ci instancie des méthodes liées aux boutons de la page 
 * 
 * @author Tom DOUAUD
 * @author François de Saint Palais
 */
public class ControleurSolution {
		
	/** Le texte du score réalisé par le joueur */
	@FXML private Text score;
	
	/** Le tableau de la solution du quiz, avec une indication si le joueur a eu bon */
	@FXML private GridPane tableauSolution;
	
	private static final String TEXT_SCORE = "Score = %s/%s";
	
    private static final Image BONNE_REPONSE 
    = new Image("application\\vue\\images\\IconeValider.png");
    
    private static final Image MAUVAISE_REPONSE 
    = new Image("application\\vue\\images\\IconeAnnuler.png");
	
	/**
	 * Méthode liée au bouton retourner à l'acceuil
	 * qui devra envoyer vers la page Accueil.fxml
	 */
	@FXML
	private void retour() {
		System.out.println("Button retour");
		Quiz.changerVue("Resultat.fxml");
	}
	
	/**
	 * Méthode exécutée au chargement de la page Solution
	 * pour récupérer le score et le message
	 */
	@FXML
	public void initialize() {
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    score.setText("Score = TODO/TODO");
	    if (modele.getPartie() != null) {
            Partie partie = modele.getPartie();
            
            int nombreBonneReponse = 0;//STUB TODO get le nombre de bonne réponse
            int nombreTotalQuestion = partie.getNombreQuestion();
            
            // TODO initialiser le tableau avec les valeurs de la partie
            score.setText(String.format(TEXT_SCORE, nombreBonneReponse,nombreTotalQuestion));
        }
	    Question question = modele.getBanqueQuestion().getQuestion(0);
	    ajouterReponse(question, false);
	    ajouterReponse(question, true);
//	    tableauSolution.add(new Text("Salut"),1,10);
//	    System.out.println(tableauSolution.getColumnCount());
	    System.out.println(tableauSolution.getRowCount());
	}
	
	/**
	 * Ajoute une ligne à la GridPane.
	 * TODO comment method role
	 * @param question
	 * @param bonneReponse
	 */
	private void ajouterReponse(Question question, boolean bonneReponse) {
	    //La dernière ligne du GridPane
	    int ligneAAjoute = tableauSolution.getRowCount();
	    
	    Text libelle = new Text(question.getLibelle());
	    Text reponseJuste = new Text(question.getReponseJuste());
	    Text feedback = new Text(question.getFeedback());
	    ImageView feedbackReponse 
	    = new ImageView(bonneReponse ? BONNE_REPONSE : MAUVAISE_REPONSE);

	    GridPane.setHalignment(libelle, HPos.CENTER);
	    GridPane.setHalignment(reponseJuste, HPos.CENTER);
	    GridPane.setHalignment(feedback, HPos.CENTER);
	    GridPane.setHalignment(feedbackReponse, HPos.CENTER);
	    
	    tableauSolution.add(libelle, 0, ligneAAjoute);
	    tableauSolution.add(reponseJuste, 1, ligneAAjoute);
	    tableauSolution.add(feedback, 2, ligneAAjoute);
	    tableauSolution.add(feedbackReponse, 3, ligneAAjoute);
	}

}