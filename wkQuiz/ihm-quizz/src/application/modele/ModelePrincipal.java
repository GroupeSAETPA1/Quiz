/*
 * ModelePrincipal.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.modele;

import java.util.ArrayList;

import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;

/** 
 * TODO comment class responsibility (SRP)
 * @author Lucas 
 */
public class ModelePrincipal {
    
    private static ModelePrincipal modele;
    private BanqueQuestion banqueQuestion;
    private BanqueCategorie banqueCategorie;
    
    private ModelePrincipal() {
        //TODO instancier les banques 
        // TODO lire les fichiers serialisé
        this.banqueQuestion = new BanqueQuestion();
        this.banqueCategorie = new BanqueCategorie();
    }
    
    /**
     */
    public static ModelePrincipal  getInstance() {
        if (ModelePrincipal.modele == null) {
            ModelePrincipal.modele = new ModelePrincipal();
        }
        return modele;
    }
    
    public BanqueQuestion getBanqueQuestion() {
        return banqueQuestion;
    }
    /**
     * Ajoute une question a la modele.banqueQuestion en appelant le
     * constructeur de question. 
     * Si la catégorie n'existe pas elle est également créer grace au 
     * de Categorie
     * @param libelle : libelle de la question
     * @param categorie : catégorie de la question
     * @param difficulte : difficulté de la question
     * @param reponseFausse : liste de mauvaise réponse
     * @param feedback : feedback de la question
     * @param reponseJuste : bonne réponse de la question
     * @throw IllegalArgumentException : propage une éventuelle exception 
     *        lève par le constructeur si un des paramètres est valide
     * @throws ReponseException 
     * @throws InvalidNameException 
     * @throws InvalidFormatException 
     * @return true si la question a été créer , false sinon
     */
    public boolean creerQuestion(String libelle ,String categorie ,
    int difficulte , String reponseJuste , ArrayList<String> reponseFausses , 
    String feedback) throws InvalidFormatException, InvalidNameException, ReponseException {
        
        boolean questionAjout = false ;
        boolean categorieExistante = categorieValide(categorie);
        ArrayList<Question> listeQuestion = banqueQuestion.getQuestions();
        

        
        if (categorieValide(categorie)) {
          //TODO recuperer LA categorie qui a pour nom exactement categorie
          Question questionAajouter = new Question(libelle , null , 
                    difficulte , reponseJuste , reponseFausses , feedback);
        // on verifie si la question a creer existe si non on l'ajoute 
          if (!listeQuestion.contains(questionAajouter)) {
              listeQuestion.add(questionAajouter);
              questionAjout  = true ; 
           }
        }
        // true question bien ajouter , false sinon
        return questionAjout;
        
    }

    /** 
     * Verifier l'existence d'une categorie ayant pour nom categorie 
     * @param categorie nom a chercher
     * @return 
     */
    private  boolean categorieValide(String categorie) {
        ArrayList<Categorie> categorieExistante = banqueCategorie.getCategoriesLibelle(categorie);
        if (!categorieExistante.contains(categorie)) {
            //TODO creer la categorie
        }
        return true; //stub
    }
}

