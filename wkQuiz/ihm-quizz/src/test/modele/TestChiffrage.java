/*
 * TestChiffrage.java                                    21 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import application.exception.CreerQuestionException;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;
import application.modele.Chiffrage;
import application.modele.ModelePrincipal;

/** 
 * TODO comment class responsibility (SRP)
 * @author Lenovo
 */
class TestChiffrage {

    /**
     * Test method for {@link application.modele.Chiffrage#generationCle()}.
     */
    @Test
    void testGenerationCle() {
        for (int i = 0 ;  i < 10000 ; i ++) {
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
     * @throws InvalidNameException 
     * @throws HomonymeException 
     * @throws IOException 
     * @throws CreerQuestionException 
     */
    @Test
    void testGenererCSV() throws HomonymeException, InvalidNameException, IOException, CreerQuestionException {
        ModelePrincipal modele = ModelePrincipal.getInstance();
        String cle = Chiffrage.generationCle();
        ArrayList<String> questionCrypter = Chiffrage.genererTableauCrypter(modele.getBanqueQuestion().getQuestions() , cle);
        System.out.println(cle);
        //System.out.println();
        Chiffrage.decrypterFichier(questionCrypter.get(0) , cle);
        System.out.println("finis");
    }
    
    
    /**
     * Test method for {@link application.modele.Chiffrage#exposantModulo(java.lang.Integer, java.lang.Integer, java.lang.Integer)}.
     */
    @Test
    void testexposantModulo() {
        assertEquals(1, Chiffrage.exposantModulo(6, 0, 11));
        assertEquals(6, Chiffrage.exposantModulo(6, 1, 11));
        assertEquals(3, Chiffrage.exposantModulo(6, 2, 11));
        assertEquals(7, Chiffrage.exposantModulo(6, 3, 11));
        assertEquals(1, Chiffrage.exposantModulo(2, 0, 7));
        assertEquals(2, Chiffrage.exposantModulo(2, 1, 7));
        assertEquals(4, Chiffrage.exposantModulo(2, 2, 7));
        assertEquals(1, Chiffrage.exposantModulo(3, 0, 5));
        assertEquals(3, Chiffrage.exposantModulo(3, 1, 5));
        assertEquals(4, Chiffrage.exposantModulo(3, 2, 5));      
        assertEquals(4, Chiffrage.exposantModulo(13, 63, 17));
    }
    
    @Test
    void genererPuissanceTest() {
        for (int i = 0 ; i < 10000 ; i++) {
            int nombre = Chiffrage.genererPuissance();
            assertTrue( nombre >= 5000 && nombre <= 9999);
        } 
    }
    
    /** Ce test est désactivé car il est basé sur l'aléatoire. */
    //@Test
    void testCleDepuisDiffe() {
       //Ce test fail car g^ab est aléatoire
       //TEST pour gab = 2899 (pris aleatoirement)
       assertEquals("wCDD3900 fgg fgg", Chiffrage.cleDepuisDiffie());
    }
}
