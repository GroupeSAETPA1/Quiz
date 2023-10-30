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
 * Permet l'interaction entre les classes du modèle et les controlleurs.
 * C'est une classe Singleton
 * @author Lucas Descriaud
 * @author François de Saint Palais
 */
public class ModelePrincipal {
    
    private static ModelePrincipal modele;
    private BanqueQuestion banqueQuestion;
    private BanqueCategorie banqueCategorie;
    
    private Question questionAModifier;
    private Categorie catgorieAModifier;
    
    private ModelePrincipal() {
        //TODO instancier les banques 
        //TODO lire les fichiers serialisé
        this.banqueQuestion = new BanqueQuestion();
        this.banqueCategorie = new BanqueCategorie();
    }
    
    /**
     * @return Renvoie l'instance unique de ModelePrincipal
     */
    public static ModelePrincipal  getInstance() {
        if (ModelePrincipal.modele == null) {
            ModelePrincipal.modele = new ModelePrincipal();
        }
        return modele;
    }
    
    public BanqueQuestion getBanqueQuestion() {
        //TODO Je pense que l'on peux changer la visibilité de certaine méthode de Question. Pour éviter les effet de bord.s
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
        
        boolean questionEstAjoute = false ;
        boolean categorieExistante = categorieValide(categorie);
        ArrayList<Question> listeQuestion = banqueQuestion.getQuestions();
        

        
        if (categorieValide(categorie)) {
          //TODO recuperer LA categorie qui a pour nom exactement categorie
          Question questionAajouter = new Question(libelle , null , 
                    difficulte , reponseJuste , reponseFausses , feedback);
        // on verifie si la question a creer existe si non on l'ajoute 
          if (!listeQuestion.contains(questionAajouter)) {
              listeQuestion.add(questionAajouter);
              questionEstAjoute  = true ; 
           }
        }
        // true question bien ajouter , false sinon
        return questionEstAjoute;
        
    }
    
    /**
     * Créer une Categorie. Si la création a été un sucés on revoie true sinon false
     * @param nom Le nom que vous voulait donnée à la Catgorie.
     * @return true si la création est un sucés false sinon
     */
    public boolean creerCategorie(String nom) {
        return false; //STUB
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
    
    
    /**
     * @return Une liste des categories
     * @throws InvalidNameException //STUB
     */
    public ArrayList<Categorie> getCategories() throws InvalidNameException {
        ArrayList<Categorie> resultat = new ArrayList<Categorie>(3);
        resultat.add(new Categorie("Premier bouchon"));
        resultat.add(new Categorie("Second bouchon"));
        resultat.add(new Categorie("Troisieme bouchon"));
        
        return resultat; //STUB
    }
    
    /**
     * Supprime de la banque de question la question passé en paramètre.
     * Si la suppression est un sucés alors on revoie true false sinon
     * @param questionASuprimer
     * @return true si la suppression est un sucés false sinon
     */
    public boolean supprimerQuestion(Question questionASuprimer) {
        return false; //STUB
    }
    
    /**
     * @param categorie
     * @return revoie true si la categorie contient des question false sinon
     */
    public boolean categorieContientQuestion(Categorie categorie) {
        return false; //STUB
    }
    
    /**
     * Supprimer de la banque de categorie la categorie passé en paramètre.
     * Si la categorie a supprimer est la categorie "Général", la suppression est annulé
     * @param categorieASupprimer
     * @return true si la suppression est un sucés false sinon
     */
    public boolean supprimerCategorie(Categorie categorieASupprimer) {
        return false; //STUB
    }
    
    /**
     * Pour garder l'instance de la question que l'on veux modifier, 
     * on garde l'instance de la question dans cet classe
     * @param questionAModifier nouvelle valeur pour questionAModifier
     */
    public void setQuestionAModifier(Question questionAModifier) {
        this.questionAModifier = questionAModifier;
    }
    
    /** @return valeur de questionAModifier */
    public Question getQuestionAModifier() {
        return questionAModifier;
    }

    /**
     * Modifie la questionAModifier
     * @param libelle Le nouveau nom de la question
     * @param categorie La nouvelle Categoriede la question
     * @param difficulte La nouvelle difficulte de la question
     * @param reponseJuste La nouvelle réponse juste de la question
     * @param reponseFausses Les nouvelles reponse fausse de la question
     * @param feedback Le nouveau feedback de la question
     * @return true si la modification a pu être faite
     */
    public boolean modifierQuestion(String libelle ,String categorie ,
            int difficulte , String reponseJuste , ArrayList<String> reponseFausses , 
            String feedback) {
        return false; //STUB
    }

    /** @return valeur de catgorieAModifier */
    public Categorie getCatgorieAModifier() {
        return catgorieAModifier;
    }

    /**
     * Pour garder l'instance de la catégorie que l'on veux modifier, 
     * on garde l'instance de la catégorie dans cet classe 
     * @param catgorieAModifier nouvelle valeur de catgorieAModifier 
     */
    public void setCatgorieAModifier(Categorie catgorieAModifier) {
        this.catgorieAModifier = catgorieAModifier;
    }
    
}

