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
    
	private static final String CLIENT_PRET = Client.CLIENT_PRET_MESSAGE;

	private static final String CLIENT_RECU_SUCCES = "Questions bien reçues";

	// Le ServerSocket 
	private ServerSocket serveur;
    
	// Le port d'écoute du serveur par défaut = 507705
	private static int port = 50705;
	
	// Le nombre maximum de tentatives d'envoi au client
	private static final int TENTATIVES_MAX = 100;
    
	// Le message pour terminer la conversation
    private final String MESSAGE_FIN_COMMUNICATION = "FIN_COMMUNICATION";
    
    public Serveur(int port) throws IOException, ClassNotFoundException  {
    	this.port = port;
        serveur = new ServerSocket(port);
    }
    
    public void lancerServeur() throws IOException, ClassNotFoundException {
        // Le booléen qui indique que le serveur est activé
        // Création d'un socket et attente d'une connexion d'un client
        System.out.println("Attente client");
        Socket socket = serveur.accept();
        System.out.println("Client accepté");

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Stream Créer");
        // Envoi des questions

        String reponseClient = (String) ois.readObject();
        System.out.println(reponseClient);

        oos.writeObject(reponseClient);
        System.out.println("Réponse envoyer");

        socket.close();
        System.out.println("Fermeture du Socket server!");
    }
    

	private void envoiQuestion(Socket socket) throws IOException, ClassNotFoundException {
    	boolean envoiReussi = false;
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Stream Créer");
        // Envoi des questions

        String reponseClient = (String) ois.readObject();
        System.out.println(reponseClient);
        envoiReussi = reponseClient == CLIENT_RECU_SUCCES;

        oos.writeObject(reponseClient);
        System.out.println("Réponse envoyer");

        // Fermeture des ressources
        oos.close();
        ois.close();
    }

    /** @return valeur de port */
    public static int getPort() {
        return port;
    }
	
	
}
