/*
 * ModelePrincipal.java                                    26 oct. 2023
 * IUT de Rodez, info1 2022-2023, aucun copyright ni copyleft
 */

package application.modele;

import java.util.ArrayList;
import java.util.HashMap;

import application.exception.CreerQuestionException;
import application.exception.DifficulteException;
import application.exception.HomonymeException;
import application.exception.InvalidFormatException;
import application.exception.InvalidNameException;
import application.exception.ReponseException;

/**
 * Contrôleur principale de l'application .
 * Permet l'interaction entre les classes du modèle et les controlleurs.
 * C'est une classe Singleton
 * @author Lucas Descriaud
 * @author François de Saint Palais
 */
public class ModelePrincipal {

    /**
     * Lie une difficulté à sont equivalent en string
     * Ex : 1 -> Facile
     */
    public static final HashMap<Integer, String> INT_DIFFICULTE_TO_LABEL
    = new HashMap<>();

    /**
     * Lie une difficulté à sont equivalent numérique
     * Ex : Facile -> 1
     */
    public static final HashMap<String, Integer> LABEL_DIFFICULTE_TO_INT
    = new HashMap<>();

    private static ModelePrincipal modele;

    /** Les CSV importé devront séparé leur élément avec une tabulation */
    public static final char SEPARATEUR_CSV = '\t';
    
    private BanqueCategorie banqueCategorie;
    private BanqueQuestion banqueQuestion;

	private Categorie catgorieAModifier;
    private boolean displayCategoriePane;

    private String pagePrecedente;

    private Partie partie;

    private Question questionAModifier;
    
    private ArrayList<Question> questionAEnvoyer;

    /**
     * Constructeur
     * @throws InvalidNameException 
     */
    private ModelePrincipal() {
        // TODO lire les fichiers serialisé
        this.banqueQuestion = new BanqueQuestion();
        this.banqueCategorie = new BanqueCategorie();
        this.partie = new Partie();


        LABEL_DIFFICULTE_TO_INT.put("Facile", 1);
        LABEL_DIFFICULTE_TO_INT.put("Moyen", 2);
        LABEL_DIFFICULTE_TO_INT.put("Difficile", 3);
        LABEL_DIFFICULTE_TO_INT.put("Tous" , 0);

        INT_DIFFICULTE_TO_LABEL.put(1, "Facile");
        INT_DIFFICULTE_TO_LABEL.put(2, "Moyen");
        INT_DIFFICULTE_TO_LABEL.put(3, "Difficile");
        INT_DIFFICULTE_TO_LABEL.put(0 , "Tous");
    }

    /**
     * @return Renvoie l'instance unique de ModelePrincipal
     * @throws InvalidNameException 
     */
    public static ModelePrincipal getInstance() {
        if (ModelePrincipal.modele == null) {
            ModelePrincipal.modele = new ModelePrincipal();
        }
        return modele;
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
     * @param nom Le nom de la catégorie recherché
     * @return true si la catégorie existe, false sinon
     */
    public boolean categorieExiste(String nom) {
        return banqueCategorie.getCategorieLibelleExact(nom) != null;
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
     * @throws DifficulteException
     */
    public boolean creerQuestion(String libelle, int idCategorie, int difficulte, String reponseJuste,
            ArrayList<String> reponseFausses, String feedback)
            throws InvalidFormatException, InvalidNameException, ReponseException, HomonymeException, DifficulteException {

        // La catégorie de la question existera toujours donc aucune vérification d'existence n'est nécessaire
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

    /** @return La BanqueCategorie */
    public BanqueCategorie getBanqueCategorie() {
        // TODO Je pense que l'on peux changer la visibilité de certaine méthode de
        // Question. Pour éviter les effet de bords
        return banqueCategorie;
    }

    /** @return La BanqueQuestion */
    public BanqueQuestion getBanqueQuestion() {
        // TODO Je pense que l'on peux changer la visibilité de certaine méthode de
        // Question. Pour éviter les effet de bords
        return banqueQuestion;
    }

    /** @return valeur de categorieAModifier */
    public Categorie getCategorieAModifier() {
        return catgorieAModifier;
    }

    /** @return Une liste des categories */
    public ArrayList<Categorie> getCategories() {
        return banqueCategorie.getCategories();
    }

    /**
     * @param libelle Le nom de catégorie recherché
     * @return Les catégories qui ont libelle dans leur nom
     */
    public ArrayList<Categorie> getCategoriesLibelle(String libelle) {
		return this.getBanqueCategorie().getCategoriesLibelle(libelle);
	}

    /**
     * Retourne l'indice de la catégorie dans la liste des catégories
     * @param string catégorie recherché
     * @return L'indice de la catégorie
     */
    public int getIndice(String string) {
        return banqueCategorie.getIndice(string);
    }

    /**
     * @param categorie La catégorie dont on veut connaître le nombre de question
     * @return le nombre de question présente dans une catégorie
     */
    public int getNombreQuestionCategorie(Categorie categorie) {
        return getBanqueQuestion().getQuestions(categorie).size();
    }

    /** @return la page précédente */
    public String getPagePrecendente(){
    	return pagePrecedente;
    }

    /** @return la partie */
    public Partie getPartie() {
		return partie;
	}

    /** @return valeur de questionAModifier */
    public Question getQuestionAModifier() {
        return questionAModifier;
    }
    
    /**
     * Renvoie la catégorie qui à exactement le même libellé que passé en paramètre
     * si il n'y en a pas cela renvoie null
     * @param libelle (String) le libellé recherché sensible à la casse
     * @return catégorie avec le libellé voulu si elle existe, null sinon
     */
    public Categorie getCategoriesLibelleExact(String libelle) {
        return banqueCategorie.getCategorieLibelleExact(libelle);
    }

    /**
     * Utilisé pour ce positionner sur le bon onglet 
     * quand on passe d'Editer à Créer
     * @return displayCategoriePane */
    public boolean isDisplayCategoriePane() {
		return displayCategoriePane;
	}

    /**
     * Modifie la catégorie catgorieAModifier stocké dans le modèle 
     * @param nouveauNom Le nouveau nom de la catégorie
     * @return true si la modification est effective
     * @throws InvalidNameException Si le nom choisi est invalide
     */
    public boolean modifierCategorie(String nouveauNom) throws InvalidNameException {
        catgorieAModifier.setNom(nouveauNom);
        //TODO vérifier si le nouveauNom n'est pas celui d'une catégorie déjà présente
        return true;
    }

    /**
     * Modifie la questionAModifier stocké dans le modèle
     *
     * @param libelle        Le nouveau nom de la question
     * @param categorie      La nouvelle Categorie de la question
     * @param difficulte     La nouvelle difficulté de la question
     * @param reponseJuste   La nouvelle réponse juste de la question
     * @param reponseFausses Les nouvelles réponse fausse de la question
     * @param feedback       Le nouveau feedback de la question
     * @return true si la modification a pu être faite
     * @throws ReponseException
     * @throws InvalidNameException
     * @throws InvalidFormatException
     * @throws DifficulteException
     */
    public boolean modifierQuestion(String libelle, String categorie,
            int difficulte, String reponseJuste,
            ArrayList<String> reponseFausses, String feedback)
            throws CreerQuestionException, InvalidNameException {
        
      Categorie nouvelleCat = banqueCategorie.getCategorieLibelleExact(categorie);
      
      questionAModifier.setLibelle(libelle);
      questionAModifier.setCategorie(nouvelleCat);
      questionAModifier.setDifficulte(difficulte);
      questionAModifier.setBonneReponse(reponseJuste);
      questionAModifier.setMauvaiseReponse(reponseFausses);
      questionAModifier.setFeedback(feedback);
      
      Question temoinAjout = new Question (libelle , nouvelleCat , difficulte ,
              reponseJuste , reponseFausses , feedback);
      return banqueQuestion.getQuestions().contains(temoinAjout);
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
     * Change la difficulte actuelle pour difficulte
     * @param difficulte nouvelle difficulte
     */
    public void setDifficultePartie(int difficulte) {
        partie.setDifficultePartie(difficulte);
    }

    public void setDisplayCategoriePane(boolean displayCategoriePane) {
		this.displayCategoriePane = displayCategoriePane;
	}

    public void setPagePrecedente(String nomPage){
    	this.pagePrecedente = nomPage;
    }

    public void setPartie(Partie partie) {
		this.partie = partie;
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

	/**
     * Supprimer de la banqueBategorie la Categorie passé en paramètre. Si la
     * Categorie a supprimer est la Categorie "Général", la suppression est annulé
     *
     * @param categorieASupprimer
     * @return true si la suppression est un sucés false sinon
     */
    public boolean supprimerCategorie(Categorie categorieASupprimer) {
        //TODO a tester
        boolean estSupprimer = false;
        
        if (categorieASupprimer.equals(banqueCategorie.categorieGeneral)) {
            estSupprimer = false;
        
        } else if (banqueCategorie.getCategories().contains(categorieASupprimer)) {
        	/*
        	 *  On parcours la Banque des Questions et on ajoute toutes les questions
        	 *  qui ont la meme catégorie que celle qu'on veut supprimer
        	 *  dans une ArrayList pour apres pouvoir les supprimer
        	 *  de la Banque de questions
        	 */
        	ArrayList<Question> questionsASupprimer = new ArrayList<>();

        	for (Question question : banqueQuestion.getQuestions()) {
				if (question.getCategorie() == categorieASupprimer.toString()) {
					questionsASupprimer.add(question);
				}
			}

        	banqueQuestion.getQuestions().removeAll(questionsASupprimer);
            estSupprimer = banqueCategorie.getCategories().remove(categorieASupprimer);
        
        } else {
            estSupprimer = false;
        }
        
        return estSupprimer;
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
     * Ajouter toute les questions de la catégorie 
     * à la liste des question a envoyer
     * @param nomCategorieAAjouter Le nom de la catégorie
     * @return true si l'ajout est un succès, false sinon
     */
    public boolean ajouterALaSelection(String nomCategorieAAjouter) {

        for (Question question : banqueQuestion.getQuestions()) {
            if (    question.getCategorie() == nomCategorieAAjouter 
                && !questionAEnvoyer.contains(question)) {
                
                questionAEnvoyer.add(question);
            }
        }
        return false; //STUB
    }
}
