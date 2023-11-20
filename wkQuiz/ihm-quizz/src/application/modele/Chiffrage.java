package application.modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Chiffrage {
	
	private static char SEPARATEUR = 'é';
	
	private static final int LONGUEUR_CLE_MINIMUM = 40 ;
	
	private static final int LONGUEUR_CLE_MAXIMUM = 60 ;

	private static final HashMap<Character, Integer> ALPAHABET_TO_INT 
	    = ModelePrincipal.ALPAHABET_TO_INT;
	   
	private static int nombreLettreAlphabet = ALPAHABET_TO_INT.size();


	
	private static final HashMap<Integer, Character> INT_TO_ALPHABET 
	= ModelePrincipal.INT_TO_ALPHABET;
	
	/**
     * @return une cle de longueur comprise entre 
	 * LONGUEUR_CLE_MINIMUM et LONGUEUR_CLE_MAXIMUM
	 * et comprenant uniquement des caractères de INT_TO_ALPHABET 
	 */
	public String generationCle() {
	    int longueurCle = (int)(Math.random()*
	            (LONGUEUR_CLE_MAXIMUM + 1 - LONGUEUR_CLE_MINIMUM) 
	             + LONGUEUR_CLE_MINIMUM);
	    StringBuilder cle = new StringBuilder();
	    for (int i = 0 ; i <= longueurCle ; i++) {
	        cle.append(INT_TO_ALPHABET.get(
	                (int)Math.random()*nombreLettreAlphabet));
	    }
		return cle.toString(); // STUB
	}
	
	public String chiffrement(String message, String cle) {
		return null; // STUB
	}
	
	public String dechiffrement(String message, String cle) {
		return null; // STUB
	}
	
	public void genererCSV(String fichier) {
		
	}
	
	public ArrayList<String[]> lireCSV(String cheminFichier) {
		return null; // STUB	
	}
	
	public void analiserResultat(ArrayList<String[]> contenu) {
		
	}
}
