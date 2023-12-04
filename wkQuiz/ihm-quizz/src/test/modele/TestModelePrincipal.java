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
 * Méthode de test du Modele Principal
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

		try {
			reinitialiserModele();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode pour que les autres tests 
	 * ne soient pas influencés par la sérialisation
	 * @throws InvalidNameException 
	 */
	void reinitialiserModele() throws InvalidNameException {
		// On récupère le modele principal et on supprime toutes ses questions et categories
		ModelePrincipal modele = ModelePrincipal.getInstance();
		modele.getBanqueCategorie().getCategories().clear();
		modele.getBanqueQuestion().getQuestions().clear();
		
		// On remets juste la catégorie general
		modele.getBanqueCategorie().getCategories().add(new Categorie("General"));
	}

	/**
	 * Methode de test pour la methode getInstance
	 * @see {@link application.modele.ModelePrincipal#getInstance()}.
	 * 
	 * @throws InvalidNameException
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(2)
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
	 * @throws HomonymeException
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 * @throws CreerQuestionException 
	 */
	@Test
	@Order(3)
	void testCreerQuestion() throws InvalidNameException, HomonymeException,
			ClassNotFoundException, InternalError, IOException, CreerQuestionException {
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
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 * @throws CreerQuestionException 
	 */
	@Test
	@Order(4)
	void testSupprimerCategorie() throws InvalidNameException, HomonymeException, CreerQuestionException {
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

		// On vérifie que supprimer une catégorie avec des question 
		// supprime les questions de cette catégorie
		modele.creerCategorie("categorieASupprimer");
		modele.creerQuestion("Question a supprimer", 
							  modele.getIndice("categorieASupprimer"), 
							  1, 
							  "reponse vrai", 
							  mauvaiseReponse1, 
							  "");
		
		assertTrue(modele.supprimerCategorie(modele.getCategoriesLibelleExact("categorieASupprimer")));
		assertEquals(new ArrayList<Question>(), modele.getBanqueQuestion().getQuestionsLibelle("Question a supprimer"));
		

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
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(5)
	void testCategorieContientQuestion() throws CreerQuestionException, InvalidNameException, HomonymeException {
		// On recupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();

		// On crée deux categories "Commentaire" et "test"
		modele.creerCategorie("Commentaire");
		modele.creerCategorie("test");

		// On mets des une question sur deux de la liste de questionValide
		// dans la categorie "Commentaire" et "test"
		modele.creerQuestion(questionValide.get(0).getLibelle(),
		                     modele.getIndice("Commentaire"), 
		                     questionValide.get(0).getDifficulte(),
		                     questionValide.get(0).getReponseJuste(), 
		                     mauvaiseReponse1,"");
		modele.creerQuestion(questionValide.get(1).getLibelle(),
		                     modele.getIndice("test"), 
                		     questionValide.get(1).getDifficulte(),
		                     questionValide.get(1).getReponseJuste(), 
		                     mauvaiseReponse1,"");

		// On verifie qu'il existe bien des questions presentes dans les deux categories
		assertTrue(modele.categorieContientQuestion(
		        modele.getCategoriesLibelleExact("Commentaire")));
		assertTrue(modele.categorieContientQuestion(
		        modele.getCategoriesLibelleExact("test")));
		
		modele.getBanqueCategorie().getCategories().remove(
		        modele.getCategoriesLibelleExact("Commentaire"));
		modele.getBanqueCategorie().getCategories().remove(
		        modele.getCategoriesLibelleExact("test"));

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
				"Le role du programme, en explicitant de maniere precise" 
				+ " ce role", 
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
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(6)
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
	 * @throws IOException 
	 * @throws InternalError 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(7)
	void testGetBanqueCategorie() throws InvalidNameException, HomonymeException {
		// On recupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();
		modele.getBanqueCategorie().getCategories().clear();
		
		
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
		
		modele.creerCategorie("General");
		modele.creerCategorie("Autre Nom");
		modele.creerCategorie(categoriesValides[0].getNom());
		modele.creerCategorie(categoriesValides[1].getNom());
		modele.creerCategorie("UneNouvelleUnique");
		modele.creerCategorie("Une categorie rajoutee");
		
		for (int i = 0; i < modele.getBanqueCategorie().getCategories().size(); i++) {
		    assertEquals(banqueTest.getCategorie(i), 
		                 modele.getBanqueCategorie().getCategorie(i));	
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
	@Order(8)
	void testGetBanqueQuestion() throws InvalidNameException, HomonymeException, CreerQuestionException {
		// On recupère l'instance du modèle principal
		ModelePrincipal modele = ModelePrincipal.getInstance();
		modele.getBanqueQuestion().getQuestions().clear();
		
		ArrayList<Question> question = new ArrayList<Question>();
		
		/*
		 * Pour pouvoir tester la methode, on doit recreer
		 * toute la banque de questions qui ont etes crees durant 
		 * tous les tests precendents
		 */ 
		BanqueQuestion banqueTestQuestions = new BanqueQuestion();
		question.add(new Question("Quel est le delimiteur de " 
											   + "debut d'un commentaire Javadoc ", 
											  	  modele.getCategories().get(0), 
											  	  1,
											  	  "non vide", 
											  	  mauvaiseReponse1, 
												  ""));
		
		question.add(new Question("Quel est le delimiteur de " 
											   + "debut d'un commentaire Javadoc ", 
											  	  modele.getCategories().get(1), 
											  	  1,
											  	 "non vide",
											  	  mauvaiseReponse1, 
				  								  ""));
		
		question.add(new Question("libelle different", 
												  modele.getCategories().get(0), 
												  1, 
									      		 "non vide", 
									      		  mauvaiseReponse1, 
				   								  ""));
		
		question.add(new Question("Quel est le delimiteur de " 
											   + "debut d'un commentaire Javadoc ", 
											   	  modele.getCategories().get(0),  
											   	  1,
											   	 "non vide", 
											   	  mauvaiseReponse2, 
				  								  ""));
		
		question.add(new Question("Quel est le delimiteur de " 
											   + "debut d'un commentaire Javadoc ", 
											   	  modele.getCategories().get(0), 
											   	  1,
											   	 "different", 
											   	  mauvaiseReponse1, 
				  								  ""));
		
		question.add(new Question("Question inexistante", 
											      modele.getCategories().get(0), 
											      1, 
											     "non vide", 
											      mauvaiseReponse1, 
												  ""));
		
		for (Question q : question) {
			banqueTestQuestions.ajouter(q);
			modele.getBanqueQuestion().ajouter(q);
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
	 * Méthode de test pour la méthode getCategorieAModifier
	 * @see {@link application.modele.ModelePrincipal#getCategorieAModifier()}.
	 * 
	 * @throws InvalidNameException
	 */
	@Test
	@Order(9)
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
	@Order(10)
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
	@Order(11)
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
	@Order(12)
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
	@Order(13)
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
	@Order(14)
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
	@Order(15)
	void testQuestionsAModifier() throws CreerQuestionException, InvalidNameException, HomonymeException {
		// On récupère le modèle principal et on vérifie qu'au départ 
		// il n'y a pas de question à modifier 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    assertNull(modele.getQuestionAModifier());
	    
	    // On crée une question, on l'ajoute au modèle et on la définit 
	    // comme question à modifier
	    
	    // Pour passer toutes les conditions de ajouter question,
	    // on ajoute une question avec une nouvelle catégorie
		Categorie categorieNonPresente = new Categorie("Categorie non presente");

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
	@Order(16)
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
	@Order(17)
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
	@Order(18)
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
	@Order(19)
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
	    								   "NouvelleBonneReponse", 
	    								    mauvaiseReponse1, 
	    								    ""));
	    assertTrue(modele.modifierQuestion("NouvelleQuestion2", 
	    		                           "Categorie existait pas encore", 
	    		                            2, 
	    		                           "NouvelleBonneReponse", 
	    		                            mauvaiseReponse1, 
	    		                           ""));
	    
	    // On vérifie que si les arguments ne sont pas valides la question n'est pas modifiée
	    assertFalse(modele.modifierQuestion("NouvelleQuestionInvalide", 
	    		                            "General", 
	    		                             0, 
	    		                            "NouvelleBonneReponse", 
	    		                             mauvaiseReponse1, 
	    		                             ""));
	    assertFalse(modele.modifierQuestion("NouvelleQuestionInvalide", 
                                            "", 
                                             1, 
                                            "NouvelleBonneReponse", 
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
	@Order(20)
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
	@Order(21)
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
	@Order(22)
	void testAjouterALaSelectionDEnvoie() throws InvalidNameException, HomonymeException, CreerQuestionException {
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    // On crée une catégorie "Categorie à envoyer" et 
	    // on y ajoute une question pour vérifier
	    // que quand on ajoute une catégorie a envoyer, 
	    // cela ajoute aussi les questions de cette catégorie
	    modele.creerCategorie("Categorie a envoyer");
	    modele.creerQuestion("QuestionTestAEnvoyer", 
	    					  modele.getIndice("Categorie a envoyer"), 
	    					  1, 
	    					 "vrai", 
	    					  mauvaiseReponse1, 
				 			 "");
	    modele.ajouterALaSelectionDEnvoie(modele.getCategoriesLibelleExact
	    								  ("Categorie a envoyer"));
	    assertTrue(modele.estAEnvoyer(modele.getCategoriesLibelleExact
	    								  ("Categorie a envoyer")));
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
	@Order(23)
	void testSupprimerALaSelectionDEnvoie() {
		/*
		 * Ce test fait le contraire du test précédent, 
		 * on supprime les questions et les catégories de la liste 
		 * et on vérifie qu'elles y sont plus 
		 */
		// On récupère le modèle principal 
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    modele.supprimerALaSelectionDEnvoie(modele.getCategoriesLibelleExact
				  							("Categorie a envoyer"));
	    assertFalse(modele.estAEnvoyer(modele.getCategoriesLibelleExact
				                      ("Categorie a envoyer")));
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
	@Order(24)
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
	
	@Test
	@Order(25)
	public void testAlphabetOk() {
		assertTrue(ModelePrincipal.alphabetOk("pas de caracteres non contenue dans l'alphabet"));
		assertFalse(ModelePrincipal.alphabetOk("caracteres non contenue dans l'alphabet : éàç"));
	}

}