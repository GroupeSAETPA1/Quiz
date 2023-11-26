/*
 * TestModelePrincipale.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import application.exception.CreerQuestionException;
import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;
import application.modele.BanqueCategorie;
import application.modele.BanqueQuestion;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Partie;
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
	 * Génère des jeux de test pour les tests unitaires
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
		mauvaiseReponseDoublon.add("doublon");
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
				categoriesValides[1], 3, "Le rôle du programme, en explicitant " + "de manière précise ce rôle ",
				mauvaiseReponse4, ""));
	}

	/**
	 * Méthode de test pour les méthodes serialiser, deSerialiserCategorie, deSerialiserQuestion
	 * @see {@link application.modele.ModelePrincipal#serialiser()}.
	 * @see {@link application.modele.ModelePrincipal#deSerialiserCategorie()}.
	 * @see {@link application.modele.ModelePrincipal#deSerialiserQuestion()}.
	 */
	@Test
	@Order(1)
	void testSerialisation() {
		// On vérifie que la serialisation et la dé-serialisation du modèle 
		// est bien effectuée et valide
		ModelePrincipal modele = ModelePrincipal.getInstance();
		assertIterableEquals(modele.getBanqueCategorie().getCategories(), modele.deSerialiserCategorie().getCategories());
		assertIterableEquals(modele.getBanqueQuestion().getQuestions(), modele.deSerialiserQuestion().getQuestions());
		// On vérifie que sérialiser ne renvoie pas d'erreur
		assertDoesNotThrow(() -> modele.serialiser());
	}
	
	/**
	 * Méthode pour que les autres tests 
	 * ne soient pas influencés par la sérialisation
	 * @throws InvalidNameException 
	 */
	@Test
	@Order(2)
	void reinitialiserModele() throws InvalidNameException {
		// On récupère le modele principal et on supprime toutes ses questions et categories
		ModelePrincipal modele = ModelePrincipal.getInstance();
		modele.getBanqueCategorie().getCategories().clear();
		modele.getBanqueQuestion().getQuestions().clear();
		
		// On remets juste la catégorie general
		modele.getBanqueCategorie().getCategories().add(new Categorie("General"));
	}
	
	/**
	 * Méthode de test pour la méthode getInstance
	 * @see {@link application.modele.ModelePrincipal#getInstance()}.
	 * 
	 * @throws InvalidNameException
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(3)
	void testGetInstance() throws InvalidNameException, ClassNotFoundException, InternalError, IOException {
		// On vérifie que deux objets de type ModelePrincipal qui sont 
		// égaux à l'instance du modelePrincipal sont les mêmes
		ModelePrincipal modele1 = ModelePrincipal.getInstance();
		ModelePrincipal modele2 = ModelePrincipal.getInstance();
		assertSame(modele1, modele2);
	}

	/**
	 * Méthode de test pour la méthode creerQuestion
	 * @see {@link application.modele.ModelePrincipal#creerQuestion(java.lang.String, application.modele.Categorie, int, java.lang.String, java.util.ArrayList, java.lang.String)}.
	 * 
	 * @throws HomonymeException
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 * @throws CreerQuestionException 
	 */
	@Test
	@Order(4)
	void testCreerQuestion() throws InvalidNameException, HomonymeException,
			ClassNotFoundException, InternalError, IOException, CreerQuestionException {
		ModelePrincipal modele = ModelePrincipal.getInstance();
		
		// Question de "reference", avec des attributs valides
		assertDoesNotThrow(() -> modele.creerQuestion("Quel est le délimiteur de " 
													+ "début d'un commentaire Javadoc ", 
													   0, 
													   1,
													   "non vide", 
													   mauvaiseReponse1, 
													   ""));
		
		modele.creerCategorie("Autre Nom");
		
		// Test ajout de question avec seulement la catégorie de différente
		assertDoesNotThrow(() -> modele.creerQuestion("Quel est le délimiteur de " 
													+ "début d'un commentaire Javadoc ", 
													   1, 
													   1,
													  "non vide",
													  mauvaiseReponse1, 
													  ""));


		// Test ajout de question avec seulement le libellé de différent
		assertDoesNotThrow(() -> modele.creerQuestion("libelle different", 
													   0, 
													   1, 
													   "non vide", 
													   mauvaiseReponse1, 
													   ""));

		// Test ajout de question avec seulement les mauvaise réponses de différent
		assertDoesNotThrow(() -> modele.creerQuestion("Quel est le délimiteur de " 
													+ "début d'un commentaire Javadoc ", 
													   0, 
													   1,
													  "non vide", 
													  mauvaiseReponse2, 
													  ""));

		// Test ajout de question avec seulement la bonne réponse de différente
		assertDoesNotThrow(() -> modele.creerQuestion("Quel est le délimiteur de " 
													+ "début d'un commentaire Javadoc ", 
													   0, 
													   1,
													  "different", 
													  mauvaiseReponse1, 
													  ""));

		// Test de l'ajout d'une question déjà existante, 
		// cela doit renvoyer l'exception "HomonymeException"
		assertThrows(HomonymeException.class,
				() -> modele.creerQuestion("Quel " 
										 + "est le délimiteur de début d'un commentaire Javadoc ",
										    0, 
										    1,
										   "non vide", 
										   mauvaiseReponse1, 
										   ""));
		
		 // Vérification de l'exception "InvalidNameException" propagée 
		 // si la question à créer est invalide
		 // (tous les paramètres sont faux, et à la première erreur, celle du libellé,
		 // l'exception "InvalidNameException" est lancée)
        assertThrows(InvalidNameException.class , 
        			()-> modele.creerQuestion("", -1, 1, "", mauvaiseReponseVide, ""));
        
		// Ajout avec une catégorie inexistante, ce qui mets 
        // la question dans la catégorie par défaut "General"
		assertTrue(modele.creerQuestion("Question inexistante", 0, 1, "non vide", mauvaiseReponse1, ""));
	}

	/**
	 * Méthode de test pour la méthode supprimerCategorie
	 * @see {@link application.modele.ModelePrincipal#supprimerCategorie(Categorie)}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 * @throws CreerQuestionException 
	 */
	@Test
	@Order(5)
	void testSupprimerCategorie() throws InvalidNameException, HomonymeException, ClassNotFoundException, InternalError, IOException, CreerQuestionException {
		// On récupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();

		// On crée une catégorie "Truc" et on vérifie qu'on puisse bien la supprimer
		Categorie uneCategorie = new Categorie("Truc");
		modele.creerCategorie(uneCategorie.getNom());
		// Suppression de la catégorie Truc
		assertTrue(modele.supprimerCategorie(uneCategorie));

		// Vu que la catégorie "Truc" n'existe plus, 
		// on ne peut plus la supprimer
		assertFalse(modele.supprimerCategorie(uneCategorie));
		
		// On vérifie que supprimer une catégorie avec des question 
		// supprime les questions de cette catégorie
		modele.creerCategorie("categorieASupprimer");
		modele.creerQuestion("Question à supprimer", 
							  modele.getIndice("categorieASupprimer"), 
							  1, 
							  "reponse vrai", 
							  mauvaiseReponse1, 
							  "");
		
		assertTrue(modele.supprimerCategorie(modele.getCategoriesLibelleExact("categorieASupprimer")));
		assertEquals(new ArrayList<Question>(), modele.getBanqueQuestion().getQuestionsLibelle("Question à supprimer"));
		
		// Le fait de rajouter une catégorie "General" ne fait rien 
		// car "General" existe déjà
		Categorie categorieGeneral = new Categorie("General");
		// On ne peut pas supprimer la catégorie "General"
		assertFalse(modele.supprimerCategorie(categorieGeneral));

		// On vérifie que l'exception "NullPointerException" est renvoyée 
		// si on tente de supprimer une catégorie null
		assertThrows(NullPointerException.class, () -> modele.supprimerCategorie(null));
		
		// On ne peut pas supprimer une catégorie qui n'existe pas dans
		// le modèle principal
		Categorie nonPresenteDansModele = new Categorie("non présente dans modèle");
		assertFalse(modele.supprimerCategorie(nonPresenteDansModele));
		
	}

	/**
	 * Méthode de test pour la méthode categorieContientQuestion
	 * @see {@link application.modele.ModelePrincipal#categorieContientQuestion(Categorie)}.
	 * 
	 * @throws CreerQuestionException
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(6)
	void testCategorieContientQuestion() throws CreerQuestionException, InvalidNameException, HomonymeException, ClassNotFoundException, InternalError, IOException {
		// On récupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();

		// On crée deux catégories "Commentaire" et "test"
		modele.creerCategorie(categoriesValides[0].getNom());
		modele.creerCategorie(categoriesValides[1].getNom());

		// On mets des une question sur deux de la liste de questionValide
		// dans la catégorie "Commentaire" et "test"
		
		int c = 1;
		for (Question q : questionValide) {
			modele.creerQuestion(q.getLibelle(), c % 2 + 2, q.getDifficulte(), q.getReponseJuste(), mauvaiseReponse1,
					"");
			c++;
		}
		
		// On vérifie qu'il existe bien des questions présentes dans les deux catégories
		assertTrue(modele.categorieContientQuestion(categoriesValides[0]));
		assertTrue(modele.categorieContientQuestion(categoriesValides[1]));

		// On crée une nouvelle catégorie sans question
		Categorie uneAutre = new Categorie("UneNouvelleUnique");
		modele.creerCategorie(uneAutre.getNom());
		// La catégorie n'a pas de question, donc la categorieContientQuestion renvoie false
		assertFalse(modele.categorieContientQuestion(uneAutre));

		// On vérifie qu'après avoir créer une question dans la catégorie uneAutre,
		// categorieContientQuestion devient vrai car maintenant cette catégorie 
		// contient bien des questions
		int indexCategorieuneAutre = indexOf(uneAutre, modele.getCategories());
		modele.creerQuestion(
				"Que doit décrire le texte écrit dans le commentaire Javadoc "
				+ "situé juste avant la ligne \"public class ...\" ?",
				indexCategorieuneAutre, 
				1, 
				"Le rôle du programme, en explicitant de manière précise" 
				+ " ce rôle", 
				mauvaiseReponse1, 
				"");
		assertTrue(modele.categorieContientQuestion(uneAutre));
	}
	
	/**
	 * Méthode de test pour la méthode categorieExiste
	 * @see {@link application.modele.ModelePrincipal#categorieExiste(String)}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(7)
	void testCategorieExiste() throws InvalidNameException, HomonymeException, ClassNotFoundException, InternalError, IOException {
		// On récupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();
		
		// On vérifie que la catégorie par défaut "General" existe bien
		assertTrue(modele.categorieExiste("General"));
		
		// On crée une nouvelle catégorie et on vérifie 
		// qu'elle existe dans le modèle principal
		modele.creerCategorie("Une catégorie rajoutée");
		assertTrue(modele.categorieExiste("Une catégorie rajoutée"));

		// On vérifie que catégories non présente dans le modèle principal 
		// ou des catégories null renvoie bien false
		assertFalse(modele.categorieExiste("Catégorie inexistante"));
		assertFalse(modele.categorieExiste(null));
	}
	
	/**
	 * Méthode de test pour la méthode getBanqueCategorie
	 * @see {@link application.modele.ModelePrincipal#getBanqueCategorie()}.
	 *  
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(8)
	void testGetBanqueCategorie() throws ClassNotFoundException, InternalError, IOException, HomonymeException, InvalidNameException  {
		// On récupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();
		
		// On vérifie que quand on récupère la banque de catégorie 
		// du modèle principal, on obtient les mêmes catégories 
		// que celle de la banque de catégorie par défaut 
		// plus les catégories rajoutés lors des autres test
		BanqueCategorie banqueTest = new BanqueCategorie();
		banqueTest.ajouter(new Categorie("Autre Nom"));
		banqueTest.ajouter(categoriesValides[0]);
		banqueTest.ajouter(categoriesValides[1]);
		banqueTest.ajouter(new Categorie("UneNouvelleUnique"));
		banqueTest.ajouter(new Categorie("Une catégorie rajoutée"));
		for (int i = 0; i < modele.getBanqueCategorie().getCategories().size(); i++) {
			assertEquals(banqueTest.getCategorie(i), modele.getBanqueCategorie().getCategorie(i));	
		}
	}
	
	/**
	 * Méthode de test pour la méthode getBanqueQuestion
	 * @see {@link application.modele.ModelePrincipal#getBanqueQuestion()}.
	 * @throws ClassNotFoundException
	 * @throws InternalError
	 * @throws IOException
	 * @throws HomonymeException
	 * @throws InvalidNameException
	 * @throws CreerQuestionException
	 */
	@Test
	@Order(9)
	void testGetBanqueQuestion() throws ClassNotFoundException, InternalError, IOException, HomonymeException, InvalidNameException, CreerQuestionException  {
		// On récupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();
		
		/*
		 * Pour pouvoir tester la méthode, on doit recréer
		 * toute la banque de questions qui ont étés créés durant 
		 * tous les tests précendents
		 */ 
		BanqueQuestion banqueTestQuestions = new BanqueQuestion();
		banqueTestQuestions.ajouter(new Question("Quel est le délimiteur de " 
											   + "début d'un commentaire Javadoc ", 
											  	  modele.getCategories().get(0), 
											  	  1,
											  	  "non vide", 
											  	  mauvaiseReponse1, 
												  ""));
		
		banqueTestQuestions.ajouter(new Question("Quel est le délimiteur de " 
											   + "début d'un commentaire Javadoc ", 
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
		
		banqueTestQuestions.ajouter(new Question("Quel est le délimiteur de " 
											   + "début d'un commentaire Javadoc ", 
											   	  modele.getCategories().get(0),  
											   	  1,
											   	 "non vide", 
											   	  mauvaiseReponse2, 
				  								  ""));
		
		banqueTestQuestions.ajouter(new Question("Quel est le délimiteur de " 
											   + "début d'un commentaire Javadoc ", 
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
		 * toutes les questions des test précédents
		 */
		
		// On vérifie que pour chaque question de la banque de question reconstituée,
		// la version du modèle principal est pareille
		for (int i = 0; i < modele.getBanqueCategorie().getCategories().size(); i++) {
			assertEquals(banqueTestQuestions.getQuestion(i), modele.getBanqueQuestion().getQuestion(i));	
		}		
	}
	
	/**
	 * Méthode de test pour la méthode getCategorieAModifier
	 * @see {@link application.modele.ModelePrincipal#getCategorieAModifier()}.
	 * 
	 * @throws InvalidNameException
	 */
	@Test
	@Order(10)
	void testGetCategorieAModifier() throws InvalidNameException {
	    // On récupère l'instance du modèle principal et on crée une catégorie
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    Categorie a = new Categorie("categorie");
		    
	    // On mets cette catégorie à modifier et 
	    // on vérifie qu'on ait bien la catégorie "categorie" qui est à modifier
	    modele.setCategorieAModifier(a);
	    assertEquals(a, modele.getCategorieAModifier());
	}
	
	/**
	 * Méthode de test pour la méthode getCategoriesLibelle
	 * @see {@link application.modele.ModelePrincipal#getCategoriesLibelle()}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(11)
	void testGetCategoriesLibelle() throws InvalidNameException, HomonymeException {
	    // On récupère l'instance du modèle principal 
	    // et de la banque de catégories du modele principal et on vide cette banque
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    BanqueCategorie banque = modele.getBanqueCategorie();
		    
	    banque.getCategories().clear();
		    
	    // On rajoute 3 Catégories dans la banque de catégorie du modèle principal
	    Categorie a1 = new Categorie("a"); 
	    Categorie a2 = new Categorie("aa");
	    Categorie a3 = new Categorie("b");
		    
	    modele.creerCategorie(a1.getNom());
	    modele.creerCategorie(a2.getNom());
	    modele.creerCategorie(a3.getNom());
		    
	    ArrayList<Categorie> attendu = new ArrayList<Categorie>();
	    
	    attendu.add(a1);
	    attendu.add(a2);
		    
	    // On vérifie que la méthode renvoie deux catégories quand on recherche "a"
	    // car deux catégories contiennent "a" dans leur libellé
	    assertIterableEquals(attendu, modele.getCategoriesLibelle("a"));
	}
	
	/**
	 * Méthode de test pour la méthode getIndice
	 * @see {@link application.modele.ModelePrincipal#getIndice()}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(12)
	void testGetIndice() throws InvalidNameException, HomonymeException {
	    // On récupère l'instance du modèle principal 
	    // et de la banque de catégories du modele principal et on vide cette banque
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    BanqueCategorie banque = modele.getBanqueCategorie();

	    banque.getCategories().clear();

	    // On rajoute 3 Catégories dans la banque de catégorie du modèle principal
	    Categorie a1 = new Categorie("a");
	    Categorie a2 = new Categorie("aa");
	    Categorie a3 = new Categorie("b");

	    modele.creerCategorie(a1.getNom());
	    modele.creerCategorie(a2.getNom());
	    modele.creerCategorie(a3.getNom());

	    // On vérifie que la catégorie "aa" qui est en deuxième position 
	    // renvoie bien 1 car la position 0 est pour "a"
	    assertEquals(1, modele.getIndice("aa"));
	}
	
	/**
	 * Méthode de test pour la méthode getNombreQuestionCategorie
	 * @see {@link application.modele.ModelePrincipal#getNombreQuestionCategorie(Categorie)}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 * @throws CreerQuestionException
	 */
	@Test
	@Order(13)
	void testGetNombreQuestionCategorie() throws InvalidNameException, HomonymeException, CreerQuestionException {
	    // On récupère l'instance du modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    // On crée une catégorie avec 3 questions
	    Categorie categorie3question = new Categorie("Categorie avec 3 questions");
	    modele.creerCategorie(categorie3question.getNom());
	    modele.creerQuestion("question 1", 3, 1, "juste", mauvaiseReponse1, "");
	    modele.creerQuestion("question 2", 3, 1, "juste", mauvaiseReponse1, "");
	    modele.creerQuestion("question 3", 3, 1, "juste", mauvaiseReponse1, "");
	    
	    // On vérifie que la catégorie possède bien 3 questions
	    assertEquals(3, modele.getNombreQuestionCategorie(categorie3question));
	}

	/**
     * Méthode de test pour la méthode getPagePrecendente et setPagePrecedente
	 * @see {@link application.modele.ModelePrincipal#getPagePrecendente()}.
	 * @see {@link application.modele.ModelePrincipal#setPagePrecendente()}.
	 */
	@Test
	@Order(14)
	void testPagePrecedente() {
		// On récupere le modèle principal et on vérifie que par défault, 
		// il n'y a pas de page précédente (null) 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
		assertNull(modele.getPagePrecendente());
		
		// On définit la page précedente et on vérifie que 
		// les modifications sont bien effectives
		modele.setPagePrecedente("TestPagePrecedente");
		assertEquals("TestPagePrecedente", modele.getPagePrecendente());
	}
	
	/**
	 * Méthode de test pour la méthode setPartie et getPartie
	 * @see {@link application.modele.ModelePrincipal#getPartie()}.
	 * @see {@link application.modele.ModelePrincipal#setPartie()}.
	 */
	@Test
	@Order(15)
	void testPartie() {
		// On récupère le modèle principal et on vérifie 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    // On set la partie du modèle et on vérifie 
	    // que la partie est bien celle mise en paramètre
		Partie partieTest = new Partie();
		modele.setPartie(partieTest);
		assertEquals(partieTest, modele.getPartie());
	}
	
	/**
	 * Méthode de test pour la méthode setQuestionAModifier et getQuestionAModifier
	 * @see {@link application.modele.ModelePrincipal#setQuestionAModifier()}.
	 * @see {@link application.modele.ModelePrincipal#getQuestionAModifier()}.
	 * 
	 * @throws CreerQuestionException
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(16)
	void testQuestionsAModifier() throws CreerQuestionException, InvalidNameException, HomonymeException {
		// On récupère le modèle principal et on vérifie qu'au départ 
		// il n'y a pas de question à modifier 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    assertNull(modele.getQuestionAModifier());
	    
	    // On crée une question, on l'ajoute au modèle et on la définit 
	    // comme question à modifier
	    
	    // Pour passer toutes les conditions de ajouter question,
	    // on ajoute une question avec une nouvelle catégorie
		Categorie categorieNonPresente = new Categorie("Catégorie non présente");

	    Question questionAModifier = new Question("Question a modifier", 
	    										   categorieNonPresente, 
				  	  				               1,
				  	  				               "non vide", 
				  	  				               mauvaiseReponse1, 
					  							   "");
	    modele.ajouterQuestion(questionAModifier);
	    modele.setQuestionAModifier(questionAModifier);
	    
	    // On vérifie que la question à modifier est bien la question créé auparavant
		assertEquals(questionAModifier, modele.getQuestionAModifier());
	}
	
	/**
	 * Méthode de test pour la méthode getCategoriesLibelleExact 
	 * @see {@link application.modele.ModelePrincipal#getCategoriesLibelleExact()}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(17)
	void testGetCategoriesLibelleExact() throws InvalidNameException, HomonymeException {
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    Categorie categorieLibelleExact = new Categorie("LibelleExact");
	    
	    // On crée une catégorie "LibelleExact" qu'on mets dans le modèle principal et 
	    // on vérifie qu'on récupère bien la catégorie recherchée avec "LibelleExact"
	    modele.creerCategorie("LibelleExact");
	    
	    assertEquals(categorieLibelleExact.getNom(), modele.getCategoriesLibelleExact("LibelleExact").getNom());
	}
	
	/**
	 * Méthode de test pour la méthode setDisplayCategoriePane et isDisplayCategoriePane
	 * @see {@link application.modele.ModelePrincipal#setDisplayCategoriePane()}.
	 * @see {@link application.modele.ModelePrincipal#isDisplayCategoriePane()}.
	 */
	@Test
	@Order(18)
	void testDisplayCategoriePane() {
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    // On vérifie que par défaut le booléen est faux
	    assertFalse(modele.isDisplayCategoriePane());
	    
	    // On vérifie qu'en le passant à true, la modification est bien prise en compte
	    modele.setDisplayCategoriePane(true);
	    assertTrue(modele.isDisplayCategoriePane());
	}
	
	/**
	 * Méthode de test pour la méthode modifierCategorie
	 * @see {@link application.modele.ModelePrincipal#modifierCategorie()}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(19)
	void testModifierCategorie() throws InvalidNameException, HomonymeException {
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    // On modifie la première catégorie "a" par "NouveauNom" et 
	    // on vérifie que la catégorie est bien modifiée
	    modele.setCategorieAModifier(modele.getBanqueCategorie().getCategorie(0));
	    modele.modifierCategorie("NouveauNom");
	    assertEquals("NouveauNom", modele.getCategories().get(0).getNom());
	    
	    // On vérifie la cas ou on essaye de modifier une catégorie par un 
	    // nom de catégorie qui existe déjà dans la banque de catégorie
	    assertThrows(HomonymeException.class,
					 () -> modele.modifierCategorie("aa"));
	}
	
	/**
	 * Méthode de test pour la méthode modifierQuestion
	 * @see {@link application.modele.ModelePrincipal#modifierQuestion()}.
	 */
	@Test
	@Order(20)
	void testModifierQuestion() {
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    // On choisit la première question à modifier
	    modele.setQuestionAModifier(modele.getBanqueQuestion().getQuestion(0));
	    
	    // On vérifie qu'on puisse bien modifier une question 
	    // avec des arguments valides et si la catégorie n'existe 
	    // pas dans la banque de catégorie on peut la créer
	    assertTrue(modele.modifierQuestion("NouvelleQuestion", 
	    								   "General", 
	    								    2, 
	    								   "NouvelleBonneRéponse", 
	    								    mauvaiseReponse1, 
	    								    ""));
	    assertTrue(modele.modifierQuestion("NouvelleQuestion2", 
	    		                           "Catégorie existait pas encore", 
	    		                            2, 
	    		                           "NouvelleBonneRéponse", 
	    		                            mauvaiseReponse1, 
	    		                           ""));
	    
	    // On vérifie que si les arguments ne sont pas valides la question n'est pas modifiée
	    assertFalse(modele.modifierQuestion("NouvelleQuestionInvalide", 
	    		                            "General", 
	    		                             0, 
	    		                            "NouvelleBonneRéponse", 
	    		                             mauvaiseReponse1, 
	    		                             ""));
	    assertFalse(modele.modifierQuestion("NouvelleQuestionInvalide", 
                                            "", 
                                             1, 
                                            "NouvelleBonneRéponse", 
                                             mauvaiseReponse1, 
                                            ""));
	}
	
	/**
	 * Méthode de test pour la méthode modifierQuestion
	 * @see {@link application.modele.ModelePrincipal#modifierQuestion()}.
	 * 
	 * @throws DifficulteException
	 */
	@Test
	@Order(21)
	void testSetDifficultePartie() throws DifficulteException {
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    // On vérifie que modifier la difficultée avec une 
	    // difficultée valide modifie bien la difficultée de la partie
	    modele.setDifficultePartie(0);
	    assertEquals(0, modele.getPartie().getDifficultePartie());
	    
	    modele.setDifficultePartie(2);
	    assertEquals(2, modele.getPartie().getDifficultePartie());
	    
	    // On vérifie que modifier une difficultée par une difficultée invalide 
	    // (inférieure à 0 ou supérieur à 3)
	    // renvoie bien l'excpetion "DifficulteException"
	    assertThrows(DifficulteException.class,
				     () -> modele.setDifficultePartie(4));
	    assertThrows(DifficulteException.class,
			         () -> modele.setDifficultePartie(-1));
	}
	
	/**
	 * Méthode de test pour la méthode supprimerQuestion
	 * @see {@link application.modele.ModelePrincipal#supprimerQuestion()}.
	 * 
	 * @throws CreerQuestionException
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(22)
	void testSupprimerQuestion() throws CreerQuestionException, InvalidNameException, HomonymeException {
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
		
	    // On crée une question a supprimer et on vérifie 
	    // que la méthode supprimerQuestion renvoie bien vrai
	    modele.creerQuestion("QuestionTestSupprimerQuestion", 
	    					  0, 
	    					  1, 
	    					 "vrai", 
	    					  mauvaiseReponse1, 
	    					 "");
		assertTrue(modele.supprimerQuestion(modele.getBanqueQuestion()
											.getQuestionsLibelle("QuestionTestSupprimerQuestion")
											.get(0)));
	}
	
	/**
	 * Méthode de test pour la méthode ajouterALaSelectionDEnvoie et estSelectionner
	 * @see {@link application.modele.ModelePrincipal#ajouterALaSelectionDEnvoie(Categorie)}.
	 * @see {@link application.modele.ModelePrincipal#ajouterALaSelectionDEnvoie(Question)}.
	 * @see {@link application.modele.ModelePrincipal#estAEnvoyer(Categorie)}.
	 * @see {@link application.modele.ModelePrincipal#estAEnvoyer(Question)}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 * @throws CreerQuestionException
	 */
	@Test
	@Order(23)
	void testAjouterALaSelectionDEnvoie() throws InvalidNameException, HomonymeException, CreerQuestionException {
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    // On crée une catégorie "Categorie à envoyer" et 
	    // on y ajoute une question pour vérifier
	    // que quand on ajoute une catégorie a envoyer, 
	    // cela ajoute aussi les questions de cette catégorie
	    modele.creerCategorie("Categorie à envoyer");
	    modele.creerQuestion("QuestionTestAEnvoyer", 
	    					  modele.getIndice("Categorie à envoyer"), 
	    					  1, 
	    					 "vrai", 
	    					  mauvaiseReponse1, 
				 			 "");
	    modele.ajouterALaSelectionDEnvoie(modele.getCategoriesLibelleExact
	    								  ("Categorie à envoyer"));
	    assertTrue(modele.estAEnvoyer(modele.getCategoriesLibelleExact
	    								  ("Categorie à envoyer")));
	    assertTrue(modele.estAEnvoyer((modele.getBanqueQuestion()
	    								   .getQuestionsLibelle
	    								   ("QuestionTestAEnvoyer").get(0))));
	    
	    // On vérifie que quand on crée une question et 
	    // qu'on l'ajoute à la sélection pour envoyer la question est 
	    // bien dans la liste
	    modele.creerQuestion("QuestionTestAEnvoyer2", 
	    					  0, 
	    					  1, 
	    					 "vrai", 
	    					  mauvaiseReponse1, 
	 			 			 "");
	    modele.ajouterALaSelectionDEnvoie(modele.getBanqueQuestion()
	    								  .getQuestionsLibelle
	    								  ("QuestionTestAEnvoyer2").get(0));
	    assertTrue(modele.estAEnvoyer((modele.getBanqueQuestion()
	    								   .getQuestionsLibelle
	    								   ("QuestionTestAEnvoyer2").get(0))));
	}
	
	/**
	 * Méthode de test pour la méthode supprimerALaSelectionDEnvoie
	 * @see {@link application.modele.ModelePrincipal#supprimerALaSelectionDEnvoie(Categorie)}.
	 * @see {@link application.modele.ModelePrincipal#supprimerALaSelectionDEnvoie(Question)}.
	 */
	@Test
	@Order(24)
	void testSupprimerALaSelectionDEnvoie() {
		/*
		 * Ce test fait le contraire du test précédent, 
		 * on supprime les questions et les catégories de la liste 
		 * et on vérifie qu'elles y sont plus 
		 */
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    modele.supprimerALaSelectionDEnvoie(modele.getCategoriesLibelleExact
				  							("Categorie à envoyer"));
	    assertFalse(modele.estAEnvoyer(modele.getCategoriesLibelleExact
				                      ("Categorie à envoyer")));
	    assertFalse(modele.estAEnvoyer((modele.getBanqueQuestion()
				                        .getQuestionsLibelle
				                        ("QuestionTestAEnvoyer").get(0))));

	    modele.supprimerALaSelectionDEnvoie(modele.getBanqueQuestion()
				  							.getQuestionsLibelle
				  							("QuestionTestAEnvoyer2").get(0));
	    assertFalse(modele.estAEnvoyer((modele.getBanqueQuestion()
				   						.getQuestionsLibelle
				   						("QuestionTestAEnvoyer2").get(0))));
	}
	
	/**
	 * Méthode de test pour la méthode toutEnvoyer et getQuestionAEnvoyer
	 * @see {@link application.modele.ModelePrincipal#toutEnvoyer()}.
	 * @see {@link application.modele.ModelePrincipal#getQuestionAEnvoyer()}.
	 */
	@Test
	@Order(25)
	void testToutEnvoyer() {
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    // On ajoute toute les questions de la banque de question et 
	    // on vérifie que la liste des questions à envoyer à toutes les questions
	    modele.toutEnvoyer();
	    assertIterableEquals(modele.getBanqueQuestion().getQuestions(), modele.getQuestionAEnvoyer());
	}
	
	/**
	 * Renvoie l'indice de la categorie dans une liste
	 * 
	 * @param categorie la catégorie qu'on recherche dans la liste de catégorie
	 * @param categories la liste de catégorie
	 * @return i, la position dans la liste de la catégorie (-1 si introuvable)
	 */
	private int indexOf(Categorie categorie, ArrayList<Categorie> categories) {
		int i = 0;
		for (Categorie categorieListe : categories) {
			if (categorieListe.equals(categorie))
				return i;
			i++;
		}
		return -1;
	}
}