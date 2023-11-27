/*
 * BanqueQuestion.java                                  18 oct. 2023
 * IUT de Rodez pas copyright, ni copyleft
 */

package application.modele;

import java.io.Serializable;
import java.util.ArrayList;

import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidFormatException;

/**
 * Gestion de toutes les questions présentes  
 * dans l’application
 * @author François de Saint Palais
 * @author Tom Douaud
 */
public class BanqueQuestion implements Serializable {
	
    /** TODO comment field role (attribute, associative role) */
    private static final long serialVersionUID = 1685481789337969601L;
    
    /** Liste des questions */
    private ArrayList<Question> questions;

    /**
     * Constructeur de la classe BanqueQuestion
     */
    public BanqueQuestion() {
        questions = new ArrayList<Question>();
    }

    /**
     * Permet d'ajouter une question à la liste des questions
     * @param question La question à ajouter
     * @throws HomonymeException Si la question existe déjà
     */
    public void ajouter(Question question) throws HomonymeException {
        if (questions.contains(question)) {
            throw new HomonymeException("La question existe déjà.");
        }
        
        questions.add(question);
    }

    /**
     * Permet de récupérer une question précise 
     * avec son indice dans l’array qui stocke toutes les Questions
     * @param id (int) L'indice de la question voulue
     * @return La question à l'indice demandé
     * @throws IndexOutOfBoundsException Si l'indice est incorrect
     */
    public Question getQuestion(int id) throws IndexOutOfBoundsException {
        if (id < 0 || questions.size() <= id) {
            throw new IndexOutOfBoundsException(String.format("Erreur : %d est "
                    + "hors de la liste de taile %s", id, questions.size()));
        }
        
        return questions.get(id);
    }

    /**
     * Permet de récupérer toutes les questions
     * @return Toutes les questions (ArrayList)
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Permet de récupérer les questions qui on la catégorie passé en paramètres.
     * @param categorie la catégorie des questions à récupérer
     * @return null
     */
    public ArrayList<Question> getQuestions(Categorie categorie) {
        ArrayList<Question> resultat = new ArrayList<Question>();
        
        for (Question question : questions) {
            if (question.getCategorie().equals(categorie.getNom())) {
                resultat.add(question);
            }
        }
        
        return resultat;
    }

    /**
     * Permet de récupérer les questions qui ont 
     * la difficultée passée en paramètre
     * @param difficulte (int) La difficulté des questions à récupérer
     * @return resultat Les questions qui ont la difficulté passée en paramètre
     * @throws InvalidFormatException si la difficultée est invalide
     */
    public ArrayList<Question> getQuestionsDifficulte(int difficulte)
    throws DifficulteException {
        
        if (difficulte < 1 || 3 < difficulte) {
            throw new DifficulteException("Une difficulte est comprise " 
                                           + "entre 1 et 3");
        }
        
        ArrayList<Question> resultat = new ArrayList<Question>();
        
        for (Question question : questions) {
            if (question.getDifficulte() == difficulte) resultat.add(question);
        }
        
        return resultat;
    }

    /**
     * Permet de récupérer les questions qui on le libellé passé en paramètre 
     * (on vérifie que le libellé contient la string passée en paramètres)
     * en ignorant la casse
     * @param nom (String) Le libellé des questions à récupérer
     * @return resultat Les questions qui ont le libellé passé en paramètre
     */
    public ArrayList<Question> getQuestionsLibelle(String nom) {
        ArrayList<Question> resultat = new ArrayList<Question>();
        
        for (Question question : questions) {
            if (question.getLibelle().toLowerCase().contains(nom.toLowerCase())) {
                resultat.add(question);                
            }
        }
        
        return resultat;
    }

    /**
     * Récupère les questions qui on le nombre de fausses réponses 
     * passées en paramètres
     * @param nb (int) Le nombre de mauvaises réponses demandé (entre 1 et 4)
     * @return Les questions qui ont le nombre 
     * de mauvaises réponses demandé (ArrayList)
     * @throws InvalidFormatException Si le nombre de mauvaises 
     * réponses est incorrect
     */
    public ArrayList<Question> getQuestionsNbFausseReponse(int nb)
    throws InvalidFormatException {
        
        if (nb <= 0 || 5 <= nb) { 
            throw new InvalidFormatException(
                String.format("Erreur : %d n'est pas un nombre correct " 
                            + "de mauvaise réponse.",nb));
        }
        
        ArrayList<Question> resultat = new ArrayList<Question>();
        
        for (Question question : questions) {
            if (question.getMauvaisesReponses().size() == nb) {
                resultat.add(question);
            }
        }
        
        return resultat;
    }

    /* non javadoc - @see java.lang.Object#toString() */
    @Override
    public String toString() {
        StringBuilder resultat = new StringBuilder();
        resultat.append("Nombre de question : " + questions.size() + "\n\n");
        for (Question question : questions) {
            resultat.append(question.toString());
            resultat.append("\n\n"
                            + "--------------------------------------------"
                            + "\n\n");
        }
        return resultat.toString();
    }
}