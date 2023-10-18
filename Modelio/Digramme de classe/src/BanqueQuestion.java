import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

/**
 * Gestion de toutes les questions présentes  
 * dans l’application
 */
@objid ("467b495b-558b-4413-8cdf-ff8efbb1efa6")
public class BanqueQuestion {
    /**
     * <Saisir le texte de la note ici>
     */
    @objid ("b7bcaaf6-0522-4248-8784-6bfbdff5c306")
    private Categorie[] questions;

    @objid ("8cb4ac81-9283-4eba-8a90-90d7323954d6")
    public Question question;

    /**
     * Permet de récupérer une question précise avec son indice dans l’array qui stocke toute les Questions
     */
    @objid ("1c0be7ce-4cd7-4ceb-8b15-cd70e498b646")
    public Question getQuestion(final int id) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui on la catégorie passé en paramètres.
     */
    @objid ("172ced37-a255-431c-8329-6046a7ced690")
    public List<Question> getQuestions(final Categorie categorie) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui ont la difficultée passée en paramètre
     */
    @objid ("881d649a-40fa-49df-b8fa-5113a1e25838")
    public List<Question> getQuestionsDifficulte(final int difficulte) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui on le nombre de fausses réponses passées en paramètres
     */
    @objid ("656a7980-526f-45f4-9921-ae5dc110d35c")
    public List<Question> getQuestionNbFausseReponse(final int nb) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer toutes les questions
     */
    @objid ("e718f999-45e1-4c8d-9dc6-ca5da9ca1906")
    public List<Question> getQuestions() {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les questions qui on le libellé passé en paramètre (on vérifie que le libellé contient la string passée en paramètres)
     */
    @objid ("5042d36b-8393-477f-9d05-537388648eeb")
    public Question getQuestionsLibelle(final List<String> nom) {
        // TODO Auto-generated return
        return null;
    }

    @objid ("70e6b249-1bfc-4099-adc8-e94ce3c33d50")
    public BanqueQuestion(final List<Question> questions) {
    }

    @objid ("8f04a0ac-65f6-4a69-a1c4-ed0afbbb1045")
    public void ajouter(final Question question) {
    }

}
