/*
 * testQuestion.java                                    18 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modele.Question;

/** 
 * Lancement des tests pour la classe Question
 * @author Lucas
 */
class testQuestion {

    @Test
    void testConstructeurFeedBack() {
        assertDoesNotThrow(() -> new Question("" , Math , ""));
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link modele.Question#Question(java.lang.String, modele.Categorie, int, java.lang.String, java.util.ArrayList)}.
     */
    @Test
    void testConstructeurSansFeedback() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link modele.Question#setLibelle(java.lang.String)}.
     */
    @Test
    void testSetLibelle() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link modele.Question#setCatgorie(java.lang.String)}.
     */
    @Test
    void testSetCatgorie() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link modele.Question#setDifficulte(int)}.
     */
    @Test
    void testSetDifficulte() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link modele.Question#setBonneReponse(java.lang.String)}.
     */
    @Test
    void testSetBonneReponse() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link modele.Question#setMauvaiseReponse(java.util.ArrayList)}.
     */
    @Test
    void testSetMauvaiseReponse() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
     */
    @Test
    void testEquals() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link java.lang.Object#toString()}.
     */
    @Test
    void testToString() {
        fail("Not yet implemented");
    }

}
