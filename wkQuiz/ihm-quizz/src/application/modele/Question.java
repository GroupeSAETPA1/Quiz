/*
 * Question.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.modele;

import java.io.Serializable;
import java.util.ArrayList;

import application.exception.CreerQuestionException;
import application.exception.DifficulteException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;


/**
 * Classe représentant une question du quiz.
 * @author Lucas Descriaud
 */
public class Question implements Serializable {
	
	/** ID de sérialisation */
    private static final long serialVersionUID = 1L;

    /**
     * Renvoie true si la reponse juste est présente 
     * dans la liste des réponses fausses
     * @param aTester
     * @param reponseJuste
     * @return true si la reponse juste est présente 
     * dans la liste des réponses fausses
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
     * Renvoie true si aTester n'a que des valeurs distinctes 
     * false sinon
     * @param aTester : ArrayList dont on veux vérifier la validité
     * @return true si aTester n'est pas vide et qu'elle n'a que des valeurs
     *         distinctes
     *         false sinon
     */
    private static boolean reponsesFausseSansDoublon(ArrayList<String> aTester){
        boolean sansDoublon = true;
        String precedent;
        for (int i = 0 ; i < aTester.size() && sansDoublon; i++) {
            precedent = aTester.get(i);
            for (int j = 0 ; j < aTester.size() && sansDoublon; j++) {
                if (i != j) {
                    sansDoublon = !precedent.equals(aTester.get(j));                    
                }
            }
        }
        return sansDoublon;
    }
	
	
	/**
     * Catégorie de la question
     */  
    private Categorie categorie;
	
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
	
	/** difficulté maximale d'une question */
	private final int DIFFICULTE_MAXIMALE = 3;
	
    /** difficulté minimale d'une question */
	private final int DIFFICULTE_MINIMALE = 1;

    /**
     * Texte optionnel correspondant a une explication 
     * de la correction de la question
     */
    private String feedback;

    /**
     * Intitulé de la question
     */
    private String libelle;

    private final static int LONGUEUR_LIBELLE_MAX = 250;

    private final static int LONGUEUR_MAX_FEEDBACK = 350 ;

    private final static int LONGUEUR_MAX_REPONSE = 250;

    /**
     * Liste des mauvaises propositions de réponses à la question. 
     * Il peut y avoir entre 1 et 4 (inclus) mauvaises réponses.
     */
    private ArrayList<String> mauvaisesReponses;

    /**
     * Bonne réponses à la questions
     */
    private String reponseJuste;
    
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
     * @throws DifficulteException 
     */
    public Question(String libelle,Categorie categorie,int difficulte,
                    String reponseJuste, ArrayList<String> reponsesFausse,
                    String feedback) throws CreerQuestionException, InvalidNameException {
        
        if (libelle.isBlank()  || libelle.length() > LONGUEUR_LIBELLE_MAX ) {
            throw new InvalidNameException("Le libéllé contient " + libelle.length() 
            + " caractères. Il faut qu'il soit entre 1 et" + LONGUEUR_LIBELLE_MAX);
        }
        if (difficulte < DIFFICULTE_MINIMALE || difficulte > DIFFICULTE_MAXIMALE) {
            throw new DifficulteException("Le niveau de difficulté doit être "
                                             + "compris entre 1 et 3");
        }
        if (reponseJuste.isBlank() || reponseJuste.length() > LONGUEUR_MAX_REPONSE) {
            throw new InvalidNameException("La réponse juste contient" + 
            libelle.length()  + "Il faut qu'elle contienne entre 1 et " 
            + LONGUEUR_MAX_REPONSE);
        }
        if (reponsesFausse.isEmpty()) {
            throw new InvalidFormatException("La liste des mauvaises réponses "
                    + "ne doit pas etre vide");
        }
        if (!reponsesFausseSansDoublon(reponsesFausse)) {
            throw new ReponseException("La liste des mauvaises réponses "
                    + "ne peut pas contenir de valeurs "
                    + "en double");
        }
        
        if (reponseFausseContientReponseJuste(reponsesFausse , reponseJuste)) {
            throw new ReponseException("La liste des réponses fausses "
                    + "contient une ou plusieurs propositions égale "
                    + "à la réponse juste (casse ignorée)");
        }
        if (contientAccent(reponseJuste, reponsesFausse, libelle, feedback)) {
        	throw new InvalidNameException("Aucun champ ne doit contenir des accents " 
        + " ou des caractères qui ne figurent pas dans la table ASCII (Exemple ç).");
        }
        
        ArrayList<String>reponseFausseTropLongue = 
                reponseFausseLongueurInvalide(reponsesFausse);
        if (!reponseFausseTropLongue.isEmpty()) {
            StringBuilder messageErreur = 
                    erreurReponsesFaussesTropLongue(reponseFausseTropLongue);
            throw new ReponseException(messageErreur.toString());
        }
        if (feedback != null && feedback.length() > LONGUEUR_MAX_FEEDBACK) {
            throw new InvalidNameException("Le feedback contient " 
            + feedback.length() + " caractères. Il peut en contenir au maximum " 
                    + LONGUEUR_MAX_FEEDBACK);
        }
        
        //else
        this.libelle = libelle;
        this.categorie = categorie;
        this.difficulte = difficulte;
        this.reponseJuste = reponseJuste;
        this.mauvaisesReponses = reponsesFausse;
        this.feedback = feedback;
        
    }

    private boolean contientAccent(String reponseJuste, ArrayList<String> reponsesFausses, String libelle, String feedback) {
    	boolean ok = true; // ok si pas d'accent
    	for (int i = 0 ; i < reponsesFausses.size(); i++) {
    		ok = ok && ModelePrincipal.alphabetOk(reponsesFausses.get(i));
    	}
    	
    	ok = ok && ModelePrincipal.alphabetOk(reponseJuste);
    	ok = ok && ModelePrincipal.alphabetOk(libelle);
    	ok = ok && ModelePrincipal.alphabetOk(feedback);
    	
    	return !ok;
	}

	/** 
     * Construire un message d'erreur a partir d'une arrayList de reponse trop
     * longue
     * @param reponseFausse liste des reponses fausses a ajouter dans le 
     *        stringBuilder
     * @return un StringBuilder 
     */
    private static StringBuilder erreurReponsesFaussesTropLongue(ArrayList<String> reponseFausse) {
        StringBuilder messageErreur = new StringBuilder();
        messageErreur.append("Une réponse fausse peut contenir au maximum " 
                + LONGUEUR_MAX_REPONSE + " caractères. Voici les réponses qui posent"
                        + " problèmes : ");
                for (String reponse : reponseFausse) {
                    messageErreur.append("- " + reponse + " " + reponse.length() 
                    + " caractères \n" );
                }
        return messageErreur;
    }
    
    /** 
     * Construit une liste des reponses ne respectant pas la conditions 
     * element de la liste.lenght < longueur max reponse
     * @param reponsesFausse liste dans laquelle on veut verifier la validite
     * @return une liste vide si tous est ok , une liste avec les element de la 
     * liste initial dont la longueur est > longuer max reponse sinon
     */
    private ArrayList<String> reponseFausseLongueurInvalide(ArrayList<String> 
    reponsesFausse) {
        ArrayList<String> aRenvoyer = new ArrayList<String>();
        for (String reponse : reponsesFausse) {
            if (reponse.length() > LONGUEUR_MAX_REPONSE) {
                aRenvoyer.add(reponse);
            }
        }
        return aRenvoyer;
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
     * Getter du feedback de la question
     * @return le feedback de la question (String)
     */
    public String getFeedback() {
    	return this.feedback;
    }

    /** 
     * Getter du libelle de la question
     * @return le libelle de la question (String) 
     */
    public String getLibelle() {
        return this.libelle;
    }

    /** 
     * Getter des mauvaises réponses de la question
	 * @return ArrayList contenant les mauvais réponses
	 */
    public ArrayList<String> getMauvaisesReponses() {
        return mauvaisesReponses;
    }
    
    /** 
     * Getter de la bonne réponse de la question
     * @return la réponse juste de la question (String)
     */
    public String getReponseJuste() {
    	return this.reponseJuste;
    }

	/**
     * Change la bonne réponse de la question
     * @param nouvelleBonneReponse (String) la nouvelle bonne réponse
     * @throws InvalidNameException si nouvelleBonneReponse est une chaine vide
     * @throws ReponseException si nouvelleBonneReponse est contenu dans mauvaiseReponse
     */
    public void setBonneReponse(String nouvelleBonneReponse) 
    throws InvalidNameException, ReponseException {
        if (reponseJuste.isBlank()) {
            throw new InvalidNameException("Bonne réponse vide");
        }
        if (reponseFausseContientReponseJuste(mauvaisesReponses, 
            nouvelleBonneReponse)) {
            throw new ReponseException("Impossible de mettre une bonne "
                    + "reponse si la valeur est deja contenu "
                    + "dans mauvaiseReponse ");
        }
        if (   reponseJuste.length() > LONGUEUR_MAX_REPONSE 
            || nouvelleBonneReponse.length() <= 0) {
            throw new InvalidNameException("La réponse juste contient" + 
            libelle.length()  + "Il faut qu'elle contienne entre 1 et " 
            + LONGUEUR_MAX_REPONSE);
        }
        //else
        this.reponseJuste = nouvelleBonneReponse;
    }

    /** 
     * Change la categorie de la question
     * @param nouvelleCategorie la categorie qui remplace l'ancienne
     */
    public void setCategorie(Categorie nouvelleCategorie) {
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
     * Change le feedback de la question
     * @param feedback (String) le nouveau feedback
     * @throws InvalidNameException 
     */
    public void setFeedback(String feedback) throws InvalidNameException {
        if (libelle.isBlank()) {
            throw new InvalidNameException("Le libellé est vide");
        }
        if (LONGUEUR_LIBELLE_MAX < feedback.length()) {
            throw new InvalidNameException("Le libellé contient " + feedback.length() 
            + " caractères. Il faut qu'il en est entre 1 et" + LONGUEUR_LIBELLE_MAX);
        }
		this.feedback = feedback;
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
     * Change les mauvaises réponses de la question
     * @param nouvellesMauvaisesReponses (ArrayList<String>) la nouvelle liste des mauvaises réponses
     * @throws InvalidFormatException si nouvellesMauvaisesReponses est vide
     * @throws ReponseException si nouvellesMauvaisesReponses 
     * contient une valeur en double ou 
     * si elle contient une valeur égale à la bonne réponse
     */
    public void setMauvaiseReponse(ArrayList<String> nouvellesMauvaisesReponses) 
    throws InvalidFormatException, ReponseException {
        if (nouvellesMauvaisesReponses.isEmpty()) {
            throw new InvalidFormatException("Impossible de modifier "
                                           + "avec une liste vide");
        }
        if (reponseFausseContientReponseJuste(nouvellesMauvaisesReponses, 
            reponseJuste)) {
            
            throw new ReponseException("Impossible de modifier si "
                                     + "la liste de mauvaise réponse " 
                                     + "contient une possibilité "
                                     + "égale à la bonne réponse (case ignorée)");
            
        }
        if (!reponsesFausseSansDoublon(nouvellesMauvaisesReponses)) {
            throw new ReponseException("Impossible de modifier si la "
                                     + "liste contient des valeurs en doublon " 
                                     + "(case ignorée)");
        }
        //else 
        mauvaisesReponses = nouvellesMauvaisesReponses;
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
    
    
    
}