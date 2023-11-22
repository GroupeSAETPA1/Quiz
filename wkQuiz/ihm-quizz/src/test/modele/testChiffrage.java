/*
 * testChiffrage.java                                    21 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.ref.Cleaner.Cleanable;

import org.junit.jupiter.api.Test;

import application.modele.Chiffrage;

/** 
 * TODO comment class responsibility (SRP)
 * @author Lenovo
 */
class testChiffrage {

    /**
     * Test method for {@link application.modele.Chiffrage#generationCle()}.
     */
    @Test
    void testGenerationCle() {
        for (int i = 0 ;  i < 1000 ; i ++) {
            String cle  = Chiffrage.generationCle();
            for (int j = 0 ; j < cle.length()  ; j++) {
                assertTrue(
                        Chiffrage.ALPAHABET_TO_INT.containsKey(cle.charAt(j))
                        && 
                        cle.length() >= 40 && cle.length() <= 60);
               
            }
        }
    }

    /** 
     * Test method for {@link application.modele.Chiffrage#chiffrement(java.lang.String, java.lang.String)}.
     */
    @Test
    void testChiffrement() {
        String cle = "x#_";
        String messageAchiffrer = "BUT";
        String chiffrerMain = "Yip";
        //27 46 45
        //23 55 63
        // Y i p
        assertEquals(chiffrerMain, Chiffrage.chiffrement(messageAchiffrer , cle));
    }

    /**
     * Test method for {@link application.modele.Chiffrage#dechiffrement(java.lang.String, java.lang.String)}.
     */
    @Test
    void testDechiffrement() {
        String cle = "x#_";
        String messageAdechiffrer = "Yip";
        String dechiffrerMain = "BUT";
        
        assertEquals(dechiffrerMain, Chiffrage.dechiffrement(messageAdechiffrer , cle));
    }

    
    
    /**
     * Test method for {@link application.modele.Chiffrage#genererCSV(java.lang.String)}.
     */
    @Test
    void testGenererCSV() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Chiffrage#lireCSV(java.lang.String)}.
     */
    @Test
    void testLireCSV() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Chiffrage#analiserResultat(java.util.ArrayList)}.
     */
    @Test
    void testAnaliserResultat() {
        fail("Not yet implemented");
    }
    
    
    /**
     * Test method for {@link application.modele.Chiffrage#exposantModulo(java.lang.Integer, java.lang.Integer, java.lang.Integer)}.
     */
    @Test
    void testexposantModulo() {
    	// Test case 1
        assertEquals(1, Chiffrage.exposantModulo(6, 0, 11));

        // Test case 2
        assertEquals(6, Chiffrage.exposantModulo(6, 1, 11));

        // Test case 3
        assertEquals(3, Chiffrage.exposantModulo(6, 2, 11));

        // Test case 4
        assertEquals(9, Chiffrage.exposantModulo(6, 3, 11));

        // Test case 5
        assertEquals(1, Chiffrage.exposantModulo(2, 0, 7));

        // Test case 6
        assertEquals(2, Chiffrage.exposantModulo(2, 1, 7));

        // Test case 7
        assertEquals(4, Chiffrage.exposantModulo(2, 2, 7));

        // Test case 8
        assertEquals(1, Chiffrage.exposantModulo(3, 0, 5));

        // Test case 9
        assertEquals(3, Chiffrage.exposantModulo(3, 1, 5));

        // Test case 10
        assertEquals(4, Chiffrage.exposantModulo(3, 2, 5));
    }

}
