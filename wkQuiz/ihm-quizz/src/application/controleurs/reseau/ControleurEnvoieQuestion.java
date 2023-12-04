/*
 * ControleurEnvoieQuestion.java                                    21 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import application.Quiz;
import application.exception.ClientDejaConnecte;
import application.exception.ClientPasConnecteException;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import outil.Serveur;

/** 
 * Gére la création du serveur pour envoyer les question à un client
 * @author François de Saint Palais
 */
public class ControleurEnvoieQuestion {

    @FXML Text txtPort;
    @FXML Text information;
    
    @FXML TextField txtIP;
    
    private static Serveur serveur;
    
    private ModelePrincipal model = ModelePrincipal.getInstance();

    @FXML
    void initialize() throws ClassNotFoundException, IOException {
        
        String nomOS = System.getProperty("os.name");
        
        String adresseIP;
        
        if (nomOS.contains("Windows")) {
            adresseIP = getIPFromWindows();
        } else if (nomOS.contains("Linux") || nomOS.contains("Mac OS X")) {
            adresseIP = getIPFromLinux();
        } else {
            adresseIP = "OS non reconnu";
        }
        
        
        txtIP.setText(adresseIP);
        txtPort.setText(Serveur.getPort() + "");
        
        if (serveur == null) {
            do {
                try {
                    serveur = new Serveur(Serveur.getPort());
                    txtIP.setText(serveur.getIPServeur());
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
     * Revoie l'adresse IPV4 sur un système type Linux
     * @return L'adresse IPV4 de la machine.
     */
    public String getIPFromLinux() {
        String resultat = "";
        try {
            Enumeration<NetworkInterface> interfaces 
            = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();

                // Filtrer les interfaces loopback et les interfaces désactivées
                if (!iface.isLoopback() && iface.isUp()) {
                    
                    Enumeration<InetAddress> addresses = iface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        // Filtrer les adresses IPv6
                        if (!addr.getHostAddress().contains(":")) {
                            resultat = addr.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultat;
    }
    
    /**
     * Getter de l'IP si l'OS est Windows
     * @return L'adresse IP de l'utilisateur
     * @throws UnknownHostException 
     */
    public static String getIPFromWindows() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
    

}
