/*
 * TestPartie.java                                    16 nov. 2023
 * IUT de Rodez, info2 2023-2024, aucun copyright ni copyleft
 */

package test.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.modele.Partie;

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
     * Test method for {@link application.modele.Partie#getQuestionPossible()}.
     */
    @Test
    void testGetQuestionPossible() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Partie#setQuestionPossible(java.util.ArrayList)}.
     */
    @Test
    void testSetQuestionPossible() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Partie#setReponseDonnee(application.modele.Question, java.lang.String)}.
     */
    @Test
    void testSetReponseDonnee() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Partie#getActuelle()}.
     */
    @Test
    void testGetActuelle() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Partie#setActuelle(application.modele.Question)}.
     */
    @Test
    void testSetActuelle() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Partie#getIndiceQuestion()}.
     */
    @Test
    void testGetIndiceQuestion() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Partie#setIndiceQuestion(int)}.
     */
    @Test
    void testSetIndiceQuestion() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Partie#getReponseDonnees()}.
     */
    @Test
    void testGetReponseDonnees() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Partie#getNbBonneReponse()}.
     */
    @Test
    void testGetNbBonneReponse() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link application.modele.Partie#pourcentageBonneRep()}.
     */
    @Test
    void testPourcentageBonneRep() {
        fail("Not yet implemented");
    }

}
