/*
 * CreerQuestionException.java                                    12 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.exception;

/** 
 * Superclass de toute les exception lié à la création d'une question
 * @author François de Saint Palais
 */
public class CreerQuestionException extends Exception {

    /** TODO comment field role (attribute, associative role) */
    private static final long serialVersionUID = 1L;

    /** 
     * Constructeur par défaut
     */
    public CreerQuestionException() {
        super();
    }

    /** 
     * TODO comment initial state properties
     * @param message
     */
    public CreerQuestionException(String message) {
        super(message);
    }
}
