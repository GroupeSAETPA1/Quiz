package application.controleurs.factories;

import application.controleurs.lignes.LigneCategorie;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class SupprimerCategorieButtonCellFactory implements Callback<TableColumn<LigneCategorie, String>, TableCell<LigneCategorie, String>> {

    @Override
    public TableCell<LigneCategorie, String> call(TableColumn<LigneCategorie, String> param) {
        return new TableCell<LigneCategorie, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Créez le bouton de suppression pour chaque ligne et associez une action
                    Button supprimerButton = new Button();
                    Image image = new Image(getClass().getResource("/application/vue/images/IconeSupprimer.png").toExternalForm());
                    supprimerButton.setGraphic(new ImageView(image));
                    supprimerButton.setStyle("-fx-background-color: transparent;");
                    supprimerButton.setOnAction(event -> {
                        LigneCategorie ligne = getTableView().getItems().get(getIndex());
                        ligne.supprimerCategorie();
                    });

                    setGraphic(supprimerButton);
                }
            }
        };
    }
}
