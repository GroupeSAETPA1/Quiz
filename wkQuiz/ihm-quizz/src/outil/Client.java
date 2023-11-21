/*
 * Client.java                                    20 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package outil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/** 
 * TODO comment class responsibility (SRP)
 * @author Tom Douaud
 * @author Francois
 */
public class Client {
    
	/** TODO comment field role (attribute, associative role) */
    public static final String CLIENT_PRET_MESSAGE = "Le client est pret";

    // Le socket 
	private Socket socket;
    
	// Le port d'écoute du client par défaut = 507705
	private int port = 507705;
	
	// L'adresse ip du serveur
	private String ip;
	
	// Le nombre maximum de tentatives d'envoi au client
	private static final int TENTATIVES_MAX = 100;
    
    public Client(String ip, int port) {
        if (ip.isEmpty()) {
            throw new IllegalArgumentException("L'adresse IP n'est pas valide");
        }
        if (port <= 0) {
            throw new IllegalArgumentException("Le port n'est pas valide");            
        }
    	this.port = port;
    	this.ip = ip;
    }
    
    public void seConnecter() throws UnknownHostException, IOException {
        socket = new Socket(ip, port);
        // Lance la connexion socket connection au serveur
        // TODO vérifier que le serveur existe et fonctionne
        
        System.out.println("le client est connecté au serveur !");
    }
    
	public void recevoirDonnees() throws UnknownHostException, IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        //On indique on serveur que le client est prêt
        oos.writeObject(CLIENT_PRET_MESSAGE);

        String messageRecu = (String) ois.readObject();
        System.out.println(messageRecu);
        // TODO vérifier que les questions sont valides et importer

        // fermetures des ressources
        ois.close();
        oos.close();
    }
}