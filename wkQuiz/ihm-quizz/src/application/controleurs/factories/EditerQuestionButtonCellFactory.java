package application.controleurs.factories;

import application.controleurs.lignes.LigneQuestion;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class EditerQuestionButtonCellFactory implements Callback<TableColumn<LigneQuestion, String>, TableCell<LigneQuestion, String>> {

    @Override
    public TableCell<LigneQuestion, String> call(TableColumn<LigneQuestion, String> param) {
        return new TableCell<LigneQuestion, String>() {
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
                        LigneQuestion ligne = getTableView().getItems().get(getIndex());
                        ligne.editerQuestion();
                    });

                    setGraphic(editerButton);
                }
            }
        };
    }
}
