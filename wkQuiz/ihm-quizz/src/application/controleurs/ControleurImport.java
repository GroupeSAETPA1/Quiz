/*
 * ControleurImport.java                                    10 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import application.Quiz;
import application.exception.CreerQuestionException;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import outil.LectureFichier;


/**
 * Controleur de l'import. Gère le fichier CSV et crée les nouvelles questions et
 * catégories présentes dans le fichier
 * 
 * @author François de Saint Palais
 */
public class ControleurImport {

    @FXML private TextField saisieCheminFichier;

    private File fichierCSVChoisie;
    
    private static ModelePrincipal modele = ModelePrincipal.getInstance();


    @FXML
    private void parcourirExplorer() {

        FileChooser fichier = new FileChooser();

        // Ajout d'un filtre sur l'extension des fichier sélectionnables
        fichier.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"));
        // e titre de la fenêtre pour sélectionner le fichier
        fichier.setTitle("Selectionner le fichier contenant les nouvelles "
                + "questions");
        // On sélectionne le chemin de départ de la fenêtre
        fichier.setInitialDirectory(new File(System.getProperty("user.home")));

        // Ouvre une fenêtre pour sélectionner un fichier
        fichierCSVChoisie = fichier.showOpenDialog(null);

        if (fichierCSVChoisie != null) {
            saisieCheminFichier.setText(fichierCSVChoisie.getAbsolutePath());
        }

    }

    @FXML
    private void aider() {
    	modele.setPagePrecedente("ImporterQuestion.fxml"); 
        Quiz.chargerEtChangerVue("Aide.fxml");
    }

    @FXML
    private void retour() {
        Quiz.changerVue("Editeur.fxml");
    }

    @FXML
    private void valider() throws IOException {

        String cheminFichierCSV = saisieCheminFichier.getText();

        File fichier = new File(cheminFichierCSV);

        if (cheminFichierCSV.isBlank()) {
        	AlertBox.showWarningBox("Aucun fichier selectionné");
        } else if (!fichier.exists()) {
        	AlertBox.showErrorBox(cheminFichierCSV + ", n'existe pas.");
        } else if (!cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf(".") 
        									   + 1).equals("csv")) {
        	AlertBox.showErrorBox(
        			 cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf("\\") + 1) 
        			 + ", n'est pas un fichier CSV");
        } else {
            
        	try {
	            ArrayList<HashMap<String, String>> lignes = 
	            		LectureFichier.getLigneCSV(fichier);
	            
	            if (!lignes.isEmpty()) {
	            	creerEtGererQuestionCategorie(lignes);
	            	Quiz.charger("CreationQuestionEtCategorie.fxml");
	            }
        	} catch (ArrayIndexOutOfBoundsException e) {
        		AlertBox.showErrorBox(
           			 cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf("\\") 
           					 					+ 1) + " est un fichier CSV " 
           					 					+ "invalide.\nVeuillez fournir " 
           					 					+ "un fichier valide comme " 
           					 					+ "décrit dans la page d'aide.");
        	}
        }
    }

    /**
     * Créer une question (et sa catégorie correspondante si nécessaire) 
     * et gère les exceptions liés à la création.
     * 
     * @param lignes Une liste de HashMap où chaque ligne est une ligne du CSV
     */
    private static void creerEtGererQuestionCategorie(
            ArrayList<HashMap<String, String>> lignes) {
        // TODO utiliser la fonction présente dans LectureFichier
        int indiceLigne = 0;
        boolean erreurCreationCategorie = false;

        HashMap<Integer , String> erreurImportLigne = new HashMap<>();
        
        // Parcours des lignes du CSV
        for (HashMap<String, String> ligneHashMap : lignes) {

            try {
                
                erreurCreationCategorie 
                = !creerCategorieSiAbsent(ligneHashMap.get("categorie"));
                
            } catch (InvalidNameException e) {
                // Si on n'arrive pas à créer une nouvelle catégorie 
                // car le nom est invalide, on passe à la question suivante
                erreurImportLigne.put(indiceLigne , e.getMessage());
                erreurCreationCategorie = true;
            }

            if (!erreurCreationCategorie) {
                ArrayList<String> reponseFausse = new ArrayList<String>();
                if (!ligneHashMap.get("1reponseFausse").isBlank()) {
                    reponseFausse.add(ligneHashMap.get("1reponseFausse"));
                }
                if (!ligneHashMap.get("2reponseFausse").isBlank()) {
                    reponseFausse.add(ligneHashMap.get("2reponseFausse"));
                }
                if (!ligneHashMap.get("3reponseFausse").isBlank()) {
                    reponseFausse.add(ligneHashMap.get("3reponseFausse"));
                }
                if (!ligneHashMap.get("4reponseFausse").isBlank()) {
                    reponseFausse.add(ligneHashMap.get("4reponseFausse"));
                }
                
                int indiceCategorie = modele.getIndice(ligneHashMap.get("categorie"));
                
                int difficulte;
                try {
                    difficulte = Integer.parseInt(ligneHashMap.get("difficulte"));				
                } catch (NumberFormatException e) {
                    continue;
                }
                
                try {
                    modele.creerQuestion(
                            ligneHashMap.get("libelle"), 
                            indiceCategorie, difficulte,
                            ligneHashMap.get("reponseJuste"), 
                            reponseFausse, 
                            ligneHashMap.get("feedback"));
                    
                } catch (CreerQuestionException | InvalidNameException | HomonymeException e) {
                    erreurImportLigne.put(indiceLigne , e.getMessage());
                }
            }

            indiceLigne++;
        }
        afficherConfirmation(erreurImportLigne);
        
        // On met à jour les pages d'Édition
        Quiz.charger("EditerQuestions.fxml");
        Quiz.charger("EditerCategories.fxml");
    }

    /**
     * Vérifie si une catégorie est présente dans le modèle et si elle n'est pas
     * présente, elle est crée.
     * 
     * @param nomCategorie
     * @return True si la catégorie existe ou si la création est fructueuse
     * @throws InvalidNameException
     */
    private static boolean creerCategorieSiAbsent(String nomCategorie) throws InvalidNameException {
        boolean creationOK = true;
        // Si la catégorie n'existe pas on la crée
        if (!modele.categorieExiste(nomCategorie)) {
            try {
                creationOK = modele.creerCategorie(nomCategorie);
            } catch (InvalidNameException e) {
                System.err.println(e.getMessage());
                throw e;
            } catch (HomonymeException e) {
                // Si la catégorie existe déjà on ne fais rien
            }
        }
        return creationOK;
    }

    /** 
     * Affiche la fenêtre de retour utilisateur correspondante.
     * Si la hashMap est vide une simple fenêtre de succès sinon
     * une fenêtre d'erreur avec la ligne et l'erreur générée 
     * @param erreurImportLigne HashMap associant la ligne et 
     *        l'erreur correspondante
     */
    private static void afficherConfirmation(
            HashMap<Integer, String> erreurImportLigne) {
        
        if (erreurImportLigne.isEmpty()) {
        
            AlertBox.showSuccessBox("Toutes les questions ont "
                    + "été importées avec succès");
        } else {
            
            String messageLignesImportees = LectureFichier.getNombreQuestionAjoute() 
					 						+ " lignes analysées.\n";
            StringBuilder logErreurLignesImportees = new StringBuilder();
           
            
            // Pour les utilisateur de l'application, une liste commence à 1
            erreurImportLigne.forEach((key , value) -> {
            	logErreurLignesImportees.append("Erreur d'import à la ligne n°" 
            						 + (key + 1)
                                     + " : " + value +"\n");
            });
            
            AlertBox.showLongErrorBox(messageLignesImportees, 
            			logErreurLignesImportees.toString());
        }
    
    }
}
