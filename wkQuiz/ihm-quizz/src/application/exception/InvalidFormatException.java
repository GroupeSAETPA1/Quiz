package application.exception;

/**
 * Exception levée lorsqu'un format n'est pas respecté
 */
public class InvalidFormatException extends CreerQuestionException {

    /** 
     * Constructeur de la classe InvalidFormatException
     * @param string
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
