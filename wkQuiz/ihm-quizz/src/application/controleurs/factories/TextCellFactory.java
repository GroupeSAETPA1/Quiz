/*
 * TextCellFactory.java                                    24 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.factories;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

/** 
 * Créer une cellule pour afficher du text.
 * Quand la souris hover le text, le text est affiche dans un Tooltip
 * @author François de Saint Palais
 */
public class TextCellFactory<P> implements Callback<TableColumn<P, String>, TableCell<P, String>> {

    /* non javadoc - @see javafx.util.Callback#call(java.lang.Object) */
    @Override
    public TableCell<P, String> call(TableColumn<P, String> arg0) {

        TableCell<P, String> cell 
        = new TableCell<P, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.toString());
                }
                Tooltip information = new Tooltip(getText());
                Tooltip.install(this, information);
            }
        };
        
        cell.setAlignment(Pos.CENTER);
        cell.textProperty().setValue(cell.getItem());
        cell.setWrapText(true);
        
        return cell;
    }
}
