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
import java.util.ArrayList;

import application.vue.AlertBox;

/** 
 * TODO comment class responsibility (SRP)
 * @author Tom Douaud
 * @author Francois
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
    }
    
	public void recevoirDonnees() throws UnknownHostException, IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        //On attend la demande du serveur pour savoir si on est prêt
        String questionRecevoir = (String) ois.readObject();
        System.out.println(questionRecevoir);
        
        if (AlertBox.showConfirmationBox("Recevoir les questions ?")) {
            //On indique on serveur que le client est prêt
            oos.writeObject(CLIENT_PRET_MESSAGE);
            
            int nbQuestion = (int) ois.readObject();
            ArrayList<Object> elementsRecu = new ArrayList<Object>(nbQuestion);
            
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
        
        // fermetures des ressources
        ois.close();
        oos.close();
        
        socket.close();

        // TODO vérifier que les questions sont valides et importer
    }
}