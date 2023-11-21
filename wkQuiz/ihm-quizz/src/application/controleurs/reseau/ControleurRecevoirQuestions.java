/*
 * ControleurRecevoirQuestions.java                                    21 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.io.IOException;

import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import outil.Client;
import outil.Serveur;

/** 
 * Représente le client qui reçoit les question
 * @author francois
 */
public class ControleurRecevoirQuestions {

    @FXML private TextField portServeur;
    @FXML private TextField ipServeur;
    
    private Client client;
    
    @FXML
    void initialize() {
        portServeur.setText(Serveur.getPort() + "");
    }
    
    @FXML
    void connexion() throws ClassNotFoundException {
        System.out.println("Connexion");
        boolean clientCreer = false;
        try {
            client = new Client(ipServeur.getText(), Integer.parseInt(portServeur.getText()));
            clientCreer = true;
            System.out.println("Client créer");
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            AlertBox.showErrorBox(e.getMessage());
        }
        if (clientCreer) {
            try {
                client.seConnecter();
                System.out.println("");
                client.recevoirDonnees();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
