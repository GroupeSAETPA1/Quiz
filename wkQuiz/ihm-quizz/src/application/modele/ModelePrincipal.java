/*
 * ModelePrincipal.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.modele;

import java.util.ArrayList;
import java.util.HashMap;

import application.exception.HomonymeException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;

/**
 * Permet l'interaction entre les classes du modèle et les controlleurs. C'est
 * une classe Singleton
 * 
 * @author Lucas Descriaud
 * @author François de Saint Palais
 */
public class ModelePrincipal {
    
    public static final HashMap<String, Integer> LABEL_DIFFICULTE_TO_INT 
    = new HashMap<String, Integer>();

    private static ModelePrincipal modele;
    private BanqueQuestion banqueQuestion;
    private BanqueCategorie banqueCategorie;

    private Question questionAModifier;
    private Categorie catgorieAModifier;
    
    private boolean displayCategoriePane;

    public boolean isDisplayCategoriePane() {
		return displayCategoriePane;
	}

	public void setDisplayCategoriePane(boolean displayCategoriePane) {
		this.displayCategoriePane = displayCategoriePane;
	}

	private ModelePrincipal() {
        // TODO lire les fichiers serialisé
        this.banqueQuestion = new BanqueQuestion();
        this.banqueCategorie = new BanqueCategorie();
        
        
        LABEL_DIFFICULTE_TO_INT.put("Facile", 1);
        LABEL_DIFFICULTE_TO_INT.put("Moyen", 2);
        LABEL_DIFFICULTE_TO_INT.put("Difficile", 3);
    }

    /**
     * @return Renvoie l'instance unique de ModelePrincipal
     */
    public static ModelePrincipal getInstance() {
        if (ModelePrincipal.modele == null) {
            ModelePrincipal.modele = new ModelePrincipal();
        }
        return modele;
    }

    public BanqueQuestion getBanqueQuestion() {
        // TODO Je pense que l'on peux changer la visibilité de certaine méthode de
        // Question. Pour éviter les effet de bord.s
        return banqueQuestion;
    }
    
    public BanqueCategorie getBanqueCategorie() {
        // TODO Je pense que l'on peux changer la visibilité de certaine méthode de
        // Question. Pour éviter les effet de bord.s
        return banqueCategorie;
    }

    /**
     * Ajoute une question a la modele.banqueQuestion en appelant le constructeur de
     * question.
     * 
     * @param libelle       : libelle de la question
     * @param idCategorie   : catégorie de la question
     * @param difficulte    : difficulté de la question
     * @param reponseFausse : liste de mauvaise réponse
     * @param feedback      : feedback de la question
     * @param reponseJuste  : bonne réponse de la question
     * @return true si la question a été créer , false sinon
     * @throws HomonymeException
     */
    public boolean creerQuestion(String libelle, int idCategorie, int difficulte, String reponseJuste,
            ArrayList<String> reponseFausses, String feedback)
            throws InvalidFormatException, InvalidNameException, ReponseException, HomonymeException {

        // La categorie de la question existera toujours donc aucune vérification d'existence n'est nécessaire
        Categorie categorieQuestion;
        try {
             categorieQuestion = banqueCategorie.getCategorie(idCategorie);            
        } catch (IndexOutOfBoundsException e) {
            categorieQuestion = banqueCategorie.categorieGeneral;
        }

        // Si exception apparais on propage au controlleur appellant
        Question aAjouter = new Question(libelle, categorieQuestion, difficulte, 
                reponseJuste, reponseFausses, feedback);

        // Si exception apparais on propage l'exception à l'appelant
        banqueQuestion.ajouter(aAjouter);
        return true;

    }

    /**
     * Créer une Categorie et l'ajoute au banque de catégorie. Si la création a été
     * un sucés on revoie true
     * 
     * @param nom Le nom que vous voulait donnée à la Categorie.
     * @return true si la création est un sucés false sinon
     * @throws InvalidNameException Si le nom choisie est invalide
     * @throws HomonymeException    Si la catégorie existe déjà
     */
    public boolean creerCategorie(String nom) throws InvalidNameException, HomonymeException {
        // On propage a l'appelant les éventuelle exception lié à la création
        Categorie aAjouter = new Categorie(nom);

        // On propage a l'appelant les éventuelle exception lié à la création
        banqueCategorie.ajouter(aAjouter);
        return true;
    }

    /**
     * @return Une liste des categories
     * @throws InvalidNameException //STUB
     */
    public ArrayList<Categorie> getCategories() {
        return banqueCategorie.getCategories();
    }

    /**
     * Supprime de la banque de question la question passé en paramètre. Si la
     * suppression est un succès alors on revoie true false sinon
     * 
     * @param questionASuprimer La question a supprimer
     * @return true si la suppression est un sucés false sinon
     */
    public boolean supprimerQuestion(Question questionASuprimer) {
        banqueQuestion.getQuestions().remove(questionASuprimer);
        return !banqueQuestion.getQuestions().contains(questionASuprimer);
    }

    /**
     * Vérifie si une Catégorie contient des Questions
     * @param categorie La Categorie pour laquelle on cherche
     * @return revoie true si la categorie contient des question, false sinon
     */
    public boolean categorieContientQuestion(Categorie categorie) {
        ArrayList<Question> questionExistante = banqueQuestion.getQuestions();
        boolean categorieVide = questionExistante.size() != 0;
        for (int i = 0; i < questionExistante.size() && categorieVide; i++) {
            categorieVide = !questionExistante.get(i).getCategorie()
                            .equals(categorie.getNom());
        }
        return !categorieVide;
    }

    /**
     * Supprimer de la banque de categorie la categorie passé en paramètre. Si la
     * categorie a supprimer est la categorie "Général", la suppression est annulé
     * 
     * @param categorieASupprimer
     * @return true si la suppression est un sucés false sinon
     */
    public boolean supprimerCategorie(Categorie categorieASupprimer) {
        //TODO a tester
        boolean estSupprimer = false;
        if (categorieASupprimer.equals(banqueCategorie.categorieGeneral)) {
            estSupprimer = false;
        }else if (banqueCategorie.getCategories().contains(categorieASupprimer)) {
            estSupprimer = 
                    banqueCategorie.getCategories().remove(categorieASupprimer);
        } else {
            estSupprimer = false;
        }
        return estSupprimer;
    }

    /**
     * Pour garder l'instance de la question que l'on veux modifier, on garde
     * l'instance de la question dans cet classe
     * 
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
     * 
     * @param libelle        Le nouveau nom de la question
     * @param categorie      La nouvelle Categoriede la question
     * @param difficulte     La nouvelle difficulte de la question
     * @param reponseJuste   La nouvelle réponse juste de la question
     * @param reponseFausses Les nouvelles reponse fausse de la question
     * @param feedback       Le nouveau feedback de la question
     * @return true si la modification a pu être faite
     * @throws ReponseException 
     * @throws InvalidNameException 
     * @throws InvalidFormatException 
     */
    public boolean modifierQuestion(String libelle, String categorie, 
            int difficulte, String reponseJuste,
            ArrayList<String> reponseFausses, String feedback) 
            throws InvalidNameException, ReponseException, InvalidFormatException {
      Categorie nouvelleCat = banqueCategorie.getExactCategoriesLibelle(categorie);
      questionAModifier.setLibelle(libelle);
      questionAModifier.setCatgorie(nouvelleCat);
      questionAModifier.setDifficulte(difficulte);
      questionAModifier.setBonneReponse(reponseJuste);
      questionAModifier.setMauvaiseReponse(reponseFausses);
      questionAModifier.setFeedback(feedback);
      Question temoinAjout = new Question (libelle , nouvelleCat , difficulte , 
              reponseJuste , reponseFausses , feedback);
      return banqueQuestion.getQuestions().contains(temoinAjout); 
    }

    /** @return valeur de categorieAModifier */
    public Categorie getCategorieAModifier() {
        return catgorieAModifier;
    }

    /**
     * Pour garder l'instance de la catégorie que l'on veux modifier, on garde
     * l'instance de la catégorie dans cet classe
     * 
     * @param catgorieAModifier nouvelle valeur de catgorieAModifier
     */
    public void setCategorieAModifier(Categorie catgorieAModifier) {
        this.catgorieAModifier = catgorieAModifier;
    }
    
    /**
     * 
     * TODO comment method role
     * @param nouveauNom
     * @return
     * @throws InvalidNameException 
     */
    public boolean modifierCategorie(String nouveauNom) throws InvalidNameException {
        catgorieAModifier.setNom(nouveauNom);
        return true;
    }
    
    /**
     * @param categorie La catégorie dont on veut connaître le nombre de question
     * @return le nombre de question présente dans une catégorie
     */
    public int getNombreQuestionCategorie(Categorie categorie) {
        return getBanqueQuestion().getQuestions(categorie).size();
    }

}
