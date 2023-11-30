/*
 * ReponseException.java                              novembre 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.exception;

/**
 * Exception levée lorsqu'une réponse (fausse ou bonne) est non valide
 */
public class ReponseException extends CreerQuestionException {

    /** 
     * Constructeur de la classe ReponseException
     * @param string
     */
    public ReponseException(String messageErreur) {
        super(messageErreur);
    }
    
    /** 
     * Constructeur de la classe ReponseException par défaut
     */
    public ReponseException() {
        super();
    }

    private static final long serialVersionUID = 1L;
}