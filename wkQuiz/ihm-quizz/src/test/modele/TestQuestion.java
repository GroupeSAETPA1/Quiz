/*
 * TestQuestion.java                                    18 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.exception.CreerQuestionException;
import application.exception.DifficulteException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;
import application.modele.Categorie;
import application.modele.Question;

/**
 * Lancement des tests pour la classe Question
 * 
 * @author Lucas Descriaud
 */

class TestQuestion {

	/** Un texte qui est trop long pour un feedback*/
    private static final String TROP_LONG_TEXT = "Lorem ipsum dolor sit amet, "
            + "consectetuer adipiscing elit. Aenean commodo ligula eget dolor. "
            + "Aenean massa. Cum sociis natoque penatibus et magnis dis parturi"
            + "ent montes, nascetur ridiculus mus. Donec quam felis, ultricies "
            + "nec, pellentesque eu, pretium Lorem ipsum dolor sit amet, consec"
            + "tetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean"
            + " massa. Cum sociis natoque penatibus et magnis dis parturient mo"
            + "ntes, nascetur ridiculus mus. Donec quam felis, ultricies nec, p"
            + "ellentesque eu, pretium";
    
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
	 * Genere des jeux de test pour les tests unitaires
	 * 
	 * @throws InvalidNameException   si le nom est invalide
	 * @throws CreerQuestionException 
	 */
	@BeforeEach
	void genererJeuxDeTest()
			throws InvalidNameException, CreerQuestionException {
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

		mauvaiseReponse1.add("le delimiteur /*");
		mauvaiseReponse1.add("le delimiteur //");
		mauvaiseReponse1.add("le delimiteur (*");

		mauvaiseReponse2.add("une facon de presenter le code choisie par le programmeur nomme Dupont");
		mauvaiseReponse2.add("un texte sans signification particuliere");

		mauvaiseReponse3.add(" // commentaire");

		mauvaiseReponse4.add("Un resume tres bref, pas plus d'une ligne, du role du programme");
		mauvaiseReponse4.add("Il n'y a pas de commentaire Javadoc juste avant la ligne \"public class ...\"");
		mauvaiseReponse4.add("Le nom du fichier contenant le programme");
		mauvaiseReponse4.add("Un texte libre laisse " + "a l'appreciation du programmeur");

		mauvaiseReponseDoublon.add("doublon");
		mauvaiseReponseDoublon.add("DOUBLON");
		mauvaiseReponseDoublon.add("dOublon");
		mauvaiseReponseDoublon.add("DoUbLOn");

		questionValide.add(new Question("Quel est le delimiteur de debut " + "d'un commentaire Javadoc ?",
				categoriesValides[0], 1, "le delimiteur /**", mauvaiseReponse1, ""));
		questionValide.add(new Question("A quoi correspond l'expression : " + "@author Dupont ?", categoriesValides[0],
				2, "une balise reconnue par Javadoc", mauvaiseReponse2,
				"Les balises Javadoc commencent par le  caractere @"));
		questionValide.add(new Question(
				"Si un commentaire est ecrit " + "sur plusieurs lignes, quel delimiteur de "
						+ "commentaire est-il preferable d'utiliser ?",
				categoriesValides[0], 1, "/* commentaire */", mauvaiseReponse3, ""));

		questionValide.add(new Question(
				"Que doit decrire le texte ecrit dans " + "le commentaire Javadoc situe juste avant "
						+ "la ligne \"public class ...\" ?",
				categoriesValides[0], 3, "Le role du programme, en explicitant " + "de maniere precise ce role ",
				mauvaiseReponse4, ""));
	}

	/**
	 * Teste le constructeur de la classe Question
	 * 
	 * @see {@link application.modele.Question#Question(String, Categorie, int, String, ArrayList, String)}
	 * @throws ReponseException       si les reponses sont invalides
	 * @throws InvalidFormatException si le format est invalide
	 * @throws InvalidNameException   si le nom est invalide
	 */
	@Test
	void testConstructeur() {
		// Le libelle est vide
		assertThrows(InvalidNameException.class,
				() -> new Question("", categoriesValides[0], 3, "le delimiteur /**", mauvaiseReponse1, ""));

		// La difficulte est negative
		assertThrows(DifficulteException.class, () -> new Question("libelle non vide", categoriesValides[0], -1,
				"le delimiteur /**", mauvaiseReponse1, ""));
		assertThrows(DifficulteException.class, () -> new Question("libelle non vide", categoriesValides[0], -999999,
				"le delimiteur /**", mauvaiseReponse1, ""));

		// La difficulte est strictement superieur a 3
		assertThrows(DifficulteException.class, () -> new Question("libelle non vide", categoriesValides[0], 4,
				"le delimiteur /**", mauvaiseReponse1, ""));
		assertThrows(DifficulteException.class, () -> new Question("libelle non vide", categoriesValides[0], 9999999,
				"le delimiteur /**", mauvaiseReponse1, ""));

		// La reponse juste est vide
		assertThrows(InvalidNameException.class,
				() -> new Question("libelle non vide", categoriesValides[0], 1, "", mauvaiseReponse3, ""));

		// La liste des mauvaise reponses est vide
		assertThrows(InvalidFormatException.class, () -> new Question("Libelle non  vide", categoriesValides[0], 1,
				"reponse juste non vide", mauvaiseReponseVide, ""));
		assertDoesNotThrow(() -> new Question("libelle non vide", categoriesValides[0], 3, "le delimiteur /**",
				mauvaiseReponse1, ""));

		// La liste des mauvaise reponses contient un doublon
		assertDoesNotThrow(() -> new Question("libelle non vide", categoriesValides[0], 3, "reponse juste non vide",
				mauvaiseReponseDoublon, ""));

		// La reponse juste est presente dans les reponses fausses
		assertThrows(ReponseException.class, () -> new Question("libelle non vide", categoriesValides[0], 3,
				"le delimiteur /*", mauvaiseReponse1, ""));
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

		// Reponse juste de longueur (251) > longueur max reponse (250)
		assertThrows(InvalidNameException.class,
				() -> new Question("non vide", categoriesValides[0], 1,
						"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
								+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
								+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
								+ "AAAAAAAAA                                      AAAAAAAAAAAAAA"
								+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAA                                " + " AAAAAAAAAAAAAA",
						mauvaiseReponse2, ""));

		// Une reponse fausse de longeur (251) > longueur max reponse (250)
		mauvaiseReponse1.add("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
				+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
				+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + "        aaaaaaaaaaaaaaaaa");
		assertThrows(ReponseException.class,
				() -> new Question("non vide", categoriesValides[0], 1, "non vide", mauvaiseReponse1, ""));
		// Plusieurs reponses fausse (251) > longueur max reponse (250)
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
	 * Teste la methode getLibelle de la classe Question
	 * 
	 * @see {@link application.modele.Question#getLibelle()}
	 * @throws InvalidNameException si le nom est invalide
	 */
	@Test
	void testGetLibelle() throws InvalidNameException {

		// Recuperer un libelle existant
		assertEquals("Quel est le delimiteur de debut d'un commentaire Javadoc ?", questionValide.get(0).getLibelle());
		assertEquals("A quoi correspond l'expression : @author Dupont ?", questionValide.get(1).getLibelle());
		assertEquals(
				"Si un commentaire est ecrit sur plusieurs lignes, "
						+ "quel delimiteur de commentaire est-il preferable d'utiliser ?",
				questionValide.get(2).getLibelle());
		assertEquals(
				"Que doit decrire le texte ecrit dans le commentaire "
						+ "Javadoc situe juste avant la ligne \"public class ...\" ?",
				questionValide.get(3).getLibelle());

		// Recupere un libelle apres modification
		questionValide.get(0).setLibelle("nouveau libelle");
		assertEquals("nouveau libelle", questionValide.get(0).getLibelle());
	}

	/**
	 * Teste la methode setLibelle de la classe Question
	 * 
	 * @see {@link application.modele.Question#setLibelle(String)}
	 * @throws InvalidNameException si le nom est invalide
	 */
	@Test
	void testSetLibelle() {
		// On verifie qu'une exception est bien levee si on donne un libelle invalide
		assertThrows(InvalidNameException.class, () -> questionValide.get(0).setLibelle(""));
		
		// On verifie qu'il n'y a pas d'exception de levee quand on donne un libelle valide
		assertDoesNotThrow(() -> questionValide.get(0).setLibelle("test1"));
		// On verifie apres que le libelle est bien modifie
		assertEquals("test1", questionValide.get(0).getLibelle());
	}

	/**
	 * Teste la methode getCategorie de la classe Question
	 * 
	 * @see {@link application.modele.Question#getCategorie()}
	 */
	@Test
	void testGetCategorie() {
		assertEquals("Commentaire", questionValide.get(0).getCategorie());
		assertNotEquals("test", questionValide.get(0).getCategorie());
		
		// Modification de la categorie de la question
		questionValide.get(0).setCategorie(categoriesValides[1]);
		// On verifie que la categorie de la question est bien changee
		assertEquals("test", questionValide.get(0).getCategorie());
		assertNotEquals("Commentaire", questionValide.get(0).getCategorie());
	}

	/**
	 * Teste la methode setCategorie de la classe Question
	 * 
	 * @see {@link application.modele.Question#setCategorie(Categorie)}
	 */
	@Test
	void testSetCategorie() {
		// On verifie que la methode ne leve pas d'exception
		assertDoesNotThrow(() -> questionValide.get(0).setCategorie(categoriesValides[1]));
		// On verifie que le changement a bien ete effectue
		assertEquals("test", questionValide.get(0).getCategorie());
	}

	/**
	 * Teste la methode getDifficulte de la classe Question
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
	 * Teste la methode setDifficulte de la classe Question
	 * 
	 * @see {@link application.modele.Question#setDifficulte(int)}
	 */
	@Test
	void testSetDifficulte() {
		// On verifie qu'il y a bien une exception quand on envoie des difficultees invalides
		assertThrows(InvalidFormatException.class, () -> questionValide.get(0).setDifficulte(6));
		assertThrows(InvalidFormatException.class, () -> questionValide.get(0).setDifficulte(-5));
		
		// On verifie qu'il n'y a pas d'exception quand on set une difficultee valide 
		assertDoesNotThrow(() -> questionValide.get(0).setDifficulte(3));
		assertEquals(3, questionValide.get(0).getDifficulte());
	}

	/**
	 * Teste la methode getFeedback de la classe Question
	 * 
	 * @see {@link application.modele.Question#getFeedback()}
	 */
	@Test
	void testGetFeedback() {
		// On verifie qu'on ait bien une chaine vide sur une question sans feedback
		assertEquals("", questionValide.get(0).getFeedback());
		assertNotEquals("test", questionValide.get(0).getFeedback());
		
		// On verifie qu'on ait bien le Feedback d'une question avec feedback
		assertEquals("Les balises Javadoc commencent par le  caractere @", questionValide.get(1).getFeedback());
	}

	/**
	 * Teste la methode setFeedback de la classe Question
	 * @throws InvalidNameException 
	 * 
	 * @see {@link application.modele.Question#setFeedback(String)}
	 */
	@Test
	void testSetFeedback() throws InvalidNameException {
		// On verifie que la methode ne leve pas d'exception 
		// si on ne mets pas de feedback ou si on le modifie
		assertDoesNotThrow(() -> questionValide.get(0).setFeedback(""));
		assertDoesNotThrow(() -> questionValide.get(0).setFeedback("test"));
		// On verifie que le changement a bien ete effectue
		assertEquals("test", questionValide.get(0).getFeedback());
		
		assertThrows(InvalidNameException.class,
		        () -> questionValide.get(0).setFeedback(TROP_LONG_TEXT));
	}

	/**
	 * Teste la methode getBonneReponse de la classe Question
	 * 
	 * @see {@link application.modele.Question#getBonneReponse()}
	 * @throws ReponseException     si les reponses sont invalides
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
	 * Teste la methode testSetBonneReponse de la classe Question
	 * 
	 * @see {@link application.modele.Question#setBonneReponse()}
	 */
	@Test
	void testSetBonneReponse() {
		// On verifie que mettre une bonne reponse vide 
		// renvoie une exception "InvalidNameException"
		assertThrows(InvalidNameException.class, () -> questionValide.get(0).setBonneReponse(""));
		// On verifie que mettre une bonne reponse 
		// qui fait partie des mauvaise reponses renvoie 
		// une exception "ReponseException"
		assertThrows(ReponseException.class, () -> questionValide.get(0).setBonneReponse("le delimiteur /*"));
		
		// On verifie qu'on puisse bien mettre une bonne reponse valide 
		// et qu'elle est enregistree
		assertDoesNotThrow(() -> questionValide.get(0).setBonneReponse("test1"));
		assertEquals("test1", questionValide.get(0).getReponseJuste());
	}

	/**
	 * Teste la methode setMauvaiseReponse de la classe Question
	 * 
	 * @see {@link application.modele.Question#setMauvaiseReponse(ArrayList)}
	 */
	
	/**
	 * Teste la methode getMauvaisesReponses de la classe Question
	 * 
	 * @see {@link application.modele.Question#getMauvaisesReponses()}
	 * @throws ReponseException       si les reponses sont invalides
	 * @throws InvalidFormatException si le format est invalide
	 */
	@Test
	void testGetMauvaisesReponses() throws InvalidFormatException, ReponseException {
		ArrayList<String> test1 = new ArrayList<String>();
		ArrayList<String> test2 = new ArrayList<String>();
		
		// Les mauvaises reponses de la premiere question du jeu de test
		test1.add("le delimiteur /*");
		test1.add("le delimiteur //");
		test1.add("le delimiteur (*");
		
		// des nouvelles mauvaises reponses pour remplacer celles de la premiere question du jeu de test
		test2.add("une facon de presenter le code choisie par " + "le programmeur nomme Dupont");
		test2.add("un texte sans signification particuliere");
		
		// On verifie que les mauvaises reponses de la question 1 
		// sont bien celles passees dans le constructeur initial
		assertEquals(test1, questionValide.get(0).getMauvaisesReponses());
		// On verifie qu'apres avoir modifier les mauvaises reponses, 
		// les mauvaises reponses sont bien modifiees
		questionValide.get(0).setMauvaiseReponse(test2);
		assertEquals(mauvaiseReponse2, questionValide.get(0).getMauvaisesReponses());
	}
	
	@Test
	void testSetMauvaiseReponse() {
		// Generation d'un jeu de mauvaises reponses 
		// qui contienent la reponse juste
		ArrayList<String> mauvaiseReponseContientJuste = new ArrayList<String>();
		mauvaiseReponseContientJuste.add("reponseOk");
		mauvaiseReponseContientJuste.add("reponseOK2");
		mauvaiseReponseContientJuste.add("le delimiteur /**");
		
		// Generation d'une ArrayList de mauvaise reponse
		// avec une mauvaise reponse valide
		ArrayList<String> test1 = new ArrayList<String>();
		test1.add("test1");
		
		// On verifie que modifier les mauvaises reponses 
		// pour mettre une liste de mauvaises reponses vide (ou une liste vide)
		// renvoie l'exception "InvalidFormatException" ou "ReponseException"
		assertThrows(InvalidFormatException.class, () -> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseVide));
		assertThrows(ReponseException.class,
				() -> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseJusteChaineVide));
		
		// On verifie que modifier les mauvaises reponses 
		// afin mettre plusieurs fois 
		// la meme mauvaise reponse ne renvoye pas d'exception
		assertDoesNotThrow(() -> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseDoublon));
		// On verifie que modifier les mauvaises reponses
		// pour mettre une liste de mauvaises reponse qui contient 
		// la bonne reponse renvoie l'exception "ReponseException"
		assertThrows(ReponseException.class,
				() -> questionValide.get(0).setMauvaiseReponse(mauvaiseReponseContientJuste));
		
		// On verifie que modifier les mauvaises reponses pour 
		// mettre une mauvaise reponse valide ne renvoie pas d'exception 
		// et que la modification est bien enregistree
		assertDoesNotThrow(() -> questionValide.get(0).setMauvaiseReponse(test1));
		assertEquals(test1, questionValide.get(0).getMauvaisesReponses());
	}

	/**
	 * Teste la methode equals de la classe Question
	 * 
	 * @see {@link application.modele.Question#equals()}
	 * @throws InvalidNameException   si le nom est invalide
	 * @throws CreerQuestionException 
	 */
	@Test
	void testEquals() throws InvalidNameException, CreerQuestionException {
		// Creation de plusieurs question :
	    // - question1Egale qui est exactement parreile que questionValide[0]
		// - question2Egale qui est une copie de questionValide[1] mais sans feedback
		// - question3Egale qui est une copie de questionValide[2] mais avec une autre difficultee
		Question question1Egale = new Question("Quel est le delimiteur de debut " + "d'un commentaire Javadoc ?",
				categoriesValides[0], 1, "le delimiteur /**", mauvaiseReponse1, "");
		Question question2Egale = new Question("A quoi correspond l'expression : " + "@author Dupont ?",
				categoriesValides[0], 2, "une balise reconnue par Javadoc", mauvaiseReponse2, "");
		Question question3Egale = new Question("A quoi correspond l'expression : " + "@author Dupont ?",
				categoriesValides[0], 3, "une balise reconnue par Javadoc", mauvaiseReponse2, "");
		
		// 2 questions complement differentes
		assertNotEquals(questionValide.get(0), questionValide.get(1));

		// 2 questions identique
		assertEquals(questionValide.get(0), question1Egale);

		// 2 questions dont 1 feedback l'autre non
		assertEquals(question2Egale, questionValide.get(1));

		// 2 questions exactement pareil mais avec une difficultee differente
		assertEquals(question3Egale, questionValide.get(1));

		// 2 fois exactement la meme question
		assertEquals(question2Egale, question2Egale);

		// 1 question et un objet null
		assertNotEquals(questionValide.get(0), null);

		// 1 question et un objet d'une autre classe
		assertNotEquals(questionValide.get(0), new String("test"));
	}

	/**
	 * Teste la methode toString de la classe Question
	 * 
	 * @see {@link application.modele.Question#toString()}
	 */
	@Test
	void testToString() {
		// les resultats attendus de toString pour les deux premieres questions
		String valide = """
				Difficulté de la question : 1
				Categorie de la question : Commentaire
				Intiltulé de la question : Quel est le delimiteur de debut d'un commentaire Javadoc ?
				Mauvaise réponses :
				- le delimiteur /*
				- le delimiteur //
				- le delimiteur (*
				Bonne réponse : le delimiteur /**""";

		String valide2 = """
				Difficulté de la question : 2
				Categorie de la question : Commentaire
				Intiltulé de la question : A quoi correspond l'expression : @author Dupont ?
				Mauvaise réponses :
				- une facon de presenter le code choisie par le programmeur nomme Dupont
				- un texte sans signification particuliere
				Bonne réponse : une balise reconnue par Javadoc
				Feedback : Les balises Javadoc commencent par le  caractere @""";
		
		// On verifie que la methode toString ne renvoie pas d'exception 
		// et renvoie bien le resultat attendu 
		assertDoesNotThrow(() -> questionValide.get(0).toString());
		assertEquals(valide, questionValide.get(0).toString());

		// Pareil que le premier test mais avec une question 
		// qui a un feedback non vide
		assertDoesNotThrow(() -> questionValide.get(1).toString());
		assertEquals(valide2, questionValide.get(1).toString());
	}
}