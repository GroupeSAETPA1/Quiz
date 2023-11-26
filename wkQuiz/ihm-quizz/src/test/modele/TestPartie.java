/*
 * TestPartie.java                                    16 nov. 2023
 * IUT de Rodez, info2 2023-2024, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.exception.CreerQuestionException;
import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Partie;
import application.modele.Question;

/** 
 * TODO comment class responsibility (SRP)
 * @author Tom Douaud
 * @author Francois DSP
 */
class TestPartie {

    /**
	 * Méthode de test pour le constructeur Partie
     * @see {@link application.modele.Partie#Partie()}.
     */
    @Test
    void testPartie() {
    	// On crée une partie et on vérifie que le pseudo
    	// soit bien celui du constructeur
        Partie partie = new Partie();
        assertEquals("Pseudo", partie.getPseudo());
    }

    /**
	 * Méthode de test pour la méthode setDifficultePartie et getDifficultePartie
     * @see {@link application.modele.Partie#setDifficultePartie(int)}.
     * @see {@link application.modele.Partie#getDifficultePartie()}.
     * 
     * @throws DifficulteException 
     */
    @Test
    void testDifficultePartie() throws DifficulteException {
    	Partie partie = new Partie();
    	
    	// On vérifie que changer la difficultée de la partie par une 
    	// nouvelle difficultée valide change bien la difficultée de la partie
    	partie.setDifficultePartie(1);
    	assertEquals(1, partie.getDifficultePartie());
    	partie.setDifficultePartie(0);
    	assertEquals(0, partie.getDifficultePartie());
    	
    	// On vérifie que changer la difficultée par une difficultée invalide 
    	// (inférieur à 0 ou supérieure à 3) renvoie bien 
    	// l'exception "DifficulteException"
    	assertThrows(DifficulteException.class, () -> partie.setDifficultePartie(-1));
    	assertThrows(DifficulteException.class, () -> partie.setDifficultePartie(4));
    }


    /**
	 * Méthode de test pour la méthode setDifficultePartie et getDifficultePartie
     * @see {@link application.modele.Partie#setDifficultePartie(int)}.
     * @see {@link application.modele.Partie#getDifficultePartie()}.
     */
    @Test
    void testNombreQuestion() {
    	Partie partie = new Partie();
    	
    	// On vérifie que changer le nombre de questions de la partie par une
    	// nouveau nombre de questions change bien 
    	// le nombre de questions de la partie
    	partie.setNombreQuestion(1);
    	assertEquals(1, partie.getNombreQuestion());
    	partie.setNombreQuestion(0);
    	assertEquals(0, partie.getNombreQuestion());
    	partie.setNombreQuestion(10);
    	assertEquals(10, partie.getNombreQuestion());
    }


    /**
	 * Méthode de test pour la méthode setCategoriePartie et getCategoriePartie
     * @see {@link application.modele.Partie#setCategoriePartie(String)}.
     * @see {@link application.modele.Partie#getCategoriePartie()}.
     *      
     * @throws HomonymeException 
     * @throws InvalidNameException 
     * @throws IOException 
     * @throws InternalError 
     * @throws ClassNotFoundException 
     */
    @Test
    void testCategoriePartie() throws InvalidNameException, HomonymeException, ClassNotFoundException, InternalError, IOException {
    	// On récupère le modèle principal et on crée 
    	// une nouvelle catégorie dans ce modèle
    	ModelePrincipal modele = ModelePrincipal.getInstance();
    	modele.creerCategorie("Nouvelle catégorie partie");
    	
    	// On définit la catégorie de la partie par la catégorie créée 
    	// juste avant et on vérifie que la catégorie séléctionée pour 
    	// la partie est bien celle qu'on à créé dans le modèle
    	modele.getPartie().setCategoriePartie("Nouvelle catégorie partie");
    	assertEquals(modele.getCategoriesLibelleExact("Nouvelle catégorie partie"),
    				 modele.getPartie().getCategoriePartie());
    }

    /**
 	 * Méthode de test pour la méthode setQuestionPossible et getQuestionPossible
     * @see {@link application.modele.Partie#setQuestionPossible(java.util.ArrayList)}.
     * @see {@link application.modele.Partie#getQuestionPossible()}.
     * 
     * @throws HomonymeException 
     * @throws InvalidNameException 
     * @throws CreerQuestionException 
     */
    @Test
    void testQuestionPossible() throws CreerQuestionException, InvalidNameException, HomonymeException {
    	Partie partie = new Partie();
    	
    	// On crée 3 questions
    	ArrayList<String> mauvaisesReponse = new ArrayList<String>();
    	ArrayList<Question> questions = new ArrayList<Question>();
    	mauvaisesReponse.add("Mauvaise Réponse");
    	questions.add(new Question("Question1", new Categorie("Categorie1"), 
    							    1, "Réponse 1", mauvaisesReponse, ""));
    	questions.add(new Question("Question2", new Categorie("Categorie1"), 
    								1, "Réponse 2", mauvaisesReponse, ""));
    	questions.add(new Question("Question3", new Categorie("Categorie1"), 
    								1, "Réponse 3", mauvaisesReponse, ""));
    	
    	// On mets ces 3 questions dans la liste des questions possibles 
    	// et on vérifie que la liste des questions possibles 
    	// à bien ces 3 questions
    	partie.setQuestionPossible(questions);
    	assertEquals(questions, partie.getQuestionPossible());
    }

    /**
 	 * Méthode de test pour la méthode setReponseDonnees et getReponseDonnees
     * @see {@link application.modele.Partie#setReponseDonnees(Question, String)}.
     * @see {@link application.modele.Partie#getReponseDonnees()}.
     * 
     * @throws InvalidNameException 
     * @throws CreerQuestionException 
     */
    @Test
    void testReponseDonnee() throws CreerQuestionException, InvalidNameException {
    	Partie partie = new Partie();
    	
    	// On ajoute une question
    	ArrayList<String> mauvaisesReponse = new ArrayList<String>();
    	mauvaisesReponse.add("Mauvaise Réponse");
    	Question question = new Question("Question1", new Categorie("Categorie1"), 
    							          1, "Réponse 1", mauvaisesReponse, "");
    	
    	// On définit la réponse de l'utilisateur a la question "question" 
    	// la réponse "Réponse Utilisateur"
    	partie.setReponseDonnees(question, "Réponse Utilisateur");
    	
    	// On vérifie que la méthode renvoie bien la question et la réponse attendue
    	HashMap<Question, String> hashmapAttendue = new HashMap<Question, String>();
    	hashmapAttendue.put(question, "Réponse Utilisateur");
    	assertEquals(hashmapAttendue, partie.getReponseDonnees());
    }


    /**
 	 * Méthode de test pour la méthode getNbBonneReponse
     * @see {@link application.modele.Partie#getNbBonneReponse()}.   
     *
     * @throws InvalidNameException 
     * @throws CreerQuestionException 
     */
    @Test
    void testQuestionActuelle() throws CreerQuestionException, InvalidNameException {
    	Partie partie = new Partie();
    	
    	// On ajoute une question
    	ArrayList<String> mauvaisesReponse = new ArrayList<String>();
    	mauvaisesReponse.add("Mauvaise Réponse");
    	Question question = new Question("Question1", new Categorie("Categorie1"), 
    							          1, "Réponse 1", mauvaisesReponse, "");
    	
        // On vérifie que quand on set la question actuelle 
        // avec la question créé auparavant	
        // la question actuelle devient la question créé auparavant
    	partie.setQuestionActuelle(question);
    	assertEquals(question, partie.getQuestionActuelle());
    }

    /**
 	 * Méthode de test pour la méthode setIndiceQuestion et getIndiceQuestion
     * @see {@link application.modele.Partie#setIndiceQuestion(int)}.
     * @see {@link application.modele.Partie#getIndiceQuestion()}.  
     */
    @Test
    void testIndiceQuestion() {
    	Partie partie = new Partie();
    	
    	// On vérifie que l'indice de la question est bien changé a chaque fois
    	partie.setIndiceQuestion(1);
    	assertEquals(1, partie.getIndiceQuestion());
    	partie.setIndiceQuestion(2);
    	assertEquals(2, partie.getIndiceQuestion());
    	partie.setIndiceQuestion(5);
    	assertEquals(5, partie.getIndiceQuestion());
    }

    /**
     * Méthode de test pour la méthode setIndiceQuestion et getIndiceQuestion
     * @see {@link application.modele.Partie#setIndiceQuestion(int)}.
     * 
     * @throws InvalidNameException 
     * @throws CreerQuestionException 
     */
    @Test
    void testGetNbBonneReponse() throws CreerQuestionException, InvalidNameException {
    	Partie partie = new Partie();
    	
    	// On crée 3 questions
    	ArrayList<String> mauvaisesReponse = new ArrayList<String>();
    	ArrayList<Question> questions = new ArrayList<Question>();
    	mauvaisesReponse.add("Mauvaise Réponse");
    	questions.add(new Question("Question1", new Categorie("Categorie1"), 
    							    1, "Réponse 1", mauvaisesReponse, ""));
    	questions.add(new Question("Question2", new Categorie("Categorie1"), 
    								1, "Réponse 2", mauvaisesReponse, ""));
    	questions.add(new Question("Question3", new Categorie("Categorie1"), 
    								1, "Réponse 3", mauvaisesReponse, ""));
    	
    	// On réponds juste à la première et à la dernière question
    	partie.setReponseDonnees(questions.get(0), "Réponse 1");
    	partie.setReponseDonnees(questions.get(1), "Réponse fausse");
    	partie.setReponseDonnees(questions.get(2), "Réponse 3");
    	
    	// On vérifie que notre score est de 2
    	assertEquals(2, partie.getNbBonneReponse());
    }

    /**
     * Test method for {@link application.modele.Partie#pourcentageBonneRep()}.
     */
    @Test
    void testPourcentageBonneRep() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Partie#pourcentageBonneRep()}.
     */
    @Test
    void testPseudo() {
        fail("Not yet implemented");
    }

}
