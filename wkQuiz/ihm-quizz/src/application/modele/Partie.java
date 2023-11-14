/**
 * 
 */
package application.modele;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 */
public class Partie {
	
	/** 
     * Difficulte des questions de la partie en cours
     * La partie en cour pourra prendre des questions de niveau egal 
     * a difficultePartie 
     **/
    private Integer difficultePartie; 
    
    
    /** Nombre de question auquel l'utilisateur 
     * repondra dans la partie actuelle 
     */ 
    private int nombreQuestionPartie ;
    
    
    /**
     * Categorie dans laquelle les questions seront tiree
     * Initialisé a null reste a null si l'option Aléatoire est choisis dans la
     * page de paramètre
     */
    private Categorie categorieQuestion ;
    
    /**
     * Liste de question correspondant au parametre 
     * selectionne par l'utilisateur
     */
    private ArrayList<Question> questionsPossibles ;
    
    /**
     * question jouée et réponses associées
     */
    private HashMap<Question, String> reponsesDonnees;
    
    /**
     * constructeur 
     * initialise tout a null sauf la hashmap
     */
    public Partie () {
    	reponsesDonnees = new HashMap<>();
    }
    
    /**
     * Change la difficulte actuelle pour difficulte 
     * @param difficulte nouvelle difficulte
     */
    public void setDifficultePartie(int difficulte) {
        this.difficultePartie = difficulte ;
    }
    
    /**
     * @return la difficulte de la partie en cour
     */
    public Integer getDifficulte() {
        return this.difficultePartie;
        
    }
    
    /**
     * @return le nombre de question dans la partie en cour 
     */
    public int getNombreQuestion() {
        return this.nombreQuestionPartie;
    }
    
    /**
     * Change le nombre de question de la partie en cours
     * @param nombreQuestion nouveau nombre de question
     */
    public void setNombreQuestion(int nombreQuestion) {
        this.nombreQuestionPartie = nombreQuestion ;
    }
    
    /**
     * Change la categorie de la partie actuelle
     * @param choisis la categorie dans laquelle on veut prendre des questions
     */
    public void setCategoriePartie(String choisis) {
        this.categorieQuestion = ModelePrincipal.getInstance().getBanqueCategorie()
                               					.getCategorieLibelleExact(choisis);
    }
    /**
     * @return la categorie dans lequelle seront 
     * tirés les questions de la partie  
     */
    public Categorie getCategoriePartie() {
        return this.categorieQuestion;          
    }
    
    /**
     * @return la liste de question possible correspondant au parametre
     */
    public ArrayList<Question> getQuestionPossible() {
        return this.questionsPossibles;
    }
    
    
    /**
     * Modifie la liste des questionPossible pour la prochaine partie
     * @param nouvelle liste de questions
     */
    public void setQuestionPossible(ArrayList<Question> aChanger) {
        this.questionsPossibles = aChanger ;   
    }

	@Override
	public String toString() {
		return "Partie [difficultePartie=" + difficultePartie + ", nombreQuestionPartie=" + nombreQuestionPartie
				+ ", categorieQuestion=" + categorieQuestion + ", questionsPossibles=" + questionsPossibles
				+ ", reponsesDonnees=" + reponsesDonnees + "]";
	}

}
