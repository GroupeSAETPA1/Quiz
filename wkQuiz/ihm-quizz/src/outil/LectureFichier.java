/*
 * LectureFichier.java                                    22 nov. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package outil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import application.exception.CreerQuestionException;
import application.exception.HomonymeException;
import application.exception.InvalidNameException;
import application.modele.ModelePrincipal;
import application.modele.Question;

/**
 * Méthode outils de lecture de fichier CSV
 * 
 * @author Lucas
 */
public class LectureFichier {

	/** Les CSV importés devront séparer leur éléments avec une tabulation */
	public static final char SEPARATEUR_CSV = '\t';

	private static ModelePrincipal modele = ModelePrincipal.getInstance();

	private static int nombreQuestionAjoute;

	/**
	 * Lit le fichier .csv et récupère les lignes
	 * 
	 * @param fichierCSV
	 * @return Une liste des ligne du fichier CSV
	 * @throws IOException
	 */
	public static ArrayList<HashMap<String, String>> getLigneCSV(File fichierCSV) throws IOException {
		String ligne;
		ArrayList<HashMap<String, String>> resultat = new ArrayList<HashMap<String, String>>();
		BufferedReader fichierReader = new BufferedReader(new FileReader(fichierCSV.getAbsolutePath()));

		nombreQuestionAjoute = 0;
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
	 * @param ligne une ligne du CSV
	 * @return une HashMap de la question analysée
	 */
	public static HashMap<String, String> getDictionnaire(String ligne, char separateur) {

		HashMap<String, String> resultat = new HashMap<String, String>();
		Pattern guillemeDebutFin = Pattern.compile("^\".*\"$");
		String[] ligneListe = ligne.split(separateur + "");

		for (int i = 0; i < ligneListe.length; i++) {
			ligneListe[i] = Accent.convertirAccent(ligneListe[i]);

			// Il est possible qu'un CSV encadre ces valeur par des ".
			// Il faut les supprimer avant d'utiliser les valeurs
			if (guillemeDebutFin.matcher(ligneListe[i]).matches()) {
				// On supprime le premier et dernier qui sont des ".
				ligneListe[i] = ligneListe[i].substring(1, ligneListe[i].length() - 1);
			}
		}
		

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
		System.out.println(resultat);
		return resultat;
	}

	/**
	 * Récupère une ligne du CSV et retourne une HashMap associant chaque élément
	 * d'une question
	 * 
	 * @param ligne une ligne du CSV
	 * @return une HashMap de la question analysée
	 */
	public static HashMap<String, String> getDictionnaire(String ligne) {
		return getDictionnaire(ligne, SEPARATEUR_CSV);
	}

	/**
	 * Crée une question à partir d'une ligne csv
	 * 
	 * @throws InvalidNameException
	 * @throws CreerQuestionException
	 * @return une Question à partir d'une ligne analysée
	 */
	public static Question creerQuestionFromLigneCSV(HashMap<String, String> ligneHashMap)
			throws CreerQuestionException, InvalidNameException {

		boolean erreurCreationCategorie;

		erreurCreationCategorie = !creerCategorieSiAbsent(ligneHashMap.get("categorie"));

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
					modele.getCategoriesLibelleExact(ligneHashMap.get("categorie")), difficulte,
					ligneHashMap.get("reponseJuste"), reponseFausse, ligneHashMap.get("feedback"));

		} else {
			return null;
		}
	}

	/**
	 * Vérifie si une catégorie est présente dans le modèle et si elle n'est pas
	 * présente, elle est créer.
	 * 
	 * @param nomCategorie
	 * @throws InvalidNameException
	 * @return un booléen à vrai si la création de la catégorie à été réussie
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

	/**
	 * Getter du nombre de question ajoutées
	 * 
	 * @return le nombre de question ajoutées
	 */
	public static int getNombreQuestionAjoute() {
		return nombreQuestionAjoute;
	}

}
