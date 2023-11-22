/*
 * BanqueCategorie.java                                  18 oct. 2023
 * IUT de Rodez pas copright, ni copyleft
 */

package application.modele;

import java.util.ArrayList;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;

/**
 * Gestion de toutes les catégories présente dans l'application
 * @author Quentin Costes
 * @author Tom Douaud
 */
public class BanqueCategorie {

	/** La catégorie Général */
    protected Categorie categorieGeneral;
    
    /** La liste des catégories */
    private ArrayList<Categorie> categories;
    
    /**
     * Constructeur de BanqueCategorie
     * @throws InvalidNameException 
     */
    public BanqueCategorie() {
        try {
            categorieGeneral = new Categorie("General");
        } catch (InvalidNameException e) {
            throw new InternalError("La création de la categorie Général à "
                    + "généré une erreur");
        }
        categories = new ArrayList<Categorie>();
        categories.add(categorieGeneral);
    }
    
    /**
     * Ajout d'une catégorie dans la banque de catégorie
     * @param categorie, la catégorie a rajouter
     * @throws HomonymeException si la catégorie est déjà dans la liste
     */
    public void ajouter(Categorie categorie) throws HomonymeException {
    	if (categories.contains(categorie)) {
            throw new HomonymeException("La categorie existe déjà.");
        }
    	
    	categories.add(categorie);
    }

    /**
     * Permet de récupérer une catégorie précise avec son indice dans l’array qui stocke toute les Categories
     * @param id (int) L'indice de la catégorie voulue
     * @return La catégorie à l'indice demandé (int)
     * @throws IndexOutOfBoundsException si l'index de la catégorie n'est pas bon
     */
    public Categorie getCategorie(int id) {
    	if (id < 0 || categories.size() <= id) {
            throw new IndexOutOfBoundsException(String.format("Erreur : %d est "
                    + "hors de la liste de taile %s", id, categories.size()));
        }
    	
        return categories.get(id);
    }

    /**
     * Renvoie la catégorie qui à exactement le même libellé que passé en paramètre
     * si il n'y en a pas cela renvoie null
     * @param libelle (String) le libellé recherché sensible à la casse
     * @return catégorie avec le libellé voulu si elle existe, null sinon
     */
    public Categorie getCategorieLibelleExact(String libelle) {
        
        for (Categorie categorie : categories) {
            if (categorie.getNom().equals(libelle)) {
                return categorie;
            } 
        }
		
        return null;
    }

    /**
     * Permet de récupérer toutes les categories
     * @return Une ArrayList de toutes les categories 
     */
    public ArrayList<Categorie> getCategories() {
        return this.categories;
    }

    /**
     * Récupère les Categories qui contiennent le libellé passé en paramètre
     * (on vérifie que le libellé contient la string passée en paramètres)
     * @param libelle (String) le libellé recherché
     * @return une ArrayList des différentes catégories qui contiennent ce libellé
     */
    public ArrayList<Categorie> getCategoriesLibelle(String libelle) {
    	ArrayList<Categorie> resultat = new ArrayList<Categorie>();
    	
        for (Categorie categorie : categories) {
            if (categorie.getNom().toLowerCase().contains(libelle.toLowerCase())) {
                resultat.add(categorie);
            } 
        }
        
        return resultat;
    }
    
    /** @return le nom de tous les catégorie de la banque */
    public ArrayList<String> getCategoriesNom() {
        ArrayList<String> resultat = new ArrayList<String>();
        
        for (Categorie categorie : categories) {
            resultat.add(categorie.getNom());
        }
        
        return resultat;
    }
    
    
    /**
     * Retourne l'indice de la catégorie dans la liste des catégories
     * @param string catégorie recherché
     * @return L'indice de la catégorie
     */
    public int getIndice(String string) {
        int reponse = 0;
        boolean categoriePasTrouve = true;
        
        for (int i = 0; i < categories.size() && categoriePasTrouve; i++) {
            if (string.equals(categories.get(i).getNom())) {
                reponse = i;
                categoriePasTrouve = false;
            }
        }
        
        if (categoriePasTrouve) {
        	throw new IllegalArgumentException("La catégorie recherchée n'est pas dans la banque de catégorie !");
        }
        
        return reponse;
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
