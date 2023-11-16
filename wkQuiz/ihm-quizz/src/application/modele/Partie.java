/**
 * 
 */
package application.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
        reponsesDonnees = new HashMap<>();
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

    /**
     * Ajoute a la HashMap une question et sa reponse associe
     * Si une reponse existe deja elle est ecrasée
     * @param question cle dans la hashMap
     * @param reponseAssocie value dans la hashMap
     */
    public void setReponseDonnee(Question question , String reponseAssocie) {
        reponsesDonnees.put(question, reponseAssocie);
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
	
	

    /** @return valeur de indiceQuestion */
    public int getIndiceQuestion() {
        return indiceQuestion;
    }

    /** @param indiceQuestion nouvelle valeur de indiceQuestion */
    public void setIndiceQuestion(int indiceQuestion) {
        this.indiceQuestion = indiceQuestion;
    }
    /**
     * @return la HashMap associant les question et les reponses donnees par
     * l'utilisateur
     */
   public HashMap<Question, String> getReponseDonnees() {
       return this.reponsesDonnees;
   }   
	
	public int getNbBonneReponse() {
	    Set<Question> cle = reponsesDonnees.keySet();
	    int nbBonneReponse = 0;
	    
	    for (Question question : cle) {
	        
	        String reponseSelectionner = reponsesDonnees.get(question);
	        String reponseAttendu = question.getReponseJuste();
	        
            if (reponseSelectionner.equals(reponseAttendu)) {
                nbBonneReponse ++;
            }
        }
		return nbBonneReponse;
	}
	
	public int pourcentageBonneRep() {
		int nbReponse = getNombreQuestion();
		int nbReponseBonne = getNbBonneReponse();
		int pourcentage;
		
		//Pour éviter l'ArithmeticException, si nbReponse est nul 
		//on le remplace par 1 
		nbReponse = nbReponse == 0 ? 1 : nbReponse;
		
		pourcentage = (nbReponseBonne / nbReponse) * 100;
		
		return pourcentage;
	}
}
