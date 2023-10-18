import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("29158db4-b81f-442d-a2e3-915e395b3e73")
public class BanqueCatgorie {
    @objid ("4e852ad8-87ec-40e0-8f0c-ce5f17d23601")
    private Categorie[] categories;

    @objid ("37db3396-1ad5-4d63-a642-b020de394374")
    public Categorie categorie;

    @objid ("36816b2b-a3a9-4d80-8fb1-7638aaf309dd")
    public List<Categorie> getCategories() {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Categories, permet de récupérer une Categories précise avec son indice dans l’array qui stocke toute les Categories
     */
    @objid ("df50f37c-c795-4e8b-bf73-269166819f3c")
    public Categorie getCategorie(final int id) {
        // TODO Auto-generated return
        return null;
    }

    /**
     * Permet de récupérer les Categories qui on le libellé passé en paramètre(on vérifie que le libellé contient la string passée en paramètres)
     */
    @objid ("e3c650e5-0e83-486f-99b0-f0e1fd5c3e50")
    public List<Categorie> getCategoriesLibelle(final String libelle) {
        // TODO Auto-generated return
        return null;
    }

    @objid ("1ce3a243-b39f-4643-a51c-e5a4d1bf3a55")
    public void ajouter(final Categorie categorie) throws HomonymeException {
    }

    @objid ("c57d5be1-f09d-40cb-b093-9a6b6de2e32f")
    public BanqueCatgorie() {
    }

}
