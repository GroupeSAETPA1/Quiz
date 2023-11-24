/*
 * TestModelePrincipale.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import application.exception.CreerQuestionException;
import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;
import application.modele.BanqueCategorie;
import application.modele.BanqueQuestion;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Question;

/**
 * TODO comment class responsibility (SRP)
 * 
 * @author Lucas Descriaud
 * @author Tom Douaud
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestModelePrincipal {

	private ArrayList<Question> questionValide;
	private ArrayList<String> mauvaiseReponse1;
	private ArrayList<String> mauvaiseReponse2;
	private ArrayList<String> mauvaiseReponse3;
	private ArrayList<String> mauvaiseReponse4;
	private ArrayList<String> mauvaiseReponseDoublon;
	private Categorie[] categoriesValides = new Categorie[2];
	private ArrayList<String> mauvaiseReponseVide;
	private ArrayList<String> mauvaiseReponseJusteChaineVide;

	/**
	 * Genère des jeux de test pour les tests unitaires
	 * 
	 * @throws CreerQuestionException
	 * @throws InvalidNameException
	 */
	@BeforeEach
	void genererJeuxDeTest() throws CreerQuestionException, InvalidNameException {

		questionValide = new ArrayList<Question>();
		mauvaiseReponse1 = new ArrayList<String>();
		mauvaiseReponse2 = new ArrayList<String>();
		mauvaiseReponse3 = new ArrayList<String>();
		mauvaiseReponse4 = new ArrayList<String>();
		mauvaiseReponseVide = new ArrayList<String>();
		mauvaiseReponseDoublon = new ArrayList<String>();
		mauvaiseReponseJusteChaineVide = new ArrayList<String>();

		categoriesValides[0] = new Categorie("Commentaire");
		categoriesValides[1] = new Categorie("test");

		mauvaiseReponseJusteChaineVide.add("");
		mauvaiseReponseJusteChaineVide.add("");
		mauvaiseReponseJusteChaineVide.add("");
		mauvaiseReponseJusteChaineVide.add("");

		mauvaiseReponse1.add("le delimiteur /*");
		mauvaiseReponse1.add("le delimiteur //");
		mauvaiseReponse1.add("le delimiteur (*");

		mauvaiseReponse2.add("une façon de presenter le code choisie par " + "le programmeur nomme Dupont");
		mauvaiseReponse2.add("un texte sans signification particulière");

		mauvaiseReponse3.add(" // commentaire");

		mauvaiseReponse4.add("Un resume très bref, pas plus d'une ligne, " + "du rôle du programme");
		mauvaiseReponse4.add("Il n'y a pas de commentaire Javadoc " + "juste avant la ligne \"public class …\"");
		mauvaiseReponse4.add("Le nom du fichier contenant le programme");
		mauvaiseReponse4.add("Un texte libre laisse " + "a l'appreciation du programmeur");

		mauvaiseReponseDoublon.add("doublon");
		mauvaiseReponseDoublon.add("DOUBLON");
		mauvaiseReponseDoublon.add("doublon");
		mauvaiseReponseDoublon.add("DoUbLOn");

		questionValide.add(new Question("Quel est le delimiteur de debut " + "d'un commentaire Javadoc ?",
				categoriesValides[0], 1, "le delimiteur /**", mauvaiseReponse1, ""));
		questionValide.add(new Question("A quoi correspond l'expression : " + "@author Dupont ?", categoriesValides[0],
				2, "une balise reconnue par Javadoc", mauvaiseReponse2,
				"Les balises Javadoc commencent par le  caractère @"));
		questionValide.add(new Question(
				"Si un commentaire est ecrit " + "sur plusieurs lignes, quel delimiteur de "
						+ "commentaire est-il preferable d'utiliser ?",
				categoriesValides[0], 1, "/* commentaire */", mauvaiseReponse3, ""));

		questionValide.add(new Question(
				"Que doit decrire le texte ecrit dans " + "le commentaire Javadoc situe juste avant "
						+ "la ligne \"public class …\" ?",
				categoriesValides[1], 3, "Le rôle du programme, en explicitant " + "de manière precise ce rôle ",
				mauvaiseReponse4, ""));
	}

	/**
	 * Methode de test pour la methode getInstance
	 * @see {@link application.modele.ModelePrincipal#getInstance()}.
	 * 
	 * @throws InvalidNameException
	 */
	@Test
	@Order(1)
	void testGetInstance() throws InvalidNameException {
		// On verifie que deux objets de type ModelePrincipal qui sont 
		// egaux a l'instance du modelePrincipal sont les mêmes
		ModelePrincipal modele1 = ModelePrincipal.getInstance();
		ModelePrincipal modele2 = ModelePrincipal.getInstance();
		assertSame(modele1, modele2);
	}

	/**
	 * Methode de test pour la methode creerQuestion
	 * @see {@link application.modele.ModelePrincipal#creerQuestion(java.lang.String, application.modele.Categorie, int, java.lang.String, java.util.ArrayList, java.lang.String)}.
	 * 
	 * @throws ReponseException
	 * @throws InvalidFormatException
	 * @throws HomonymeException
	 * @throws DifficulteException
	 */
	@Test
	@Order(2)
	void testCreerQuestion() throws InvalidNameException, InvalidFormatException, ReponseException, HomonymeException,
			DifficulteException {
		ModelePrincipal modele = ModelePrincipal.getInstance();

		// Question de "reference", avec des attributs valides
		assertDoesNotThrow(() -> modele.creerQuestion("Quel est le delimiteur de " 
													+ "debut d'un commentaire Javadoc ", 
													   0, 
													   1,
													   "non vide", 
													   mauvaiseReponse1, 
													   ""));

		modele.creerCategorie("Autre Nom");

		// Test ajout de question avec seulement la categorie de differente
		assertDoesNotThrow(() -> modele.creerQuestion("Quel est le delimiteur de " 
													+ "debut d'un commentaire Javadoc ", 
													   1, 
													   1,
													  "non vide",
													  mauvaiseReponse1, 
													  ""));

		// Test ajout de question avec seulement le libelle de different
		assertDoesNotThrow(() -> modele.creerQuestion("libelle different", 
													   0, 
													   1, 
													   "non vide", 
													   mauvaiseReponse1, 
													   ""));

		// Test ajout de question avec seulement les mauvaise reponses de different
		assertDoesNotThrow(() -> modele.creerQuestion("Quel est le delimiteur de " 
													+ "debut d'un commentaire Javadoc ", 
													   0, 
													   1,
													  "non vide", 
													  mauvaiseReponse2, 
													  ""));

		// Test ajout de question avec seulement la bonne reponse de differente
		assertDoesNotThrow(() -> modele.creerQuestion("Quel est le delimiteur de " 
													+ "debut d'un commentaire Javadoc ", 
													   0, 
													   1,
													  "different", 
													  mauvaiseReponse1, 
													  ""));

		// Test de l'ajout d'une question deja existante, 
		// cela doit renvoyer l'exception "HomonymeException"
		assertThrows(HomonymeException.class,
				() -> modele.creerQuestion("Quel " 
										 + "est le delimiteur de debut d'un commentaire Javadoc ",
										    0, 
										    1,
										   "non vide", 
										   mauvaiseReponse1, 
										   ""));
		
		 // Verification de l'exception "InvalidNameException" propagee 
		 // si la question a creer est invalide
		 // (tous les paramètres sont faux, et a la première erreur, celle du libelle,
		 // l'exception "InvalidNameException" est lancee)
        assertThrows(InvalidNameException.class , 
        			()-> modele.creerQuestion("", -1, 1, "", mauvaiseReponseVide, ""));
        
		// Ajout avec une categorie inexistante, ce qui mets 
        // la question dans la categorie par defaut "General"
		assertTrue(modele.creerQuestion("Question inexistante", 0, 1, "non vide", mauvaiseReponse1, ""));
	}

	/**
	 * Methode de test pour la methode supprimerCategorie
	 * {@link application.modele.ModelePrincipal#supprimerCategorie(Categorie)}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(3)
	void testSupprimerCategorie() throws InvalidNameException, HomonymeException {
		// On recupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();

		// On cree une categorie "Truc" et on verifie qu'on puisse bien la supprimer
		Categorie uneCategorie = new Categorie("Truc");
		modele.creerCategorie(uneCategorie.getNom());
		// Suppression de la categorie Truc
		assertTrue(modele.supprimerCategorie(uneCategorie));

		// Vu que la categorie "Truc" n'existe plus, 
		// on ne peut plus la supprimer
		assertFalse(modele.supprimerCategorie(uneCategorie));

		// Le fait de rajouter une categorie "General" ne fait rien 
		// car "General" existe deja
		Categorie categorieGeneral = new Categorie("General");
		// On ne peut pas supprimer la categorie "General"
		assertFalse(modele.supprimerCategorie(categorieGeneral));

		// On verifie que l'exception "NullPointerException" est renvoyee 
		// si on tente de supprimer une categorie null
		assertThrows(NullPointerException.class, () -> modele.supprimerCategorie(null));
		
		// On ne peut pas supprimer une categorie qui n'existe pas dans
		// le modèle principal
		Categorie nonPresenteDansModele = new Categorie("non presente dans modele");
		assertFalse(modele.supprimerCategorie(nonPresenteDansModele));
	}

	/**
	 * Methode de test pour la methode categorieContientQuestion
	 * {@link application.modele.ModelePrincipal#categorieContientQuestion(Categorie)}.
	 * 
	 * @throws CreerQuestionException
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(4)
	void testCategorieContientQuestion() throws CreerQuestionException, InvalidNameException, HomonymeException {
		// On recupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();

		// On cree deux categories "Commentaire" et "test"
		modele.creerCategorie(categoriesValides[0].getNom());
		modele.creerCategorie(categoriesValides[1].getNom());

		// On mets des une question sur deux de la liste de questionValide
		// dans la categorie "Commentaire" et "test"
		int c = 1;
		for (Question q : questionValide) {
			modele.creerQuestion(q.getLibelle(), c % 2 + 2, q.getDifficulte(), q.getReponseJuste(), mauvaiseReponse1,
					"");
			c++;
		}

		// On verifie qu'il existe bien des questions presentes dans les deux categories
		assertTrue(modele.categorieContientQuestion(categoriesValides[0]));
		assertTrue(modele.categorieContientQuestion(categoriesValides[1]));

		// On cree une nouvelle categorie sans question
		Categorie uneAutre = new Categorie("UneNouvelleUnique");
		modele.creerCategorie(uneAutre.getNom());
		// La categorie n'a pas de question, donc la categorieContientQuestion renvoie false
		assertFalse(modele.categorieContientQuestion(uneAutre));

		// On verifie qu'après avoir creer une question dans la categorie uneAutre,
		// categorieContientQuestion devient vrai car maintenant cette categorie 
		// contient bien des questions
		int indexCategorieuneAutre = indexOf(uneAutre, modele.getCategories());
		modele.creerQuestion(
				"Que doit decrire le texte ecrit dans le commentaire Javadoc "
				+ "situe juste avant la ligne \"public class ...\" ?",
				indexCategorieuneAutre, 
				1, 
				"Le rôle du programme, en explicitant de manière precise" 
				+ " ce rôle", 
				mauvaiseReponse1, 
				"");
		assertTrue(modele.categorieContientQuestion(uneAutre));
	}
	
	/**
	 * Methode de test pour la methode categorieExiste
	 * {@link application.modele.ModelePrincipal#categorieExiste(String)}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(5)
	void testCategorieExiste() throws InvalidNameException, HomonymeException {
		// On recupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();
		
		// On verifie que la categorie par defaut "General" existe bien
		assertTrue(modele.categorieExiste("General"));
		
		// On cree une nouvelle categorie et on verifie 
		// qu'elle existe dans le modèle principal
		modele.creerCategorie("Une categorie rajoutee");
		assertTrue(modele.categorieExiste("Une categorie rajoutee"));

		// On verifie que categories non presente dans le modèle principal 
		// ou des categories null renvoie bien false
		assertFalse(modele.categorieExiste("Categorie inexistante"));
		assertFalse(modele.categorieExiste(null));
	}
	
	/**
	 * Methode de test pour la methode getBanqueCategorie
	 * {@link application.modele.ModelePrincipal#getBanqueCategorie()}.
	 *  
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(6)
	void testGetBanqueCategorie() throws InvalidNameException, HomonymeException {
		// On recupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();
		
		// On verifie que quand on recupère la banque de categorie 
		// du modèle principal, on obtient les mêmes categories 
		// que celle de la banque de categorie par defaut 
		// plus les categories rajoutes lors des autres test
		BanqueCategorie banqueTest = new BanqueCategorie();
		banqueTest.ajouter(new Categorie("Autre Nom"));
		banqueTest.ajouter(categoriesValides[0]);
		banqueTest.ajouter(categoriesValides[1]);
		banqueTest.ajouter(new Categorie("UneNouvelleUnique"));
		banqueTest.ajouter(new Categorie("Une categorie rajoutee"));
		for (int i = 0; i < modele.getBanqueCategorie().getCategories().size(); i++) {
			assertEquals(banqueTest.getCategorie(i), modele.getBanqueCategorie().getCategorie(i));	
		}
	}
	
	@Test
	@Order(7)
	void testGetBanqueQuestion() throws InvalidNameException, HomonymeException, InvalidFormatException, ReponseException, DifficulteException {
		// On recupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();
		
		/*
		 * Pour pouvoir tester la methode, on doit recreer
		 * toute la banque de questions qui ont etes crees durant 
		 * tous les tests precendents
		 */ 
		BanqueQuestion banqueTestQuestions = new BanqueQuestion();
		banqueTestQuestions.ajouter(new Question("Quel est le delimiteur de " 
											   + "debut d'un commentaire Javadoc ", 
											  	  modele.getCategories().get(0), 
											  	  1,
											  	  "non vide", 
											  	  mauvaiseReponse1, 
												  ""));
		
		banqueTestQuestions.ajouter(new Question("Quel est le delimiteur de " 
											   + "debut d'un commentaire Javadoc ", 
											  	  modele.getCategories().get(1), 
											  	  1,
											  	 "non vide",
											  	  mauvaiseReponse1, 
				  								  ""));
		
		banqueTestQuestions.ajouter(new Question("libelle different", 
												  modele.getCategories().get(0), 
												  1, 
									      		 "non vide", 
									      		  mauvaiseReponse1, 
				   								  ""));
		
		banqueTestQuestions.ajouter(new Question("Quel est le delimiteur de " 
											   + "debut d'un commentaire Javadoc ", 
											   	  modele.getCategories().get(0),  
											   	  1,
											   	 "non vide", 
											   	  mauvaiseReponse2, 
				  								  ""));
		
		banqueTestQuestions.ajouter(new Question("Quel est le delimiteur de " 
											   + "debut d'un commentaire Javadoc ", 
											   	  modele.getCategories().get(0), 
											   	  1,
											   	 "different", 
											   	  mauvaiseReponse1, 
				  								  ""));
		
		banqueTestQuestions.ajouter(new Question("Question inexistante", 
											      modele.getCategories().get(0), 
											      1, 
											     "non vide", 
											      mauvaiseReponse1, 
												  ""));
		
		int c = 1;
		for (Question q : questionValide) {
			banqueTestQuestions.ajouter(new Question(q.getLibelle(), modele.getCategories().get(c % 2 + 2), q.getDifficulte(), q.getReponseJuste(), mauvaiseReponse1,
					""));
			c++;
		}
		
		/*
		 * Fin de la reconstitution de la banque de question avec 
		 * toutes les questions des test precedents
		 */
		
		// On verifie que pour chaque question de la banque de question reconstituee,
		// la version du modèle principal est pareille
		for (int i = 0; i < modele.getBanqueCategorie().getCategories().size(); i++) {
			assertEquals(banqueTestQuestions.getQuestion(i), modele.getBanqueQuestion().getQuestion(i));	
		}		
	}

	/**
	 * Renvoie l'indice de la categorie dans une liste
	 * 
	 * @param categorie
	 * @param categories
	 * @return
	 */
	private int indexOf(Categorie categorie, ArrayList<Categorie> categories) {
		// TODO Auto-generated method stub
		int i = 0;
		for (Categorie categorieListe : categories) {
			if (categorieListe.equals(categorie))
				return i;
			i++;
		}
		return -1;
	}
	
	@Test
	@Order(8)
	public void testAlphabetOk() {
		assertTrue(ModelePrincipal.alphabetOk("pas de caracteres non contenue dans l'alphabet"));
		assertFalse(ModelePrincipal.alphabetOk("caracteres non contenue dans l'alphabet : éàç"));
	}

}
