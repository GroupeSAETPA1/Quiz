/*
 * testModelePrincipale.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Question;

/** 
 * TODO comment class responsibility (SRP)
 * @author Lucas
 */
class testModelePrincipale {
    
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
                          categoriesValides[1],3 ,  
                          "Le rôle du programme, en explicitant "
                          + "de manière précise ce rôle " , mauvaiseReponse4 ,
                          ""));
    }

    /**
     * Test method for {@link application.modele.ModelePrincipal#getInstance()}.
     */
    @Test
    void testGetInstance() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.ModelePrincipal#creerQuestion(java.lang.String, application.modele.Categorie, int, java.lang.String, java.util.ArrayList, java.lang.String)}.
     */
    @Test
    void testCreerQuestion() {
        ModelePrincipal modele = ModelePrincipal.getInstance();
        
        // Question de "reference"
        assertTrue(modele.creerQuestion("Quel est le délimiteur de "
                + "début d'un commentaire Javadoc ", "Commentaire", 
                1, "non vide", mauvaiseReponse1, ""));
        
        // Test ajout de question avec seulement categorie differente
        assertTrue(modele.creerQuestion("Quel est le délimiteur de "
                + "début d'un commentaire Javadoc ", "Javadoc", 
                1, "non vide", mauvaiseReponse1, ""));
        
        // Test ajout de question avec seulement libelle différent
        assertTrue(modele.creerQuestion("libelle different","Commentaire", 
                1, "non vide", mauvaiseReponse1, ""));
        
        //Test ajout de question avec seulement mauvaise reponse differente
        assertTrue(modele.creerQuestion("Quel est le délimiteur de "
                + "début d'un commentaire Javadoc ", "Commentaire", 
                1, "non vide", mauvaiseReponse2, ""));
        
        //Test ajout de question avec seulement bonne reponse differente
        assertTrue(modele.creerQuestion("Quel est le délimiteur de "
                + "début d'un commentaire Javadoc ", "Commentaire", 
                1, "different", mauvaiseReponse1, ""));
        
        // Test de non ajout d'une question deja existante
        assertFalse(modele.creerQuestion("Quel est le délimiteur de "
                + "début d'un commentaire Javadoc ", "Commentaire", 
                1, "non vide", mauvaiseReponse1, ""));
        
        
        /* verification de l'exception propager si la question a  creer 
         * est invalide (tous les paramètres faux)
         */

        assertThrows(IllegalArgumentException.class , 
               ()-> modele.creerQuestion("", "", 1, "",mauvaiseReponseVide, ""));
        
        // Ajout avec une categorie inexistance
        assertTrue(modele.creerQuestion("Quel est le délimiteur de "
                + "début d'un commentaire Javadoc ", "categorieTest", 
                1, "non vide", mauvaiseReponse1, ""));
        
        
        

    }
    

}
