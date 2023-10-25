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
    private ArrayList<String> mauvaiseReponseJusteChaineVide;
    
    @BeforeEach
    void genererJeuxDeTest() {
        questionValide = new ArrayList<Question>();
        mauvaiseReponse1 = new ArrayList<String>();
        mauvaiseReponse2 = new ArrayList<String>();
        mauvaiseReponse3 = new ArrayList<String>();
        mauvaiseReponse4 = new ArrayList<String>();
        mauvaiseReponseVide =  new ArrayList<String>();
        mauvaiseReponseDoublon = new ArrayList<String>();
        mauvaiseReponseJusteChaineVide = new ArrayList<String>();
                
        mauvaiseReponseJusteChaineVide.add("");
        mauvaiseReponseJusteChaineVide.add("");
        mauvaiseReponseJusteChaineVide.add("");
        mauvaiseReponseJusteChaineVide.add("");
        
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
                          categoriesValides[0],1,"/* commentaire */", 
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
       
       //La liste ne contient que des chaine vide
       assertThrows(IllegalArgumentException.class,
               ()->new Question("Libelle non vide" , categoriesValides[0],2,
                       "non vide",mauvaiseReponseJusteChaineVide , ""));
        
    }

     @Test
     void testGetLibelle() {
         
         // Recuperer un libelle existant
         assertEquals("Quel est le délimiteur de début d'un commentaire Javadoc ?" , 
                 questionValide.get(0).getLibelle());
         assertEquals("A quoi correspond l'expression : @author Dupont ?" , 
                 questionValide.get(1).getLibelle());
         assertEquals("Si un commentaire est écrit sur plusieurs lignes, "
                 + "quel délimiteur de commentaire est-il préférable d'utiliser ?" , 
                 questionValide.get(2).getLibelle());
         assertEquals("Que doit décrire le texte écrit dans le commentaire "
                 + "Javadoc situé juste avant la ligne \"public class …\" ?" , 
                 questionValide.get(3).getLibelle());
         
         //Recupere un libelle apres modification
         questionValide.get(0).setLibelle("nouveau libelle");
         assertEquals("nouveau libelle" , questionValide.get(0).getLibelle());
         
         
         
     
     }
    
     
    @Test
    void testSetLibelle() {
        
        assertThrows(IllegalArgumentException.class ,
                ()-> questionValide.get(0).setLibelle(""));
        
        assertDoesNotThrow(() -> questionValide.get(0).setLibelle("test1"));
        assertEquals("test1", questionValide.get(0).getLibelle());
        
    }

    
    @Test
    void testGetCategorie() {
        assertEquals("Commentaire", questionValide.get(0).getCategorie());
        assertNotEquals("test", questionValide.get(0).getCategorie());
        		
        questionValide.get(0).setCatgorie(categoriesValides[1]);
        
        assertEquals("test" , questionValide.get(0).getCategorie());
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
        assertThrows(IllegalArgumentException.class,
                ()-> questionValide.get(0).setBonneReponse(""));
        assertThrows(IllegalArgumentException.class,
                ()-> questionValide.get(0).setBonneReponse("le délimiteur /*"));
        assertDoesNotThrow(()-> questionValide.get(0).setBonneReponse("test1"));
        assertEquals("test1" , questionValide.get(0).getReponseJuste());
    }

    @Test
    void testGetBonneReponse() {
        //Reponse juste deja existante
        assertEquals("/* commentaire */",questionValide.get(2).getReponseJuste());
        //Reponse juste apres un changement
        questionValide.get(2).setBonneReponse("test45");
        assertEquals("test45" , questionValide.get(2).getReponseJuste());
    }

    
    @Test
    void testSetMauvaiseReponse() {
        ArrayList<String> mauvaiseReponseContientJuste = new ArrayList<String>();
        mauvaiseReponseContientJuste.add("reponseOk");
        mauvaiseReponseContientJuste.add("reponseOK2");
        mauvaiseReponseContientJuste.add("le délimiteur /**");
        ArrayList<String> test1 = new ArrayList<String>();
        test1.add("test1");
        test1.add("test2");
        assertThrows(IllegalArgumentException.class,
                ()-> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseVide));
        assertThrows(IllegalArgumentException.class,
                ()-> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseDoublon));
        assertThrows(IllegalArgumentException.class,
                ()-> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseContientJuste));
        assertThrows(IllegalArgumentException.class,
                ()-> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseJusteChaineVide));
        assertDoesNotThrow(()-> questionValide.get(0).setMauvaiseReponse(test1));
        assertEquals(test1, questionValide.get(0).getMauvaisesReponses());
    }

    
    @Test
    void testEquals() {
        Question question1Egale = new Question("Quel est le délimiteur de début "
                                      + "d'un commentaire Javadoc ?" , 
                                      categoriesValides[0] , 0 , 
                                      "le délimiteur /**" ,mauvaiseReponse1 ,"");
        Question question2Egale = new Question("A quoi correspond l'expression : "
                                                + "@author Dupont ?" ,categoriesValides[0],
                                                2 , "une balise reconnue par Javadoc" , 
                                                mauvaiseReponse2 , "");
        Question question3Egale = new Question("A quoi correspond l'expression : "
                + "@author Dupont ?" ,categoriesValides[0],
                3 , "une balise reconnue par Javadoc" , 
                mauvaiseReponse2 , "");
        // 2 question complement differente
        assertNotEquals(questionValide.get(0), questionValide.get(1));
        
        // 2 question identique
        assertEquals(questionValide.get(0), question1Egale);
        
        // 2 question dont 1 feedback l'autre non
        assertEquals(question2Egale, questionValide.get(1));
        
        // 2 question exactemet pareil mais difficulté différente
        assertEquals(question3Egale, questionValide.get(1));
        
    }

    @Test
    void testGetMauvaiseReponse() {
        ArrayList<String> test1 = new ArrayList<String>();
        ArrayList<String> test2 = new ArrayList<String>();
        test1.add("le délimiteur /*");
        test1.add("le delimiteur //");
        test1.add("le délimiteur (*");
        
        test2.add("une façon de présenter le code choisie par "
                             + "le programmeur nommé Dupont");
        test2.add("un texte sans signification particulière");
        assertEquals(test1, questionValide.get(0).getMauvaisesReponses());
        questionValide.get(0).setMauvaiseReponse(test2);
        assertEquals(mauvaiseReponse2, questionValide.get(0).getMauvaisesReponses());
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

        assertDoesNotThrow(()-> questionValide.get(0).toString());
        assertEquals(valide, questionValide.get(0).toString());
    }

}
