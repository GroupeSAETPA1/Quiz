/*
 * TestModelePrincipale.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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
	 * Méthode de test pour la méthode getInstance
	 * @see {@link application.modele.ModelePrincipal#getInstance()}.
	 * 
	 * @throws InvalidNameException
	 */
	@Test
	@Order(1)
	void testGetInstance() throws InvalidNameException {
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
	 * {@link application.modele.ModelePrincipal#supprimerCategorie(Categorie)}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(3)
	void testSupprimerCategorie() throws InvalidNameException, HomonymeException {
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

		// Le fait de rajouter une catégorie "Général" ne fait rien 
		// car "General" existe déjà
		Categorie categorieGeneral = new Categorie("Général");
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
	 * {@link application.modele.ModelePrincipal#categorieContientQuestion(Categorie)}.
	 * 
	 * @throws CreerQuestionException
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(4)
	void testCategorieContientQuestion() throws CreerQuestionException, InvalidNameException, HomonymeException {
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
	 * {@link application.modele.ModelePrincipal#categorieExiste(String)}.
	 * 
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(5)
	void testCategorieExiste() throws InvalidNameException, HomonymeException {
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
	 * {@link application.modele.ModelePrincipal#getBanqueCategorie()}.
	 *  
	 * @throws InvalidNameException
	 * @throws HomonymeException
	 */
	@Test
	@Order(6)
	void testGetBanqueCategorie() throws InvalidNameException, HomonymeException {
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
	
	@Test
	@Order(7)
	void testGetBanqueQuestion() throws InvalidNameException, HomonymeException, InvalidFormatException, ReponseException, DifficulteException {
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
	
	@Test
	void testGetCategorieAModifier() throws InvalidNameException {
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    Categorie a = new Categorie("categorie");
	    
	    modele.setCategorieAModifier(a);
	    assertEquals(a, modele.getCategorieAModifier());
	}
	
	@Test
	void testGetCategoriesLibelle() throws InvalidNameException, HomonymeException {
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    BanqueCategorie banque = modele.getBanqueCategorie();
	    
	    banque.getCategories().clear();
	    
	    Categorie a1 = new Categorie("a");
	    Categorie a2 = new Categorie("aa");
	    Categorie a3 = new Categorie("b");
	    
	    modele.creerCategorie(a1.getNom());
	    modele.creerCategorie(a2.getNom());
	    modele.creerCategorie(a3.getNom());
	    
	    ArrayList<Categorie> attendu = new ArrayList<Categorie>();
	    
	    attendu.add(a1);
	    attendu.add(a2);
	    
	    assertIterableEquals(attendu, modele.getCategoriesLibelle("a"));
	}
	    
	
    @Test
    void testGetIndice() throws InvalidNameException, HomonymeException {
        ModelePrincipal modele = ModelePrincipal.getInstance();
        BanqueCategorie banque = modele.getBanqueCategorie();

        banque.getCategories().clear();

        Categorie a1 = new Categorie("a");
        Categorie a2 = new Categorie("aa");
        Categorie a3 = new Categorie("b");

        modele.creerCategorie(a1.getNom());
        modele.creerCategorie(a2.getNom());
        modele.creerCategorie(a3.getNom());

        assertEquals(1, modele.getIndice("aa"));
    }

    @Test
    void testGetNombreQuestionCategorie() throws InvalidNameException, HomonymeException, CreerQuestionException {
        ModelePrincipal modele = ModelePrincipal.getInstance();
        BanqueCategorie banque = modele.getBanqueCategorie();
        BanqueQuestion banqueQ = modele.getBanqueQuestion();

        banque.getCategories().clear();
        banqueQ.getQuestions().clear();

        Categorie a1 = new Categorie("a");
        Categorie a2 = new Categorie("aa");
        Categorie a3 = new Categorie("b");

        modele.creerCategorie(a1.getNom());
        modele.creerCategorie(a2.getNom());
        modele.creerCategorie(a3.getNom());


        assertEquals(0, modele.getNombreQuestionCategorie(a1));
        assertEquals(0, modele.getNombreQuestionCategorie(a2));
        assertEquals(0, modele.getNombreQuestionCategorie(a3));
        
        modele.creerQuestion("fq", 0, 1, "vze", mauvaiseReponse1, "");

        assertEquals(1, modele.getNombreQuestionCategorie(a1));
    }

	@Test
	void testGetQuestionAModifier() throws InvalidNameException, CreerQuestionException, HomonymeException {
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    BanqueCategorie banque = modele.getBanqueCategorie();
        BanqueQuestion banqueQ = modele.getBanqueQuestion();

        banque.getCategories().clear();
        banqueQ.getQuestions().clear();
	    
	    Categorie c = new Categorie("ez");
	    Question q = new Question("fq", c, 1, "vze", mauvaiseReponse1, "");
	    
	    modele.creerCategorie(c.getNom());
	    modele.creerQuestion(q.getLibelle(), 0, q.getDifficulte(), q.getReponseJuste(), q.getMauvaisesReponses(), q.getFeedback());

	    modele.setQuestionAModifier(q);
	    
	    assertEquals(q, modele.getQuestionAModifier());
	}
	
	@Test
	void testGetPartie() throws InvalidNameException {
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    modele.setPartie(null);
	    assertNull(modele.getPartie());
	    
	    Partie p = new Partie();
	    modele.setPartie(p);
	    assertEquals(p, modele.getPartie());
	}
	
	@Test
	void testGetPagePrecendente() throws InvalidNameException {
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    modele.setPagePrecedente(null);
	    assertNull(modele.getPagePrecendente());

	    String p = "page.fxml";
	    modele.setPagePrecedente(p);
	    assertEquals(p,modele.getPagePrecendente());
	}
	
	@Test
	void testIsDisplay() {
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    
	    modele.setDisplayCategoriePane(false);
	    assertFalse(modele.isDisplayCategoriePane());

	    modele.setDisplayCategoriePane(true);
	    assertTrue(modele.isDisplayCategoriePane());
	}
	
	@Test
	@Order(8)
	void testModifierNomCategorie() throws InvalidNameException, HomonymeException {
	    ModelePrincipal modele = ModelePrincipal.getInstance();
	    BanqueCategorie banque = modele.getBanqueCategorie();

	    System.out.println(banque.getCategoriesNom());
	    assertThrows(HomonymeException.class,
                ()-> modele.modifierCategorie("General"));
	    
        banque.getCategories().clear();
	    
	    Categorie c = new Categorie("A changer");
	    modele.setCategorieAModifier(c);
	    
	    String nouveauNon = "Nouveau nom";
	    modele.modifierCategorie(nouveauNon);
	    
	    c = modele.getCategorieAModifier();
	    assertEquals(nouveauNon, c.getNom());
	    
	    assertThrows(InvalidNameException.class, 
	            ()->modele.modifierCategorie(""));
	    assertThrows(InvalidNameException.class, 
	            ()->modele.modifierCategorie("abcdefghijklmnopqrstuvwxyzabcde"));
	    
	    Categorie autreC = new Categorie("Nom");
	    modele.creerCategorie(autreC.getNom());
	    
	    assertThrows(HomonymeException.class,
	            ()-> modele.modifierCategorie(autreC.getNom()));
	    assertThrows(HomonymeException.class,
	            ()-> modele.modifierCategorie(autreC.getNom().toLowerCase()));	    
	    assertThrows(HomonymeException.class,
	            ()-> modele.modifierCategorie(autreC.getNom().toUpperCase()));	    
	    
	}
	
	@Test
	void testModifierQuestion() throws InvalidNameException, CreerQuestionException, HomonymeException {
	    ModelePrincipal modele = ModelePrincipal.getInstance();
        BanqueCategorie banque = modele.getBanqueCategorie();
        BanqueQuestion banqueQ = modele.getBanqueQuestion();

        banque.getCategories().clear();
        banqueQ.getQuestions().clear();
        
        Categorie c = new Categorie("Categorie");
        Question q = new Question("fq", c, 1, "vze", mauvaiseReponse1, "");
        
        modele.creerCategorie(c.getNom());
        modele.creerQuestion(q.getLibelle(), 0, q.getDifficulte(), q.getReponseJuste(), q.getMauvaisesReponses(), q.getFeedback());

        
	}
	
	

	/**
	 * Renvoie l'indice de la categorie dans une liste
	 * 
	 * @param categorie
	 * @param categories
	 * @return
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
