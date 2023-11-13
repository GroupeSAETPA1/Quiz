package application.controleurs.lignes;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

import application.Quiz;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * classe permettant l'affichage des lignes dans la tableView de la vue EditerQuestions
 * @author quentin COSTES
 */
public class LigneQuestion {
	
    private final SimpleStringProperty categorie;
    private final SimpleStringProperty nomQuestion;
    private final String libelleNonFormater;
    /*
    private final SimpleStringProperty reponseJuste;
    private final SimpleStringProperty reponsesFausses;
    private final SimpleStringProperty feedback;
    */
    private final Button editerButton;
    private final Button supprimerButton;

    public LigneQuestion(Categorie categorie, String libelle, String reponseJuste, ArrayList<String> reponsesFausses, String feedback) {
        this.categorie = new SimpleStringProperty(categorie.getNom());
        
        // si la question est trop longue, on met des \n tout les 31 char
        String nomQuestion = "";
        
        if (libelle.length() > 31) {
        	for (int i = 0; i <= libelle.length() - 1; i ++) {
        		nomQuestion += libelle.charAt(i);
        		if (i % 31 == 0 && i != 0) {
        			nomQuestion += "\n";
        		}
        	}
        }
        
        this.nomQuestion = new SimpleStringProperty(nomQuestion);
		this.libelleNonFormater = libelle;
        
        
        
        /* On remet si la prof veut a tout prix tout les colonnes
        this.reponseJuste = new SimpleStringProperty(reponseJuste);
        this.feedback = new SimpleStringProperty(feedback);
        
        // on baillaie les reponses fausses pour les concatener dans une seule chaine de caractere
        String reponsesFaussesString = "";
        for (String reponse : reponsesFausses) {
        	reponsesFaussesString += "• " + reponse + "\n";
        }
        this.reponsesFausses = new SimpleStringProperty(reponsesFaussesString);
        */
        this.editerButton = new Button("Éditer");
        this.supprimerButton = new Button("Supprimer");
        editerButton.setOnAction(event -> editerQuestion());
        supprimerButton.setOnAction(event -> supprimerQuestion());
    }

    
    public String getCategorie() {
		return categorie.get();
	}


	public String getNomQuestion() {
		return nomQuestion.get();
	}

	/* On remet si la prof veut a tout prix tout les colonnes
	public String getReponseJuste() {
		return reponseJuste.get();
	}


	public String getReponsesFausses() {
		return reponsesFausses.get();
	}


	public String getFeedback() {
		return feedback.get();
	}

	*/
	public Button getEditerButton() {
		return editerButton;
	}


	public Button getSupprimerButton() {
		return supprimerButton;
	}

	public String getLibelleNonFormater() {
		return libelleNonFormater;
	}


	public void editerQuestion(){
<<<<<<< Updated upstream
		ModelePrincipal.getInstance().setQuestionAModifier(ModelePrincipal.getInstance().getBanqueQuestion().getQuestionsLibelle(libelleNonFormater).get(0));
=======
		ModelePrincipal.getInstance().setQuestionAModifier(ModelePrincipal.getInstance().getBanqueQuestion().getQuestionsLibelle(getNomQuestion()).get(0));
>>>>>>> Stashed changes
		Quiz.chargerEtChangerVue("EditerQuestion.fxml");
    }

    public void supprimerQuestion() {
    	// méthode appelée lors de l'appuie sur le bouton de suppression de la categorie

        // on demande confirmation à l'utilisateur
        boolean result = AlertBox.showConfirmationBox("Êtes-vous sûr de vouloir supprimer la question " + this.getNomQuestion() + " ?");
        if (result) {
        	// si l'utilisateur confirme
        	// on supprime la question
        	if (ModelePrincipal.getInstance().supprimerQuestion(ModelePrincipal.getInstance().getBanqueQuestion().getQuestionsLibelle(this.getLibelleNonFormater()).get(0))) {
        		// si la suppression a réussi
        		AlertBox.showSuccessBox("Suppression effectuée");
        		Quiz.chargerEtChangerVue("EditerQuestions.fxml");
        	} else {
        		// si la suppression a échoué
        		AlertBox.showErrorBox("Suppression échouée");
        	}
        } else {
        	// si l'utilisateur annule
        	AlertBox.showErrorBox("Suppression annulée");
        }
    	
    }
}
