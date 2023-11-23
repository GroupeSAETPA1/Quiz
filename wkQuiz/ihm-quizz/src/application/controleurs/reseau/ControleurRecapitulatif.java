package application.controleurs.reseau;


import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import application.Quiz;
import application.modele.ModelePrincipal;





public class ControleurRecapitulatif {

	
	@FXML TableView<LigneRecapitulatif> tableRecap;
	@FXML TableColumn<LigneRecapitulatif, String> libelleQuestion;
	@FXML TableColumn<LigneRecapitulatif, String> categorie;
	@FXML TableColumn<LigneRecapitulatif, String> difficulte;
	@FXML TableColumn<LigneRecapitulatif, String> repVrai;
	@FXML TableColumn<LigneRecapitulatif, String> repFausse;
	
	 
	 
	 
	 @FXML
	    public void initialize() {
		 
		 libelleQuestion.setCellValueFactory(
	                new PropertyValueFactory<LigneRecapitulatif, String>
	                ("libelleQuestion"));
		 
		 categorie.setCellValueFactory(
	                new PropertyValueFactory<LigneRecapitulatif, String>
	                ("categorie"));
		 
		 difficulte.setCellValueFactory(
	                new PropertyValueFactory<LigneRecapitulatif, String>
	                ("difficulte"));
		 
		 repVrai.setCellValueFactory(
	                new PropertyValueFactory<LigneRecapitulatif, String>
	                ("repVrai"));
		 
		 repFausse.setCellValueFactory(
	                new PropertyValueFactory<LigneRecapitulatif, String>
	                ("repFausse"));
		
		 recapChoix();
	 }
	 
	 private void recapChoix() {
		 
		 private ModelePrincipal modele = ModelePrincipal.getInstance();
		 
		 
	 }
	
	
	@FXML
	private void retour() {
		Quiz.changerVue("ChoixEnvoie.fxml");
	}
	
	@FXML
	private void valider() {
		Quiz.changerVue("EnvoieQuestion.fxml");
	}
	
	  public  class LigneRecapitulatif {
	        
	        String libelleQuestion;
	        String categorie;
	        String difficulte;
	        String repVrai;
	        String repFausse;
	        
	        public LigneRecapitulatif(String libelleQuestion, String categorie,  String difficulte, String repVrai,String repFausse ) {
	            super();
	            this.libelleQuestion = libelleQuestion;
	            this.categorie = categorie;
	            this.difficulte = difficulte;
	            this.repVrai = repVrai;
	            this.repFausse = repFausse;
	        }
	        
	        public String getLibelleQuestion() {
	            return libelleQuestion;
	        }
	        
	        public String getCategorie() {
	            return categorie;
	        }
	        
	        public String getDifficulte() {
	            return difficulte;
	        }
	        
	        public String getRepVrai() {
	            return repVrai;
	        }
	        
	        public String getRepFausse() {
	            return repFausse;
	        }
	        
	  }
}
