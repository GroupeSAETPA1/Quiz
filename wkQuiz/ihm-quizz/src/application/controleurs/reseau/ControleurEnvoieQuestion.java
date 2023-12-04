/*
 * ControleurEnvoieQuestion.java                                    21 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import application.Quiz;
import application.exception.ClientDejaConnecte;
import application.exception.ClientPasConnecteException;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import outil.Serveur;

/** 
 * Gére la création du serveur pour envoyer les question à un client
 * @author François de Saint Palais
 */
public class ControleurEnvoieQuestion {

    @FXML Text txtPort;
    @FXML Text information;
    
    @FXML ComboBox<String> listeIP;
    
    private static Serveur serveur;
    
    private ModelePrincipal model = ModelePrincipal.getInstance();

    @FXML
    void initialize() throws ClassNotFoundException, IOException {
        
        miseAJourListeIP();
        
        txtPort.setText(Serveur.getPort() + "");
        
        if (serveur == null) {
            do {
                try {
                    serveur = new Serveur(Serveur.getPort());
                } catch (BindException e) {
                    Serveur.setPort(Serveur.getPort() + 1);
                }
                txtPort.setText(Serveur.getPort() + "");
            } while (serveur == null);
        }
        
        information.setText("");
    }
    
    @FXML
    void retour() {
    	Quiz.changerVue("ChoixEnvoie.fxml");
    }
    
    /**
	 * Méthode liée au groupe aider,
	 * envoie vers la page Aide.fxml
	 */
	@FXML
	private void aider() {
		model.setPagePrecedente("EnvoieQuestion.fxml");
		Quiz.chargerEtChangerVue("Aide.fxml");
	}

    @FXML
    void connexionEtEnvoie() throws ClassNotFoundException, IOException {
        
        if (!serveur.clientEstConnecte()) {
            information.setText("En attente d'un client ...");
            AlertBox.showSuccessBox("Êtes-vous prêt à recevoir un client ?");
            try {
                serveur.lancerServeur();
                AlertBox.showSuccessBox("Client connecté");
                information.setText("Adresse IP du client : " + serveur.getIPClient());
                envoyer();
            } catch (ClientDejaConnecte e) {
                AlertBox.showWarningBox("Un client est déjà connecté.");
            } catch (SocketTimeoutException e) {
                AlertBox.showErrorBox("TimeOut : Aucun client n'a tenté de se "
                        + "connecter");
            }
        } else {
            AlertBox.showWarningBox("Un client est déjà connecté.");
        }
        
        
    }

    void envoyer() {
        try {
            boolean envoieReussi = serveur.envoiQuestion();
             if (!envoieReussi) {
                 AlertBox.showWarningBox("Le client à refusé les questions");
             }
             information.setText("Pas de client connecté");
        } catch (ClientPasConnecteException e) {
            AlertBox.showErrorBox("Pas de client connecté");
        } catch (ClassNotFoundException | IOException e) {
            AlertBox.showErrorBox(e.getMessage());
        }
        
        
    }
    
    /**
     * @return La liste de toutes les adresses IP
     */
    public static ArrayList<String> getIPs() {
        ArrayList<String> reponse = new ArrayList<String>();
        Enumeration<NetworkInterface> networkInterface;
        try {
            networkInterface = NetworkInterface.getNetworkInterfaces();
        
            while (networkInterface.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) networkInterface.nextElement();
                Enumeration<InetAddress> inetAdress = n.getInetAddresses();
                while (inetAdress.hasMoreElements()) {
                    InetAddress i = (InetAddress) inetAdress.nextElement();
                    if (!i.getHostAddress().contains(":")) {
                        reponse.add(i.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            reponse.add("Aucune adresse IP n'a été trouvé !");
        }
        return reponse;
    }
    
    /**
     * Mise à jour de la liste des IP
     */
    public void miseAJourListeIP() {
        ArrayList<String> adresseIP = new ArrayList<String>();
        try {
            adresseIP.add(adresseIpLocale());
        } catch (Exception e) {
            adresseIP = getIPs();
        }
        listeIP.getItems().clear();
        listeIP.getItems().addAll(adresseIP);
    }
    
    /**
     * Pour récupérer l'adresse IP,
     * on execute une requête via une socket, puis on récupère l'adresse.
     * @return L'adresse ip local du PC
     * @throws UnknownHostException
     * @throws SocketException
     */
    public static String adresseIpLocale()
    throws UnknownHostException, SocketException {

        DatagramSocket socket = new DatagramSocket();
        
        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);

        String adresseIP = socket.getLocalAddress().getHostAddress();

        socket.close();
        
        return adresseIP;
    }
    

}
