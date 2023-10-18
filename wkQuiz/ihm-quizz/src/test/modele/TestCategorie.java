package test.modele;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modele.Categorie;

class TestCategorie {

	@Test
	void testCategorie() {
		assertDoesNotThrow(() -> new Categorie("test"));
		
		assertThrows(IllegalArgumentException.class, () -> new Categorie(""));
		assertThrows(IllegalArgumentException.class, () -> new Categorie("   "));
	}

	@Test
	void testNomValide() {
		assertTrue(Categorie.nomValide("test"));
		
		assertFalse(Categorie.nomValide(""));
		assertFalse(Categorie.nomValide("   "));
	}

	@Test
	void testSetNom() {
		Categorie test = new Categorie("test");
		
		assertDoesNotThrow(() -> test.setNom("nom"));
		assertEquals("nom", test.getNom());
		
		assertThrows(IllegalArgumentException.class, () -> test.setNom(""));
		assertThrows(IllegalArgumentException.class, () -> test.setNom("   "));
	}

	@Test
	void testGetNom() {
		Categorie test = new Categorie("test");
		
		assertEquals("test", test.getNom());
		assertNotEquals("test2", test.getNom());
		
		test.setNom("test2");
		assertEquals("test2", test.getNom());
		assertNotEquals("test", test.getNom());
	}

	@Test
	void testEquals() {
		Categorie test = new Categorie("test");
		Categorie test2 = new Categorie("test2");
		Categorie test3 = new Categorie("test");
		
		assertTrue(test.equals(test3));
		assertFalse(test.equals(test2));
		
		assertFalse(test.equals("test"));
		assertFalse(test.equals(null));
	}

	@Test
	void testToString() {
		Categorie test = new Categorie("test");
		
		assertEquals("Nom de la catégorie : test", test.toString());
		assertNotEquals("Nom de la catégorie : test2", test.toString());
		
		test.setNom("test2");
		assertEquals("Nom de la catégorie : test2", test.toString());
		assertNotEquals("Nom de la catégorie : test", test.toString());
	}

}
