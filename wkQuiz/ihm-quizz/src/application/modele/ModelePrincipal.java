/*
 * ModelePrincipal.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.modele;

import java.util.ArrayList;

/** 
 * TODO comment class responsibility (SRP)
 * @author Lucas 
 */
public class ModelePrincipal {
    
    private static ModelePrincipal modele;
    private BanqueQuestion banqueQuestion;
    private BanqueCatgorie banqueCategorie;
    
    private ModelePrincipal() {
        //TODO instancier les banques 
        // TODO lire les fichiers serialisé
        this.banqueQuestion = new BanqueQuestion();
        this.banqueCategorie = new BanqueCatgorie();
    }
    
    /**
     */
    public static ModelePrincipal  getInstance() {
        if (modele==null) {
            ModelePrincipal  modele = new ModelePrincipal();
        }
        return modele;
    }
    
    /**
     * 
     * TODO comment method role
     * @return
     */
    public boolean creerQuestion(String libelle ,Categorie categorie ,
    int difficulte , String reponseJuste , ArrayList<String> reponseFausses , 
    String feedback) {
        
        ArrayList<Question> listeQuestion = banqueQuestion.getQuestions(); 
        
        //  exception propager 
        Question questionAajouter = new Question(libelle , categorie , 
                difficulte , reponseJuste , reponseFausses , feedback);
        
        // on verifie si la categorie de la question existe sinon on la cree
        if (!banqueCategorie.getCategories().contains(categorie)) {
            banqueCategorie.getCategories().add(categorie);
        }
        
        // on verifie si la question a creer existe si non on l'ajoute 
        if (!listeQuestion.contains(questionAajouter)) {
            listeQuestion.add(questionAajouter);            
        }
        
        // true question bien ajouter , false sinon
        return listeQuestion.contains(questionAajouter);
        
    }
}

