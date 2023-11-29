/*
 * InvalidNameException.java                              novembre 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.exception;

/**
 * Exception levée lorsqu'un nom est invalide
 */
public class InvalidNameException extends Exception {

    /** 
     * Constructeur de la classe InvalidNameException
     * @param string
     */
    public InvalidNameException(String messageErreur) {
        super(messageErreur);
    }
    
    /** 
     * Constructeur de la classe InvalidNameException par défaut
     */
    public InvalidNameException() {
        super();
    }

    private static final long serialVersionUID = 1L;
}