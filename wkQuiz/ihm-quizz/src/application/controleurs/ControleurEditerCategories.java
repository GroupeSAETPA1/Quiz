package application.controleurs;

import java.util.ArrayList;

import application.Quiz;
import application.controleurs.factories.TextCellFactory;
import application.modele.Categorie;
import application.modele.ModelePrincipal;
import application.vue.AlertBox;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * Controlleur de la page d'édition des catégories. Celui-ci instance des
 * méthodes liée au bouton de la page
 * 
 * @author Quentin COSTES
 */

public class ControleurEditerCategories {

    private ModelePrincipal modele = ModelePrincipal.getInstance();

    @FXML
    private TableView<LigneCategorie> table;

    @FXML
    private TextField barreRecherche;

    private boolean filtre = false;

    /**
     * Méthodes liée au group retour evoie vers la page précédente
     */
    @FXML
    private void retour() {
        Quiz.changerVue("Editeur.fxml");
    }

    /**
     * Méthodes liée au bouton Créer Categorie envoie vers la page
     * CreationQuestionEtCategorie.fxml
     */
    @FXML
    private void versCreerCategorie() {
        ModelePrincipal.getInstance().setDisplayCategoriePane(true);
        Quiz.chargerEtChangerVue("CreationQuestionEtCategorie.fxml");
    }

    @FXML
    public void initialize() {
        table.setPlaceholder(new Label("Pas de Catégorie trouvé"));
        
        table.setRowFactory(tr->{
            TableRow<LigneCategorie> row 
            = new TableRow<ControleurEditerCategories.LigneCategorie>();
            row.setPrefHeight(80);
            return row;
        });

        TableColumn<LigneCategorie, String> nomColumn = new TableColumn<>("Nom de la categorie");
        TableColumn<LigneCategorie, String> nbColumn = new TableColumn<>("Nombre de questions");
        TableColumn<LigneCategorie, String> modifColumn = new TableColumn<>("Modifier");
        TableColumn<LigneCategorie, String> supColumn = new TableColumn<>("Supprimer");

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomCategorie"));
        nomColumn.setCellFactory(new TextCellFactory<LigneCategorie>());

        nbColumn.setCellValueFactory(new PropertyValueFactory<>("nombreQuestion"));
        nbColumn.setCellFactory(new TextCellFactory<LigneCategorie>());

        modifColumn.setCellFactory(new EditerCategorieButtonCellFactory());

        supColumn.setCellFactory(new SupprimerCategorieButtonCellFactory());

        /* style initial de la table (avec ajouts via mainStyle.css) */
        double tableWidth = 1272;
        nomColumn.setPrefWidth(tableWidth * 0.45);
        nomColumn.setResizable(false);
        nbColumn.setPrefWidth(tableWidth * 0.25);
        nbColumn.setResizable(false);
        modifColumn.setPrefWidth(tableWidth * 0.15);
        modifColumn.setResizable(false);
        supColumn.setPrefWidth(tableWidth * 0.15);
        supColumn.setResizable(false);

        table.getColumns().addAll(nomColumn, nbColumn, modifColumn, supColumn);

        miseAJourTableau();

    }

    public void filtrer() {
        filtre = true;
        miseAJourTableau();
    }

    /**
     * Modifie le tableau des catégories
     */
    private void miseAJourTableau() {
        ModelePrincipal modele = ModelePrincipal.getInstance();

        ObservableList<LigneCategorie> data = table.getItems();
//        FilteredList<LigneCategorie> dataFiltre = new FilteredList<>(data);

        ArrayList<Categorie> categories;
        if (filtre) {
            data.clear();
            categories = modele.getCategoriesLibelle(barreRecherche.getText().strip());
        } else {
            categories = modele.getBanqueCategorie().getCategories();
        }

        for (Categorie categorie : categories) {
            data.add(new LigneCategorie(categorie.getNom(),
                    String.valueOf(modele.getNombreQuestionCategorie(categorie))));

        }
    }

    /**
     * Classe permettant l'affichage des lignes dans la tableView de la vue
     * EditerCategories
     * 
     * @author Quentin COSTES
     */
    public class LigneCategorie {

        private final String nomCategorie;
        private final String nombreQuestion;
        private final Button editerButton;
        private final Button supprimerButton;

        private static ModelePrincipal modele = ModelePrincipal.getInstance();

        public LigneCategorie(String nom, String nb) {
            this.nomCategorie = nom;
            this.nombreQuestion = nb;
            this.editerButton = new Button("Éditer");
            this.supprimerButton = new Button("Supprimer");

            editerButton.setOnAction(event -> editerCategorie());
            supprimerButton.setOnAction(event -> supprimerCategorie());
        }

        public String getNomCategorie() {
            return nomCategorie;
        }

        public String getNombreQuestion() {
            return nombreQuestion;
        }

        public Button getEditerButton() {
            return editerButton;
        }

        public Button getSupprimerButton() {
            return supprimerButton;
        }

        public void editerCategorie() {
            // méthode appelée lors de l'appuie sur le bouton d'edition de la catégorie
            if (!this.getNomCategorie().equals("General")) {
                // si la catégorie n'est pas général
                modele.setCategorieAModifier(modele.getCategoriesLibelleExact(getNomCategorie()));
                Quiz.chargerEtChangerVue("EditerCategorie.fxml");
            } else {
                AlertBox.showErrorBox("La catégorie générale ne peut pas etre modifiée");
            }
        }

        public void supprimerCategorie() {
            if (!this.getNomCategorie().equals("General")) {
                boolean result = AlertBox.showConfirmationBox("Supprimer la categorie : " + getNomCategorie());
                if (result) {

                    if (modele.supprimerCategorie(modele.getCategoriesLibelleExact(getNomCategorie()))) {
                        AlertBox.showSuccessBox("Suppresion effectuée");
                        Quiz.chargerEtChangerVue("EditerCategories.fxml");
                    } else {
                        AlertBox.showErrorBox("Suppression échouée de " + getNomCategorie());
                    }

                } else {
                    AlertBox.showErrorBox("Suppression annulée");
                }
            } else {
                AlertBox.showErrorBox("Vous ne pouvez modifier la catégorie principale");
            }
        }
    }

    /**
     * Permet la création d'un bouton dans la TableView
     * 
     * @author Quentin Costes
     * @author François de Saint Palais
     */
    public class EditerCategorieButtonCellFactory
            implements Callback<TableColumn<LigneCategorie, String>, TableCell<LigneCategorie, String>> {

        @Override
        public TableCell<LigneCategorie, String> call(TableColumn<LigneCategorie, String> param) {
            return new TableCell<LigneCategorie, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    super.setAlignment(Pos.CENTER);

                    if (empty || getTableRow() == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        LigneCategorie ligne = getTableView().getItems().get(getIndex());

                        if (!modele.estGeneral(ligne.getNomCategorie())) {
                            Button editerButton = new Button("");
                            Image image = new Image(getClass().getResource("/application/vue/images/IconeEdition.png")
                                    .toExternalForm());
                            editerButton.setGraphic(new ImageView(image));
                            editerButton.setStyle("-fx-background-color: transparent;");

                            editerButton.setOnAction(event -> {
                                ligne.editerCategorie();
                            });

                            setGraphic(editerButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };
        }
    }

    /**
     * 
     * TODO comment class responsibility (SRP)
     * 
     * @author Quentin Costes
     * @author François de Saint Palais
     */
    public class SupprimerCategorieButtonCellFactory
            implements Callback<TableColumn<LigneCategorie, String>, TableCell<LigneCategorie, String>> {

        @Override
        public TableCell<LigneCategorie, String> call(TableColumn<LigneCategorie, String> param) {
            return new TableCell<LigneCategorie, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    super.setAlignment(Pos.CENTER);

                    if (empty || getTableRow() == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        
                        LigneCategorie ligne = getTableView().getItems().get(getIndex());

                        if (!modele.estGeneral(ligne.getNomCategorie())) {
                        
                            // Créez le bouton de suppression pour chaque ligne et associez une action
                            Button supprimerButton = new Button();
                            Image image = new Image(getClass().getResource("/application/vue/images/IconeSupprimer.png")
                                    .toExternalForm());
                            supprimerButton.setGraphic(new ImageView(image));
                            supprimerButton.setStyle("-fx-background-color: transparent;");
                            supprimerButton.setOnAction(event -> {
                                ligne.supprimerCategorie();
                            });

                            setGraphic(supprimerButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                }
            };
        }
    }
}