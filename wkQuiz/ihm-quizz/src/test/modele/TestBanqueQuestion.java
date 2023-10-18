/*
 * TestBanqueQuestion.java                                    18 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.HomonymeException;
import modele.BanqueQuestion;
import modele.Categorie;
import modele.Question;

/** 
 * Classe de test unitaire de la classe {@link modele.BanqueQuestion}
 * @author Francois de Saint Palais
 */
class TestBanqueQuestion {
    
    private ArrayList<Question> ensembleQuestion;
    private ArrayList<Question> ensembleQuestionCategorieNom;
    private ArrayList<Question> ensembleQuestionDifficulteFacile;
    private ArrayList<Question> ensembleQuestionLibelleNom;
    private BanqueQuestion banqueQuestion;
    
    @BeforeEach
    void genererQuestionValide() {
        ArrayList<String> reponsesFausses = new ArrayList<String>();
        reponsesFausses.add("Faux");
        Question question1 = new Question("Libelle", new Categorie("Nom"), 1,
                "Vrai", reponsesFausses);
        
        banqueQuestion = new BanqueQuestion();
        ensembleQuestion = new ArrayList<Question>();
        ensembleQuestionCategorieNom = new ArrayList<Question>();
        ensembleQuestionDifficulteFacile = new ArrayList<Question>();
        ensembleQuestionLibelleNom = new ArrayList<Question>();
        
        ensembleQuestion.add(question1);
        ensembleQuestionCategorieNom.add(question1);
        ensembleQuestionDifficulteFacile.add(question1);
        ensembleQuestionLibelleNom.add(question1);
        
    }


    /**
     * Test method for {@link modele.BanqueQuestion#ajouter(modele.Question)}.
     */
    @Test
    void testAjouter() {
        assertDoesNotThrow(()->banqueQuestion.ajouter(ensembleQuestion.get(0)));
        //Question déjà présente
        assertThrows(HomonymeException.class, 
                ()->banqueQuestion.ajouter(ensembleQuestion.get(0)));
    }

    /**
     * Test method for {@link modele.BanqueQuestion#BanqueQuestion(java.util.List)}.
     */
    @Test
    void testBanqueQuestion() {
        assertDoesNotThrow(()->new BanqueQuestion());
    }

    /**
     * Test method for {@link modele.BanqueQuestion#getQuestion(int)}.
     */
    @Test
    void testGetQuestion() {
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestion(-1));
        
        banqueQuestion.ajouter(ensembleQuestion.get(0));
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestion(ensembleQuestion.size()));
        assertEquals(ensembleQuestion.get(0), banqueQuestion.getQuestion(0));
    }

    /**
     * Test method for {@link modele.BanqueQuestion#getQuestionsNbFausseReponse(int)}.
     */
    @Test
    void testGetQuestionNbFausseReponse() {
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestionsNbFausseReponse(Integer.MIN_VALUE));
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestionsNbFausseReponse(-1));
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestionsNbFausseReponse(0));
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestionsNbFausseReponse(5));
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestionsNbFausseReponse(Integer.MAX_VALUE));
        
        banqueQuestion.ajouter(ensembleQuestion.get(0));
        assertEquals(ensembleQuestion.get(0), 
                banqueQuestion.getQuestionsNbFausseReponse(1));
    }

    /**
     * Test method for {@link modele.BanqueQuestion#getQuestions()}.
     */
    @Test
    void testGetQuestions() {
        assertIterableEquals(ensembleQuestion, banqueQuestion.getQuestions());
    }



    /**
     * Test method for 
     * {@link modele.BanqueQuestion#getQuestions(modele.Categorie)}.
     */
    @Test
    void testGetQuestionsCategorie() {
        banqueQuestion.ajouter(ensembleQuestionCategorieNom.get(0));
        assertEquals(ensembleQuestionCategorieNom.get(0), 
                banqueQuestion.getQuestions(new Categorie("Nom")));
    }

    /**
     * Test method for 
     * {@link modele.BanqueQuestion#getQuestionsDifficulte(int)}.
     */
    @Test
    void testGetQuestionsDifficulte() {
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestionsDifficulte(Integer.MIN_VALUE));
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestionsDifficulte(-1));
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestionsDifficulte(0));
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestionsDifficulte(4));
        assertThrows(IllegalArgumentException.class, ()->banqueQuestion.getQuestionsDifficulte(Integer.MAX_VALUE));
        
        banqueQuestion.ajouter(ensembleQuestionDifficulteFacile.get(0));
        assertIterableEquals(ensembleQuestionDifficulteFacile, 
                banqueQuestion.getQuestionsDifficulte(1));
    }

    /**
     * Test method for 
     * {@link modele.BanqueQuestion#getQuestionsLibelle(java.util.List)}.
     */
    @Test
    void testGetQuestionsLibelle() {
        banqueQuestion.ajouter(ensembleQuestionLibelleNom.get(0));
        
        assertIterableEquals(ensembleQuestionLibelleNom, 
                banqueQuestion.getQuestionsLibelle("Nom"));
        assertIterableEquals(ensembleQuestionLibelleNom, 
                banqueQuestion.getQuestionsLibelle("nom"));
        //On attend toutes les questions
        assertIterableEquals(ensembleQuestionLibelleNom, 
                banqueQuestion.getQuestionsLibelle(""));
    }
    
    

}
