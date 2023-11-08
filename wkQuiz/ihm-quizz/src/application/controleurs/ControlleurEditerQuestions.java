package application.controleurs;

import application.controleurs.lignes.LigneQuestion;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class ControlleurEditerQuestions {

        TableColumn<LigneQuestion, String> reponseJusteColumn = new TableColumn<>("réponse juste");
        reponseJusteColumn.setCellValueFactory(new PropertyValueFactory<>("reponseJuste"));
        reponseJusteColumn.setCellFactory(tc -> {
            TableCell<LigneQuestion, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            cell.setStyle("-fx-font-size: 30px");
            return cell;
        });

        TableColumn<LigneQuestion, String> reponsesFaussesColumn = new TableColumn<>("réponses fausses");
        reponsesFaussesColumn.setCellValueFactory(new PropertyValueFactory<>("reponsesFausses"));
        reponsesFaussesColumn.setCellFactory(tc -> {
            TableCell<LigneQuestion, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            cell.setStyle("-fx-font-size: 30px");
            return cell;
        });

        TableColumn<LigneQuestion, String> feedbackColumn = new TableColumn<>("feedback");
        feedbackColumn.setCellValueFactory(new PropertyValueFactory<>("feedback"));
        feedbackColumn.setCellFactory(tc -> {
            TableCell<LigneQuestion, String> cell = new TableCell<>();
            cell.setAlignment(Pos.CENTER);
            cell.textProperty().bind(cell.itemProperty());
            cell.setStyle("-fx-font-size: 30px");
            return cell;
        });

        TableColumn<LigneQuestion, String> modifColumn = new TableColumn<>("Modifier la question");
        modifColumn.setCellFactory(new EditerQuestionButtonCellFactory());

        TableColumn<LigneQuestion, String> supColumn = new TableColumn<>("Supprimer la question");
        supColumn.setCellFactory(new SupprimerQuestionButtonCellFactory());

        /** style de la table */
        double tableWidth = 1272;
        categorieColumn.setPrefWidth(tableWidth * 0.15);
        libelleColumn.setPrefWidth(tableWidth * 0.15);
        reponseJusteColumn.setPrefWidth(tableWidth * 0.15);
        reponsesFaussesColumn.setPrefWidth(tableWidth * 0.15);
        feedbackColumn.setPrefWidth(tableWidth * 0.15);
        modifColumn.setPrefWidth(tableWidth * 0.1);
        supColumn.setPrefWidth(tableWidth * 0.1);

        // table.getColumns().addAll(categorieColumn);
	}

}