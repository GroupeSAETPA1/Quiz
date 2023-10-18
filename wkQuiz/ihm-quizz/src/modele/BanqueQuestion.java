/*
 * BanqueQuestion.java                                  18 oct. 2023
 * IUT de Rodez pas copyright, ni copyleft
 */

package modele;

import java.util.List;

/**
 * Gestion de toutes les questions présentes  
 * dans l’application
 * @author François de Saint Palais
 */
public class BanqueQuestion {
    /**
     * <Saisir le texte de la note ici>
     */
    private Categorie[] questions;

    public Question question;

    /**
     * Permet de récupérer une question précise avec son indice dans l’array qui stocke toute les Questions
     */
    public Question getQuestion(final int id) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui on la catégorie passé en paramètres.
     */
    public List<Question> getQuestions(final Categorie categorie) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui ont la difficultée passée en paramètre
     */
    public List<Question> getQuestionsDifficulte(final int difficulte) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui on le nombre de fausses réponses passées en paramètres
     */
    public List<Question> getQuestionNbFausseReponse(final int nb) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer toutes les questions
     */
    public List<Question> getQuestions() {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui on le libellé passé en paramètre (on vérifie que le libellé contient la string passée en paramètres)
     */
    public Question getQuestionsLibelle(final List<String> nom) {
        // TODO Auto-generated return
        return null;
    }

    public BanqueQuestion(final List<Question> questions) {
    }

    public void ajouter(final Question question) {
    }

}
