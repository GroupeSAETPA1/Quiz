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

	private static final String CLIENT_RECU_SUCCES = "Questions bien reçues";

	// Le ServerSocket 
	private ServerSocket serveur;
    
	// Le port d'écoute du serveur par défaut = 507705
	private static int port = 50705;
	
	// Le nombre maximum de tentatives d'envoi au client
	private static final int TENTATIVES_MAX = 100;
    
	// Le message pour terminer la conversation
    private final String MESSAGE_FIN_COMMUNICATION = "FIN_COMMUNICATION";
    
    private Socket socket;
    
    public Serveur(int port) throws IOException, ClassNotFoundException  {
    	this.port = port;
        serveur = new ServerSocket(port);
    }
    
    public void lancerServeur() throws IOException, ClassNotFoundException {
        // Attente d'une connexion d'un client
        System.out.println("Attente client...");
        socket = serveur.accept();
        System.out.println("Client accepté");
    }
    

	public void envoiQuestion() throws IOException, ClassNotFoundException, 
	ClientPasConnecterException {
	    
	    if (socket == null) {
            throw new ClientPasConnecterException("Le serveur n'est connecté à "
                    + "personne");
        }
	    ModelePrincipal modele = ModelePrincipal.getInstance();
    	boolean clientEstPret = false;
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Stream Créer");
        ArrayList<Object> elementAEnvoyer = new ArrayList<Object>();
        
        System.out.println("Envoie question");
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
            
            //On envoie de le nombre d'élements à envoyer
            
            oos.writeObject(elementAEnvoyer.size());
            
            for (Object object : elementAEnvoyer) {
                oos.writeObject(object);
                System.out.println("Message envoyer : " + object);                
            }
        }
        
//        //Fin de la communication
//        oos.writeObject(MESSAGE_FIN_COMMUNICATION);

        // Fermeture des ressources
        oos.close();
        ois.close();
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
}
