package modele;

import java.util.ArrayList;

public class Question {
    private String libelle;

    private int difficulte;

    private String reponseJuste;

    private String[] reponseFausse = new String[4];

    private String feedback;

    private Categorie categorie;

    public Question(final String libelle, final Categorie categorie, final int difficulte, final String reponseJuste, final ArrayList<String> reponsesFausse, final String feedback) {
    }

    public Question(final String libelle, final Categorie categorie, final int difficulte, final String reponseJuste, final ArrayList<String> reponsesFausse) {
    }

    public void setLibelle(final String nouveauIntitulle) {
    }

    public void setCatgorie(final String nouvelleCategorie) {
    }

    public void setDifficulte(final int nouvelleDifficulte) {
    }

    public void setBonneReponse(final String nouvelleBonneReponse) {
    }

    public void setMauvaiseReponse(final ArrayList<String> nouvellesMauvaisesReponses) {
    }

}
