package src.application;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import src.application.modele.ModelePrincipale;
import src.application.vue.GestionVues;

@objid ("b9024959-931c-46de-aec1-eb66d513747d")
public class Quiz {
    @objid ("22a17bd8-d55c-4bf6-b11f-2c748200fef5")
    public GestionVues gestionVues;

    @objid ("f2363b42-b306-41f7-b0e4-20eb11624284")
    public ModelePrincipale modelePrincipale;

    @objid ("d91b29c7-6467-411b-9973-ea42e0911559")
    private ModelePrincipale modele;

    @objid ("3bf806ab-a9db-4d7a-a70f-828ba9ef9d1b")
    public GestionVues vuesManager;

    @objid ("bee1e32e-cf71-4f07-b0a0-366f91bf7e6a")
    public static void main() {
    }

}
