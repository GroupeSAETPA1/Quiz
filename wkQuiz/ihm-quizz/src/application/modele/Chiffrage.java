package application.modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvWriter;

import application.outil.lectureFichier;
import application.vue.AlertBox;

public class Chiffrage {
	
	private static char SEPARATEUR = '\u00e9';
	
	private static final int LONGUEUR_CLE_MINIMUM = 40 ;
	
	private static final int LONGUEUR_CLE_MAXIMUM = 60 ;
	
	private static int nombreLettreAlphabet;
	
	private static String CUSTOM_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ&~\"#'({[-|`_\\^@)]}/*.!?,;:<>1234567890$% ";

	public static final HashMap<Character, Integer> ALPAHABET_TO_INT = new HashMap<>();
	
	public static final HashMap<Integer, Character> INT_TO_ALPHABET = new HashMap<>();
	
	private static final String CHEMIN_FICHIER = "./../../crypter.csv";
	
	private static final File fichier = new File(CHEMIN_FICHIER);
	        
    static {

        for (int i = 0; i < CUSTOM_ALPHABET.length(); i++) {
            char c = CUSTOM_ALPHABET.charAt(i);
            ALPAHABET_TO_INT.put(c, i);
            INT_TO_ALPHABET.put(i, c);
        }
        nombreLettreAlphabet = ALPAHABET_TO_INT.size();
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
	    System.out.println("message : " + message);
	      StringBuilder aCrypter = new StringBuilder();
	        for (int i = 0 ; i < message.length() ; i++) {
	            // valeur du caractere message.charAt(i)
	            int messageI = ALPAHABET_TO_INT.get(message.charAt(i));
	           
	            
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
	
	public static void genererCSV(ArrayList<Question> aEnvoyer , String cle) throws IOException {
	    
	        File file = new File(CHEMIN_FICHIER);
	        FileWriter outputFile = new FileWriter(file);
	        for (int i = 0 ; i < aEnvoyer.size() ;  i++ ) {
	            outputFile.write(crypterQuestion(aEnvoyer.get(i), cle));
	            if (i != aEnvoyer.size()) {
	                outputFile.write('\n');
	            }
	        }
	        outputFile.close();
	    
	}
	
	/** 
     * TODO comment method role
	 * @param question 
     * @return
     */
    private static String crypterQuestion(Question question , String cle) {
        StringBuilder ligne = new StringBuilder();
        ligne.append(chiffrement(question.getCategorie(), cle) +SEPARATEUR);
        ligne.append(chiffrement(""+question.getDifficulte() ,cle)+SEPARATEUR);
        ligne.append(chiffrement(question.getLibelle(), cle)+ SEPARATEUR);
        ligne.append(chiffrement(question.getReponseJuste(), cle)+SEPARATEUR);
        for (int i = 0 ; i < 4 ; i++) {
           if (i < question.getMauvaisesReponses().size() ) {
               ligne.append(chiffrement(question.getMauvaisesReponses().get(i) , cle) + SEPARATEUR );
           } else {
               ligne.append(SEPARATEUR);
           }
        }
        return ligne.toString();
    }

    public static ArrayList<String[]> decrypterFichier(String cleD) throws IOException {
        ArrayList<HashMap<String, String>> questionCrypter = lectureFichier.getLigneCSV(fichier);
        
        for (HashMap<String, String> question : questionCrypter) {
            Set<String> listeCle = question.keySet();
            for (String cle : listeCle) {
                System.err.println(dechiffrement(question.get(cle),cleD));
            }
        }
        return null;
	}
    
    
	
	public void decrypterLigne(ArrayList<String[]> contenu) {
		
	}
}
