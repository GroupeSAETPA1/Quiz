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
import java.util.ArrayList;

import application.exception.ClientDejaConnecter;
import application.exception.ClientPasConnecterException;
import application.modele.ModelePrincipal;

/** 
 * TODO comment class responsibility (SRP)
 * @author Francois
 * @author Tom Douaud
 */
public class Serveur {
    
	private static final String CLIENT_PRET = Client.CLIENT_PRET_MESSAGE;

	private static final String QUESTION_CLIENT_PRET = "ES-TU PRET ?";

	// Le ServerSocket 
	private ServerSocket serveur;
    
	// Le port d'écoute du serveur par défaut = 507705
	private static int port = 50705;
	
	// Le message pour terminer la conversation
    protected static final String MESSAGE_FIN_COMMUNICATION = "FIN_COMMUNICATION";
    
    private Socket socket;
    
    public Serveur(int port) throws IOException, ClassNotFoundException  {
    	this.port = port;
        serveur = new ServerSocket(port);
    }
    
    public void lancerServeur() throws IOException, ClassNotFoundException, 
    ClientDejaConnecter {
        
        // Attente d'une connexion d'un client
        if (socket != null && !socket.isClosed()) {
            throw new ClientDejaConnecter("Un client est déjà connecté");
        }
        System.out.println("Attente client...");
        socket = serveur.accept();
        System.out.println("Client accepté");
    }
    

    /**
     * TODO comment method role
     * @return true si le client a reçu les questions
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws ClientPasConnecterException
     */
	public boolean envoiQuestion() throws IOException, ClassNotFoundException, 
	ClientPasConnecterException {
	    
	    if (socket == null || socket.isClosed()) {
            throw new ClientPasConnecterException("Le serveur n'est connecté à "
                    + "personne");
        }
	    ModelePrincipal modele = ModelePrincipal.getInstance();
    	boolean clientEstPret = false;
    	boolean clientARecu = false;
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Stream Créer");
        ArrayList<Object> elementAEnvoyer = new ArrayList<Object>();
        
        System.out.println("Envoie question : " + QUESTION_CLIENT_PRET);
        // Demande si le client est prêt
        oos.writeObject(QUESTION_CLIENT_PRET);

        //On vérifie si le client est prêt
        String reponseClient = (String) ois.readObject();
        System.out.println("Réponse client :  " + reponseClient);
        clientEstPret = reponseClient.equals(CLIENT_PRET);
        
        System.out.println(clientEstPret);

        if (clientEstPret) {
            //TODO Recupérer les question a envoyer
            elementAEnvoyer.add(modele.getCategories().getFirst());
            elementAEnvoyer.add("Coucou");
            
            //On envoie de le nombre d'élements à envoyer
            oos.writeObject(elementAEnvoyer.size());
            
            for (Object object : elementAEnvoyer) {
                oos.writeObject(object);
                System.out.println("Message envoyer : " + object);                
            }
            clientARecu = true;
        }
        
        //Fin de la communication
        oos.writeObject(MESSAGE_FIN_COMMUNICATION);
        String messageFin = (String) ois.readObject();
        System.out.println(messageFin);
        
        // Fermeture des chemins de communication
        oos.close();
        ois.close();
        
        socket.close();//Fin de communication
        
        return clientARecu;
    }

    /** @return valeur de port */
    public static int getPort() {
        return port;
    }
	
    /**
     * Renvoie l'adresse IP du client si il est connecté
     * @return
     */
	public String getIPClient() {
	    if (socket != null) {
	        return socket.getInetAddress().getHostName();            
        }
	    return null;
	}
	
	/** @return Renvoie true si le serveur est connecté à un client */
	public boolean clientEstConnecte() {
	    return socket != null && !socket.isClosed();
	}
}
