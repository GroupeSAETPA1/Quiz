/*
 * Partie.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.modele;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import application.exception.DifficulteException;

/**
 * Modèle d'une partie du quiz
 * @author Quentin Costes
 * @author Lucas Descriaud
 */
public class Partie {
	
	/** 
     * Difficultée des questions de la partie en cours.
     * La partie en cours pourra prendre des questions de niveau égal 
     * à difficultePartie 
     */
    private Integer difficultePartie; 
    
    
    /** 
     * Nombre de questions auquel l'utilisateur 
     * répondra dans la partie actuelle 
     */ 
    private int nombreQuestionPartie ;
    
    /**
     * Pseudonyme du joueur du quiz
     */
    private String pseudo;
    
    
    /**
     * Categorie dans laquelle les questions seront tirées.
     * Initialisé a null et reste a null si l'option Aléatoire est choisi dans la
     * page de paramètres
     */
    private Categorie categorieQuestion;
    
    /**
     * Liste de questions correspondant au paramètres
     * selectionnés par l'utilisateur
     */
    private ArrayList<Question> questionsPossibles ;
    
    /**
     * Question jouée et ses réponses associées
     */
    private HashMap<Question, String> reponsesDonnees;
    
    /**
     * Question actuelle à laquelle l'utilisateur doit répondre
     */
    private Question actuelle;
    
    /**
     * Indice de la question actuelle
     */
    private int indiceQuestion;
    

    /**
     * Constructeur qui
     * initialise tout a null sauf le pseudo
     */
    public Partie () {
        reponsesDonnees = new HashMap<>();
    	questionsPossibles = new ArrayList<Question>();
    	pseudo = "";
    }
    
    /**
     * Change la difficultée actuelle pour difficultée 
     * @param difficulte la nouvelle difficultée du quiz
     * @throws DifficulteException si la difficultée est invalide
     */
    public void setDifficultePartie(int difficulte) throws DifficulteException {
    	if (difficulte > -1 && difficulte < 4) {
    		this.difficultePartie = difficulte ;
    	} else {
    		throw new DifficulteException("La difficultée est invalide, " 
    									+ "elle doit etre comprise " 
    									+ " entre 0 et 3 compris");
    	}
    }
    
    /**
     * Getter de la difficultée de la partie
     * @return (Int) la difficultée de la partie
     */
    public Integer getDifficultePartie() {
        return this.difficultePartie;
        
    }
    
    /**
     * Getter du nombre de questions de la partie
     * @return (int) le nombre de question dans la partie
     */
    public int getNombreQuestion() {
        return this.nombreQuestionPartie;
    }
    
    /**
     * Change le nombre de question de la partie en cours
     * @param nombreQuestion nouveau nombre de question
     */
    public void setNombreQuestion(int nombreQuestion) {
        this.nombreQuestionPartie = nombreQuestion;
    }
    
    /**
     * Change la catégorie de la partie actuelle
     * @param la categorie choisie pour le quiz
     * @throws IOException 
     * @throws InternalError 
     * @throws ClassNotFoundException 
     */
    public void setCategoriePartie(String choisis) throws ClassNotFoundException, InternalError, IOException {
        this.categorieQuestion = ModelePrincipal.getInstance().getBanqueCategorie()
                               					.getCategorieLibelleExact(choisis);
    }
    /**
     * Getter de la catégorie de la partie
     * @return (Categorie) la catégorie de la partie
     */
    public Categorie getCategoriePartie() {
        return this.categorieQuestion;          
    }
    
    /**
     * Getter des Questions possibles par rapport au paramètre de la partie
     * @return la liste de questions possible correspondant au paramètre
     */
    public ArrayList<Question> getQuestionPossible() {
        return this.questionsPossibles;
    }
    
    
    /**
     * Modifie la liste des questionPossible pour la prochaine partie
     * @param nouvelle liste de questions
     */
    public void setQuestionPossible(ArrayList<Question> aChanger) {
        this.questionsPossibles = aChanger;   
    }

    /**
     * Ajoute à la HashMap une question et sa réponse associée.
     * Si une réponse existe déja elle est écrasée
     * @param question la clé dans la hashMap
     * @param reponseAssocie la valeur dans la hashMap
     */
    public void setReponseDonnees(Question question , String reponseAssocie) {
        reponsesDonnees.put(question, reponseAssocie);
    }

    /**
     * Getter de la réponse donnée par l'utilisateur
     * @return la HashMap associant les question et les réponses données par
     * l'utilisateur
     */
    public HashMap<Question, String> getReponseDonnees() {
    	return this.reponsesDonnees;
    }   
    
    /**
     * Getter de la question actuelle
	 * @return la question actuelle
	 */
	public Question getQuestionActuelle() {
	    return this.actuelle;
	}
	
	/**
	 * Change la question actuelle
	 * @param la nouvelle question actuelle
	 */
	public void setQuestionActuelle(Question aChanger) {
	    this.actuelle = aChanger;
	}
	
	

    /**
     * Getter de l'indice de la question actuelle
     * @return valeur (int) de indiceQuestion 
     */
    public int getIndiceQuestion() {
        return indiceQuestion;
    }

    /** 
     * Change l'indice de la question par le nouvel indice
     * @param indiceQuestion (int) nouvelle valeur de indiceQuestion 
     */
    public void setIndiceQuestion(int indiceQuestion) {
        this.indiceQuestion = indiceQuestion;
    }
	
    /**
     * Getter du nombre de bonne réponses
     * @return (int) le nombre de bonnes réponses
     */
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
	
	/**
	 * Getter du pseudo du joueur
	 * @return (String) le pseudo du joueur
	 */
    public String getPseudo() {
		return pseudo;
	}

    /**
     * Setter du pseudo du joueur
     * @param pseudo (String) le pseudo du joueur
     */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	

	/**
	 * Récupere le pourcentage de bonnes réponses du joueur
	 * @return (douvle) le pourcentage de 0.0 à 100 du joueur
	 */
	public double pourcentageBonneRep() {
		double nbReponse = 0;
		if (getQuestionPossible().size() == getIndiceQuestion()) {
			nbReponse = getNombreQuestion();
	    } else {
	    	nbReponse = getQuestionPossible().size();
	    }	
		
		double nbReponseBonne = getNbBonneReponse();
		double pourcentage;
		
		// Pour éviter l'ArithmeticException, si nbReponse est nul 
		// on le remplace par 1 
		nbReponse = nbReponse == 0 ? 1 : nbReponse;
		
		pourcentage = (nbReponseBonne / nbReponse) * 100;
		
		return pourcentage;
	}
}
