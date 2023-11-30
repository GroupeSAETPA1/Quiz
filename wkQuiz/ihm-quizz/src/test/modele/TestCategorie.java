package test.modele;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import application.exception.InvalidNameException;
import application.modele.Categorie;

/**
 * Classe de test de la classe Categorie
 * @see {@link application.modele.Categorie}
 */
class TestCategorie {

	/**
	 * Test du constructeur avec arguments valides et invalides
	 * @see {@link application.modele.Categorie#Categorie(String)}
	 */
	@Test
	void testCategorie() {
		// On vérifie qu'il n'y a pas d'erreurs 
		// avec des constructeurs valides (longueur nom max < 31)
		assertDoesNotThrow(() -> new Categorie("test"));
		assertDoesNotThrow(() -> new Categorie("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
		
		// On vérifie que des Catégories avec des constructeurs invalides 
		// renvoie bien l'exception "InvalidNameException"
		assertThrows(InvalidNameException.class, () -> new Categorie(""));
		assertThrows(InvalidNameException.class, () -> new Categorie("   "));
		// Le constructeur dépasse la longueur nom max
		assertThrows(InvalidNameException.class, () -> new Categorie("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));		
		// caractere invalide
		assertThrows(InvalidNameException.class, () -> new Categorie("é"));	
	}

	/**
	 * Test de la méthode nomValide
	 * @see {@link application.modele.Categorie#nomValide(String)}
	 */
	@Test
	void testNomValide() {
	    // On vérifie qu'un nom valide renvoie bien true
		assertTrue(Categorie.nomValide("test"));
		// On vérifie que des noms de catégories invalides renvoie false 
		assertFalse(Categorie.nomValide(""));
		assertFalse(Categorie.nomValide("   "));
		
		// On teste la limite de caractères qui est de 30 
		assertTrue(Categorie.nomValide("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
		// On dépasse la limite de caractères (31 caractères)
		assertFalse(Categorie.nomValide("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}

	/**
	 * Test de la méthode getNom
	 * @throws InvalidNameException si le nom est invalide
	 * @see {@link application.modele.Categorie#getNom()}
	 */
	@Test
	void testGetNom() throws InvalidNameException {
		// On crée une Catégorie "test" et on vérifie 
		// que le nom est bien celui défini
		Categorie test = new Categorie("test");
		assertEquals("test", test.getNom());
		assertNotEquals("test2", test.getNom());
		
		// On change le nom de la catégorie en "test2" et 
		// on vérifie que le nom est bien modifié
		test.setNom("test2");
		assertEquals("test2", test.getNom());
		assertNotEquals("test", test.getNom());
	}
	
	/**
	 * Test de la méthode setNom
	 * @throws InvalidNameException si le nom est invalide
	 * @see {@link application.modele.Categorie#setNom(String)}
	 */
	@Test
	void testSetNom() throws InvalidNameException {
		// On crée une catégorie avec comme nom "test"
		Categorie test = new Categorie("test");
		// On vérifie que changer le nom de cette catégorie 
		// par un autre nom valide ne renvoie pas d'erreur
		// et que le nom est bien modifié
		assertDoesNotThrow(() -> test.setNom("nom"));
		assertEquals("nom", test.getNom());
		
		// On vérifie que changer le nom de la catégorie 
		// par un nom invalide renvoie bien l'exception "InvalidNameException"
		assertThrows(InvalidNameException.class, () -> test.setNom(""));
		assertThrows(InvalidNameException.class, () -> test.setNom("   "));
	}
	
	/**
	 * Test de la méthode equals
	 * @throws InvalidNameException si le nom est invalide
	 * @see {@link application.modele.Categorie#equals(Object)}
	 */
	@Test
	void testEquals() throws InvalidNameException {
		// On crée 4 catégories :
		// - La première  "test"
		// - La seconde   "test2"
		// - La troisième "test", qui a donc le même nom que la première catégorie
		// - La quatrième, qui a la même référence mémoire que la première
		Categorie test = new Categorie("test");
		Categorie test2 = new Categorie("test2");
		Categorie test3 = new Categorie("test");
		Categorie test4 = test;
		
		// On vérifie qu'une catégorie ayant 
		// le même nom ou la même référence mémoire renvoie true
		assertTrue(test.equals(test4));
		assertTrue(test.equals(test3));
		// Deux catégories avec des noms différents renvoie false
		assertFalse(test.equals(test2));
		
		// Une catégorie avec un autre objet renvoie false
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
		// On crée une catégorie "test"
		Categorie test = new Categorie("test");
		
		// On vérifie que toString renvoie exactement "test" et non "test2"
		assertEquals("test", test.toString());
		assertNotEquals("test2", test.toString());
		
		// On change le nom de la catégorie par "test2" et on vérifie que 
		// toString renvoie bien "test2" (le nom modifié) et non "test"
		test.setNom("test2");
		assertEquals("test2", test.toString());
		assertNotEquals("test", test.toString());
	}
}