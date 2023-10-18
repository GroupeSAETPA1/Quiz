/*
 * BanqueCategorie.java                                  18 oct. 2023
 * IUT de Rodez pas copright, ni copyleft
 */

package modele;

import java.util.List;

/**
 * Gestion de toutes les catégories présente dans l'application
 * 
 */
public class BanqueCatgorie {
    private Categorie[] categories;

    public Categorie categorie;

    public List<Categorie> getCategories() {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Categories, permet de récupérer une Categories précise avec son indice dans l’array qui stocke toute les Categories
     */
    public Categorie getCategorie(int id) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les Categories qui on le libellé passé en paramètre(on vérifie que le libellé contient la string passée en paramètres)
     */
    public List<Categorie> getCategoriesLibelle(String libelle) {
        // TODO Auto-generated return
        return null;
    }

    public void ajouter(Categorie categorie) throws HomonymeException {
    }

    public BanqueCatgorie() {
    }

}
