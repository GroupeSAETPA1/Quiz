/*
 * Client.java                                    20 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package outil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import application.modele.Chiffrage;
import application.vue.AlertBox;

/** 
 * 
 * @author Tom Douaud
 * @author Francois de Saint Palais
 */
public class Client {
    
	/** TODO comment field role (attribute, associative role) */
    public static final String CLIENT_PRET_MESSAGE = "OUI JE SUIS PRET";

    // Le socket 
	private Socket socket;
    
	// Le port d'écoute du client par défaut = 507705
	private int port = 507705;
	
	// L'adresse ip du serveur
	private String ip;
	
	/**
	 * Le temps que le client attend le serveur pour se connecter 
	 * avant d'abandonner
	 * Durée en millisecond (5000ms = 5s)
	 */
	private static final int TIMEOUT_CONNEXION = 5000;
	
    
    public Client(String ip, int port) {
        if (ip.isEmpty()) {
            throw new IllegalArgumentException("L'adresse IP est vide");
        }
        if (port <= 0) {
            throw new IllegalArgumentException("Le port n'est pas valide");            
        }
    	this.port = port;
    	this.ip = ip;
    }
    
    public void seConnecter() throws UnknownHostException, IOException, SocketTimeoutException {
        // Lance la connexion socket connection au serveur
        socket = new Socket();
        InetSocketAddress adresseServeur = new InetSocketAddress(ip, port);
        socket.connect(adresseServeur, TIMEOUT_CONNEXION);
    }
    
    /**
     * TODO comment method role
     * @throws UnknownHostException
     * @throws IOException
     * @throws ClassNotFoundException
     */
	public ArrayList<Object> recevoirDonnees() 
	        throws UnknownHostException, IOException, ClassNotFoundException {
	    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        
        ArrayList<Object> elementsRecu = null;

        //On attend la demande du serveur pour savoir si on est prêt
        String questionRecevoir = (String) ois.readObject();
        System.out.println(questionRecevoir);
        
        if (AlertBox.showConfirmationBox("Recevoir les questions ?")) {
            //On indique on serveur que le client est prêt
            oos.writeObject(CLIENT_PRET_MESSAGE);
            
            int b =  Chiffrage.genererPuissance();
            int gab;
            
            //On récupère g^a envoyé par le serveur
            int ga = (int) ois.readObject();
            gab = Chiffrage.exposantModulo(ga, b, Chiffrage.P);
            Chiffrage.setGab(gab);
            
            int gb = Chiffrage.exposantModulo(Chiffrage.G, b, Chiffrage.P);
            //On envoie g^b au serveur
            oos.writeObject(gb);
            
            
            String cleVigenereCrypte = (String) ois.readObject();
            
            
            //Récupération du nombre de question
            int nbQuestion = (int) ois.readObject();
            elementsRecu = new ArrayList<Object>(nbQuestion);
            
            for (int i = 0; i < nbQuestion; i++) {
                Object eltRecu = ois.readObject();
                elementsRecu.add(eltRecu);
                System.out.println("Element reçu : " + eltRecu);
            }
        } else {
            oos.writeObject("Non");
        }

        String messageFin = (String) ois.readObject();
        oos.writeObject(Serveur.MESSAGE_FIN_COMMUNICATION);
        System.out.println(messageFin);
        
        // Fermeture des chemins de communication
        ois.close();
        oos.close();
        
        socket.close();//Fin de communication
        System.out.println("Fin. La socket est fermé");
        
        return elementsRecu;
    }
}