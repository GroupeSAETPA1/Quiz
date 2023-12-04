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
import application.modele.Categorie;
import application.modele.Chiffrage;
import static application.modele.Chiffrage.chiffrement;
import static application.modele.Chiffrage.dechiffrement;
import application.modele.ModelePrincipal;
import application.modele.Question;

/** 
 * Méthode de test pour le chiffrement
 * @author Lucas Descriaud
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
        assertEquals(messageAchiffrer, 
                dechiffrement(chiffrement(messageAchiffrer , cle), cle) );
    }

    /**
     * Test method for {@link application.modele.Chiffrage#dechiffrement(java.lang.String, java.lang.String)}.
     */
    @Test
    void testDechiffrement() {
        String cle = "x#_";
        String messageAdechiffrer = "Yip";
        
       assertEquals(messageAdechiffrer, 
               chiffrement(dechiffrement(messageAdechiffrer , cle), cle));
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
        
        ArrayList<String> mauvaiseReponse = new ArrayList<String>();
        mauvaiseReponse.add("Mauvaise Reponse 1");
        mauvaiseReponse.add("Mauvaise Reponse 2");
        mauvaiseReponse.add("Mauvaise Reponse 3");
        mauvaiseReponse.add("Mauvaise Reponse 4");
        
        modele.creerCategorie("testChiffrement"); 
        
        Question question = new Question("QuestionACrypter", modele.getCategoriesLibelleExact("testChiffrement"), 1, "ReponseJuste", mauvaiseReponse, "");        
        modele.getBanqueQuestion().ajouter(question);
        
        ArrayList<String> questionCrypter = Chiffrage.genererTableauCrypter(modele.getBanqueQuestion().getQuestions() , cle);
        
        String questionDechiffrer 
        = Chiffrage.decrypterFichier(questionCrypter.getLast() , cle);
        
        System.out.println("==========Question attendue=========");
        System.out.println(question);
        System.out.println("==========Question obtenue==========");
        System.out.println(questionDechiffrer.replace("é", "\n"));
        
        // On supprime la question et la catégorie pour nettoyer 
        // l'environement de test pour les autres tests
        modele.supprimerQuestion(question);
        modele.supprimerCategorie(modele.getCategoriesLibelleExact("testChiffrement"));
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
    
    @Test
    void testCleDepuisDiffe() {
       //TEST pour gab = 2899 (pris aleatoirement)
        Chiffrage.setGab(2899);
       assertEquals("wCDD3900 dee dee", Chiffrage.cleDepuisDiffie());
    }
}
