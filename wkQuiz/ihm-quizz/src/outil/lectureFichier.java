/*
 * lectureFichier.java                                    22 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.outil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import application.modele.ModelePrincipal;

/** 
 * Méthode outils de lecture de fichier CSV
 * @author Lucas
 */
public class lectureFichier {
    
    /** Les CSV importé devront séparé leur élément avec une tabulation */
    public static final char SEPARATEUR_CSV = 'é';

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
                HashMap<String, String> dicoLigne = lectureFichier.getDicotionnaire(ligne);
                resultat.add(dicoLigne);
                nombreQuestionAjoute ++;
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
    public static HashMap<String, String> getDicotionnaire(String ligne) {
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

}
