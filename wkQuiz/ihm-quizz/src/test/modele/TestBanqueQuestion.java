/*
 * TestBanqueQuestion.java                                    18 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.exception.CreerQuestionException;
import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.modele.BanqueQuestion;
import application.modele.Categorie;
import application.modele.Question;

/**
 * Classe de test unitaire de la classe
 * {@link application.modele.BanqueQuestion}
 * 
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
	 * 
	 * @throws InvalidNameException
	 * @throws CreerQuestionException 
	 */
	@BeforeEach
	void genererQuestionValide()
			throws InvalidNameException, CreerQuestionException {
		ArrayList<String> reponsesFausses = new ArrayList<String>();
		reponsesFausses.add("Faux");
		Question question1 = new Question("Libelle", new Categorie("Nom"), 1, "Vrai", reponsesFausses, "");

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
	 * Méthode de test pour le constructeur
	 * @see {@link application.modele.BanqueQuestion#BanqueQuestion(java.util.List)}.
	 */
	@Test
	void testBanqueQuestion() {
		assertDoesNotThrow(() -> new BanqueQuestion());
	}
	
	/**
	 * Méthode de test pour ajouter
	 * @see {@link application.modele.BanqueQuestion#ajouter(application.modele.Question)}.
	 */
	@Test
	void testAjouter() {
		// On ajoute une question valide qui n'est pas déjà présente
		assertDoesNotThrow(() -> banqueQuestion.ajouter(ensembleQuestion.get(0)));
		// La question déjà présente, on renvoie l'exception "HomonymeException"
		assertThrows(HomonymeException.class, () -> banqueQuestion.ajouter(ensembleQuestion.get(0)));
	}

	/**
	 * Méthode de test pour getQuestion
	 * @see {@link application.modele.BanqueQuestion#getQuestion(int)}.
	 * 
	 * @throws IndexOutOfBoundsException
	 * @throws HomonymeException
	 */
	@Test
	void testGetQuestion() throws IndexOutOfBoundsException, HomonymeException {
		// On vérifie que rechercher une question a un index invalide 
		// (index négatif ou supérieur ou égal à size)
		// renvoie bien l'exception "IndexOutOfBoundsException"
		assertThrows(IndexOutOfBoundsException.class, () -> banqueQuestion.getQuestion(-1));

		// Ajouter une question à l'index 0 fait que la size de la 
		// banque de question est de 1, mais il faut chercher l'index 0 
		// car les collections commencent à 0
		banqueQuestion.ajouter(ensembleQuestion.get(0));
		assertThrows(IndexOutOfBoundsException.class, () -> banqueQuestion.getQuestion(ensembleQuestion.size()));
		assertEquals(ensembleQuestion.get(0), banqueQuestion.getQuestion(0));
	}

	/**
	 * Méthode de test pour getQuestionsNbFausseReponse
	 * @see {@link application.modele.BanqueQuestion#getQuestionsNbFausseReponse(int)}.
	 * 
	 * @throws HomonymeException
	 * @throws InvalidFormatException
	 */
	@Test
	void testGetQuestionNbFausseReponse() throws HomonymeException, InvalidFormatException {
		// On vérifie que demander des nombres de mauvaises réponses invalides
		// (le min et max value, des nombres inférieur à 1 ou supérieur à 4)
		// à la banque de question renvoie  l'exception "InvalidFormatException"
		assertThrows(InvalidFormatException.class, () -> banqueQuestion.getQuestionsNbFausseReponse(Integer.MIN_VALUE));
		assertThrows(InvalidFormatException.class, () -> banqueQuestion.getQuestionsNbFausseReponse(-1));
		assertThrows(InvalidFormatException.class, () -> banqueQuestion.getQuestionsNbFausseReponse(0));
		assertThrows(InvalidFormatException.class, () -> banqueQuestion.getQuestionsNbFausseReponse(5));
		assertThrows(InvalidFormatException.class, () -> banqueQuestion.getQuestionsNbFausseReponse(Integer.MAX_VALUE));

		// On vérifie qu'on retrouve bien une question si on a 
		// dans la banque de question qu'une seule question qui 
		// à une seule mauvaise réponse 
		banqueQuestion.ajouter(ensembleQuestion.get(0));
		assertEquals(ensembleQuestion.get(0), banqueQuestion.getQuestionsNbFausseReponse(1).get(0));
	}

	/**
	 * Méthode de test pour getQuestions
	 * @see {@link application.modele.BanqueQuestion#getQuestions()}.
	 * 
	 * @throws HomonymeException
	 */
	@Test
	void testGetQuestions() throws HomonymeException {
		// On vérifie qu'on aie bien une liste vide de questions si on veut 
		// récupérer la liste des questions d'une banque de question vide
		ArrayList<Question> listeVide = new ArrayList<Question>();
		assertIterableEquals(listeVide, banqueQuestion.getQuestions());
		
		// On ajoute une liste de questions à la banque de question vide et 
		// on vérifie que chaque question de la banque de question est égale à
		// chaque question de la liste des questions ajoutées
		for (Question question : ensembleQuestion) {
			banqueQuestion.ajouter(question);
		}
		assertIterableEquals(ensembleQuestion, banqueQuestion.getQuestions());
	}

	/**
	 * Méthode de test pour getQuestionsCategorie
	 * @see {@link application.modele.BanqueQuestion#getQuestions(application.modele.Categorie)}.
	 * 
	 * @throws HomonymeException
	 * @throws InvalidNameException
	 */
	@Test
	void testGetQuestionsCategorie() throws HomonymeException, InvalidNameException {
		// On ajoute une question qui à une catégorie "Nom" et on vérifie
		// qu'on récupere bien cette question en recherchant une catégorie "Nom"
		// dans la banque de questions 
		banqueQuestion.ajouter(ensembleQuestionCategorieNom.get(0));
		assertEquals(ensembleQuestionCategorieNom.get(0), banqueQuestion.getQuestions(new Categorie("Nom")).get(0));
	}

	/**
	 * Méthode de test pour getQuestionsDifficulte
	 * @see {@link application.modele.BanqueQuestion#getQuestionsDifficulte(int)}.
	 * 
	 * @throws HomonymeException  
	 * @throws DifficulteException
	 */
	@Test
	void testGetQuestionsDifficulte() throws HomonymeException, DifficulteException {
		// On vérifie que si on recherche une question dans la banque de question 
		// qui à une difficultée invalide 
		// (les min et max values, les valeurs inférieures à 1 et supérieures à 3),
		// on renvoie une exception "DifficulteException"
		assertThrows(DifficulteException.class, () -> banqueQuestion.getQuestionsDifficulte(Integer.MIN_VALUE));
		assertThrows(DifficulteException.class, () -> banqueQuestion.getQuestionsDifficulte(-1));
		assertThrows(DifficulteException.class, () -> banqueQuestion.getQuestionsDifficulte(0));
		assertThrows(DifficulteException.class, () -> banqueQuestion.getQuestionsDifficulte(4));
		assertThrows(DifficulteException.class, () -> banqueQuestion.getQuestionsDifficulte(Integer.MAX_VALUE));
		
		// On rajoute une question de difficultée facile dans la banque de question 
		// et on vérifie que la question est renvoyée quand on récupere 
		// les question de difficultée facile (1) 
		banqueQuestion.ajouter(ensembleQuestionDifficulteFacile.get(0));
		assertIterableEquals(ensembleQuestionDifficulteFacile, banqueQuestion.getQuestionsDifficulte(1));
	}

	/**
	 * Méthode de test pour getQuestionsLibelle
	 * @see {@link application.modele.BanqueQuestion#getQuestionsLibelle(java.util.List)}.
	 * 
	 * @throws HomonymeException
	 */
	@Test
	void testGetQuestionsLibelle() throws HomonymeException {
		banqueQuestion.ajouter(ensembleQuestionLibelleNom.get(0));

		assertIterableEquals(ensembleQuestionLibelleNom, banqueQuestion.getQuestionsLibelle("Libelle"));
		assertIterableEquals(ensembleQuestionLibelleNom, banqueQuestion.getQuestionsLibelle("libelle"));
		assertIterableEquals(ensembleQuestionLibelleNom, banqueQuestion.getQuestionsLibelle("LIBELLE"));
		// On attend toutes les questions
		assertIterableEquals(ensembleQuestionLibelleNom, banqueQuestion.getQuestionsLibelle(""));
	}

	/**
	 * Méthode de test pour toString de BanqueQuestion
	 * @see {@link application.modele.BanqueQuestion#toString()}.
	 * @throws HomonymeException 
	 */
	@Test
	void testToString() throws HomonymeException {
		banqueQuestion.ajouter(ensembleQuestion.get(0));
		assertTrue(banqueQuestion.toString() instanceof String);
	}
}
