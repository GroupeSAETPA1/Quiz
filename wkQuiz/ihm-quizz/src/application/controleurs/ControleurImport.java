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
 * Controlleur de Import
 * Gère le fichier CSV et créer les nouvelles questions et catégorie
 * présente dans le fichier
 * @author François de Saint Palais
 */
public class ControleurImport {

    @FXML private TextField saisieCheminFichier;

    private File fichierCSVChoisie;

    private ModelePrincipal modele = ModelePrincipal.getInstance();


    @FXML
    private void parcourirExplorer () {
        System.out.println("Parcourir Explorer");
        FileChooser fichier = new FileChooser();
        fichier.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        fichier.setTitle("Selectionner le fichier contenant les nouvelles questions");

        fichierCSVChoisie = fichier.showOpenDialog(null);

        if (fichierCSVChoisie != null) {
            saisieCheminFichier.setText(fichierCSVChoisie.getAbsolutePath());
            System.out.println(fichierCSVChoisie.getAbsolutePath()); 
        }

    }

    @FXML
    private void aider() {
        System.out.println("Aider");
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

        //Le fichier n'existe pas
        if (!fichier.exists()) {
            AlertBox.showErrorBox(cheminFichierCSV + ", n'existe pas.");
            
        //Le fichier n'est pas un csv
        } else if (!cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf(".") + 1).equals("csv")) {
            AlertBox.showErrorBox(
                    cheminFichierCSV.substring(cheminFichierCSV.lastIndexOf("\\") + 1)
                    + ", n'est pas un fichier CSV");
        
        //Création des question
        } else {
            ArrayList<HashMap<String,String>> lignes = getLigneCSV(fichier);

            for (HashMap<String, String> ligneHashMap : lignes) {
                if (!modele.categorieExiste(ligneHashMap.get("categorie"))) {
                    try {
                        modele.creerCategorie(ligneHashMap.get("categorie"));
                    } catch (InvalidNameException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (HomonymeException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                
                ArrayList<String> reponseFausse = new ArrayList<String>();
                //TODO Récupérer les réponse fausse non vide
                
                //TODO Récupérer l'id de la catégorie
                
                
                
                try {
                    modele.creerQuestion(ligneHashMap.get("libelle"), 0, 0, cheminFichierCSV, null, cheminFichierCSV);
                } catch (InvalidFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvalidNameException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ReponseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (HomonymeException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    /** 
     * Un fichier CSV valide contient :
     * <ul>
     *     <li>Colonne 1 la catégorie</li>
     *     <li>Colonne 2 la difficulté</li>
     *     <li>Colonne 3 le libellé</li>
     *     <li>Colonne 4 la réponse juste</li>
     *     <li>Colonne 5 la première réponse fausse</li>
     *     <li>Colonne 6 la deuxième réponse fausse</li>
     *     <li>Colonne 7 la troisième réponse fausse</li>
     *     <li>Colonne 8 la quatrième réponse fausse</li>
     * </ul>
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
    private ArrayList<HashMap<String,String>> getLigneCSV(File fichierCSV) throws IOException {
        String ligne;
        ArrayList<HashMap<String,String>> resultat = new ArrayList<HashMap<String,String>>();
        BufferedReader fichierReader 
        = new BufferedReader(new FileReader(fichierCSV.getAbsolutePath()));

        do {
            ligne = fichierReader.readLine();

            if (ligne != null) {
                //TODO Ajouter la vérification de la ligne
                resultat.add(getDicotionnaire(ligne));
            }
        } while (ligne != null);

        return resultat;
    }

    /**
     * 
     * TODO comment method role
     * @param ligne
     * @return
     */
    private HashMap<String,String> getDicotionnaire(String ligne) {
        HashMap<String,String> resultat = new HashMap<String,String>();
        String[] ligneListe = ligne.split(ModelePrincipal.SEPARATEUR_CSV + "");
        resultat.put("categorie",ligneListe[0]);
        resultat.put("difficulte",ligneListe[1]);
        resultat.put("libelle",ligneListe[2]);
        resultat.put("reponseJuste",ligneListe[3]);
        
        try {
            resultat.put("1reponseFausse",ligneListe[4]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("1reponseFausse",null);    
        }
        try {
            resultat.put("2reponseFausse",ligneListe[5]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("2reponseFausse",null);    
        }
        try {
            resultat.put("3reponseFausse",ligneListe[6]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("3reponseFausse",null);    
        }
        try {
            resultat.put("4reponseFausse",ligneListe[7]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("4reponseFausse",null);    
        }
        try {
            resultat.put("feedback",ligneListe[8]);
        } catch (IndexOutOfBoundsException e) {
            resultat.put("feedback","");    
        }
        return resultat;
    }
}
