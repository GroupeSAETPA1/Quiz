/*
 * Accent.java                                    28 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package outil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import application.modele.Chiffrage;

import static java.util.Map.entry;

/** 
 * Méthode utile pour gérer les accents
 * @author François de Saint Palais
 */
public class Accent {
    
    public static final String LETTRE_ACCENTUE 
    = "éèàçôöîïâäûüùêëÉÈÀÇÔÖÎÏÂÄÛÜÙÊË";
    
    public static final Map<Character, Character> ACCENT_TO_NORMAL 
    = Map.ofEntries(
            entry('é','e'),entry('è','e'),entry('ê','e'),entry('ë','e'),
            entry('à','a'),entry('â','a'),entry('ä','a'),
            entry('ô','o'),entry('ö','o'),
            entry('î','i'),entry('ï','i'),
            entry('û','u'),entry('ü','u'),
            entry('ç','c'),
            entry('É','E'),entry('È','E'),entry('Ê','E'),entry('Ë','E'),
            entry('Û','U'),entry('Ü','U'),entry('Ù','U'),
            entry('Ô','O'),entry('Ö','O'),
            entry('Î','I'),entry('Ï','I'),
            entry('Â','A'),entry('Ä','A'),
            entry('Ç','C')
            );

    /** 
     * Remplace toute les lettres accentué des valeur de ligneHashMap 
     * par leur version non accentué.
     * Si un caractère est inconnu alors on le remplace par un point d'interrogation.
     * @param ligneHashMap la HashMap à transformé
     * @return la hashMap transformé
     */
    public static HashMap<String, String> convertirAccent(HashMap<String, String> ligneHashMap) {
        
        Set<String> cles = ligneHashMap.keySet();
        HashMap<String, String> reponse = new HashMap<String, String>();
        for (String cle : cles) {
            reponse.put(cle, convertirAccent(ligneHashMap.get(cle)));
        }
        
        return reponse;
    }
    
    /**
     * Remplace toute les lettres accentué de la string par leur version non accentué.
     * Si un caractère est inconnu alors on le remplace par un point d'interrogation.
     * @param chaineARemplacer
     * @return la chaîne transformé
     */
    public static String convertirAccent(String chaineARemplacer) {
        StringBuilder reponse = new StringBuilder();
        for (int i = 0; i < chaineARemplacer.length(); i++) {
            reponse.append(convertirAccent(chaineARemplacer.charAt(i)));
        }
        return reponse.toString();
    }
    
    /**
     * Si le caractère est un caractère accentué 
     * alors on retourne sa version  non accentué.
     * Si le caractère est inconnu on le remplace par un point d'interrogation.
     * Un caractère inconnu est ceux qui ne sont pas présent dans {@link Chiffrage.CUSTOM_ALPHABET}
     * @param caratereARemplacer
     * @return le caractère transformé
     */
    public static char convertirAccent(char caratereARemplacer) {
        char reponse = caratereARemplacer;
        if (!Chiffrage.CUSTOM_ALPHABET.contains(caratereARemplacer + "")) {
            reponse = ACCENT_TO_NORMAL.containsKey(caratereARemplacer) ? 
                    ACCENT_TO_NORMAL.get(caratereARemplacer) : '?';
        }
        return reponse;
        
    }

}
