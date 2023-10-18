/*
 * BanqueQuestion.java                                  18 oct. 2023
 * IUT de Rodez pas copyright, ni copyleft
 */

package modele;

import java.util.ArrayList;
import java.util.Arrays;
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
    private ArrayList<Question> questions;


    public BanqueQuestion(ArrayList<Question> questions) {
    }

    public void ajouter(Question question) {
    }

    /**
     * Permet de récupérer une question précise avec son indice dans l’array qui stocke toute les Questions
     */
    public Question getQuestion(int id) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui on le nombre de fausses réponses passées en paramètres
     */
    public ArrayList<Question> getQuestionNbFausseReponse(int nb) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer toutes les questions
     */
    public ArrayList<Question> getQuestions() {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui on la catégorie passé en paramètres.
     */
    public ArrayList<Question> getQuestions(Categorie categorie) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui ont la difficultée passée en paramètre
     */
    public ArrayList<Question> getQuestionsDifficulte(int difficulte) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui on le libellé passé en paramètre (on vérifie que le libellé contient la string passée en paramètres)
     */
    public Question getQuestionsLibelle(ArrayList<String> nom) {
        // TODO Auto-generated return
        return null;
    }

    /* non javadoc - @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return "BanqueQuestion [questions=" + Arrays.toString(questions) + "]";
    }
    
    

}
