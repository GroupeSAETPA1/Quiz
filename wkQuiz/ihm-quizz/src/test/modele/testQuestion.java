/*
 * testQuestion.java                                    18 oct. 2023
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

class testQuestion {
    
    private ArrayList<Question> questionValide;
    private  ArrayList<String> mauvaiseReponse1;
    private ArrayList<String> mauvaiseReponse2;
    private ArrayList<String> mauvaiseReponse3;
    private ArrayList<String> mauvaiseReponse4;
    private ArrayList<String> mauvaiseReponseDoublon;
    private Categorie[] categoriesValides = {new Categorie("Commentaire")};
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
        mauvaiseReponse1.add("le délimiteur //");
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
                           categoriesValides[0] , 1 , "le délimiteur /**" ,
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
    void testConstructeurFeedBack() {
        assertThrows(IllegalArgumentException.class,
                () -> new Question("" , categoriesValides[0] , 3 ,
                                   "le délimiteur /**" , mauvaiseReponse1 ,""));
        assertThrows(IllegalArgumentException.class,
                ()->  new Question("libelle non vide" , categoriesValides[0],-1,
                                   "le délimiteur /**" , mauvaiseReponse1 ,""));
        assertThrows(IllegalArgumentException.class,
                ()-> new Question("libelle non vide" , categoriesValides[0],5,
                                   "le délimiteur /**" , mauvaiseReponse1 ,""));
        assertThrows(IllegalArgumentException.class,
                ()->  new Question("libelle non vide" , categoriesValides[0],
                                    -999999,
                                   "le délimiteur /**" , mauvaiseReponse1 ,""));
        assertThrows(IllegalArgumentException.class,
                ()->  new Question("libelle non vide" , categoriesValides[0],
                                    9999999,
                                   "le délimiteur /**" , mauvaiseReponse1 ,""));
        assertThrows(IllegalArgumentException.class,
                ()-> new Question("libelle non vide" , categoriesValides[0],1,
                                  "" , mauvaiseReponse3 , ""));
        assertThrows(IllegalArgumentException.class,
                ()-> new Question("Libelle non  vide" , categoriesValides[0],1,
                                  "reponse juste non vide" ,
                                  mauvaiseReponseVide , ""));
        assertDoesNotThrow(()->new Question("libelle non vide" , 
                           categoriesValides[0] , 3 ,"le délimiteur /**" , 
                           mauvaiseReponse1 ,""));
        assertThrows(IllegalArgumentException.class,
                ()->new Question("libelle non vide" , categoriesValides[0],3,
                        "reponse juste non vide" , mauvaiseReponseDoublon ,""));
        
    }


    /**
     * Test method for {@link application.modele.Question#setLibelle(java.lang.String)}.
     */
    @Test
    void testSetLibelle() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Question#setCatgorie(java.lang.String)}.
     */
    @Test
    void testSetCatgorie() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Question#setDifficulte(int)}.
     */
    @Test
    void testSetDifficulte() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Question#setBonneReponse(java.lang.String)}.
     */
    @Test
    void testSetBonneReponse() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Question#setMauvaiseReponse(java.util.ArrayList)}.
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
