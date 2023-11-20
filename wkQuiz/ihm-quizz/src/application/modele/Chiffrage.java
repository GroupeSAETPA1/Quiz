package application.modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Chiffrage {
	
	private static char SEPARATEUR = 'é';
	
	

	private static final HashMap<Character, Integer> ALPAHABET_TO_INT 
	= ModelePrincipal.ALPAHABET_TO_INT;
	
	private static final HashMap<Integer, Character> INT_TO_ALPHABET 
	= ModelePrincipal.INT_TO_ALPHABET;
	
	public String generationCle() {
		return null; // STUB
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
