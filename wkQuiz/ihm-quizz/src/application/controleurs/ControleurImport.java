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
import java.io.FileNotFoundException;

import application.Quiz;
import application.exception.HomonymeException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
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
        // TODO rediriger vers Aide.fxml
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
     * TODO comment method role
     * @param lignes
     */
    private void creerEtGererQuestionCategorie(ArrayList<HashMap<String, String>> lignes) {
        // Création des nouvelles catégories et des nouvelles questions
        for (HashMap<String, String> ligneHashMap : lignes) {
            // Si la catégorie n'existe pas on la créer
            if (!modele.categorieExiste(ligneHashMap.get("categorie"))) {
                try {
                    modele.creerCategorie(ligneHashMap.get("categorie"));
                } catch (InvalidNameException e) {
                    AlertBox.showErrorBox(ligneHashMap.get("categorie") + " : nom de catégorie invalide");
                    e.printStackTrace();
                    continue;
                } catch (HomonymeException e) {
                    // Si la catégorie existe déjà on ne fais rien
                }
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

            // TODO Récupérer l'indice de la catégorie
            int indiceCategorie = modele.getIndice(ligneHashMap.get("categorie"));

            int difficulte = Integer.parseInt(ligneHashMap.get("difficulte"));

            boolean questionCreer = false;

            try {
                questionCreer = modele.creerQuestion(
                        ligneHashMap.get("libelle"), indiceCategorie, 
                        difficulte, ligneHashMap.get("reponseJuste"), 
                        reponseFausse, ligneHashMap.get("feedback"));
            } catch (InvalidFormatException e) {
                AlertBox.showErrorBox("Veuillez saisir au minimum une réponse fausse.");
            } catch (InvalidNameException e) {
                AlertBox.showErrorBox(
                        "Attention, veuillez saisir le nom de la " + "question ET une réponse juste.");
            } catch (ReponseException e) {
                AlertBox.showErrorBox("Attention, les mauvaise réponse ne doivent "
                        + "pas être en double ET la bonne réponse ne peut pas être " + "une mauvaise réponse");
            } catch (HomonymeException e) {
                AlertBox.showWarningBox("La question saisie existe déjà");
            }
            if (questionCreer) {
                AlertBox.showSuccessBox("Question créer !");
            }
        }
    }

    /**
     * Un fichier CSV valide contient :
     * <ul>
     * <li>Colonne 1 la catégorie</li>
     * <li>Colonne 2 la difficulté</li>
     * <li>Colonne 3 le libellé</li>
     * <li>Colonne 4 la réponse juste</li>
     * <li>Colonne 5 la première réponse fausse</li>
     * <li>Colonne 6 la deuxième réponse fausse</li>
     * <li>Colonne 7 la troisième réponse fausse</li>
     * <li>Colonne 8 la quatrième réponse fausse</li>
     * </ul>
     * 
     * @param ligne La ligne de texte à vérifier
     * @return true si la ligne est valide false sinon
     */
    private boolean ligneCSVValide(String ligne) {
        Pattern fichierValide = Pattern.compile("^(.*;){2}(.+;){3}(.*;){4}", Pattern.CASE_INSENSITIVE);

        Matcher matcher = fichierValide.matcher(ligne);
        if (matcher.find()) {
            System.out.println("Ligne valide : " + ligne);
            return true;
        } else {
            System.out.println("Ligne invalide : " + ligne);
            return false;
        }
    }

    /**
     * @param fichierCSV
     * @return Une liste des ligne du fichier CSV
     * @throws IOException
     */
    private ArrayList<HashMap<String, String>> getLigneCSV(File fichierCSV) throws IOException {
        String ligne;
        ArrayList<HashMap<String, String>> resultat = new ArrayList<HashMap<String, String>>();
        BufferedReader fichierReader = new BufferedReader(new FileReader(fichierCSV.getAbsolutePath()));

        do {
            ligne = fichierReader.readLine();

            if (ligne != null) {
                // TODO Ajouter la vérification de la ligne
                resultat.add(getDicotionnaire(ligne));
            }
        } while (ligne != null);

        return resultat;
    }

    /**
     * 
     * TODO comment method role
     * 
     * @param ligne
     * @return
     */
    private HashMap<String, String> getDicotionnaire(String ligne) {
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
