/*
 * TestBanqueQuestion.java                                    18 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.exception.HomonymeException;
import application.modele.BanqueQuestion;
import application.modele.Categorie;
import application.modele.Question;

/** 
 * Classe de test unitaire de la classe {@link application.modele.BanqueQuestion}
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
                "Vrai", reponsesFausses,"");
        
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
     * Test method for {@link application.modele.BanqueQuestion#ajouter(application.modele.Question)}.
     */
    @Test
    void testAjouter() {
        assertDoesNotThrow(()->banqueQuestion.ajouter(ensembleQuestion.get(0)));
        //Question déjà présente
        assertThrows(HomonymeException.class, 
                ()->banqueQuestion.ajouter(ensembleQuestion.get(0)));
    }

    /**
     * Test method for {@link application.modele.BanqueQuestion#BanqueQuestion(java.util.List)}.
     */
    @Test
    void testBanqueQuestion() {
        assertDoesNotThrow(()->new BanqueQuestion());
    }

    /**
     * Test method for {@link application.modele.BanqueQuestion#getQuestion(int)}.
     * @throws HomonymeException 
     */
    @Test
    void testGetQuestion() throws HomonymeException {
        assertThrows(IndexOutOfBoundsException.class, ()->banqueQuestion.getQuestion(-1));
        
        banqueQuestion.ajouter(ensembleQuestion.get(0));
        assertThrows(IndexOutOfBoundsException.class, ()->banqueQuestion.getQuestion(ensembleQuestion.size()));
        assertEquals(ensembleQuestion.get(0), banqueQuestion.getQuestion(0));
    }

    /**
     * Test method for {@link application.modele.BanqueQuestion#getQuestionsNbFausseReponse(int)}.
     * @throws HomonymeException 
     */
    @Test
    void testGetQuestionNbFausseReponse() throws HomonymeException {
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
     * Test method for {@link application.modele.BanqueQuestion#getQuestions()}.
     * @throws HomonymeException 
     */
    @Test
    void testGetQuestions() throws HomonymeException {
        ArrayList<Question> listeVide = new ArrayList<Question>();
        assertIterableEquals(listeVide, banqueQuestion.getQuestions());
        for (Question question : ensembleQuestion) {
            banqueQuestion.ajouter(question);
        }
        assertIterableEquals(ensembleQuestion, banqueQuestion.getQuestions());
    }



    /**
     * Test method for 
     * {@link application.modele.BanqueQuestion#getQuestions(application.modele.Categorie)}.
     * @throws HomonymeException 
     */
    @Test
    void testGetQuestionsCategorie() throws HomonymeException {
        banqueQuestion.ajouter(ensembleQuestionCategorieNom.get(0));
        assertEquals(ensembleQuestionCategorieNom.get(0), 
                banqueQuestion.getQuestions(new Categorie("Nom")));
    }

    /**
     * Test method for 
     * {@link application.modele.BanqueQuestion#getQuestionsDifficulte(int)}.
     * @throws HomonymeException 
     */
    @Test
    void testGetQuestionsDifficulte() throws HomonymeException {
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
     * {@link application.modele.BanqueQuestion#getQuestionsLibelle(java.util.List)}.
     * @throws HomonymeException 
     */
    @Test
    void testGetQuestionsLibelle() throws HomonymeException {
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
