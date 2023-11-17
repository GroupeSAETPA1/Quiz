package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.exception.HomonymeException;
import application.exception.InvalidNameException;
import application.modele.BanqueCategorie;
import application.modele.Categorie;

/**
 * Classe de test unitaire de la classe
 * {@link application.modele.BanqueCategorie}
 * 
 * @author Costes Quentin
 * @author Tom Douaud
 */
class TestBanqueCategorie {

	private ArrayList<Categorie> ensembleCategories;

	private BanqueCategorie banqueCategorie;

	private ArrayList<Categorie> ensembleCategorieLibelleNom;

	/**
	 * Avant chaque test, initialise la banque de catégorie et les catégories a
	 * ajouter dans la banque pour les tests suivant
	 * {@link application.modele.BanqueCategorie}
	 * 
	 * @throws InvalidNameException
	 */
	@BeforeEach
	void genererBanque() throws InvalidNameException {
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
		assertDoesNotThrow(() -> new BanqueCategorie());
	}

	/**
	 * Test de la méthode ajouter et vérifie que les catégories ne sont pas ajoutées
	 * si elles ont le même nom (insensible à la casse)
	 * {@link application.modele.BanqueCategorie#ajouter(Categorie)}
	 */
	@Test
	void testAjouter() {
		// On ajoute une première catégorie dans la banque de catégorie
		assertDoesNotThrow(() -> banqueCategorie.ajouter(ensembleCategories.get(0)));
		// Cas quand la catégorie est déjà présente 
		// (la catégorie à été ajoutée juste avant)
		// On renvoie l'exception "HomonymeException"
		assertThrows(HomonymeException.class, () -> banqueCategorie.ajouter(ensembleCategories.get(0)));
		assertThrows(HomonymeException.class, () -> banqueCategorie.ajouter(new Categorie("PreMIerE")));
	}

	/**
	 * Test de la méthode getCategories
	 * {@link application.modele.BanqueCategorie#getCategories()}
	 * 
	 * @throws HomonymeException  
	 * @throws InvalidNameException
	 */
	@Test
	void testGetCategories() throws HomonymeException, InvalidNameException {
		// On ajoute à la banque de catégorie qui a déjà 
		// la catégorie "General" un ensemble de catégories
		for (Categorie categorie : ensembleCategories) {
			banqueCategorie.ajouter(categorie);
		}
		// On ajoute à notre ensemble de catégorie la catégorie "General" 
		// et on vérifie que toutes les catégories de l'ensemble de catégorie
		// est égal aux catégories de la banque de catégorie 
		ensembleCategories.add(0, new Categorie("General"));
		assertIterableEquals(ensembleCategories, banqueCategorie.getCategories());
	}

	/**
	 * Test de la méthode getCategorie en vérifiant que les index invalides lèvent
	 * bien une exception et que les index valide renvoie bien la catégorie
	 * {@link application.modele.BanqueCategorie#getCategorie(int)}
	 * 
	 * @throws HomonymeException 
	 */
	@Test
	void testGetCategorie() throws HomonymeException {
		// Quand on recherche une categorie a un indice invalide
		// on reçoit bien l'exception "IndexOutOfBoundsException"
		assertThrows(IndexOutOfBoundsException.class, () -> banqueCategorie.getCategorie(-1));
		banqueCategorie.ajouter(ensembleCategories.get(0));
		assertThrows(IndexOutOfBoundsException.class, () -> banqueCategorie.getCategorie(ensembleCategories.size()));
		
		// Quand on recherche une catégorie à un bon index 
		// on à la bonne catégorie recherchée
		assertEquals(ensembleCategories.get(0), banqueCategorie.getCategorie(1));
	}

	/**
	 * Test de la méthode getCategoriesLibelle et vérifie qu'on puisse bien accéder
	 * a une catégorie par son nom (insensible à la casse)
	 * 
	 * @throws HomonymeException    
	 * @throws InvalidNameException 
	 */
	@Test
	void testGetCategoriesLibelle() throws HomonymeException, InvalidNameException {
		ensembleCategorieLibelleNom.add(new Categorie("premiere"));
		banqueCategorie.ajouter(ensembleCategorieLibelleNom.get(0));

		assertIterableEquals(ensembleCategorieLibelleNom, banqueCategorie.getCategoriesLibelle("premiere"));
		assertIterableEquals(ensembleCategorieLibelleNom, banqueCategorie.getCategoriesLibelle("preMiERe"));

		ensembleCategorieLibelleNom.add(new Categorie("premiere categorie"));
		banqueCategorie.ajouter(ensembleCategorieLibelleNom.get(1));
		assertIterableEquals(ensembleCategorieLibelleNom, banqueCategorie.getCategoriesLibelle("premiere"));
		assertIterableEquals(ensembleCategorieLibelleNom, banqueCategorie.getCategoriesLibelle("preMiERe"));
	}

	/**
	 * Test de la méthode getExactCategoriesLibelle et vérifie qu'on puisse bien
	 * accéder a une catégorie par son nom exact (sensible à la casse)
	 * 
	 * @throws HomonymeException    
	 * @throws InvalidNameException 
	 */
	@Test
	void testGetExactCategoriesLibelle() throws HomonymeException, InvalidNameException {
		// On ajoute a la banque de categorie une categorie "premiere"
		ensembleCategorieLibelleNom.add(new Categorie("premiere"));
		banqueCategorie.ajouter(ensembleCategorieLibelleNom.get(0));
		
		// On vérifie que la catégorie "premiere" est bien dans 
		// la banque des categories et non "preMiERe"
		assertTrue(ensembleCategorieLibelleNom.contains(banqueCategorie.getCategorieLibelleExact("premiere")));
		assertFalse(ensembleCategorieLibelleNom.contains(banqueCategorie.getCategorieLibelleExact("preMiERe")));

		// On ajoute une autre catégorie
		ensembleCategorieLibelleNom.add(new Categorie("premiere categorie"));
		banqueCategorie.ajouter(ensembleCategorieLibelleNom.get(1));

		ArrayList<Categorie> listeUneCategorie = new ArrayList<Categorie>();
		listeUneCategorie.add(new Categorie("premiere categorie"));

		assertEquals(banqueCategorie.getCategorieLibelleExact("categorie"), null);
		assertEquals(banqueCategorie.getCategorieLibelleExact("premiere categorie"), listeUneCategorie.get(0));
	}

	/**
	 * Test de la méthode toString et vérifie qu'une chaine de caractère est renvoyé
	 * {@link application.modele.BanqueCategorie#toString()}
	 * 
	 * @throws HomonymeException
	 */
	@Test
	void testToString() throws HomonymeException {
		banqueCategorie.ajouter(ensembleCategories.get(0));
		assertTrue(banqueCategorie.toString() instanceof String);
	}
}
