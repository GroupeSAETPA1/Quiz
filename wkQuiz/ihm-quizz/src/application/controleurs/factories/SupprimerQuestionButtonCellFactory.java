package application.controleurs.factories;

import application.controleurs.lignes.LigneQuestion;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class SupprimerQuestionButtonCellFactory implements Callback<TableColumn<LigneQuestion, String>, TableCell<LigneQuestion, String>> {

    @Override
    public TableCell<LigneQuestion, String> call(TableColumn<LigneQuestion, String> param) {
        return new TableCell<LigneQuestion, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                super.setAlignment(Pos.CENTER);

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
                    	LigneQuestion ligne = getTableView().getItems().get(getIndex());
                        ligne.supprimerQuestion();
                    });

                    setGraphic(supprimerButton);
                }
            }
        };
    }
}
