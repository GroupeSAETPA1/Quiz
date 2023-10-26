/*
 * BanqueQuestion.java                                  18 oct. 2023
 * IUT de Rodez pas copyright, ni copyleft
 */

package application.modele;

import java.lang.reflect.Array;
import java.util.ArrayList;

import application.exception.HomonymeException;

/**
 * Gestion de toutes les questions présentes  
 * dans l’application
 * @author François de Saint Palais
 */
public class BanqueQuestion {
	
    private ArrayList<Question> questions;


    public BanqueQuestion() {
        questions = new ArrayList<Question>();
    }

    public void ajouter(Question question) throws HomonymeException {
        if (questions.contains(question)) {
            throw new HomonymeException("La question existe déjà.");
        }
        questions.add(question);
    }

    /**
     * Permet de récupérer une question précise avec son indice dans l’array qui stocke toute les Questions
     * @param id L'indice de la question voulue
     * @return La question à l'indice demandé
     */
    public Question getQuestion(int id) {
        if (id < 0 || questions.size() <= id) {
            throw new IndexOutOfBoundsException(String.format("Erreur : %d est "
                    + "hors de la liste de taile %s", id, questions.size()));
        }
        return questions.get(id);
    }

    /**
     * Récupère les questions qui on le nombre de fausses réponses 
     * passées en paramètres
     * @param nb Le nombre possible de mauvaise réponse
     * @return
     */
    public ArrayList<Question> getQuestionsNbFausseReponse(int nb) {
        if (nb <= 0 || 4 <= nb) {
            throw new IllegalArgumentException(String.format("Erreur : %d n'est pas un nombre correct de mauvaise réponse.",nb));
        }
        ArrayList<Question> resultat = new ArrayList<Question>();
        for (Question question : questions) {
            if (question.getMauvaisesReponses().size() == nb) {
                resultat.add(question);
            }
        }
        return resultat;
    }

    /**
     * Permet de récupérer toutes les questions
     * @return Toutes les questions 
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Permet de récupérer les questions qui on la catégorie passé en paramètres.
     * @param categorie 
     * @return
     */
    public ArrayList<Question> getQuestions(Categorie categorie) {
        ArrayList<Question> resultat = new ArrayList<Question>();
        for (Question question : questions) {
            if (question.getCategorie().equals(categorie)) {
                resultat.add(question);
            }
        }
        return null;
    }

    /**
     * Permet de récupérer les questions qui ont la difficultée passée en paramètre
     */
    public ArrayList<Question> getQuestionsDifficulte(int difficulte) {
        if (difficulte < 1 || 3 < difficulte) {
            throw new IllegalArgumentException("Une difficulte est comprise entre 1 et 3");
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
     */
    public ArrayList<Question> getQuestionsLibelle(String nom) {
        ArrayList<Question> resultat = new ArrayList<Question>();
        for (Question question : questions) {
            if (question.getLibelle().contains(nom.toLowerCase())) resultat.add(question);
        }
        return resultat;
    }

    /* non javadoc - @see java.lang.Object#toString() */
    @Override
    public String toString() {
        StringBuilder resultat = new StringBuilder();
        for (Question question : questions) {
            resultat.append(question);
            resultat.append("\n\n"
                            + "--------------------------------------------"
                            + "\n\n");
        }
        return resultat.toString();
    }
    
    

}
