/*
 * ClientDejaConnecte.java                                    22 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.exception;

/** 
 * Exception levée si un client est déja connecté au serveur
 * @author francois
 */
public class ClientDejaConnecte extends Exception {

    /** 
     * Constructeur de l'exception
     * @param message Le message d'erreur
     */
    public ClientDejaConnecte(String message) {
        super(message);
    }
    
    private static final long serialVersionUID = 1L;
}