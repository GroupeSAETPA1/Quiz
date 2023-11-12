package test.modele;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import application.exception.InvalidNameException;
import application.modele.Categorie;

/**
 * Classe de test de la classe Categorie
 * @see {@link application.modele.Categorie}
 */
class TestCategorie {

	/**
	 * Test du constructeur avec argument valides et invalides
	 * @see {@link application.modele.Categorie#Categorie(String)}
	 */
	@Test
	void testCategorie() {
		assertDoesNotThrow(() -> new Categorie("test"));
		// 30 caractère
		assertDoesNotThrow(() -> new Categorie("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
		
		/* Tests avec constructeur invalide */
		assertThrows(InvalidNameException.class, () -> new Categorie(""));
		assertThrows(InvalidNameException.class, () -> new Categorie("   "));
		// 31 caractère
		assertThrows(InvalidNameException.class, () -> new Categorie("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
		
		
	}

	/**
	 * Test de la méthode nomValide
	 * @see {@link application.modele.Categorie#nomValide(String)}
	 */
	@Test
	void testNomValide() {
	    
		assertTrue(Categorie.nomValide("test"));
		
		assertFalse(Categorie.nomValide(""));
		assertFalse(Categorie.nomValide("   "));
		// 30 caractère
		assertTrue(Categorie.nomValide("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
		// 31 caractère
		assertFalse(Categorie.nomValide("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}

	/**
	 * Test de la méthode setNom
	 * @throws InvalidNameException si le nom est invalide
	 * @see {@link application.modele.Categorie#setNom(String)}
	 */
	@Test
	void testSetNom() throws InvalidNameException {
		Categorie test = new Categorie("test");
		
		assertDoesNotThrow(() -> test.setNom("nom"));
		assertEquals("nom", test.getNom());
		
		assertThrows(InvalidNameException.class, () -> test.setNom(""));
		assertThrows(InvalidNameException.class, () -> test.setNom("   "));
	}

	/**
	 * Test de la méthode getNom
	 * @throws InvalidNameException si le nom est invalide
	 * @see {@link application.modele.Categorie#getNom()}
	 */
	@Test
	void testGetNom() throws InvalidNameException {
		Categorie test = new Categorie("test");
		
		assertEquals("test", test.getNom());
		assertNotEquals("test2", test.getNom());
		
		test.setNom("test2");
		assertEquals("test2", test.getNom());
		assertNotEquals("test", test.getNom());
	}

	/**
	 * Test de la méthode equals
	 * @throws InvalidNameException si le nom est invalide
	 * @see {@link application.modele.Categorie#equals(Object)}
	 */
	@Test
	void testEquals() throws InvalidNameException {
		Categorie test = new Categorie("test");
		Categorie test2 = new Categorie("test2");
		Categorie test3 = new Categorie("test");
		Categorie test4 = test;
		
		assertTrue(test.equals(test4));
		assertTrue(test.equals(test3));
		assertFalse(test.equals(test2));
		
		assertFalse(test.equals("test"));
		assertFalse(test.equals(null));
	}

	/**
	 * Test de la méthode toString
	 * @throws InvalidNameException si le nom est invalide
	 * @see {@link application.modele.Categorie#toString()}
	 */
	@Test
	void testToString() throws InvalidNameException {
		Categorie test = new Categorie("test");
		
		assertEquals("test", test.toString());
		assertNotEquals("test2", test.toString());
		
		test.setNom("test2");
		assertEquals("test2", test.toString());
		assertNotEquals("test", test.toString());
	}
}