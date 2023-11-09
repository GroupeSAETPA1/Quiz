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
