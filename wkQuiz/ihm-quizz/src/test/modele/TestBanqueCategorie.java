package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.exception.HomonymeException;
import application.modele.BanqueCategorie;
import application.modele.Categorie;

/** 
 * Classe de test unitaire de la classe {@link application.modele.BanqueCategorie}
 * @author Costes Quentin
 * @author Tom Douaud
 */
class TestBanqueCategorie {
	
	private ArrayList<Categorie> ensembleCategories;
	
	private BanqueCategorie banqueCategorie;

	private ArrayList<Categorie> ensembleCategorieLibelleNom;
	
	/**
	 * Avant chaque test, initialise la banque de catégorie 
	 * et les catégories a ajouter dans la banque pour les tests suivant
	 * {@link application.modele.BanqueCategorie}
	 */
	@BeforeEach
	void genererBanque() {
		banqueCategorie = new BanqueCategorie();
		ensembleCategories = new ArrayList<Categorie>();
		ensembleCategorieLibelleNom = new ArrayList<Categorie>();
		
		ensembleCategories.add(new Categorie("premiere"));
		ensembleCategories.add(new Categorie("seconde"));
		ensembleCategories.add(new Categorie("troisieme"));
		ensembleCategories.add(new Categorie("quatrieme"));
		ensembleCategories.add(new Categorie("cinquieme"));
		
	}
	
	/**
	 * Test du constructeur de la classe 
	 * {@link application.modele.BanqueCategorie#BanqueCategorie()}
	 */
	@Test
	void testBanqueCategorie() {
		assertDoesNotThrow(()->new BanqueCategorie());
	}

	/**
	 * Test de la méthode ajouter et vérifie que les catégories 
	 * ne sont pas ajoutées si elles ont le même nom (insensible à la casse) 
	 * {@link application.modele.BanqueCategorie#ajouter(Categorie)}
	 */
	@Test
	void testAjouter() {
		assertDoesNotThrow(()->banqueCategorie.ajouter(ensembleCategories.get(0)));
        //Question déjà présente
        assertThrows(HomonymeException.class, 
                ()->banqueCategorie.ajouter(ensembleCategories.get(0)));
        assertThrows(HomonymeException.class, 
                ()->banqueCategorie.ajouter(new Categorie("PreMIerE")));
	}

	/**
	 * Test de la méthode getCategories et vérifie que 
	 * la liste initialisé est vide
	 * {@link application.modele.BanqueCategorie#getCategories()}
	 * @throws HomonymeException si jamais une catégorie est déjà présente
	 */
	@Test
	void testGetCategories() throws HomonymeException {
        ArrayList<Categorie> listeVide = new ArrayList<Categorie>();
        assertIterableEquals(listeVide, banqueCategorie.getCategories());
        for (Categorie categorie : ensembleCategories) {
        	banqueCategorie.ajouter(categorie);
        }
        assertIterableEquals(ensembleCategories, banqueCategorie.getCategories());
	}

	/**
	 * Test de la méthode getCategorie en vérifiant que les index invalides
	 * lèvent bien une exception et que les index valide renvoie bien la catégorie
	 * {@link application.modele.BanqueCategorie#getCategorie(int)}
	 * @throws HomonymeException si jamais une catégorie est déjà présente
	 */
	@Test
	void testGetCategorie() throws HomonymeException {
        assertThrows(IndexOutOfBoundsException.class, ()->banqueCategorie.getCategorie(-1));
        
        banqueCategorie.ajouter(ensembleCategories.get(0));
        assertThrows(IndexOutOfBoundsException.class, ()->banqueCategorie.getCategorie(ensembleCategories.size()));
        assertEquals(ensembleCategories.get(0), banqueCategorie.getCategorie(0));
	}

	/**
	 * Test de la méthode getCategoriesLibelle et vérifie qu'on puisse
	 * bien accéder a une catégorie par son nom (insensible à la casse)
	 * @throws HomonymeException si jamais une catégorie est déjà présente
	 */
	@Test
	void testGetCategoriesLibelle() throws HomonymeException {
		ensembleCategorieLibelleNom.add(new Categorie("premiere"));
		banqueCategorie.ajouter(ensembleCategorieLibelleNom.get(0));
		
		assertIterableEquals(ensembleCategorieLibelleNom, 
				banqueCategorie.getCategoriesLibelle("premiere"));
        assertIterableEquals(ensembleCategorieLibelleNom, 
        					 banqueCategorie.getCategoriesLibelle("preMiERe"));
        
        
        ensembleCategorieLibelleNom.add(new Categorie("premiere categorie"));
        banqueCategorie.ajouter(ensembleCategorieLibelleNom.get(1));
		assertIterableEquals(ensembleCategorieLibelleNom, 
				banqueCategorie.getCategoriesLibelle("premiere"));
        assertIterableEquals(ensembleCategorieLibelleNom, 
        					 banqueCategorie.getCategoriesLibelle("preMiERe"));
	}

	/**
	 * Test de la méthode toString et vérifie qu'une chaine de caractère est
	 * renvoyé
	 * {@link application.modele.BanqueCategorie#toString()}
	 * @throws HomonymeException 
	 */
	@Test
	void testToString() throws HomonymeException {
		banqueCategorie.ajouter(ensembleCategories.get(0));
		assertTrue(banqueCategorie.toString() instanceof String);
	}
}
