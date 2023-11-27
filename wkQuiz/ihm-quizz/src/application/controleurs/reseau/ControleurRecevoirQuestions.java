/*
 * ControleurRecevoirQuestions.java                                    21 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.reseau;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

import application.Quiz;
import application.exception.HomonymeException;
import application.modele.Chiffrage;
import application.modele.ModelePrincipal;
import application.modele.Question;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import outil.Client;
import outil.LectureFichier;
import outil.Serveur;

/** 
 * Représente le client qui reçoit les question
 * @author François de Saint Palais
 */
public class ControleurRecevoirQuestions {

    @FXML private TextField portServeur;
    @FXML private TextField ipServeur;
    
    private ModelePrincipal modele = ModelePrincipal.getInstance();
    
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
		modele.setPagePrecedente("RecevoirQuestions.fxml");
		System.out.println("Aider");
		Quiz.chargerEtChangerVue("Aide.fxml");
	}
	
	@FXML
    void retour() {
    	Quiz.changerVue("ModeEnLigne.fxml");
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
            AlertBox.showErrorBox(e.getMessage());
        }
        
        
        if (clientCreer) {
            try {
                ArrayList<Object> elementRecu;
                ArrayList<String> questionClair;
                
                client.seConnecter();
                System.out.println("On est connecter");
                elementRecu = client.recevoirDonnees();
                questionClair = new ArrayList<String>(elementRecu.size());
                
                if (elementRecu != null) {
                    //TODO décrypter
                    for (Object questionCrypte : elementRecu) {
                        questionClair.add(
                                Chiffrage.decrypterFichier((String) questionCrypte, 
                                        Client.getCleVigenere()));
                    }
                    
                    //TODO Ajouter à la banque
                    int nbErreur = 0;
                    int nbDejaPresent = 0;
                    for (String ligne : questionClair) {
                        try {
                            HashMap<String, String> dictionnaire 
                            = LectureFichier.getDictionnaire(ligne);
                            
                            Question question = LectureFichier
                                    .creerQuestionFromLigneCSV(dictionnaire);
                            
                            modele.ajouterQuestion(question);
                        } catch (HomonymeException e) {
                            nbDejaPresent ++;
                        } catch (Exception e) {
                            nbErreur ++;
                        }
                    }
                    if (nbErreur != 0) {                        
                        AlertBox.showWarningBox(String.format(
                                "Sur les %d question envoyés, %d n'ont pas pu "
                                + "être ajouté et %d existe déjà", 
                                questionClair.size(), nbErreur, nbDejaPresent));
                    }
                }
                
            } catch (SocketTimeoutException e) {
                AlertBox.showErrorBox("TimeOut : Pas de serveur trouvé");
            } catch (IOException e) {
                AlertBox.showErrorBox(e.getMessage());
            }
        }
    }

}
