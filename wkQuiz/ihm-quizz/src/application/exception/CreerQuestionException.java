/*
 * CreerQuestionException.java                                    12 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.exception;

/** 
 * Superclass de toutes les exceptions liées à la création d'une question
 * @author François de Saint Palais
 */
public class CreerQuestionException extends Exception {


    /** 
     * Constructeur par défaut
     */
    public CreerQuestionException() {
        super();
    }

    /** 
     * Constructeur avec message spécifique
     * @param message le message d'erreur à renvoyer
     */
    public CreerQuestionException(String message) {
        super(message);
    }
    
    private static final long serialVersionUID = 1L;
}
