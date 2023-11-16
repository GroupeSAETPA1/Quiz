/*
 * TestQuestion.java                                    18 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.modele.Categorie;
import application.modele.Question;
import application.exception.DifficulteException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;

/**
 * Lancement des tests pour la classe Question
 * 
 * @author Lucas Descriaud
 */

class TestQuestion {

	private ArrayList<Question> questionValide;
	private ArrayList<String> mauvaiseReponse1;
	private ArrayList<String> mauvaiseReponse2;
	private ArrayList<String> mauvaiseReponse3;
	private ArrayList<String> mauvaiseReponse4;
	private ArrayList<String> mauvaiseReponseDoublon;
	private Categorie[] categoriesValides;
	private ArrayList<String> mauvaiseReponseVide;
	private ArrayList<String> mauvaiseReponseJusteChaineVide;

	/**
	 * Génère des jeux de test pour les tests unitaires
	 * 
	 * @throws ReponseException       si les réponses sont invalides
	 * @throws InvalidFormatException si le format est invalide
	 * @throws InvalidNameException   si le nom est invalide
	 * @throws DifficulteException
	 */
	@BeforeEach
	void genererJeuxDeTest()
			throws InvalidFormatException, InvalidNameException, ReponseException, DifficulteException {
		categoriesValides = new Categorie[] { new Categorie("Commentaire"), new Categorie("test") };
		questionValide = new ArrayList<Question>();
		mauvaiseReponse1 = new ArrayList<String>();
		mauvaiseReponse2 = new ArrayList<String>();
		mauvaiseReponse3 = new ArrayList<String>();
		mauvaiseReponse4 = new ArrayList<String>();
		mauvaiseReponseVide = new ArrayList<String>();
		mauvaiseReponseDoublon = new ArrayList<String>();
		mauvaiseReponseJusteChaineVide = new ArrayList<String>();

		mauvaiseReponseJusteChaineVide.add("");
		mauvaiseReponseJusteChaineVide.add("");
		mauvaiseReponseJusteChaineVide.add("");
		mauvaiseReponseJusteChaineVide.add("");

		mauvaiseReponse1.add("le délimiteur /*");
		mauvaiseReponse1.add("le delimiteur //");
		mauvaiseReponse1.add("le délimiteur (*");

		mauvaiseReponse2.add("une façon de présenter le code choisie par " + "le programmeur nommé Dupont");
		mauvaiseReponse2.add("un texte sans signification particulière");

		mauvaiseReponse3.add(" // commentaire");

		mauvaiseReponse4.add("Un résumé très bref, pas plus d'une ligne, " + "du rôle du programme");
		mauvaiseReponse4.add("Il n'y a pas de commentaire Javadoc " + "juste avant la ligne \"public class …\"");
		mauvaiseReponse4.add("Le nom du fichier contenant le programme");
		mauvaiseReponse4.add("Un texte libre laissé " + "à l'appréciation du programmeur");

		mauvaiseReponseDoublon.add("doublon");
		mauvaiseReponseDoublon.add("DOUBLON");
		mauvaiseReponseDoublon.add("dOublon");
		mauvaiseReponseDoublon.add("DoUbLOn");

		questionValide.add(new Question("Quel est le délimiteur de début " + "d'un commentaire Javadoc ?",
				categoriesValides[0], 1, "le délimiteur /**", mauvaiseReponse1, ""));
		questionValide.add(new Question("A quoi correspond l'expression : " + "@author Dupont ?", categoriesValides[0],
				2, "une balise reconnue par Javadoc", mauvaiseReponse2,
				"Les balises Javadoc commencent par le  caractère @"));
		questionValide.add(new Question(
				"Si un commentaire est écrit " + "sur plusieurs lignes, quel délimiteur de "
						+ "commentaire est-il préférable d'utiliser ?",
				categoriesValides[0], 1, "/* commentaire */", mauvaiseReponse3, ""));

		questionValide.add(new Question(
				"Que doit décrire le texte écrit dans " + "le commentaire Javadoc situé juste avant "
						+ "la ligne \"public class …\" ?",
				categoriesValides[0], 3, "Le rôle du programme, en explicitant " + "de manière précise ce rôle ",
				mauvaiseReponse4, ""));
	}

	/**
	 * Teste le constructeur de la classe Question
	 * 
	 * @see {@link application.modele.Question#Question(String, Categorie, int, String, ArrayList, String)}
	 * @throws ReponseException       si les réponses sont invalides
	 * @throws InvalidFormatException si le format est invalide
	 * @throws InvalidNameException   si le nom est invalide
	 */
	@Test
	void testConstructeur() {
		// Le libellé est vide
		assertThrows(InvalidNameException.class,
				() -> new Question("", categoriesValides[0], 3, "le délimiteur /**", mauvaiseReponse1, ""));

		// La difficulté est negative
		assertThrows(DifficulteException.class, () -> new Question("libelle non vide", categoriesValides[0], -1,
				"le délimiteur /**", mauvaiseReponse1, ""));
		assertThrows(DifficulteException.class, () -> new Question("libelle non vide", categoriesValides[0], -999999,
				"le délimiteur /**", mauvaiseReponse1, ""));

		// La difficulté est strictement supérieur à 3
		assertThrows(DifficulteException.class, () -> new Question("libelle non vide", categoriesValides[0], 4,
				"le délimiteur /**", mauvaiseReponse1, ""));
		assertThrows(DifficulteException.class, () -> new Question("libelle non vide", categoriesValides[0], 9999999,
				"le délimiteur /**", mauvaiseReponse1, ""));

		// La réponse juste est vide
		assertThrows(InvalidNameException.class,
				() -> new Question("libelle non vide", categoriesValides[0], 1, "", mauvaiseReponse3, ""));

		// La liste des mauvaise réponses est vide
		assertThrows(InvalidFormatException.class, () -> new Question("Libelle non  vide", categoriesValides[0], 1,
				"reponse juste non vide", mauvaiseReponseVide, ""));
		assertDoesNotThrow(() -> new Question("libelle non vide", categoriesValides[0], 3, "le délimiteur /**",
				mauvaiseReponse1, ""));

		// La liste des mauvaise réponses contient un doublon
		assertDoesNotThrow(() -> new Question("libelle non vide", categoriesValides[0], 3, "reponse juste non vide",
				mauvaiseReponseDoublon, ""));

		// La réponse juste est présente dans les réponses fausses
		assertThrows(ReponseException.class, () -> new Question("libelle non vide", categoriesValides[0], 3,
				"le délimiteur /*", mauvaiseReponse1, ""));
		assertThrows(ReponseException.class, () -> new Question("libelle non vide", categoriesValides[0], 3,
				"LE DELIMITEUR //", mauvaiseReponse1, ""));
		assertThrows(ReponseException.class, () -> new Question("libelle non vide", categoriesValides[0], 3,
				"LE DELimiTEUR //", mauvaiseReponse1, ""));
		assertThrows(ReponseException.class, () -> new Question("libelle non vide", categoriesValides[0], 3,
				"LE delIMIteur //", mauvaiseReponse1, ""));

		// La liste ne contient que des chaines vide
		assertThrows(ReponseException.class, () -> new Question("Libelle non vide", categoriesValides[0], 2, "non vide",
				mauvaiseReponseJusteChaineVide, ""));

		// Libelle longueur (201) > longueur max libelle (200)
		assertThrows(InvalidNameException.class, () -> new Question(
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
						+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
						+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
						+ "                                      AAAAAAAAAAAAAAA"
						+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAA                         " + "        AAAAAAAAAAAAAA",
				categoriesValides[0], 2, "non vide", mauvaiseReponse1, ""));

		// Réponse juste de longueur (251) > longueur max réponse (250)
		assertThrows(InvalidNameException.class,
				() -> new Question("non vide", categoriesValides[0], 1,
						"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
								+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
								+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
								+ "AAAAAAAAA                                      AAAAAAAAAAAAAA"
								+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAA                                " + " AAAAAAAAAAAAAA",
						mauvaiseReponse2, ""));

		// Une réponse fausse de longeur (251) > longueur max réponse (250)
		mauvaiseReponse1.add("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
				+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
				+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + "        aaaaaaaaaaaaaaaaa");
		assertThrows(ReponseException.class,
				() -> new Question("non vide", categoriesValides[0], 1, "non vide", mauvaiseReponse1, ""));
		// Plusieurs réponses fausse (251) > longueur max réponse (250)
		mauvaiseReponse1.add("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
				+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
				+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
				+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA                ");
		assertThrows(ReponseException.class,
				() -> new Question("non vide", categoriesValides[0], 1, "non vide", mauvaiseReponse1, ""));
		// Longueur feedback > longeur feedback (350)
		assertThrows(InvalidNameException.class,
				() -> new Question("non vide", categoriesValides[0], 1, "non vide", mauvaiseReponse2,
						"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
								+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
								+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
								+ "AAAAAAAAAA                                      "
								+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA     "
								+ "                            AAAAAAAAAAAAAA      "
								+ "          aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa  "
								+ "          aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaaaaaa aaa"));

	}

	/**
	 * Teste la méthode getLibelle de la classe Question
	 * 
	 * @see {@link application.modele.Question#getLibelle()}
	 * @throws InvalidNameException si le nom est invalide
	 */
	@Test
	void testGetLibelle() throws InvalidNameException {

		// Récuperer un libellé existant
		assertEquals("Quel est le délimiteur de début d'un commentaire Javadoc ?", questionValide.get(0).getLibelle());
		assertEquals("A quoi correspond l'expression : @author Dupont ?", questionValide.get(1).getLibelle());
		assertEquals(
				"Si un commentaire est écrit sur plusieurs lignes, "
						+ "quel délimiteur de commentaire est-il préférable d'utiliser ?",
				questionValide.get(2).getLibelle());
		assertEquals(
				"Que doit décrire le texte écrit dans le commentaire "
						+ "Javadoc situé juste avant la ligne \"public class …\" ?",
				questionValide.get(3).getLibelle());

		// Récupere un libellé après modification
		questionValide.get(0).setLibelle("nouveau libelle");
		assertEquals("nouveau libelle", questionValide.get(0).getLibelle());
	}

	/**
	 * Teste la méthode setLibelle de la classe Question
	 * 
	 * @see {@link application.modele.Question#setLibelle(String)}
	 * @throws InvalidNameException si le nom est invalide
	 */
	@Test
	void testSetLibelle() {
		// On vérifie qu'une exception est bien levée si on donne un libellé invalide
		assertThrows(InvalidNameException.class, () -> questionValide.get(0).setLibelle(""));
		
		// On vérifie qu'il n'y a pas d'exception de levée quand on donne un libellé valide
		assertDoesNotThrow(() -> questionValide.get(0).setLibelle("test1"));
		// On vérifie après que le libellé est bien modifié
		assertEquals("test1", questionValide.get(0).getLibelle());
	}

	/**
	 * Teste la méthode getCategorie de la classe Question
	 * 
	 * @see {@link application.modele.Question#getCategorie()}
	 */
	@Test
	void testGetCategorie() {
		assertEquals("Commentaire", questionValide.get(0).getCategorie());
		assertNotEquals("test", questionValide.get(0).getCategorie());
		
		// Modification de la catégorie de la question
		questionValide.get(0).setCategorie(categoriesValides[1]);
		// On vérifie que la catégorie de la question est bien changée
		assertEquals("test", questionValide.get(0).getCategorie());
		assertNotEquals("Commentaire", questionValide.get(0).getCategorie());
	}

	/**
	 * Teste la méthode setCategorie de la classe Question
	 * 
	 * @see {@link application.modele.Question#setCategorie(Categorie)}
	 */
	@Test
	void testSetCategorie() {
		// On vérifie que la méthode ne lève pas d'exception
		assertDoesNotThrow(() -> questionValide.get(0).setCategorie(categoriesValides[1]));
		// On vérifie que le changement a bien été effectué
		assertEquals("test", questionValide.get(0).getCategorie());
	}

	/**
	 * Teste la méthode getDifficulte de la classe Question
	 * 
	 * @see {@link application.modele.Question#getDifficulte()}
	 */
	@Test
	void testGetDifficulte() {
		assertEquals(1, questionValide.get(0).getDifficulte());
		assertNotEquals(2, questionValide.get(0).getDifficulte());

		assertEquals(2, questionValide.get(1).getDifficulte());
		assertNotEquals(0, questionValide.get(1).getDifficulte());
	}

	/**
	 * Teste la méthode setDifficulte de la classe Question
	 * 
	 * @see {@link application.modele.Question#setDifficulte(int)}
	 */
	@Test
	void testSetDifficulte() {
		// On vérifie qu'il y a bien une exception quand on envoie des difficultées invalides
		assertThrows(InvalidFormatException.class, () -> questionValide.get(0).setDifficulte(6));
		assertThrows(InvalidFormatException.class, () -> questionValide.get(0).setDifficulte(-5));
		
		// On vérifie qu'il n'y a pas d'exception quand on set une difficultée valide 
		assertDoesNotThrow(() -> questionValide.get(0).setDifficulte(3));
		assertEquals(3, questionValide.get(0).getDifficulte());
	}

	/**
	 * Teste la méthode getFeedback de la classe Question
	 * 
	 * @see {@link application.modele.Question#getFeedback()}
	 */
	@Test
	void testGetFeedback() {
		// On vérifie qu'on ait bien une chaine vide sur une question sans feedback
		assertEquals("", questionValide.get(0).getFeedback());
		assertNotEquals("test", questionValide.get(0).getFeedback());
		
		// On vérifie qu'on ait bien le Feedback d'une question avec feedback
		assertEquals("Les balises Javadoc commencent par le  caractère @", questionValide.get(1).getFeedback());
	}

	/**
	 * Teste la méthode setFeedback de la classe Question
	 * 
	 * @see {@link application.modele.Question#setFeedback(String)}
	 */
	@Test
	void testSetFeedback() {
		// On vérifie que la méthode ne lève pas d'exception 
		// si on ne mets pas de feedback ou si on le modifie
		assertDoesNotThrow(() -> questionValide.get(0).setFeedback(""));
		assertDoesNotThrow(() -> questionValide.get(0).setFeedback("test"));
		// On vérifie que le changement a bien été effectué
		assertEquals("test", questionValide.get(0).getFeedback());
	}

	/**
	 * Teste la méthode getBonneReponse de la classe Question
	 * 
	 * @see {@link application.modele.Question#getBonneReponse()}
	 * @throws ReponseException     si les réponses sont invalides
	 * @throws InvalidNameException si le nom est invalide
	 */
	@Test
	void testGetBonneReponse() throws InvalidNameException, ReponseException {
		// Reponse juste deja existante
		assertEquals("/* commentaire */", questionValide.get(2).getReponseJuste());
		// Reponse juste apres un changement
		questionValide.get(2).setBonneReponse("test45");
		assertEquals("test45", questionValide.get(2).getReponseJuste());
	}
	
	/**
	 * Teste la méthode testSetBonneReponse de la classe Question
	 * 
	 * @see {@link application.modele.Question#setBonneReponse()}
	 */
	@Test
	void testSetBonneReponse() {
		assertThrows(InvalidNameException.class, () -> questionValide.get(0).setBonneReponse(""));
		assertThrows(ReponseException.class, () -> questionValide.get(0).setBonneReponse("le délimiteur /*"));
		assertDoesNotThrow(() -> questionValide.get(0).setBonneReponse("test1"));
		assertEquals("test1", questionValide.get(0).getReponseJuste());
	}

	/**
	 * Teste la méthode setMauvaiseReponse de la classe Question
	 * 
	 * @see {@link application.modele.Question#setMauvaiseReponse(ArrayList)}
	 */
	@Test
	void testSetMauvaiseReponse() {
		ArrayList<String> mauvaiseReponseContientJuste = new ArrayList<String>();
		mauvaiseReponseContientJuste.add("reponseOk");
		mauvaiseReponseContientJuste.add("reponseOK2");
		mauvaiseReponseContientJuste.add("le délimiteur /**");
		ArrayList<String> test1 = new ArrayList<String>();
		test1.add("test1");
		test1.add("test2");
		assertThrows(InvalidFormatException.class, () -> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseVide));
		assertDoesNotThrow(() -> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseDoublon));
		assertThrows(ReponseException.class,
				() -> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseContientJuste));
		assertThrows(ReponseException.class,
				() -> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseJusteChaineVide));
		assertDoesNotThrow(() -> questionValide.get(0).setMauvaiseReponse(test1));
		assertEquals(test1, questionValide.get(0).getMauvaisesReponses());
	}

	/**
	 * Teste la méthode equals de la classe Question
	 * 
	 * @see {@link application.modele.Question#equals()}
	 * @throws InvalidFormatException si le format est invalide
	 * @throws ReponseException       si les réponses sont invalides
	 * @throws InvalidNameException   si le nom est invalide
	 * @throws DifficulteException
	 */
	@Test
	void testEquals() throws InvalidFormatException, InvalidNameException, ReponseException, DifficulteException {
		Question question1Egale = new Question("Quel est le délimiteur de début " + "d'un commentaire Javadoc ?",
				categoriesValides[0], 1, "le délimiteur /**", mauvaiseReponse1, "");
		Question question2Egale = new Question("A quoi correspond l'expression : " + "@author Dupont ?",
				categoriesValides[0], 2, "une balise reconnue par Javadoc", mauvaiseReponse2, "");
		Question question3Egale = new Question("A quoi correspond l'expression : " + "@author Dupont ?",
				categoriesValides[0], 3, "une balise reconnue par Javadoc", mauvaiseReponse2, "");
		// 2 question complement differente
		assertNotEquals(questionValide.get(0), questionValide.get(1));

		// 2 question identique
		assertEquals(questionValide.get(0), question1Egale);

		// 2 question dont 1 feedback l'autre non
		assertEquals(question2Egale, questionValide.get(1));

		// 2 question exactemet pareil mais difficulté différente
		assertEquals(question3Egale, questionValide.get(1));

		// 2 fois exactement la meme question
		assertEquals(question2Egale, question2Egale);

		// 1 question et un objet null
		assertNotEquals(questionValide.get(0), null);

		// 1 question et un objet d'une autre classe
		assertNotEquals(questionValide.get(0), new String("test"));

	}

	/**
	 * Teste la méthode getMauvaisesReponses de la classe Question
	 * 
	 * @see {@link application.modele.Question#getMauvaisesReponses()}
	 * @throws ReponseException       si les réponses sont invalides
	 * @throws InvalidFormatException si le format est invalide
	 */
	@Test
	void testGetMauvaisesReponses() throws InvalidFormatException, ReponseException {
		ArrayList<String> test1 = new ArrayList<String>();
		ArrayList<String> test2 = new ArrayList<String>();
		test1.add("le délimiteur /*");
		test1.add("le delimiteur //");
		test1.add("le délimiteur (*");

		test2.add("une façon de présenter le code choisie par " + "le programmeur nommé Dupont");
		test2.add("un texte sans signification particulière");
		assertEquals(test1, questionValide.get(0).getMauvaisesReponses());
		questionValide.get(0).setMauvaiseReponse(test2);
		assertEquals(mauvaiseReponse2, questionValide.get(0).getMauvaisesReponses());
	}

	/**
	 * Teste la méthode toString de la classe Question
	 * 
	 * @see {@link application.modele.Question#toString()}
	 */
	@Test
	void testToString() {
		String valide = """
				Difficulté de la question : 1
				Categorie de la question : Commentaire
				Intiltulé de la question : Quel est le délimiteur de début d'un commentaire Javadoc ?
				Mauvaise réponses :
				- le délimiteur /*
				- le delimiteur //
				- le délimiteur (*
				Bonne réponse : le délimiteur /**""";

		String valide2 = """
				Difficulté de la question : 2
				Categorie de la question : Commentaire
				Intiltulé de la question : A quoi correspond l'expression : @author Dupont ?
				Mauvaise réponses :
				- une façon de présenter le code choisie par le programmeur nommé Dupont
				- un texte sans signification particulière
				Bonne réponse : une balise reconnue par Javadoc
				Feedback : Les balises Javadoc commencent par le  caractère @""";

		System.out.println(questionValide.get(1).toString());
		assertDoesNotThrow(() -> questionValide.get(0).toString());
		assertEquals(valide, questionValide.get(0).toString());

		// deuxième test avec une question qui a un feedback non vide
		assertDoesNotThrow(() -> questionValide.get(1).toString());
		assertEquals(valide2, questionValide.get(1).toString());

	}

}
