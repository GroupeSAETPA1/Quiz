/*
 * CheckBoxCategorieCellFactory.java                                    20 nov. 2023
 * IUT de Rodez, info1 2023-2024, aucun copyright ni copyleft
 */

package application.controleurs.factories;

import application.controleurs.reseau.ControleurSelectionCategorie.LigneSelectionCategorie;
import application.controleurs.reseau.ControleurSelectionQuestion.LigneSelectionQuestion;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/** 
 * Créer et gère les actions sur les CheckBox
 * @author François de Saint Palais
 */
public class CheckBoxQuestionCellFactory 
implements  Callback<TableColumn<LigneSelectionQuestion, CheckBox>, 
                     TableCell<LigneSelectionQuestion, CheckBox>> {

    /* non javadoc - @see javafx.util.Callback#call(java.lang.Object) */
    @Override
    public TableCell<LigneSelectionQuestion, CheckBox> 
    call(TableColumn<LigneSelectionQuestion, CheckBox> arg0) {
        return new TableCell<LigneSelectionQuestion, CheckBox>() {
            @Override
            protected void updateItem(CheckBox item, boolean empty) {
                super.updateItem(item, empty);
                super.setAlignment(Pos.CENTER);
                //On créer une CheckBox
                CheckBox checkbox = new CheckBox();

                //On ajoute l'évenement lié au côchage/décochage
                checkbox.setOnAction(event -> {
                    //La ligne de la checkbox
                    LigneSelectionQuestion ligne = getTableView().getItems().get(getIndex());
                    if (checkbox.isSelected()) {
                        ligne.ajouterALaSelection();
                    } else {
                        ligne.retirerALaSelection();
                    }
                });

                setGraphic(checkbox);
            }
        };
    }

}
