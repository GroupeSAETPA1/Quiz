/*
 * Chiffrage.java 								18/10/2023
 * IUT de Rodez, pas de copyrights ni copyleft
 */

package application.modele;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * La classe Chiffrage fournit des méthodes pour le chiffrement
 * et le déchiffrement grace a un algorithme de vigenere
 */
public class Chiffrage {
	
    // Caractère utilisé comme séparateur dans le chiffrement
	private static char SEPARATEUR = '\u00e9';
	
	// Longueur minimale et maximale d'une clé de chiffrement
	private static final int LONGUEUR_CLE_MINIMUM = 40 ;
	private static final int LONGUEUR_CLE_MAXIMUM = 60 ;
	
	// Alphabet personnalisé pouvant etre chiffré 
	private static String CUSTOM_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGH"
	        + "IJKLMNOPQRSTUVWXYZ&~\"#'({[-|`_\\^@)]}/*.!?,;:<>1234567890$% +=";

	// Mapping des caractères de l'alphabet vers des entiers
    public static final HashMap<Character, Integer> ALPAHABET_TO_INT 
    = new HashMap<>();
	
    // Mapping des entiers vers des caractères de l'alphabet
	public static final HashMap<Integer, Character> INT_TO_ALPHABET 
	= new HashMap<>();
	
	
	// Paramètres pour l'algorithme de Diffie-Hellman
	public static final int P = 8269;
	public static final int G = 4537;
	private static final int PUISSANCE_MINI = 5000 ;
	private static final int PUISSANCE_MAXI = 9999 ;
	
	// Valeur partagée dans l'algorithme de Diffie-Hellman
	private static int gab = 0;
	
	// Initialisation des mappings entre caractères et entiers
    static {

        for (int i = 0; i < CUSTOM_ALPHABET.length(); i++) {
            char c = CUSTOM_ALPHABET.charAt(i);
            ALPAHABET_TO_INT.put(c, i);
            INT_TO_ALPHABET.put(i, c);
        }
        System.out.println(ALPAHABET_TO_INT);
    }
    // Nombre de lettres dans l'alphabet personnalisé
    private final static int NOMBRE_LETTRE_ALPHABET = CUSTOM_ALPHABET.length();
    
    
    
	/**
	 * Méthode de génération de clé
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
                    (int)(Math.random()*NOMBRE_LETTRE_ALPHABET));
            
	        cle.append(ajout);
	    }
     	return cle.toString();
	}
	
	
    /**
     * Chiffre un message donné en utilisant une clé fournie.
     *
     * @param message Le message à chiffrer.
     * @param cle     La clé de chiffrement.
     * @return Le message chiffré.
     */
	public static String chiffrement(String message, String cle) {
		StringBuilder aCrypter = new StringBuilder();
		for (int i = 0 ; i < message.length() ; i++) {
		    // Valeurs du caractère message.charAt(i)
		    int messageI;
		    try {                
		        messageI = ALPAHABET_TO_INT.get(message.charAt(i));
            } catch (Exception e) {
                System.err.print(message + " -> ");
                System.err.println(message.charAt(i));
                throw e;
            }
		    
		    // Valeur du caractère de la cle
		    int cleI = ALPAHABET_TO_INT.get(cle.charAt(i%cle.length()));
		    
		    char crypter = INT_TO_ALPHABET.get(
		            (messageI + cleI) % NOMBRE_LETTRE_ALPHABET);
		    aCrypter.append(crypter);	    
		}
		return aCrypter.toString();
	}
	
    /**
     * Déchiffre un message donné en utilisant une clé fournie.
     *
     * @param message Le message à déchiffrer.
     * @param cle     La clé de déchiffrement.
     * @return Le message déchiffré.
     */
	public static String dechiffrement(String message, String cle) {
	    StringBuilder aCrypter = new StringBuilder();
	    for (int i = 0 ; i < message.length() ; i++) {
	        // Valeur du caractère message.charAt(i)
	        int messageI = ALPAHABET_TO_INT.get(message.charAt(i));
            // Valeur du caractère de la cle
            int cleI = ALPAHABET_TO_INT.get(cle.charAt(i%cle.length()));
            
	        int positionReelle  = (messageI - cleI) % NOMBRE_LETTRE_ALPHABET;
	        // On repasse le modulo en positif
	        positionReelle = positionReelle < 0 ? 
	                         positionReelle + NOMBRE_LETTRE_ALPHABET 
	                         : positionReelle;
	        char crypter = INT_TO_ALPHABET.get(positionReelle);
	        aCrypter.append(crypter);       
	    }
	    return aCrypter.toString();
	}
	
	
    /**
     * Génère un tableau de chaînes chiffrées pour une 
     * collection de questions donnée en utilisant une clé fournie.
     *
     * @param aEnvoyer La collection de questions à chiffrer.
     * @param cle      La clé de chiffrement.
     * @return Un tableau de chaînes chiffrées.
     */
	public static ArrayList<String> genererTableauCrypter(
	        ArrayList<Question> aEnvoyer , String cle) 	{    
	 	ArrayList<String> crypter = new ArrayList<String>();
	 	for (int i = 0 ; i < aEnvoyer.size() ;  i++ ) {
	           crypter.add(crypterQuestion(aEnvoyer.get(i), cle));
	 	}
	 	return crypter;
	}
	
    /**
     * Chiffre une question donnée en utilisant une clé fournie.
     *
     * @param question La question à chiffrer.
     * @param cle      La clé de chiffrement.
     * @return La question chiffrée sous forme de chaîne.
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
        
        ligne.append(chiffrement(question.getFeedback(), cle));
        return ligne.toString();
    }

    /**
     * Déchiffre une chaîne de question donnée en utilisant 
     * une clé de déchiffrement fournie.
     *
     * @param question La chaîne de question à déchiffrer.
     * @param cleD     La clé de déchiffrement.
     * @return La chaîne de question déchiffrée.
     */
    public static String decrypterFichier(String question , String cleD) {
        StringBuilder resultat = new StringBuilder();
        String[] morceauADecrypter = question.split(""+SEPARATEUR);
        for (int i = 0 ; i < morceauADecrypter.length ; i++) {
            resultat.append(dechiffrement(morceauADecrypter[i], cleD));
            if (i != morceauADecrypter.length - 1) {
                resultat.append(SEPARATEUR);
            }
        }
        return resultat.toString();
	}
    
    /**
     * Calcule le résultat de (a^exp) % modulo de manière efficace.
     *
     * @param a      La base.
     * @param exp    L'exposant.
     * @param modulo Le modulo.
     * @return Le résultat de (a^exp) % modulo.
     */
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
	
    /** 
     * @return Une puissance aléatoire  dans la plage spécifiée 
     * [PUISSANCE_MINI, PUISSANCE_MAXI].
     */
	public static int genererPuissance() {
        return (int)(Math.random()*
                (PUISSANCE_MAXI + 1 - PUISSANCE_MINI)+PUISSANCE_MINI);
	    
	}
	
    /**
     * Génère une clé secrète basée sur l'algorithme 
     * d'échange de clés Diffie-Hellman.
     *
     * @return La clé secrète générée.
     */
	public static String cleDepuisDiffie() {
	   String gabString = "" + gab;
	   int actuelle ;
	   StringBuilder cle = new StringBuilder();
	   for (int  i  = 0 ; i < gabString.length() ; i++ ) {
	       for (int j = 0 ; j < gabString.length() ; j++) {
	           actuelle = Integer.parseInt("" + gabString.charAt(i) 
	                                          + gabString.charAt(j));
	           actuelle = actuelle%NOMBRE_LETTRE_ALPHABET;
	           cle.append(INT_TO_ALPHABET.get(actuelle));
	       }
	   }
       return cle.toString(); 
	}
	
    /**
     * Définit la valeur de la clé partagée (gab) 
     * utilisée dans l'échange de clés Diffie-Hellman.
     *
     * @param nouveau La nouvelle valeur de la clé partagée.
     */
    public static void setGab(int nouveau) {
        gab = nouveau;
    }

    /**
     * Obtient la valeur de la clé partagée (gab) 
     * utilisée dans l'échange de clés Diffie-Hellman.
     *
     * @return La valeur de la clé partagée.
     */
    public static int getGab() {
        return gab;
    }
}