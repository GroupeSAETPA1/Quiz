/*
 * testChiffrage.java                                    21 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.ref.Cleaner.Cleanable;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;
import application.modele.Categorie;
import application.modele.Chiffrage;
import application.modele.ModelePrincipal;
import application.modele.Question;

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
     * @throws InvalidNameException 
     * @throws HomonymeException 
     * @throws DifficulteException 
     * @throws ReponseException 
     * @throws InvalidFormatException 
     * @throws IOException 
     */
    @Test
    void testGenererCSV() throws HomonymeException, InvalidNameException, InvalidFormatException, ReponseException, DifficulteException, IOException {
        {   // TODO c'est temporaire, c'est pour tester
            ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test1"));
            ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test2"));
            ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test3"));
            ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test4"));
            ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test5"));
            ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test6"));
            ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test7"));
            ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test8"));
            ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("test9"));
            ModelePrincipal.getInstance().getBanqueCategorie().ajouter(new Categorie("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
            
            ArrayList<String> rep = new ArrayList<>();
            rep.add("coubeh");
            rep.add("je vais me prendre a cause des tableView");
            
           
            
            String char250 = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium";
            String char350 = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate.";
            ArrayList<String> rep250 = new ArrayList<>();
            rep250.add(char250 + "1");
            rep250.add(char250 + "2");
            rep250.add(char250 + "3");
            rep250.add(char250 + "4");
            
            ModelePrincipal.getInstance().getBanqueQuestion().ajouter(new Question("quoi ?", ModelePrincipal.getInstance().getBanqueCategorie().getCategorieLibelleExact("test1"), 1, "feur", rep, ""));
            ModelePrincipal.getInstance().getBanqueQuestion().ajouter(new Question("qui ?", ModelePrincipal.getInstance().getBanqueCategorie().getCategorieLibelleExact("test2"), 2, "quette", rep, ""));
            ModelePrincipal.getInstance().getBanqueQuestion().ajouter(new Question(char250, ModelePrincipal.getInstance().getBanqueCategorie().getCategorieLibelleExact("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"), 3, char250, rep250, char250));
        }
        //System.out.println(ModelePrincipal.getInstance().getBanqueQuestion().getQuestions());
        String cle = Chiffrage.generationCle();
        Chiffrage.genererCSV(ModelePrincipal.getInstance().getBanqueQuestion().getQuestions(), cle);
        System.out.println(cle);
        //System.out.println();
        Chiffrage.decrypterFichier(cle);    
        System.out.println("finis");
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
