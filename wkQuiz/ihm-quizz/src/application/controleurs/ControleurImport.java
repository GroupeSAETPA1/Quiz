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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private ModelePrincipal modele = ModelePrincipal.getInstance();

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
            System.out.println(fichierCSVChoisie.getAbsolutePath());
        }

    }

    @FXML
    private void aider() {
        System.out.println("Aider");
        Quiz.changerVue("Aide.fxml");
    }

    @FXML
    private void retour() {
        System.out.println("Retour");
        Quiz.changerVue("Editeur.fxml");
    }

    @FXML
    private void valider() throws IOException {

        System.out.println("Valider");
        String cheminFichierCSV = saisieCheminFichier.getText();

        File fichier = new File(cheminFichierCSV);

        if (cheminFichierCSV.isBlank()) {
            AlertBox.showWarningBox("Aucun fichier selectionner");

            // Le fichier n'existe pas
        } else if (!fichier.exists()) {
            AlertBox.showErrorBox(cheminFichierCSV + ", n'existe pas.");

            // Le fichier n'est pas un csv
        } else if (!cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf(".") + 1).equals("csv")) {
            AlertBox.showErrorBox(
                    cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf("\\") + 1) + ", n'est pas un fichier CSV");

            // Création des question
        } else {

            // Récupération des lignes du CSV
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
        for (HashMap<String, String> ligneHashMap : lignes) {

            try {
                creerCategorieSiAbsent(ligneHashMap.get("categorie"));
            } catch (InvalidNameException e) {
                continue;
            }

            ArrayList<String> reponseFausse = new ArrayList<String>();
            if (ligneHashMap.get("1reponseFausse") != null) {
                reponseFausse.add(ligneHashMap.get("1reponseFausse"));
            }
            if (ligneHashMap.get("2reponseFausse") != null) {
                reponseFausse.add(ligneHashMap.get("2reponseFausse"));
            }
            if (ligneHashMap.get("3reponseFausse") != null) {
                reponseFausse.add(ligneHashMap.get("3reponseFausse"));
            }
            if (ligneHashMap.get("4reponseFausse") != null) {
                reponseFausse.add(ligneHashMap.get("4reponseFausse"));
            }

            int indiceCategorie = modele.getIndice(ligneHashMap.get("categorie"));

            int difficulte;
            try {
            	System.out.println(ligneHashMap.get("difficulte"));
            	difficulte = Integer.parseInt(ligneHashMap.get("difficulte"));				
			} catch (NumberFormatException e) {
				continue;
			}

            try {
                modele.creerQuestion(ligneHashMap.get("libelle"), indiceCategorie, difficulte,
                        ligneHashMap.get("reponseJuste"), reponseFausse, ligneHashMap.get("feedback"));
                nombreQuestionCreer++;
            } catch (InvalidFormatException | InvalidNameException | ReponseException | HomonymeException | DifficulteException e) {
                System.err.println("Question n°" + indiceLigne + e.getMessage());
                AlertBox.showErrorBox("Erreur de création de la question n°" + indiceLigne + "\nPour plus "
                        + "d'information consulter la page d'aide");
            }

            indiceLigne++;
        }

        AlertBox.showSuccessBox(nombreQuestionCreer + "/" + indiceLigne + " questions créer");
        System.out.println(modele.getCategories());
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
    private static void creerCategorieSiAbsent(String nomCategorie) throws InvalidNameException {
        ModelePrincipal modele = ModelePrincipal.getInstance();
        // Si la catégorie n'existe pas on la créer
        if (!modele.categorieExiste(nomCategorie)) {
            try {
                modele.creerCategorie(nomCategorie);
                System.out.println("nouvelle categorie" + modele.getBanqueCategorie().getCategorieLibelleExact(nomCategorie));
            } catch (InvalidNameException e) {
                AlertBox.showErrorBox(
                        nomCategorie + " : nom de catégorie invalide\nL'insertion de la catégorie est impossible");
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

        int numeroLigne = 1;
        int nombreQuestionAjoute = 0;
        do {
            ligne = fichierReader.readLine();

            if (ligne != null) {
                HashMap<String, String> dicoLigne = getDicotionnaire(ligne);
                resultat.add(dicoLigne);
                nombreQuestionAjoute ++;
            }
            numeroLigne++;
        } while (ligne != null);
        fichierReader.close();
        AlertBox.showSuccessBox(nombreQuestionAjoute + " lignes analysé.");
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
            resultat.put("1reponseFausse", null);
        }
        try {
            resultat.put("2reponseFausse", ligneListe[5]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("2reponseFausse", null);
        }
        try {
            resultat.put("3reponseFausse", ligneListe[6]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("3reponseFausse", null);
        }
        try {
            resultat.put("4reponseFausse", ligneListe[7]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("4reponseFausse", null);
        }
        try {
            resultat.put("feedback", ligneListe[8]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("feedback", "");
        }
        return resultat;
    }
}
