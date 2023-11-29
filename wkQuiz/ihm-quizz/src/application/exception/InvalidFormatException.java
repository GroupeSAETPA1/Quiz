/*
 * InvalidFormatException.java                              novembre 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.exception;

/**
 * Exception levée lorsqu'un format n'est pas respecté
 */
public class InvalidFormatException extends CreerQuestionException {

    /** 
     * Constructeur de la classe InvalidFormatException
     * @param string le message d'erreur à renvoyer
     */
    public InvalidFormatException(String messageErreur) {
        super(messageErreur);
    }
    
    /** 
     * Constructeur de la classe InvalidFormatException par défaut
     */
    public InvalidFormatException() {
        super();
    }

    private static final long serialVersionUID = 1L;
}