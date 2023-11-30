/*
 * HomonymeException.java                                    novembre 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.exception;

/**
 * Exception levée lorsqu'un libellé est déjà utilisé
 */
public class HomonymeException extends Exception {

    /** 
     * Constructeur de la classe HomonymeException
     * @param string Le message d'erreur
     */
    public HomonymeException(String messageErreur) {
        super(messageErreur);
    }
    
    /** 
     * Constructeur de la classe HomonymeException par défaut
     */
    public HomonymeException() {
        super();
    }

    private static final long serialVersionUID = 1L;
}