/*
 * ClientPasConnecterException.java                                    21 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.exception;

/** 
 * Exception si le serveur n'a pas de client
 * @author François de Saint Palais
 */
public class ClientPasConnecterException extends Exception {

    /** 
     * Constructeur de l'exception avec un message
     * @param message
     */
    public ClientPasConnecterException(String message) {
        super(message);
    }

    /** TODO comment field role (attribute, associative role) */
    private static final long serialVersionUID = 1L;

    
}
