/*
 * lectureFichier.java                                    22 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package outil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.util.Map.entry;

import application.exception.CreerQuestionException;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;
import application.modele.Chiffrage;
import application.modele.ModelePrincipal;
import application.modele.Question;

/**
 * Méthode outils de lecture de fichier CSV
 * 
 * @author Lucas
 */
public class LectureFichier {

    /** Les CSV importé devront séparé leur élément avec une tabulation */
    public static final char SEPARATEUR_CSV = '\t';
    
    private static ModelePrincipal modele = ModelePrincipal.getInstance();

    /**
     * @param fichierCSV
     * @return Une liste des ligne du fichier CSV
     * @throws IOException
     */
    public static ArrayList<HashMap<String, String>> getLigneCSV(File fichierCSV) throws IOException {
        String ligne;
        ArrayList<HashMap<String, String>> resultat = new ArrayList<HashMap<String, String>>();
        BufferedReader fichierReader = new BufferedReader(new FileReader(fichierCSV.getAbsolutePath()));

        int nombreQuestionAjoute = 0;
        do {
            ligne = fichierReader.readLine();

            if (ligne != null) {
                HashMap<String, String> dicoLigne = LectureFichier.getDictionnaire(ligne);
                resultat.add(dicoLigne);
                nombreQuestionAjoute++;
            }
        } while (ligne != null);
        fichierReader.close();
        return resultat;
    }

    /**
     * Récupère une ligne du CSV et retourne une HashMap associant chaque élément
     * d'une question
     * 
     * @param ligne
     * @return
     */
    public static HashMap<String, String> getDictionnaire(String ligne) {
        HashMap<String, String> resultat = new HashMap<String, String>();
        String[] ligneListe = ligne.split(SEPARATEUR_CSV + "");
        resultat.put("categorie", ligneListe[0].trim());
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
     * 
     * @return
     * @throws InvalidNameException
     * @throws CreerQuestionException
     */
    public static Question creerQuestionFromLigneCSV(HashMap<String, String> ligneHashMap)
            throws CreerQuestionException, InvalidNameException {
        
        ligneHashMap = Accent.convertirAccent(ligneHashMap);
        
        boolean erreurCreationCategorie;

        erreurCreationCategorie = !creerCategorieSiAbsent(ligneHashMap.get("categorie"));

        int idCategorie = modele.getIndice(ligneHashMap.get("categorie"));

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

            int difficulte = Integer.parseInt(ligneHashMap.get("difficulte"));

            return new Question(ligneHashMap.get("libelle"), 
                modele.getCategoriesLibelleExact(ligneHashMap.get("categorie")),
                difficulte, ligneHashMap.get("reponseJuste"), reponseFausse,
                ligneHashMap.get("feedback"));

        } else {
            return null;
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
    private static boolean creerCategorieSiAbsent(String nomCategorie) throws InvalidNameException {
        // True si la catégorie existe ou si la création est fructueuse
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

}
