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
     * </ul>
     */
    public Question(String libelle,Categorie categorie,int difficulte,
                    String reponseJuste,ArrayList<String> reponsesFausse,
                    String feedback) {
        
    }

    /**
     * Constructeur de la classe utilisant les paramètres
     * <ul>
     *  <li>le libellé</li>
     *  <li>la catégorie</li>
     *  <li>la difficulté</li>
     *  <li>la bonne proposition de réponse</li>
     *  <li>la liste des mauvaises propositions</li>
     * </ul>
     * @throw IllegalArgumentException 
     * <ul>
     *  <li>si le libellé est une chaîne vide</li> 
     *  <li>si la reponseJuste est une chaîne vide</li>
     *  <li>si la difficulté est différente de 1 , 2 ou 3</li>
     *  <li>si reponsesFausses est une liste vide</li>
     * </ul>
     */
    public Question(String libelle,Categorie categorie,int difficulte ,
                    String reponseJuste, ArrayList<String> reponsesFausse) {
        this.libelle = libelle;
        this.categorie = categorie;
        this.difficulte = difficulte;
        this.reponseJuste = reponseJuste;
        this.mauvaisesReponses = reponsesFausse;
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

}
