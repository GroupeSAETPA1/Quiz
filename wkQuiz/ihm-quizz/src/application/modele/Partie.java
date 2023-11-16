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
     * Liste representant l'ordre des question ainsi que les reponses donnéee
     */  
    private ArrayList<HashMap<Question, String>> ordreQuestion;
    
    /**
     * Question actuelle a laquelle l'utilisateur doit repondre
     */
    private Question actuelle ;
    
    /**
     * Indice de la question actuelle
     */
    private int indiceQuestion ;
    


    /**
     * constructeur 
     * initialise tout a null sauf la hashmap
     */
    public Partie () {
    	questionsPossibles = new ArrayList<Question>();
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

    
	
	/* non javadoc - @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "Partie [difficultePartie=" + difficultePartie + ", nombreQuestionPartie=" + nombreQuestionPartie
                + ", categorieQuestion=" + categorieQuestion + ", questionsPossibles=" + questionsPossibles
                + ", ordreQuestion=" + ordreQuestion + ", actuelle=" + actuelle + "]";
    }

    /**
	 * @return la question actuelle
	 */
	public Question getActuelle() {
	    return this.actuelle;
	}
	
	/**
	 * Change la question actuelle
	 * @param nouvelle question actuelle
	 */
	public void setActuelle(Question aChanger) {
	    this.actuelle = aChanger;
	}
	
	
	/**
	 * @return la HashMap associant les question et les reponses donnees par
	 * l'utilisateur
	 */
	public ArrayList<HashMap<Question, String>> getReponseDonnees() {
        return ordreQuestion;
	    
	}
	
	
	/**
	 * Ajoute en dernière position une question et sa reponse dans la liste
	 * @param aAjouter dernière Question repondu
	 */
	public void setReponseDonnee(HashMap<Question, String> aAjouter) {
	    ordreQuestion.addLast(aAjouter);
	}

    /** @return valeur de indiceQuestion */
    public int getIndiceQuestion() {
        return indiceQuestion;
    }

    /** @param indiceQuestion nouvelle valeur de indiceQuestion */
    public void setIndiceQuestion(int indiceQuestion) {
        this.indiceQuestion = indiceQuestion;
    }
	
	/**
     * Creer une hashmap et lui associe une question et la reponse
     * donne par l'utilisateur
     * @param question cle dans la hashMap
     * @param reponseAssocie value dans la hashMap
	 * @return 
     */
    private HashMap<Question, String> associerQuestionReponse(Question question, 
            String reponseAssocie) {
        HashMap<Question, String> reponseDonne 
	    = new HashMap<Question, String>();
	    reponseDonne.put(question, reponseAssocie);
	    return reponseDonne;
    }
	
	/**
	 * Choisis aléatoirement une question qui sera la prochaine question
	 * posée et l'enlève de la liste des question pouvant etre posée
	 */
	public void GenererProchaineQuestion() {
	    
	}
}
