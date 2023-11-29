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
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import application.exception.ClientDejaConnecte;
import application.exception.ClientPasConnecteException;
import application.modele.Chiffrage;
import application.modele.ModelePrincipal;
import application.modele.Question;

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
    
    /**
     * Le temps que le serveur attend pour accepter un client.
     */
    private static final int TIMEOUT_SERVEUR_ACCEPT = 20_000;
    
    public Serveur(int port) throws IOException, ClassNotFoundException  {
    	this.port = port;
        serveur = new ServerSocket(port);
    }
    
    public void lancerServeur() throws IOException, ClassNotFoundException, 
    ClientDejaConnecte, SocketTimeoutException {
        
        // Attente d'une connexion d'un client
        if (socket != null && !socket.isClosed()) {
            throw new ClientDejaConnecte("Un client est déjà connecté");
        }
        System.out.println("Attente client...");
        //Ajout d'un TimeOut
        serveur.setSoTimeout(TIMEOUT_SERVEUR_ACCEPT);
        socket = serveur.accept();
        //On enlève le TimeOut
        serveur.setSoTimeout(0);
        System.out.println("Client accepté");
    }
    

    /**
     * TODO comment method role
     * @return true si le client a reçu les questions
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws ClientPasConnecteException
     */
	public boolean envoiQuestion() throws IOException, ClassNotFoundException, 
	ClientPasConnecteException {
	    
	    if (socket == null || socket.isClosed()) {
            throw new ClientPasConnecteException("Le serveur n'est connecté à "
                    + "personne");
        }
	    ModelePrincipal modele = ModelePrincipal.getInstance();
    	boolean clientEstPret = false;
    	boolean clientARecu = false;
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Stream Créer");
        ArrayList<Question> elementAEnvoyer = new ArrayList<Question>();
        
        System.out.println("Envoie question : " + QUESTION_CLIENT_PRET);
        // Demande si le client est prêt
        oos.writeObject(QUESTION_CLIENT_PRET);

        //On vérifie si le client est prêt
        String reponseClient = (String) ois.readObject();
        System.out.println("Réponse client :  " + reponseClient);
        clientEstPret = reponseClient.equals(CLIENT_PRET);
        
        System.out.println(clientEstPret);

        if (clientEstPret) {
            
            
            int a =  Chiffrage.genererPuissance();
            int ga = Chiffrage.exposantModulo(Chiffrage.G, a, Chiffrage.P);
            
            //On envoie g^a au client
            oos.writeObject(ga);
            
            //Récuperè g^b
            int gb = (int) ois.readObject();
            
            //On cacule, la donnée partagé
            int gab = Chiffrage.exposantModulo(gb, a, Chiffrage.P);
            Chiffrage.setGab(gab);
            
            
            //Définition clé pour crypter le donnée
            String cleVigenere = Chiffrage.generationCle();
            
            //Cryptage de clé pour l'envoie
            String cleVigenereCrypte 
            = Chiffrage.chiffrement(cleVigenere, Chiffrage.cleDepuisDiffie());
            
            
            //Envoyer la clé Vigenère
            oos.writeObject(cleVigenereCrypte);
            System.out.println("Cle vigenère crypté : " + cleVigenereCrypte);
            System.out.println("Cle vigenère : " + cleVigenere);
            
            //Récupérer les question a envoyer
            elementAEnvoyer.addAll(modele.getQuestionAEnvoyer());
            
            ArrayList<String> questionCrypte 
            = Chiffrage.genererTableauCrypter(elementAEnvoyer, cleVigenere);
            
            //On envoie de le nombre d'élements à envoyer
            oos.writeObject(questionCrypte.size());
            
            for (Object object : questionCrypte) {
                oos.writeObject(object);
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
        System.out.println("Fin. La socket est fermé");
        
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
	
	/**
	 * TODO comment method role
	 * @param nouveauPort
	 */
	public static void setPort(int nouveauPort) {
	    port = nouveauPort;
	}
}
