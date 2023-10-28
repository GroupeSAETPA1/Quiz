/*
 * BanqueCategorie.java                                  18 oct. 2023
 * IUT de Rodez pas copright, ni copyleft
 */

package application.modele;

import java.util.ArrayList;
import application.exception.HomonymeException;

/**
 * Gestion de toutes les catégories présente dans l'application
 * @author Quentin Costes
 * @author Tom Douaud
 */
public class BanqueCategorie {
	
    /* La liste des catégories */
    private ArrayList<Categorie> categories;
    
    /**
     * Constructeur de BanqueCategorie
     */
    public BanqueCategorie() {
    	categories = new ArrayList<Categorie>();
    }
    
    /**
     * Ajout d'une catégorie dans la banque de catégorie
     * @param categorie, la categorie a rajouter
     * @throws HomonymeException si la catégorie est déja dans la liste
     */
    public void ajouter(Categorie categorie) throws HomonymeException {
    	if (categories.contains(categorie)) {
            throw new HomonymeException("La categorie existe déjà.");
        }
    	categories.add(categorie);
    }

    /**
     * Permet de récupérer toutes les categories
     * @return Une ArrayList de toutes les categories 
     */
    public ArrayList<Categorie> getCategories() {
        return this.categories;
    }

    /**
     * Permet de récupérer une categorie précise avec son indice dans l’array qui stocke toute les Categories
     * @param id (int) L'indice de la categorie voulue
     * @return La categorie à l'indice demandé (int)
     */
    public Categorie getCategorie(int id) {
    	if (id < 0 || categories.size() <= id) {
            throw new IndexOutOfBoundsException(String.format("Erreur : %d est "
                    + "hors de la liste de taile %s", id, categories.size()));
        }
        return categories.get(id);
    }

    /**
     * Permet de récupérer les Categories qui ont le libellé passé en paramètre
     * (on vérifie que le libellé contient la string passée en paramètres)
     * @param libelle (String) le libellé recherché
     * @return une ArrayList des différentes catégories qui ont ce libellé
     */
    public ArrayList<Categorie> getCategoriesLibelle(String libelle) {
    	ArrayList<Categorie> resultat = new ArrayList<Categorie>();
        for (Categorie categorie : categories) {
            if (categorie.getNom().contains(libelle.toLowerCase())) resultat.add(categorie);
        }
        return resultat;
    }
    
    /* non javadoc - @see java.lang.Object#toString() */
    @Override
    public String toString() {
        StringBuilder resultat = new StringBuilder();
        for (Categorie categorie : categories) {
            resultat.append(categorie);
            resultat.append("\n\n"
                            + "--------------------------------------------"
                            + "\n\n");
        }
        return resultat.toString();
    }

    

    

}
