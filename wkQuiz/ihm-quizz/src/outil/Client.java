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
    
	// Le socket 
	private Socket socket;
    
	// Le port d'écoute du client par défaut = 507705
	private int port = 507705;
	
	// L'adresse ip du serveur
	private String ip;
	
	// Le nombre maximum de tentatives d'envoi au client
	private static final int TENTATIVES_MAX = 100;
    
    public Client(String ip, int port) throws IOException, ClassNotFoundException {
    	this.port = port;
    	this.ip = ip;
    	recevoirDonnees();
    }
    
	private void recevoirDonnees() throws UnknownHostException, IOException, ClassNotFoundException {
	// TODO Auto-generated method stub
		boolean connexionTermine = false;
		
		while (connexionTermine) {
			// Lance la connexion socket connection au serveur
			// TODO vérifier que le serveur existe et fonctionne
			socket = new Socket(ip, port);
			System.out.println("le client est connecté au serveur !");
		
			// Création d'un objet ObjectOutputStream  et ObjectInputStream
    		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    		
    		oos.writeObject("Le client est pret");
    		
    		String messageRecu = (String) ois.readObject();
    		System.out.println(messageRecu);
    		// TODO vérifier que les questions sont valides et importer
    		
    		// fermetures des ressources
            ois.close();
            oos.close();
            connexionTermine = true;
		}
    }
}