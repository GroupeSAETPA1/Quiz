/*
 * DifficulteException.java                                    12 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.exception;

/** 
 * Est généré lorsque que la difficultée spécifiée est incorrecte
 * @author François de Saint Palais
 */
public class DifficulteException extends CreerQuestionException {

    /**
     * Constructeur de l'exception DifficulteException avec un message
     * @param message Le message d'erreur
     */
    public DifficulteException(String message) {
        super(message);
    }

    /** 
     * Constructeur de l'exception DifficulteException par défaut
     */
    public DifficulteException() {
        super();
    }

    private static final long serialVersionUID = 1L;
}