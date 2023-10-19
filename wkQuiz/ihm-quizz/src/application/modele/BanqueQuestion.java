/*
 * BanqueQuestion.java                                  18 oct. 2023
 * IUT de Rodez pas copyright, ni copyleft
 */

package application.modele;

import java.util.ArrayList;

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


    public BanqueQuestion() {
        questions = new ArrayList<Question>();
    }

    public void ajouter(Question question) {
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
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui on le libellé passé en paramètre 
     * (on vérifie que le libellé contient la string passée en paramètres)
     * en ignorant la casse
     */
    public ArrayList<Question> getQuestionsLibelle(String nom) {
        // TODO Auto-generated return
        return null;
    }

    /* non javadoc - @see java.lang.Object#toString() */
    @Override
    public String toString() {
        return questions.toString();
    }
    
    

}
