/*
 * Serveur.java                                    24 oct. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package outil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * TODO comment class responsibility (SRP)
 * @author francois
 */
public class Serveur {
    
    private int port;
    
    private ServerSocket serveur;
    
    public final String MESSAGE_FIN_COMMUNICATION = "FIN_COMMUNICATION";
    
    public Serveur (int port) throws IOException {
        this.port = port;
        serveur = new ServerSocket();
    }
}
