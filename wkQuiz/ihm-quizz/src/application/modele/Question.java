package application.modele;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * TODO comment class responsibility (SRP)
 * @author Lucas
 */

public class Question implements Serializable {
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
    public Categorie categorie;

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
     * @throw IllegalArgumentException 
     * <ul>
     *  <li>si le libellé est une chaîne vide</li> 
     *  <li>si la reponseJuste est une chaîne vide</li>
     *  <li>si la difficulté est différente de 1 , 2 ou 3</li>
     *  <li>si reponsesFausses est une liste vide</li>
     *  <li>si repoonseFausses contient une valeur en double 
     *      (case ignorée) <li>
     * </ul>
     */
    public Question(String libelle,Categorie categorie,int difficulte,
                    String reponseJuste,ArrayList<String> reponsesFausse,
                    String feedback) {
        if (libelle.equals("")) {
            throw new IllegalArgumentException("Le libelle est vide");
        }
        if (difficulte < 0 || difficulte > 3) {
            throw new IllegalArgumentException("Le niveau est compris "
                                               + "entre 1 et 3");
        }
        if (reponseJuste.equals("")) {
            throw new IllegalArgumentException("La réponse juste est vide");
        }
        if (reponsesFausse.isEmpty()) {
            //TODO lever illegalArgumentException
        }
        if (reponsesFausseSansDoublon(reponsesFausse)) {
            //TODO changer message erreur
            throw new IllegalArgumentException("La liste des mauvaises reponses "
                    + "ne doit pas etre vide et ne peut pas contenir de valeurs "
                    + "en double (casse ignoré)");
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
    //TODO a tester
    private static boolean reponsesFausseSansDoublon(ArrayList<String>aTester) {
        boolean sansDoublon = true ;
        String precedent;
        for (int i = 0 ; i < aTester.size() && sansDoublon ; i++) {
            precedent = aTester.get(i);
            for (int j = 0 ; j < aTester.size() && sansDoublon ; j++) {
                if (i != j) {
                    sansDoublon = precedent.equalsIgnoreCase(aTester.get(j));                    
                }
            }           
        }
        return sansDoublon;
        
    }
    public void setLibelle(final String nouveauIntitulle) {
    }

    /**
     * Change l’ancienne catégorie en nouvelleCategorie
     */
    public void setCatgorie(final String nouvelleCategorie) {
    }

    public void setDifficulte(final int nouvelleDifficulte) {
    }

    public void setBonneReponse(final String nouvelleBonneReponse) {
    }

    public void setMauvaiseReponse(ArrayList<String>nouvellesMauvaisesReponses){
    }

    /** @return valeur de mauvaisesReponses */
    public ArrayList<String> getMauvaisesReponses() {
        return mauvaisesReponses;
    }

    
}
