package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.exception.HomonymeException;
import application.modele.BanqueCategorie;
import application.modele.BanqueQuestion;
import application.modele.Categorie;
import application.modele.Question;

/** 
 * Classe de test unitaire de la classe {@link application.modele.BanqueCategorie}
 * @author Costes quentin
 */
class TestBanqueCategorie {
	
	private ArrayList<Categorie> ensembleCategories;
	
	private BanqueCategorie banqueCategorie;

	private ArrayList<Categorie> ensembleCategorieLibelleNom;
	
	@BeforeEach
	void genererBannque() {
		banqueCategorie = new BanqueCategorie();
		ensembleCategories = new ArrayList<Categorie>();
		ensembleCategorieLibelleNom = new ArrayList<Categorie>();
		
		ensembleCategories.add(new Categorie("premiere"));
		ensembleCategories.add(new Categorie("seconde"));
		ensembleCategories.add(new Categorie("troisieme"));
		ensembleCategories.add(new Categorie("quatrieme"));
		ensembleCategories.add(new Categorie("cinquieme"));
		
	}
	
	@Test
	void testBanqueCategorie() {
		assertDoesNotThrow(()->new BanqueCategorie());
	}

	@Test
	void testAjouter() {
		assertDoesNotThrow(()->banqueCategorie.ajouter(ensembleCategories.get(0)));
        //Question déjà présente
        assertThrows(HomonymeException.class, 
                ()->banqueCategorie.ajouter(ensembleCategories.get(0)));
        assertThrows(HomonymeException.class, 
                ()->banqueCategorie.ajouter(new Categorie("PreMIerE")));
	}

	@Test
	void testGetCategories() throws HomonymeException {
        ArrayList<Categorie> listeVide = new ArrayList<Categorie>();
        assertIterableEquals(listeVide, banqueCategorie.getCategories());
        for (Categorie categorie : ensembleCategories) {
        	banqueCategorie.ajouter(categorie);
        }
        assertIterableEquals(ensembleCategories, banqueCategorie.getCategories());
	}

	@Test
	void testGetCategorie() throws HomonymeException {
        assertThrows(IndexOutOfBoundsException.class, ()->banqueCategorie.getCategorie(-1));
        
        banqueCategorie.ajouter(ensembleCategories.get(0));
        assertThrows(IndexOutOfBoundsException.class, ()->banqueCategorie.getCategorie(ensembleCategories.size()));
        assertEquals(ensembleCategories.get(0), banqueCategorie.getCategorie(0));
	}

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
}
