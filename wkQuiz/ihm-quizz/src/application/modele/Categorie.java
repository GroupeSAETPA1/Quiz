/*
 * Categorie.java 								18/10/2023
 * IUT de Rodez, pas de copyrights ni copyleft
 */

package application.modele;

import java.io.Serializable;

import application.exception.InvalidNameException;

/**
 * Modelisation d'une catégorie pour l'application de quiz
 * @author Costes Quentin
 */
public class Categorie implements Serializable {
	
    /** ID de sérialisation */
    private static final long serialVersionUID = 1L;
    
    /** Le nom de la catégorie */
    private String nom;

    private static final  int LONGUEUR_NOM_MAX = 30 ;
    /**
     * Constructeur de la classe
     * @param nom de la catégorie (String)
     * @throws InvalidNameException si le nom est invalide
     */
    public Categorie(String nom) throws InvalidNameException {
    	if (nomValide(nom)) {
    		this.nom = nom;
    	} else {
    		throw new InvalidNameException("Le nom saisie est invalide");
    	}
    }
    
    /**
     * Vérifie la validitée du nom
     * @param nom à vérifier (String)
     * @return true si le nom est valide, false sinon
     */
    public static boolean nomValide(String nom) {		
        return !nom.isBlank() && nom.length() <= LONGUEUR_NOM_MAX  && ModelePrincipal.alphabetOk(nom);
    }
      

    /**
     * Setter du nom de la catégorie
     * @param nom de la categorie (String)
     * @throws InvalidNameException si le nom est invalide
     */
    public void setNom(String nom) throws InvalidNameException {
    	if (nomValide(nom)) {
    		this.nom = nom;
    	} else {
    		throw new InvalidNameException("Le nom saisi est incorrect");
    	}
    }

    /**
     * Getter du nom de la catégorie
     * @return nom de la categorie (String)
     */
    public String getNom() {
        return this.nom;
    }
    
    /**
     * Redéfinition de la méthode toString
     * @return le nom de la catégorie (String)
     */
    @Override
    public String toString() {
    	return this.nom;
    }
    
    /**
     * Redéfinition de la méthode equals
     * @param obj la catégorie à comparer (Object)
     * @return true si les objets sont égaux, false sinon
     */
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