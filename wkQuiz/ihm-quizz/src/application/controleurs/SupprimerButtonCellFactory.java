package application.controleurs;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class SupprimerButtonCellFactory implements Callback<TableColumn<LigneCategorie, String>, TableCell<LigneCategorie, String>> {

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
                    Button supprimerButton = new Button("Supprimer");
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
