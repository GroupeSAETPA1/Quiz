/*
 * ClientDejaConnecter.java                                    22 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.exception;

/** 
 * TODO comment class responsibility (SRP)
 * @author francois
 */
public class ClientDejaConnecter extends Exception {

    /** 
     * @param message Le message d'erreur
     */
    public ClientDejaConnecter(String message) {
        super(message);
    }

    /** TODO comment field role (attribute, associative role) */
    private static final long serialVersionUID = 1L;
    
}
