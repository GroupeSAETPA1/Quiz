package test.modele;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	 * @see {@link application.modele.BanqueCategorie}
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
	 * @see {@link application.modele.BanqueCategorie#BanqueCategorie()}
	 */
	@Test
	void testBanqueCategorie() {
		assertDoesNotThrow(() -> new BanqueCategorie());
	}

	/**
	 * Test de la méthode ajouter et vérifie que les catégories ne sont pas ajoutées
	 * si elles ont le même nom (insensible à la casse)
	 * @see {@link application.modele.BanqueCategorie#ajouter(Categorie)}
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
	 * @see {@link application.modele.BanqueCategorie#getCategories()}
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
	 * @see {@link application.modele.BanqueCategorie#getCategorie(int)}
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
	 * à une catégorie par son nom (insensible à la casse et avec un nom partiel)
	 * @see {@link application.modele.BanqueCategorie#getCategoriesLibelle(String)}
	 * 
	 * @throws HomonymeException    
	 * @throws InvalidNameException 
	 */
	@Test
	void testGetCategoriesLibelle() throws HomonymeException, InvalidNameException {
		// On ajoute une catégorie "premiere" dans la banque de catégorie
		ensembleCategorieLibelleNom.add(new Categorie("premiere"));
		banqueCategorie.ajouter(ensembleCategorieLibelleNom.get(0));

		// On vérifie qu'on puisse récupérer la catégorie avec son nom exact, 
		// insensible à la casse
		assertIterableEquals(ensembleCategorieLibelleNom, banqueCategorie.getCategoriesLibelle("premiere"));
		assertIterableEquals(ensembleCategorieLibelleNom, banqueCategorie.getCategoriesLibelle("preMiERe"));

		// On ajoute une autre catégorie à la banque de catégorie 
		// nommée "premiere categorie"
		ensembleCategorieLibelleNom.add(new Categorie("premiere categorie"));
		banqueCategorie.ajouter(ensembleCategorieLibelleNom.get(1));
		
		// On vérifie qu'on puisse récupérer la catégorie avec son nom partiel, 
		// insensible à la casse
		assertEquals(ensembleCategorieLibelleNom.get(1), banqueCategorie.getCategoriesLibelle("premiere").get(1));
		assertEquals(ensembleCategorieLibelleNom.get(1), banqueCategorie.getCategoriesLibelle("preMiERe").get(1));
	}

	/**
	 * Test de la méthode getExactCategoriesLibelle et vérifie qu'on puisse bien
	 * accéder à une catégorie par son nom exact (sensible à la casse)
	 * @see {@link application.modele.BanqueCategorie#getCategorieLibelleExact(String)}
	 * 
	 * @throws HomonymeException    
	 * @throws InvalidNameException 
	 */
	@Test
	void testGetCategorieLibelleExact() throws HomonymeException, InvalidNameException {
		// On ajoute à la banque de catégorie une catégorie "premiere"
		ensembleCategorieLibelleNom.add(new Categorie("premiere"));
		banqueCategorie.ajouter(ensembleCategorieLibelleNom.get(0));
		
		// On vérifie que la catégorie "premiere" est bien dans 
		// la banque des catégories et non "preMiERe"
		assertTrue(ensembleCategorieLibelleNom.contains(banqueCategorie.getCategorieLibelleExact("premiere")));
		assertFalse(ensembleCategorieLibelleNom.contains(banqueCategorie.getCategorieLibelleExact("preMiERe")));

		// On ajoute une autre catégorie "premiere categorie" dans la banque de catégorie
		ensembleCategorieLibelleNom.add(new Categorie("premiere categorie"));
		banqueCategorie.ajouter(ensembleCategorieLibelleNom.get(1));
		ArrayList<Categorie> listeUneCategorie = new ArrayList<Categorie>();
		listeUneCategorie.add(new Categorie("premiere categorie"));

		// On vérifie que la méthode nous renvoie null car il n'y a pas 
		// de catégorie qui s'appele exactement "categorie"
		assertEquals(banqueCategorie.getCategorieLibelleExact("categorie"), null);
		// On vérifie que la méthode nous renvoie la catégorie "premiere categorie" 
		// car il y a une catégorie éponyme dans la banque de categorie
		assertEquals(banqueCategorie.getCategorieLibelleExact("premiere categorie"), listeUneCategorie.get(0));
	}
	
	/**
	 * Test de la méthode testGetCategoriesNom qui vérifie qu'une 
	 * ArrayList de String contenant les noms des catégories 
	 * de la banque de catégorie
	 * @see {@link application.modele.BanqueCategorie#getCategoriesNom()}
	 * 
	 * @throws HomonymeException
	 * @throws InvalidNameException
	 */
	@Test
	void testGetCategoriesNom() throws HomonymeException, InvalidNameException {
		// On crée une liste de String représentant ce que doit renvoyer 
		// la méthode getCategoriesNom sur une banque de catégorie vide 
		// (avec que la catégorie "General")
		ArrayList<String> nomCategoriesVide  = new ArrayList<String>();
		nomCategoriesVide.add("General");
		
		// On crée une deuxième liste de String représentant ce que doit renvoyer 
		// la méthode getCategoriesNom sur une banque de catégorie 
		// avec 2 catégories en plus 
		ArrayList<String> nomCategories  = new ArrayList<String>();
		nomCategories.add("General");
		nomCategories.add("premiere");
		nomCategories.add("seconde");
		
		// On vérifie que la méthode renvoie bien l'ArrayList de String attendue
		// (cela doit être égal à nomCategoriesVide)
		assertIterableEquals(nomCategoriesVide, banqueCategorie.getCategoriesNom());
		
		// On ajoute deux catégories à la banque de catégorie et on vérifie
		// que la méthode renvoie bien l'ArrayList attendue (nomCategories)
		banqueCategorie.ajouter(new Categorie("premiere"));
		banqueCategorie.ajouter(new Categorie("seconde"));
		assertIterableEquals(nomCategories, banqueCategorie.getCategoriesNom());
	}
	
	/**
	 * Test de la méthode getIndice et vérifie que l'indice de la catégorie 
	 * dans la banque de catégorie soit bien l'index attendu
	 * @see {@link application.modele.BanqueCategorie#getIndice(String)}
	 * 
	 * @throws HomonymeException
	 * @throws InvalidNameException
	 */
	@Test
	void testGetIndice() throws HomonymeException, InvalidNameException {
		// On ajoute des catégories à la banque de catégories 
		banqueCategorie.ajouter(new Categorie("premiere"));
		banqueCategorie.ajouter(new Categorie("seconde"));
		banqueCategorie.ajouter(new Categorie("troisieme"));
		banqueCategorie.ajouter(new Categorie("quatrieme"));
		
		// On vérifie que la catégorie "troiseme" est bien 
		// à la troisième position dans la banque de catégories 
		// (la catégorie par défaut "General" est a la position 0)
		assertEquals(3, banqueCategorie.getIndice("troisieme"));
		
		// On vérifie que si on recherche une catégorie qui n'existe pas dans 
		// la banque de catégorie, on revoie l'exception "IllegalArgumentException"
		assertThrows(IllegalArgumentException.class, () ->  banqueCategorie.getIndice("catégorie inexistante"));
	}

	/**
	 * Test de la méthode toString et vérifie qu'une chaîne de caractère est renvoyé
	 * @see {@link application.modele.BanqueCategorie#toString()}
	 * 
	 * @throws HomonymeException
	 */
	@Test
	void testToString() throws HomonymeException {
		banqueCategorie.ajouter(ensembleCategories.get(0));
		assertTrue(banqueCategorie.toString() instanceof String);
	}
}
