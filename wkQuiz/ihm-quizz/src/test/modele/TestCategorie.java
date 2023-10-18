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
	}

	@Test
	void testNomValide() {
		fail("Not yet implemented");
	}

	@Test
	void testSetNom() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNom() {
		fail("Not yet implemented");
	}

	@Test
	void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

}
