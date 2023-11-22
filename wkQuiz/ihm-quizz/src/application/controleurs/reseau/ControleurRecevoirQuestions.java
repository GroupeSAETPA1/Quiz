/*
 * ControleurRecevoirQuestions.java                                    21 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.io.IOException;
import java.net.SocketTimeoutException;

import application.Quiz;
import application.modele.ModelePrincipal;
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
    
    private ModelePrincipal model = ModelePrincipal.getInstance();
    
    private Client client;
    
    @FXML
    void initialize() {
        portServeur.setText(Serveur.getPort() + "");
    }
    
    /**
	 * Méthode liée au groupe aider,
	 * envoie vers la page Aide.fxml
	 */
	@FXML
	private void aider() {
		model.setPagePrecedente("RecevoirQuestions.fxml");
		System.out.println("Aider");
		Quiz.chargerEtChangerVue("Aide.fxml");
	}
	
	@FXML
    void retour() {
    	Quiz.changerVue("ChoixEnvoie.fxml");
        System.out.println("Retour");
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
                System.out.println("On est connecter");
                client.recevoirDonnees();
            } catch (SocketTimeoutException e) {
                AlertBox.showErrorBox("TimeOut : Pas de serveur trouvé");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                
            }
        }
    }

}
