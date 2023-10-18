/*
 * Categorie.java 								18/10/2023
 * IUT de Rodez, pas de copyrights ni copyleft
 */

package modele;

import java.io.Serializable;

/**
 * Modelisation d'une catégorie pour l'application de quizz
 * @author Costes Quentin
 */
public class Categorie implements Serializable{
	
    /** Le nom de la catégorie */
    private String nom;

    /**
     * Constructeur de la classe
     * @param nom de la catégorie
     * @throws HomonymeException
     */
    public Categorie(String nom) {
    	if (nomValide(nom)) {
    		this.nom = nom;
    	} else {
    		throw new IllegalArgumentException("le nom est invalide");
    	}
    }
    
    /**
     * verifie la validitée du nom
     * @param nom a verifier
     * @return true si le nom est valide, false sinon
     */
    public static boolean nomValide(String nom) {
        return nom != "";
    }

    /**
     * setter du nom de la catégorie
     * @param nom de la categorie
     */
    public void setNom(String nom) {
    }

    /**
     * getter du nom de la catégorie
     * @return
     */
    public String getNom() {
        // TODO Auto-generated return
        return null;
    }

}
