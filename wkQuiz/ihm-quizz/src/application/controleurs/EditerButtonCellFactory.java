package application.controleurs;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class EditerButtonCellFactory implements Callback<TableColumn<LigneCategorie, String>, TableCell<LigneCategorie, String>> {

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
                    Button editerButton = new Button("Éditer");
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
