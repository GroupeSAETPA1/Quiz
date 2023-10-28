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
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;
import application.modele.BanqueQuestion;
import application.modele.Categorie;
import application.modele.Question;

/** 
 * Classe de test unitaire de la classe {@link application.modele.BanqueQuestion}
 * @author Francois de Saint Palais
 * @author Tom Douaud
 */
class TestBanqueQuestion {
    
    private ArrayList<Question> ensembleQuestion;
    private ArrayList<Question> ensembleQuestionCategorieNom;
    private ArrayList<Question> ensembleQuestionDifficulteFacile;
    private ArrayList<Question> ensembleQuestionLibelleNom;
    private BanqueQuestion banqueQuestion;
    
    /**
     * Génère une question valide pour les tests
     * @throws ReponseException 
     * @throws InvalidNameException 
     * @throws InvalidFormatException 
     */
    @BeforeEach
    void genererQuestionValide() throws InvalidFormatException, InvalidNameException, ReponseException {
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
     * Méthode de test pour ajouter
     * {@link application.modele.BanqueQuestion#ajouter(application.modele.Question)}.
     */
    @Test
    void testAjouter() {
        assertDoesNotThrow(()->banqueQuestion.ajouter(ensembleQuestion.get(0)));
        // Question déjà présente
        assertThrows(HomonymeException.class, 
                ()->banqueQuestion.ajouter(ensembleQuestion.get(0)));
    }

    /**
     * Méthode de test pour le constructeur
     * {@link application.modele.BanqueQuestion#BanqueQuestion(java.util.List)}.
     */
    @Test
    void testBanqueQuestion() {
        assertDoesNotThrow(()->new BanqueQuestion());
    }

    /**
     * Méthode de test pour getQuestion
     * {@link application.modele.BanqueQuestion#getQuestion(int)}.
     * @throws IndexOutOfBoundsException 
     * @throws HomonymeException
     */
    @Test
    void testGetQuestion() throws IndexOutOfBoundsException, HomonymeException {
        assertThrows(IndexOutOfBoundsException.class, ()->banqueQuestion.getQuestion(-1));
        
        banqueQuestion.ajouter(ensembleQuestion.get(0));
        assertThrows(IndexOutOfBoundsException.class, ()->banqueQuestion.getQuestion(ensembleQuestion.size()));
        assertEquals(ensembleQuestion.get(0), banqueQuestion.getQuestion(0));
    }

    /**
     * Méthode de test pour getQuestionsNbFausseReponse
     * {@link application.modele.BanqueQuestion#getQuestionsNbFausseReponse(int)}.
     * @throws HomonymeException 
     * @throws InvalidFormatException 
     */
    @Test
    void testGetQuestionNbFausseReponse() throws HomonymeException,  InvalidFormatException {
        assertThrows(InvalidFormatException.class, ()->banqueQuestion.getQuestionsNbFausseReponse(Integer.MIN_VALUE));
        assertThrows(InvalidFormatException.class, ()->banqueQuestion.getQuestionsNbFausseReponse(-1));
        assertThrows(InvalidFormatException.class, ()->banqueQuestion.getQuestionsNbFausseReponse(0));
        assertThrows(InvalidFormatException.class, ()->banqueQuestion.getQuestionsNbFausseReponse(5));
        assertThrows(InvalidFormatException.class, ()->banqueQuestion.getQuestionsNbFausseReponse(Integer.MAX_VALUE));
        
        banqueQuestion.ajouter(ensembleQuestion.get(0));
        assertEquals(ensembleQuestion.get(0), 
                banqueQuestion.getQuestionsNbFausseReponse(1).get(0));
    }

    /**
     * Méthode de test pour getQuestions
     * {@link application.modele.BanqueQuestion#getQuestions()}.
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
     * Méthode de test pour getQuestionsCategorie
     * {@link application.modele.BanqueQuestion#getQuestions(application.modele.Categorie)}.
     * @throws HomonymeException 
     * @throws InvalidNameException 
     */
    @Test
    void testGetQuestionsCategorie() throws HomonymeException, InvalidNameException {
        banqueQuestion.ajouter(ensembleQuestionCategorieNom.get(0));
        assertEquals(ensembleQuestionCategorieNom.get(0), 
                banqueQuestion.getQuestions(new Categorie("Nom")).get(0));
    }

    /**
     * Méthode de test pour getQuestionsDifficulte
     * {@link application.modele.BanqueQuestion#getQuestionsDifficulte(int)}.
     * @throws HomonymeException 
     * TODO FIXME
     */
    @Test
    void testGetQuestionsDifficulte() throws InvalidFormatException, HomonymeException {
        assertThrows(InvalidFormatException.class, ()->banqueQuestion.getQuestionsDifficulte(Integer.MIN_VALUE));
        assertThrows(InvalidFormatException.class, ()->banqueQuestion.getQuestionsDifficulte(-1));
        assertThrows(InvalidFormatException.class, ()->banqueQuestion.getQuestionsDifficulte(0));
        assertThrows(InvalidFormatException.class, ()->banqueQuestion.getQuestionsDifficulte(4));
        assertThrows(InvalidFormatException.class, ()->banqueQuestion.getQuestionsDifficulte(Integer.MAX_VALUE));
        
        banqueQuestion.ajouter(ensembleQuestionDifficulteFacile.get(0));
        assertIterableEquals(ensembleQuestionDifficulteFacile, 
                banqueQuestion.getQuestionsDifficulte(1));
    }

    /**
     * Méthode de test pour getQuestionsLibelle
     * {@link application.modele.BanqueQuestion#getQuestionsLibelle(java.util.List)}.
     * @throws HomonymeException 
     */
    @Test
    void testGetQuestionsLibelle() throws HomonymeException {
        banqueQuestion.ajouter(ensembleQuestionLibelleNom.get(0));

        assertIterableEquals(ensembleQuestionLibelleNom,
                banqueQuestion.getQuestionsLibelle("Libelle"));
        assertIterableEquals(ensembleQuestionLibelleNom, 
                banqueQuestion.getQuestionsLibelle("libelle"));
        assertIterableEquals(ensembleQuestionLibelleNom, 
                banqueQuestion.getQuestionsLibelle("LIBELLE"));
        //On attend toutes les questions
        assertIterableEquals(ensembleQuestionLibelleNom, 
                banqueQuestion.getQuestionsLibelle(""));
    }

    /**
     * Méthode de test pour toString de BanqueQuestion
     * {@link application.modele.BanqueQuestion#toString()}.
     */
    @Test
    void testToString() {
        assertTrue(banqueQuestion.toString() instanceof String);
    }
}
