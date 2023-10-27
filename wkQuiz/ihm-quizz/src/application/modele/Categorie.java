/*
 * Categorie.java 								18/10/2023
 * IUT de Rodez, pas de copyrights ni copyleft
 */

package application.modele;

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
     * @throws IllegalArgumentException
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
        return !nom.isBlank();
    }

    /**
     * setter du nom de la catégorie
     * @param nom de la categorie
     * @throws IllegalArgumentException
     */
    public void setNom(String nom) {
    	if (nomValide(nom)) {
    		this.nom = nom;
    	} else {
    		throw new IllegalArgumentException("le nom est invalide");
    	}
    }

    /**
     * getter du nom de la catégorie
     * @return nom de la categorie
     */
    public String getNom() {
        return this.nom;
    }
    
    @Override
    public String toString() {
    	return "Nom de la catégorie : " + this.nom;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
    		return true;
    	}
    	if (obj == null) {
    		return false;
    	}
    	if (this.getClass() != obj.getClass()) {
    		return false;
    	}
    	Categorie categorie = (Categorie) obj;
		return categorie.getNom().equalsIgnoreCase(this.getNom());
    }

}