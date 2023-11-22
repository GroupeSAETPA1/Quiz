/*
 * ControleurEnvoieQuestion.java                                    21 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.io.IOException;
import java.net.InetAddress;

import application.Quiz;
import application.exception.ClientPasConnecterException;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Text;
import outil.Serveur;

/** 
 * TODO comment class responsibility (SRP)
 * @author François de Saint Palais
 */
public class ControleurEnvoieQuestion {

    @FXML Text txtPort;
    @FXML Text information;
    
    @FXML TextField txtIP;
    
    private Serveur serveur;
    
    private ModelePrincipal model = ModelePrincipal.getInstance();

    @FXML
    void initialize() throws ClassNotFoundException, IOException {
        InetAddress ip = InetAddress.getLocalHost();
        
        System.out.print("Mon adresse IP est: ");
        System.out.println(ip.getHostAddress());
        
        txtIP.setText(ip.getHostAddress());
        txtPort.setText(Serveur.getPort() + "");
        
        serveur = new Serveur(Serveur.getPort());
        
        
    }
    
    @FXML
    void retour() {
    	Quiz.changerVue("ChoixEnvoie.fxml");
        System.out.println("Retour");
    }
    
    /**
	 * Méthode liée au groupe aider,
	 * envoie vers la page Aide.fxml
	 */
	@FXML
	private void aider() {
		model.setPagePrecedente("EnvoieQuestion.fxml");
		System.out.println("Aider");
		Quiz.chargerEtChangerVue("Aide.fxml");
	}

    @FXML
    void envoyer() {
        System.out.println(serveur.getIPClient());
        try {
            serveur.envoiQuestion();
        } catch (ClientPasConnecterException e) {
            AlertBox.showErrorBox("Pas de client connecté");
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void lancerServeur() throws ClassNotFoundException, IOException {
        System.out.println("Lancement serveur ...");
        information.setText("En attente d'un client ...");
        AlertBox.showSuccessBox("Prêt à recevoir un client ?");
        serveur.lancerServeur();
        
        information.setText("Adresse IP du client : " + serveur.getIPClient());
    }
    
    
    
    

}
