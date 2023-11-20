/*
 * Serveur.java                                    24 oct. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package outil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * TODO comment class responsibility (SRP)
 * @author Francois
 * @author Tom Douaud
 */
public class Serveur {
    
	// Le ServerSocket statique
	private ServerSocket serveur;
    
	// Le port d'écoute du serveur par défaut = 501705
	private int port = 501705;
	
	// Le nombre maximum de tentatives d'envoi au client
	private static final int TENTATIVES_MAX = 100;
    
	// Le message pour terminer la conversation
    private final String MESSAGE_FIN_COMMUNICATION = "FIN_COMMUNICATION";
    
    public Serveur(int port) throws IOException, ClassNotFoundException  {
    	this.port = port;
        serveur = new ServerSocket(port);
        lancerServeur();
    }
    
    private void lancerServeur() throws IOException, ClassNotFoundException {
    	// Le booléen qui indique que le serveur est activé
    	boolean serveurActtif = true;
    	while (serveurActtif) {
    		// Création d'un socket et attente d'une connexion d'un client
    		Socket socket = serveur.accept();
    		
    		// Lecture du socket en objet ObjectInputStream et conversion
    		// en String
    		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    		String messageRecu = (String) ois.readObject();
            System.out.println("Message du client reçu : " + messageRecu);
            
            ois.close();
            // Si le client dit qu'il est pret, envoyer les questions
            if (messageRecu == "Le client est pret") {
            	// FIXME parce que quand on voudra appeler la méthode pour envoyer des question !
            	envoiQuestion(socket);
    		}
            socket.close();
    	}
        System.out.println("Fermeture du Socket server!");
    }
    

	private void envoiQuestion(Socket socket) throws IOException, ClassNotFoundException {
    	boolean envoiReussi = false;
    	for (int i = 0; i <= TENTATIVES_MAX || envoiReussi; i++) {
    		// Création d'un objet ObjectOutputStream  et ObjectInputStream
    		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    		// Envoi des questions
    		oos.writeObject("Les questions choisies");
    		
    		String reponseClient = (String) ois.readObject();
    		if (reponseClient == "Questions bien reçues") {
    			envoiReussi = true;
    		}
    		// Fermeture des ressources 		
    		oos.close();
    		ois.close();
    	}
    }
}
