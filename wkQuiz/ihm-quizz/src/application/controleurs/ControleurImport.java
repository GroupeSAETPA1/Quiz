/*
 * ControleurImport.java                                    10 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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


/**
 * Controlleur de Import Gère le fichier CSV et créer les nouvelles questions et
 * catégorie présente dans le fichier
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

        //Ajout d'un filtre sur l'extensions des fichier sélectionnable
        fichier.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"));
        //Le titre de la fenêtre pour sélectionner le fichier
        fichier.setTitle("Selectionner le fichier contenant les nouvelles "
                + "questions");
        //On sélectionne le chemin de départ de la fenêtre
        fichier.setInitialDirectory(new File(System.getProperty("user.home")));

        //Ouvre une fenêtre pour sélectionner un fichier
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
        	AlertBox.showWarningBox("Aucun fichier selectionner");
        } else if (!fichier.exists()) {
        	AlertBox.showErrorBox(cheminFichierCSV + ", n'existe pas.");
        } else if (!cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf(".") + 1).equals("csv")) {
        	AlertBox.showErrorBox(
        			 cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf("\\") + 1) + ", n'est pas un fichier CSV");
        } else {
            
            ArrayList<HashMap<String, String>> lignes = getLigneCSV(fichier);
            creerEtGererQuestionCategorie(lignes);
        
        }
    }

    /**
     * Créer une question (et sa catégorie correspondante si nécessaire) 
     * Et gère les exceptions lié à la création.
     * 
     * @param lignes Une liste de HashMap où chaque ligne est une ligne du CSV
     */
    private static void creerEtGererQuestionCategorie(
            ArrayList<HashMap<String, String>> lignes) {

        int indiceLigne = 0;
        boolean erreurCreationCategorie = false;

        HashMap<Integer , String> erreurImportLigne = new HashMap<>();
        
        //Parcours des lignes du CSV
        for (HashMap<String, String> ligneHashMap : lignes) {

            try {
                
                erreurCreationCategorie 
                = !creerCategorieSiAbsent(ligneHashMap.get("categorie"));
                
            } catch (InvalidNameException e) {
                //Si on n'arrive pas à créer une nouvelle catégorie 
                //car le nom est invalide, on passe à la question suivante
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
        
        //On met à jour les pages d'Édition
        Quiz.charger("EditerQuestions.fxml");
        Quiz.charger("EditerCategories.fxml");
    }

    /**
     * Vérifie si une catégorie est présente dans le modèle et si elle n'est pas
     * présente, elle est créer.
     * 
     * @param nomCategorie
     * @return
     * @throws InvalidNameException
     */
    private static boolean creerCategorieSiAbsent(String nomCategorie) throws InvalidNameException {
        //True si la catégorie existe ou si la création est fructueuse
        boolean creationOK = true;
        // Si la catégorie n'existe pas on la créer
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
     * @param fichierCSV
     * @return Une liste des ligne du fichier CSV
     * @throws IOException
     */
    private static ArrayList<HashMap<String, String>> getLigneCSV(File fichierCSV) throws IOException {
        String ligne;
        ArrayList<HashMap<String, String>> resultat = new ArrayList<HashMap<String, String>>();
        BufferedReader fichierReader = new BufferedReader(new FileReader(fichierCSV.getAbsolutePath()));

        int nombreQuestionAjoute = 0;
        do {
            ligne = fichierReader.readLine();

            if (ligne != null) {
                HashMap<String, String> dicoLigne = getDicotionnaire(ligne);
                resultat.add(dicoLigne);
                nombreQuestionAjoute ++;
            }
        } while (ligne != null);
        fichierReader.close();
        AlertBox.showSuccessBox(nombreQuestionAjoute + " lignes analysé."); // TODO changer pour mettre le truc a lucas
        return resultat;
    }

    /**
     * Récupère une ligne du CSV et retourne une HashMap associant chaque élément
     * d'une question
     * 
     * @param ligne
     * @return
     */
    private static HashMap<String, String> getDicotionnaire(String ligne) {
        HashMap<String, String> resultat = new HashMap<String, String>();
        String[] ligneListe = ligne.split(ModelePrincipal.SEPARATEUR_CSV + "");
        resultat.put("categorie", ligneListe[0]);
        resultat.put("difficulte", ligneListe[1]);
        resultat.put("libelle", ligneListe[2]);
        resultat.put("reponseJuste", ligneListe[3]);

        try {
            resultat.put("1reponseFausse", ligneListe[4]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("1reponseFausse", "");
        }
        try {
            resultat.put("2reponseFausse", ligneListe[5]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("2reponseFausse", "");
        }
        try {
            resultat.put("3reponseFausse", ligneListe[6]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("3reponseFausse", "");
        }
        try {
            resultat.put("4reponseFausse", ligneListe[7]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("4reponseFausse", "");
        }
        try {
            resultat.put("feedback", ligneListe[8]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("feedback", "");
        }
        return resultat;
    }

    /** 
     * Affiche la fenêtre de retour utilisateur correspondante.
     * Si la hashMap est vide une simple fenêtre de succès sinon
     * une fenêtre d'erreur avec la ligne et l'erreur généré 
     * @param erreurImportLigne HashMap associant la ligne et 
     *        l'erreur correspondante
     */
    private static void afficherConfirmation(
            HashMap<Integer, String> erreurImportLigne) {
        
        if (erreurImportLigne.isEmpty()) {
        
            AlertBox.showSuccessBox("Toutes les questions ont "
                    + "été importées avec succès");
        } else {
            
            StringBuilder messageErreur = new StringBuilder() ;
            
            //Pour les utilisateur de l'application, une liste commence à 1
            erreurImportLigne.forEach((key , value) -> {
                messageErreur.append("Erreur d'import a la ligne n°" + (key + 1)
                                     + " : " + value +"\n");
            });
            
            AlertBox.showErrorBox(messageErreur.toString());
        }
    
    }
}
