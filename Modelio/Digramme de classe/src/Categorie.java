import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("e3599152-7e26-4986-9f52-9033f37b1fef")
public class Categorie {
    /**
     * Le nom de la catégorie
     */
    @objid ("01dd089a-cb6c-4dfa-b82d-797b06571215")
    private String nom;

    @objid ("9911a65c-d902-4803-a8a9-55e9602d5978")
    public Categorie(final String nom) {
    }

    @objid ("aef60c3b-69ea-45ef-91d2-104349452ddc")
    public boolean nomValide(final String nom) {
        // TODO Auto-generated return
        return false;
    }

    @objid ("10374c45-8daf-45bb-8968-4436172626c0")
    public void setNom(final String nom) {
    }

    @objid ("9ace7c2a-5053-44b9-803b-25b761f93105")
    public String getNom() {
        // TODO Auto-generated return
        return null;
    }

}
