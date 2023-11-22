package application.modele;

import java.util.ArrayList;
import java.util.HashMap;

public class Chiffrage {
	
	private static char SEPARATEUR = 'é';
	
	private static final int LONGUEUR_CLE_MINIMUM = 40 ;
	
	private static final int LONGUEUR_CLE_MAXIMUM = 60 ;
	
	private static int nombreLettreAlphabet;
	
	private static String CUSTOM_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ&~\"#'({[-|`_\\^@)]}/*.!?,;:<>1234567890$% ";

	public static final HashMap<Character, Integer> ALPAHABET_TO_INT = new HashMap<>();
	
	public static final HashMap<Integer, Character> INT_TO_ALPHABET = new HashMap<>();
	
	private static final int P = 8269;
	
	private static final int G = 4537;
	        
    static {

        for (int i = 0; i < CUSTOM_ALPHABET.length(); i++) {
            char c = CUSTOM_ALPHABET.charAt(i);
            ALPAHABET_TO_INT.put(c, i);
            INT_TO_ALPHABET.put(i, c);
        }
        nombreLettreAlphabet = ALPAHABET_TO_INT.size();
        System.out.println(nombreLettreAlphabet);
    }
	
	/**
     * @return une cle de longueur comprise entre 
	 * LONGUEUR_CLE_MINIMUM et LONGUEUR_CLE_MAXIMUM
	 * et comprenant uniquement des caractères de INT_TO_ALPHABET 
	 */
	public static String generationCle() {
	    int longueurCle = (int)(Math.random()*
	            (LONGUEUR_CLE_MAXIMUM + 1 - LONGUEUR_CLE_MINIMUM) 
	            + LONGUEUR_CLE_MINIMUM);
	    StringBuilder cle = new StringBuilder();
	    for (int i = 0 ; i < longueurCle ; i++) {
	        char ajout = INT_TO_ALPHABET.get(
                    (int)(Math.random()*nombreLettreAlphabet));
            
	        cle.append(ajout);
	    }
     	return cle.toString();
	}
	
	public static String chiffrement(String message, String cle) {
		StringBuilder aCrypter = new StringBuilder();
		for (int i = 0 ; i < message.length() ; i++) {
		    // valeur du caractere message.charAt(i)
		    int messageI = ALPAHABET_TO_INT.get(message.charAt(i));
		    
		    // valeur du caractère de la cle
		    int cleI = ALPAHABET_TO_INT.get(cle.charAt(i%cle.length()));
		    
		    char crypter = INT_TO_ALPHABET.get(
		            (messageI + cleI) % nombreLettreAlphabet);
		    aCrypter.append(crypter);	    
		}
		return aCrypter.toString();
	}
	
	public static String dechiffrement(String message, String cle) {
	    System.out.println(INT_TO_ALPHABET);
	      StringBuilder aCrypter = new StringBuilder();
	        for (int i = 0 ; i < message.length() ; i++) {
	            // valeur du caractere message.charAt(i)
	            int messageI = ALPAHABET_TO_INT.get(message.charAt(i));
	            System.out.println("message i : " + messageI);
	            
	            // valeur du caractère de la cle
	            int cleI = ALPAHABET_TO_INT.get(cle.charAt(i%cle.length()));

	            
	            int positionReelle  = (messageI - cleI) % nombreLettreAlphabet;
	            // on repasse le modulo en positif
	            positionReelle 
	            = positionReelle < 0 ? positionReelle + 93 : positionReelle;
	            char crypter = INT_TO_ALPHABET.get(positionReelle);
	            aCrypter.append(crypter);       
	        }
	        return aCrypter.toString();
	}
	
	public void genererCSV(String fichier) {
	    
	}
	
	public ArrayList<String[]> lireCSV(String cheminFichier) {
		return null; // STUB	
	}
	
	public void analiserResultat(ArrayList<String[]> contenu) {
		
	}
	
	public static int exposantModulo(int a, int exp, int modulo) {
		int result = 1;
        a = a % modulo;
        
        while (exp > 0) {
            if (exp % 2 == 1) {
                result = (result * a) % modulo;
            }
            exp = exp / 2;
            a = (a * a) % modulo;
        }
        
        return result;
	}
}
