/*
 * TestQuestion.java                                    18 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.modele.Categorie;
import application.modele.Question;

/** 
 * Lancement des tests pour la classe Question
 * @author Lucas
 */

class TestQuestion {
    
    private ArrayList<Question> questionValide;
    private  ArrayList<String> mauvaiseReponse1;
    private ArrayList<String> mauvaiseReponse2;
    private ArrayList<String> mauvaiseReponse3;
    private ArrayList<String> mauvaiseReponse4;
    private ArrayList<String> mauvaiseReponseDoublon;
    private Categorie[] categoriesValides = {new Categorie("Commentaire"), new Categorie("test")};
    private ArrayList<String> mauvaiseReponseVide;
    
    @BeforeEach
    void genererJeuxDeTest() {
        questionValide = new ArrayList<Question>();
        mauvaiseReponse1 = new ArrayList<String>();
        mauvaiseReponse2 = new ArrayList<String>();
        mauvaiseReponse3 = new ArrayList<String>();
        mauvaiseReponse4 = new ArrayList<String>();
        mauvaiseReponseVide =  new ArrayList<String>();
        mauvaiseReponseDoublon = new ArrayList<String>();
                
        
        mauvaiseReponse1.add("le délimiteur /*");
        mauvaiseReponse1.add("le delimiteur //");
        mauvaiseReponse1.add("le délimiteur (*");
        
        mauvaiseReponse2.add("une façon de présenter le code choisie par "
                             + "le programmeur nommé Dupont");
        mauvaiseReponse2.add("un texte sans signification particulière");
        
        
        mauvaiseReponse3.add(" // commentaire");
        
        
        mauvaiseReponse4.add("Un résumé très bref, pas plus d'une ligne, "
                             + "du rôle du programme");
        mauvaiseReponse4.add("Il n'y a pas de commentaire Javadoc "
                             + "juste avant la ligne \"public class …\"");
        mauvaiseReponse4.add("Le nom du fichier contenant le programme");
        mauvaiseReponse4.add("Un texte libre laissé "
                              + "à l'appréciation du programmeur");
        
        mauvaiseReponseDoublon.add("doublon");
        mauvaiseReponseDoublon.add("DOUBLON");
        mauvaiseReponseDoublon.add("doublon");
        mauvaiseReponseDoublon.add("DoUbLOn");
        
        
        questionValide.add(new Question("Quel est le délimiteur de début "
                                        + "d'un commentaire Javadoc ?" , 
                           categoriesValides[0] , 0 , "le délimiteur /**" ,
                           mauvaiseReponse1 ,""));
        questionValide.add(new Question("A quoi correspond l'expression : "
                                         + "@author Dupont ?" ,
                           categoriesValides[0] , 2 , 
                           "une balise reconnue par Javadoc" , mauvaiseReponse2,
                           "Les balises Javadoc commencent par le  caractère @"));
       questionValide.add(new Question("Si un commentaire est écrit "
                          + "sur plusieurs lignes, quel délimiteur de "
                          + "commentaire est-il préférable d'utiliser ?" , 
                          categoriesValides[0],1," /* commentaire */", 
                          mauvaiseReponse3 ,""));
       
       questionValide.add(new Question("Que doit décrire le texte écrit dans "
                          + "le commentaire Javadoc situé juste avant "
                          + "la ligne \"public class …\" ?" ,
                          categoriesValides[0],3 ,  
                          "Le rôle du programme, en explicitant "
                          + "de manière précise ce rôle " , mauvaiseReponse4 ,
                          ""));
    }
    
    @Test
    void testConstructeur() {
        //Le Libelle est vide
        assertThrows(IllegalArgumentException.class,
                () -> new Question("" , categoriesValides[0] , 3 ,
                                   "le délimiteur /**" , mauvaiseReponse1 ,""));
        
        //La difficulté est negative
        assertThrows(IllegalArgumentException.class,
                ()->  new Question("libelle non vide" , categoriesValides[0],-1,
                                   "le délimiteur /**" , mauvaiseReponse1 ,""));
        assertThrows(IllegalArgumentException.class,
                ()->  new Question("libelle non vide" , categoriesValides[0],
                        -999999,
                        "le délimiteur /**" , mauvaiseReponse1 ,""));
        
        //La difficulté est strictement supérieur à 3
        assertThrows(IllegalArgumentException.class,
                ()-> new Question("libelle non vide" , categoriesValides[0],4,
                                   "le délimiteur /**" , mauvaiseReponse1 ,""));
        assertThrows(IllegalArgumentException.class,
                ()->  new Question("libelle non vide" , categoriesValides[0],
                                    9999999,
                                   "le délimiteur /**" , mauvaiseReponse1 ,""));
        
        // La réponse juste est vide
        assertThrows(IllegalArgumentException.class,
                ()-> new Question("libelle non vide" , categoriesValides[0],1,
                                  "" , mauvaiseReponse3 , ""));
        
        //La liste des mauvaise réponse est vide
        assertThrows(IllegalArgumentException.class,
                ()-> new Question("Libelle non  vide" , categoriesValides[0],1,
                                  "reponse juste non vide" ,
                                  mauvaiseReponseVide , ""));
        assertDoesNotThrow(()->new Question("libelle non vide" , 
                           categoriesValides[0] , 3 ,"le délimiteur /**" , 
                           mauvaiseReponse1 ,""));
        
        //La liste des mauvaise réponse contient un doublon
        assertThrows(IllegalArgumentException.class,
                ()->new Question("libelle non vide" , categoriesValides[0],3,
                        "reponse juste non vide" , mauvaiseReponseDoublon ,""));
        
        //La reponse juste est présente dans les reponses fausses 
       assertThrows(IllegalArgumentException.class,
                ()->new Question("libelle non vide" , categoriesValides[0],3,
                       "le délimiteur /*",mauvaiseReponse1 , ""));        
       assertThrows(IllegalArgumentException.class,
                ()->new Question("libelle non vide" , categoriesValides[0],3,
                       "LE DELIMITEUR //",mauvaiseReponse1 , ""));
       assertThrows(IllegalArgumentException.class,
                ()->new Question("libelle non vide" , categoriesValides[0],3,
                       "LE DELimiTEUR //",mauvaiseReponse1 , ""));        
       assertThrows(IllegalArgumentException.class,
                ()->new Question("libelle non vide" , categoriesValides[0],3,
                       "LE delIMIteur //",mauvaiseReponse1 , ""));
        
    }

     @Test
     void testGetLibelle() {
         assertEquals("Commentaire", questionValide.get(0).getCategorie());
         assertEquals("A quoi correspond l'expression : @author Dupont ?" , 
                 questionValide.get(1).getLibelle());
         //assertEquals("Quel est le délimiteur de début d'un commentaire Javadoc ?" , 
         //        questionValide.get(0).getLibelle());
         //assertEquals("Quel est le délimiteur de début d'un commentaire Javadoc ?" , 
         //        questionValide.get(0).getLibelle());
     
     }
    
     
    @Test
    void testSetLibelle() {
        fail("Not yet implemented");
    }

    
    @Test
    void testGetCategorie() {
        assertEquals("Commentaire", questionValide.get(0).getCategorie());
        assertNotEquals("test", questionValide.get(0).getCategorie());
        		
        questionValide.get(0).setCatgorie(categoriesValides[1]);
        
        assertEquals("test" , questionValide.get(1).getCategorie());
        assertNotEquals("Commentaire", questionValide.get(0).getCategorie());
    }
    
    
    @Test
    void testSetCatgorie() {
    	// on verifie que la méthode ne leve pas d'exception
    	assertDoesNotThrow(()->questionValide.get(0).setCatgorie(categoriesValides[1]));
    	// on verifie que le changement a bien été effectuer
        assertEquals("test", questionValide.get(0).getCategorie());
    }
    
    @Test
    void testGetDifficulte() {
    	assertEquals(0, questionValide.get(0).getDifficulte());
    	assertNotEquals(1, questionValide.get(0).getDifficulte());
    	
    	assertEquals(2, questionValide.get(1).getDifficulte());
    	assertNotEquals(0, questionValide.get(1).getDifficulte());
    }
    
    
    @Test
    void testSetDifficulte() {
    	assertDoesNotThrow(()->questionValide.get(0).setDifficulte(3));
    	assertThrows(IllegalArgumentException.class, 
    				 ()-> questionValide.get(0).setDifficulte(6));
    	assertThrows(IllegalArgumentException.class, 
				 ()-> questionValide.get(0).setDifficulte(-5));
    	assertEquals(3, questionValide.get(0).getDifficulte());
    }
    
    @Test
    void testGetFeedback() {
    	assertEquals("", questionValide.get(0).getFeedback());
    	assertNotEquals("test", questionValide.get(0).getFeedback());
    	
    	assertEquals("Les balises Javadoc commencent par le  caractère @"
    			     , questionValide.get(1).getFeedback());
    	assertEquals("Les balises Javadoc commencent par le  caractère @"
			     , questionValide.get(1).getFeedback());
    }
    
    @Test
    void testSetFeedback() {
    	// on verifie que la méthode ne leve pas d'exception
    	assertDoesNotThrow(()->questionValide.get(0).setFeedback(""));
    	assertDoesNotThrow(()->questionValide.get(0).setFeedback("test"));
    	// on verifie que le changement a bien été effectuer
        assertEquals("test", questionValide.get(0).getFeedback());
    }

    
    @Test
    void testSetBonneReponse() {
        fail("Not yet implemented");
    }

    
    @Test
    void testSetMauvaiseReponse() {
        fail("Not yet implemented");
    }

    
    @Test
    void testEquals() {
        fail("Not yet implemented");
    }

    
    @Test
    void testToString() {
    	String valide = """ 
    			difficulté de la question : 0
    			Categorie de la question : Commentaire
    			Intiltulé de la question : Quel est le délimiteur de début d'un commentaire Javadoc ?
    			Mauvaise réponses :
    			- le délimiteur /*
    			- le delimiteur //
    			- le délimiteur (*
    			Bonne réponse : le délimiteur /**""";
        assertEquals(valide, questionValide.get(0).toString());
    }

}
