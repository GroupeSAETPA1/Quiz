package application.modele;

import java.io.Serializable;
import java.util.ArrayList;

import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;


/**
 * TODO comment class responsibility (SRP)
 * Classe représentant une question du quiz.
 * @author Lucas Descriaud
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
     * @throws InvalidFormatException ,
     * @throws InvalidNameException ,
     * @throws ReponseException si
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
                    String feedback) throws InvalidFormatException, 
                    InvalidNameException, ReponseException {
        if (libelle.equals("")) {
            throw new InvalidNameException("Le libelle est vide");
        }
        if (difficulte < DIFFICULTE_MINIMALE || difficulte > DIFFICULTE_MAXIMALE) {
            throw new InvalidFormatException("Le niveau est compris "
                                               + "entre 1 et 3");
        }
        if (reponseJuste.equals("")) {
            throw new InvalidNameException("La réponse juste est vide");
        }
        if (reponsesFausse.isEmpty()) {
            throw new InvalidFormatException("La liste des mauvaises reponses "
                    + "ne doit pas etre vide");
        }
        if (!reponsesFausseSansDoublon(reponsesFausse)) {
            throw new ReponseException("La liste des mauvaises reponses "
                    + "ne peut pas contenir de valeurs "
                    + "en double (casse ignoré)");
        }
        
        if (reponseFausseContientReponseJuste(reponsesFausse , reponseJuste)) {
            throw new ReponseException("La liste des reponses fausses "
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
     * Vérifie la validité d'une ArrayList pour le constructeur de question
     * @param aTester : ArrayList dont on veux vérifier la validité
     * @return true si aTester n'est pas vide et qu'elle n'a que des valeurs
     *         distinctes (casse ignoré)
     *         false sinon
     */
    private static boolean reponsesFausseSansDoublon(ArrayList<String>aTester) {
        boolean sansDoublon = true;
        String precedent;
        for (int i = 0 ; i < aTester.size() && sansDoublon; i++) {
            precedent = aTester.get(i);
            for (int j = 0 ; j < aTester.size() && sansDoublon; j++) {
                if (i != j) {
                    sansDoublon = !precedent.equalsIgnoreCase(aTester.get(j));                    
                }
            }           
        }
        return sansDoublon;
    }

    /**
     * Vérifie si une réponse juste est parmis les mauvaises réponses
     * @param aTester : ArrayList dont on veux verifier la validité
     * @return true si aTester contient une chaine de caractère identique ,
     *         la casse est ignorée des deux cotées.
     *         false sinon.      
     */
    private static boolean reponseFausseContientReponseJuste
    (ArrayList<String> aTester ,  String reponseJuste) {
        boolean fauxContientJuste = false;
        for (int i = 0; i < aTester.size() && !fauxContientJuste; i++) {
            fauxContientJuste =  reponseJuste.equalsIgnoreCase(aTester.get(i));
        }
        return fauxContientJuste;
        
    }
    
    /** 
     * Change le libelle de la question
     * @param nouveauIntitulle (String)
     * @throws InvalidNameException si nouveauIntitulle est une chaine vide
     */
    public void setLibelle(String nouveauIntitulle) 
    throws InvalidNameException {
        if (nouveauIntitulle.equals("")) {
            throw new InvalidNameException("Intitule vide");
        }
        //else
        this.libelle = nouveauIntitulle;
    }

    /** 
     * Change la categorie de la question
     * @param nouvelleCategorie la categorie qui remplace l'ancienne
     */
    public void setCatgorie(Categorie nouvelleCategorie) {
        this.categorie = nouvelleCategorie ;
    }

    /**
     * Change la difficultée de la question
     * @param nouvelleDifficulte (int) la nouvelle difficultée
     * @throws InvalidFormatException si nouvelleDifficulte n'est pas entre 1 et 3
     */
    public void setDifficulte(int nouvelleDifficulte) 
    throws InvalidFormatException {
    	if (nouvelleDifficulte < DIFFICULTE_MINIMALE 
            || nouvelleDifficulte > DIFFICULTE_MAXIMALE) {
            throw new InvalidFormatException("Le niveau est compris "
                                           + "entre 1 et 3");
        }
    	this.difficulte = nouvelleDifficulte;
    }

    /**
     * Change la bonne réponse de la question
     * @param nouvelleBonneReponse (String) la nouvelle bonne réponse
     * @throws InvalidNameException si nouvelleBonneReponse est une chaine vide
     * @throws ReponseException si nouvelleBonneReponse est contenu dans mauvaiseReponse
     */
    public void setBonneReponse(String nouvelleBonneReponse) 
    throws InvalidNameException, ReponseException {
        if (nouvelleBonneReponse.equals("")) {
            throw new InvalidNameException("Bonne réponse vide");
        }
        if (reponseFausseContientReponseJuste(mauvaisesReponses, 
            nouvelleBonneReponse)) {
            throw new ReponseException("Impossible de set une bonne "
                    + "reponse si la valeur est deja contenu "
                    + "dans mauvaiseReponse ");
        }
        //else
        this.reponseJuste = nouvelleBonneReponse;
    }

    /**
     * Change les mauvaises réponses de la question
     * @param nouvellesMauvaisesReponses (ArrayList<String>) la nouvelle liste des mauvaises réponses
     * @throws InvalidFormatException si nouvellesMauvaisesReponses est vide
     * @throws ReponseException si nouvellesMauvaisesReponses 
     * contient une valeur en double ou 
     * si elle contient une valeur égale à la bonne réponse
     */
    public void setMauvaiseReponse(ArrayList<String>nouvellesMauvaisesReponses) 
    throws InvalidFormatException, ReponseException {
        if (nouvellesMauvaisesReponses.isEmpty()) {
            throw new InvalidFormatException("Impossible de modifier "
                                           + "avec une liste vide");
        }
        if (reponseFausseContientReponseJuste(nouvellesMauvaisesReponses, 
            reponseJuste)) {
            
            throw new ReponseException("Impossible de modifier si "
                                     + "la liste de mauvaise reponse " 
                                     + "contient une possibilité "
                                     + "égale a la bonne reponse (case ignorée)");
            
        }
        if (!reponsesFausseSansDoublon(nouvellesMauvaisesReponses)) {
            throw new ReponseException("Impossible de modifier si la "
                                     + "liste contient des valeurs en doublon " 
                                     + "(case ignorée)");
        }
        //else 
        mauvaisesReponses = nouvellesMauvaisesReponses;
    }
    
    /**
     * Change le feedback de la question
     * @param feedback (String) le nouveau feedback
     */
    public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	/** 
     * Getter des mauvaises réponses de la question
	 * @return ArrayList contenant les mauvais réponses
	 */
    public ArrayList<String> getMauvaisesReponses() {
        return mauvaisesReponses;
    }

    /**
     * Getter de la catégorie de la question
     *  @return nom de la categorie (String)
     */
    public String getCategorie() {
        return categorie.getNom();
    }

    /**
     * Getter de la difficultée de la question
     * @return la difficultée de la question (int)
     */
    public int getDifficulte() {
        return this.difficulte;
    }

    /** 
     * Getter du libelle de la question
     * @return le libelle de la question (String) 
     */
    public String getLibelle() {
        return this.libelle;
    }
    
    /** 
     * Getter de la bonne réponse de la question
     * @return la réponse juste de la question (String)
     */
    public String getReponseJuste() {
    	return this.reponseJuste;
    }
    
    /** 
     * Getter du feedback de la question
     * @return le feedback de la question (String)
     */
    public String getFeedback() {
    	return this.feedback;
    }
    
    /**
     * Override de la méthode toString
     * @return une chaine de caractère contenant les informations de la question
     */
    @Override
    public String toString() {
    	String aRetouner =  "Difficulté de la question : " + this.getDifficulte()
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
                && libelle.equalsIgnoreCase(other.libelle)
                && mauvaisesReponses.equals(other.mauvaisesReponses)
                && reponseJuste.equalsIgnoreCase(other.reponseJuste);
    }
}
