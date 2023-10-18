import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("aaa1748e-8446-4718-b452-8fe6e90eaf2a")
public class Question {
    /**
     * Intitulé de la question
     */
    @objid ("51b78efd-bfac-49ae-89e1-fa0e6f5b40b3")
    private String libelle;

    /**
     * <p>Entier entre 1 et 3</p>
     * 
     * <ul>
     * 	<li>1 repr&eacute;sentant le niveau facile</li>
     * 	<li>2 le moyen</li>
     * 	<li>3 le difficile</li>
     * </ul>
     */
    @objid ("936bbfc7-cdbb-4386-a46c-ef009cf8971c")
    private int difficulte;

    /**
     * Bonne réponses à la questions
     */
    @objid ("de675407-b3d1-4161-9410-de3c17486c85")
    private String reponseJuste;

    /**
     * Liste des mauvaises propositions de réponses à la question. 
     * Il peut y avoir entre 1 et 4 (inclus) mauvaises réponses.
     */
    @objid ("0b2338b8-0603-4c04-a568-e7903047a8c6")
    private String[] reponseFausse = new String[4];

    /**
     * Texte optionnel correspondant a une explication de la correction de la question
     */
    @objid ("44544805-13ad-4f67-9bdc-565a9037858b")
    private String feedback;

    /**
     * Catégorie de la question
     */
    @objid ("3d254095-6ca3-4f3e-b0dd-be252bd5144e")
    private Categorie categorie;

    @objid ("1b676821-35b1-4010-83ce-9c61a6054914")
    public Categorie categorie;

    /**
     * Constructeur de la classe avec pour paramètres (le libellé , la catégorie , la difficulté , la bonne proposition de réponse ainsi que la liste des mauvaises propositions.
     * @throw IllegalArgumentException si le libellé est une chaîne vide , si la reponseJuste est une chaîne vide , si la difficulté est différente de 1 , 2 ou 3 , si reponsesFausses est une liste vide.
     */
    @objid ("71ce09d4-8a72-45fd-a3aa-c9c331764191")
    public Question(final String libelle, final Categorie categorie, final int difficulte, final String reponseJuste, final List<String> reponsesFausse, final String feedback) {
    }

    @objid ("4a057655-43d4-44c8-9247-fc80f90f11c2")
    public Question(final String libelle, final Categorie categorie, final int difficulte, final String reponseJuste, final List<String> reponsesFausse) {
    }

    @objid ("f5eb304f-cf8f-4f54-b7ad-c042c3681eaa")
    public void setLibelle(final String nouveauIntitulle) {
    }

    /**
     * Change l’ancienne catégorie en nouvelleCategorie
     */
    @objid ("bcce1aec-2419-43a1-948b-922f18024b69")
    public void setCatgorie(final String nouvelleCategorie) {
    }

    @objid ("d11847de-552b-4e84-b22a-1b2b1c53baa3")
    public void setDifficulte(final int nouvelleDifficulte) {
    }

    @objid ("34bc2fb1-e028-4ce2-a245-14cbe9bbd0bc")
    public void setBonneReponse(final String nouvelleBonneReponse) {
    }

    @objid ("d25c4c8e-7c4d-4782-81bd-0bb51e97eb0b")
    public void setMauvaiseReponse(final List<String> nouvellesMauvaisesReponses) {
    }

}
