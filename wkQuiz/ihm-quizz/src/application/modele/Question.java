package application.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


/**
 * TODO comment class responsibility (SRP)
 * @author Lucas
 */

public class Question implements Serializable {
	
	/** difficultée minimale d'une question */
	private final int DIFFICULTE_MINIMALE = 0;
	
	/** difficultée maximale d'une question */
	private final int DIFFICULTE_MAXIMALE = 3;
	
    /**
     * Intitulé de la question
     */
    private String libelle;

    /**
     * <p>Entier entre 1 et 3</p>
     * 
     * <ul>
     * 	<li>1 représentant le niveau facile</li>
     * 	<li>2 le moyen</li>
     * 	<li>3 le difficile</li>
     * </ul>
     */
    private int difficulte;

    /**
     * Bonne réponses à la questions
     */
    private String reponseJuste;

    /**
     * Liste des mauvaises propositions de réponses à la question. 
     * Il peut y avoir entre 1 et 4 (inclus) mauvaises réponses.
     */
    private ArrayList<String> mauvaisesReponses;

    /**
     * Texte optionnel correspondant a une explication 
     * de la correction de la question
     */
    private String feedback;

    /**
     * Catégorie de la question
     */  
    private Categorie categorie;

    /**
     * Constructeur de la classe utilisant les paramètres
     * <ul>
     *  <li>le libellé</li>
     *  <li>la catégorie</li>
     *  <li>la difficulté</li>
     *  <li>la bonne proposition de réponse</li>
     *  <li>la liste des mauvaises propositions</li>
     *  <li>le feedback</li>
     * </ul>
     * @throw IllegalArgumentException si
     * <ul>
     *  <li> le libellé est une chaîne vide</li> 
     *  <li> la reponseJuste est une chaîne vide</li>
     *  <li> la difficulté est différente de 1 , 2 ou 3</li>
     *  <li> reponsesFausses est une liste vide</li>
     *  <li> reponseFausses contient une valeur en double 
     *       (case ignorée) </li>
     *  <li> reponseFausses contient une chaine egale a reponse juste
     *       (case ignorée) </li>
     *  <li> reponseFausse contient uniquement des chaines vides </li>
     * </ul>
     */
    public Question(String libelle,Categorie categorie,int difficulte,
                    String reponseJuste,ArrayList<String> reponsesFausse,
                    String feedback) {
        if (libelle.equals("")) {
            throw new IllegalArgumentException("Le libelle est vide");
        }
        if (difficulte < DIFFICULTE_MINIMALE || difficulte > DIFFICULTE_MAXIMALE) {
            throw new IllegalArgumentException("Le niveau est compris "
                                               + "entre 1 et 3");
        }
        if (reponseJuste.equals("")) {
            throw new IllegalArgumentException("La réponse juste est vide");
        }
        if (reponsesFausse.isEmpty()) {
            throw new IllegalArgumentException("La liste des mauvaises reponses "
                    + "ne doit pas etre vide");
        }
        if (!reponsesFausseSansDoublon(reponsesFausse)) {
            throw new IllegalArgumentException("La liste des mauvaises reponses "
                    + "ne peut pas contenir de valeurs "
                    + "en double (casse ignoré)");
        }
        
        if (reponseFausseContientReponseJuste(reponsesFausse , reponseJuste)) {
            throw new IllegalArgumentException("La liste des reponses fausses "
                    + "contient une ou plusieurs propositions égale "
                    + "a la réponse juste (casse ignorée");
        }
        //else
        this.libelle = libelle;
        this.categorie = categorie;
        this.difficulte = difficulte;
        this.reponseJuste = reponseJuste;
        this.mauvaisesReponses = reponsesFausse;
        this.feedback = feedback;
        
    }
    
    /**
     * verifie la validité d'une ArrayList pour le constructeur de question
     * @param aTester : ArrayList dont on veux verifier la validité
     * @return true si aTester n'est pas vide et qu'elle n'a que des valeurs
     *         distinctes (casse ignoré)
     *         false sinon
     */
    private static boolean reponsesFausseSansDoublon(ArrayList<String>aTester) {
        boolean sansDoublon = true ;
        String precedent;
        for (int i = 0 ; i < aTester.size() && sansDoublon ; i++) {
            precedent = aTester.get(i);
            for (int j = 0 ; j < aTester.size() && sansDoublon ; j++) {
                if (i != j) {
                    sansDoublon = !precedent.equalsIgnoreCase(aTester.get(j));                    
                }
            }           
        }
        return sansDoublon;
        
    }
    /**
     * @param aTester : ArrayList dont on veux verifier la validité
     * @return true si aTester contient une chaine de caractère identique ,
     *         la casse est ignorée des deux cotées.
     *         false sinon.      
     */
    private static boolean reponseFausseContientReponseJuste
    (ArrayList<String> aTester ,  String reponseJuste) {
        boolean fauxContientJuste = false ;
        for (int i = 0 ; i < aTester.size() && !fauxContientJuste  ; i++) {
            fauxContientJuste =  reponseJuste.equalsIgnoreCase(aTester.get(i));
        }
        return fauxContientJuste ;
        
    }
    
    /** 
     * Change le libelle de la question
     * @param nouveauIntitulle
     */
    public void setLibelle(String nouveauIntitulle) {
        if (nouveauIntitulle.equals("")) {
            throw new IllegalArgumentException("Intitule vide");
        }
        //else
        this.libelle = nouveauIntitulle;
    }

    /** 
     * change la categorie de la question
     * @param nouvelleCategorie
     */
    public void setCatgorie(Categorie nouvelleCategorie) {
        this.categorie = nouvelleCategorie ;
    }

    /**
     * change la difficultée de la question
     * @param nouvelleDifficulte
     */
    public void setDifficulte(int nouvelleDifficulte) {
    	if (nouvelleDifficulte < DIFFICULTE_MINIMALE 
            || nouvelleDifficulte > DIFFICULTE_MAXIMALE) {
            throw new IllegalArgumentException("Le niveau est compris "
                                               + "entre 1 et 3");
        }
    	this.difficulte = nouvelleDifficulte;
    }

    /**
     * change la bonne réponse de la question
     * @param nouvelleBonneReponse
     */
    public void setBonneReponse(String nouvelleBonneReponse) {
        if (nouvelleBonneReponse.equals("")) {
            throw new IllegalArgumentException("Bonne réponse vide");
        }
        if (reponseFausseContientReponseJuste(mauvaisesReponses, 
            nouvelleBonneReponse)) {
            throw new IllegalArgumentException("Impossible de set une bonne "
                    + "reponse si la valeur est deja contenu "
                    + "dans mauvaiseReponse ");
        }
        //else
        this.reponseJuste = nouvelleBonneReponse;
    }

    /**
     * changes les mauvaises réponses de la question
     * @param nouvellesMauvaisesReponses
     */
    public void setMauvaiseReponse(ArrayList<String>nouvellesMauvaisesReponses){
        if (nouvellesMauvaisesReponses.isEmpty()) {
            throw new IllegalArgumentException("Impossible de modifier "
                    + "avec une liste vide");
        }
        if (reponseFausseContientReponseJuste(nouvellesMauvaisesReponses, 
            reponseJuste)) {
            
            throw new IllegalArgumentException("Impossible de modifier si "
                    + ""
                    + "la liste de mauvaise reponse contient une possibilité "
                    + "égale a la bonne reponse (case ignorée)");
            
        }
        if (!reponsesFausseSansDoublon(nouvellesMauvaisesReponses)) {
            throw new IllegalArgumentException("Impossible de modifier si la "
                    + "liste contient des valeurs en doublon (case ignorée)");
        }
        //else 
        mauvaisesReponses = nouvellesMauvaisesReponses;
    }
    
    /**
     * change le feedback de la question
     * @param feedback
     */
    public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	/** 
	 * @return ArrayList contenant les mauvais réponses
	 */
    public ArrayList<String> getMauvaisesReponses() {
        return mauvaisesReponses;
    }

    /** @return nom de la categorie */
    public String getCategorie() {
        return categorie.getNom();
    }

    /** @return difficultée de la question*/
    public int getDifficulte() {
        return this.difficulte;
    }

    /** @return libelle de la question (this) */
    public String getLibelle() {
        return this.libelle;
    }
    
    /** @return reponse juste de la question (this)*/
    public String getReponseJuste() {
    	return this.reponseJuste;
    }
    
    /** @return feedback de la question */
    public String getFeedback() {
    	return this.feedback;
    }
    
    @Override
    public String toString() {
    	String aRetouner =  "difficulté de la question : " + this.getDifficulte()
    		 + "\nCategorie de la question : " + this.getCategorie()
    		 + "\nIntiltulé de la question : " + this.getLibelle()
    		 + "\nMauvaise réponses :\n";
    		 
    	for (String reponse : this.mauvaisesReponses) {
			aRetouner += "- " + reponse + "\n";
		}
    	
    	aRetouner += "Bonne réponse : " + getReponseJuste();
    	if (getFeedback() != "") {
    		aRetouner += "\nFeedback : " + getFeedback();    	
    	}
    	return aRetouner; 	
    }


    /* non javadoc - @see java.lang.Object#equals(java.lang.Object) */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Question other = (Question) obj;
        return categorie.equals(other.categorie) 
                && libelle.equals(other.libelle)
                && mauvaisesReponses.equals(other.mauvaisesReponses)
                && reponseJuste.equals(other.reponseJuste);
    }
    

}
