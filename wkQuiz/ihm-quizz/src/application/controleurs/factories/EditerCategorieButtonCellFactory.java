package application.controleurs.factories;

import application.controleurs.lignes.LigneCategorie;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class EditerCategorieButtonCellFactory implements Callback<TableColumn<LigneCategorie, String>, TableCell<LigneCategorie, String>> {

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
                    Button editerButton = new Button("");
                    Image image = new Image(getClass().getResource("/application/vue/images/IconeEdition.png").toExternalForm());
                    editerButton.setGraphic(new ImageView(image));
                    editerButton.setStyle("-fx-background-color: transparent;");
                    editerButton.setOnAction(event -> {
                        LigneCategorie ligne = getTableView().getItems().get(getIndex());
                        ligne.editerCategorie();
                    });

                    setGraphic(editerButton);
                }
            }
        };
    }
}
