/*
 * ControleurEnvoieQuestion.java                                    21 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.io.IOException;
import java.net.InetAddress;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import outil.Serveur;

/** 
 * TODO comment class responsibility (SRP)
 * @author François de Saint Palais
 */
public class ControleurEnvoieQuestion {

    @FXML Text txtPort;
    @FXML TextField txtIP;
    
    private Serveur serveur;

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
        //TODO Faire retour
        System.out.println("Retour");
    }

    @FXML
    void annuler() throws ClassNotFoundException, IOException {
        //TODO Faire annuler
        System.out.println("Annuler");
        serveur.lancerServeur();
    }

}
