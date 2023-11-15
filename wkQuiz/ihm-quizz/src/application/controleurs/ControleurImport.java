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
import java.util.Map;

import application.Quiz;
import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;
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

    @FXML
    private TextField saisieCheminFichier;

    private File fichierCSVChoisie;


    @FXML
    private void parcourirExplorer() {
        System.out.println("Parcourir Explorer");

        FileChooser fichier = new FileChooser();

        fichier.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        fichier.setTitle("Selectionner le fichier contenant les nouvelles questions");
        fichier.setInitialDirectory(new File(System.getProperty("user.home")));

        fichierCSVChoisie = fichier.showOpenDialog(null);

        if (fichierCSVChoisie != null) {
            saisieCheminFichier.setText(fichierCSVChoisie.getAbsolutePath());
        }

    }

    @FXML
    private void aider() {
        Quiz.changerVue("Aide.fxml");
    }

    @FXML
    private void retour() {
        Quiz.changerVue("Editeur.fxml");
    }

    @FXML
    private void valider() throws IOException {

        System.out.println("Valider");
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
     * Créer une question (et sa catégorie correspondante si nécessaire) Et gère les
     * exceptions lié à la création.
     * 
     * @param lignes Une liste de HashMap où chaque ligne est une ligne du CSV
     */
    private static void creerEtGererQuestionCategorie(ArrayList<HashMap<String, String>> lignes) {
        ModelePrincipal modele = ModelePrincipal.getInstance();
        // Création des nouvelles catégories et des nouvelles questions
        int indiceLigne = 0;
        int nombreQuestionCreer = 0;
        HashMap<Integer , String> erreurImportLigne = new HashMap<>();
        for (HashMap<String, String> ligneHashMap : lignes) {

            try {
                creerCategorieSiAbsent(ligneHashMap.get("categorie"));
            } catch (InvalidNameException e) {
                continue;
            }

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
                modele.creerQuestion(ligneHashMap.get("libelle"), indiceCategorie, difficulte,
                        ligneHashMap.get("reponseJuste"), reponseFausse, ligneHashMap.get("feedback"));
                nombreQuestionCreer++;
            } catch (InvalidFormatException | InvalidNameException | ReponseException | HomonymeException | DifficulteException e) {
                erreurImportLigne.put(indiceLigne , e.getMessage());
            }

            indiceLigne++;
        }
        afficherConfirmation(erreurImportLigne);
        Quiz.charger("EditerQuestions.fxml");
        Quiz.charger("EditerCategories.fxml");
    }

    /** 
     * Affiche la fenetre de retour utilisateur correspondante.
     * Si la hashMap est vide une simple fenetre de succes sinon
     * une fenetre d'erreur avec la ligne et l'erreur généré 
     * @param erreurImportLigne HashMap associant la ligne et 
     *        l'erreur correspondante
     */
    private static void afficherConfirmation(
            HashMap<Integer, String> erreurImportLigne) {
        StringBuilder messageErreur = new StringBuilder() ;
        if (erreurImportLigne.isEmpty()) {
            AlertBox.showSuccessBox("Toutes les questions ont "
                    + "été importées avec succès");
        } else {
            erreurImportLigne.forEach((key , value) -> {
                messageErreur.append("Erreur d'import a la ligne " + key + " : " 
                                     + value +"\n");
            });
            AlertBox.showErrorBox(messageErreur.toString());
        }

    }

    /**
     * Vérifie si une catégorie est présente dans le modèle et si elle n'est pas
     * présente, elle est créer.
     * 
     * @param nomCategorie
     * @return
     * @throws InvalidNameException
     */
    private static void creerCategorieSiAbsent(String nomCategorie) throws InvalidNameException {
        ModelePrincipal modele = ModelePrincipal.getInstance();
        // Si la catégorie n'existe pas on la créer
        if (!modele.categorieExiste(nomCategorie)) {
            try {
                modele.creerCategorie(nomCategorie);
            } catch (InvalidNameException e) {
                e.printStackTrace();
                throw e;
            } catch (HomonymeException e) {
                // Si la catégorie existe déjà on ne fais rien
            }
        }
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
                System.out.println(dicoLigne);
                nombreQuestionAjoute ++;
            }
        } while (ligne != null);
        fichierReader.close();
        AlertBox.showSuccessBox(nombreQuestionAjoute + " lignes analysé."); // TODO changer pour mettre le truc a lucas
        return resultat;
    }

    /**
     * 
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
}
