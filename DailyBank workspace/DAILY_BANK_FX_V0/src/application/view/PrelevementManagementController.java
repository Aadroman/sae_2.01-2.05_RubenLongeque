package application.view;

import application.DailyBankState;
import application.control.ComptesManagement;
import application.control.OperationsManagement;
import application.control.PrelevementManagement;
import application.tools.NoSelectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.Client;
import model.data.CompteCourant;
import model.data.Operation;
import model.data.PrelevementAutomatique;

/**
 * @author yann
 * controller de la classe PrelevementManagement
 */

public class PrelevementManagementController {
	
	// Etat application
		private DailyBankState dbs;
		private PrelevementManagement pm;
		
	// Fenêtre physique
	private Stage primaryStage;
	
	// Données de la fenêtre
		private Client clientDesComptes;
		private Client clientDuCompte;
		private CompteCourant compteConcerne;
		private ObservableList<PrelevementAutomatique> olPrelevement;
	
	// Manipulation de la fenêtre
		public void initContext(Stage _primaryStage, PrelevementManagement _pm, DailyBankState _dbstate, Client client, CompteCourant compte) {
			this.primaryStage = _primaryStage;
			this.dbs = _dbstate;
			this.pm = _pm;
			this.clientDuCompte = client;
			this.compteConcerne = compte;
			this.configure();
		}
		
		private void configure() {
			this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
			this.olPrelevement = FXCollections.observableArrayList();
			this.lvPrelevement.setItems(this.olPrelevement);
			this.lvPrelevement.setSelectionModel(new NoSelectionModel<PrelevementAutomatique>());
			//this.updateInfoCompteClient();
			//this.validateComponentState();
		}
		
		public void displayDialog() {
			this.primaryStage.showAndWait();
		}
		
		// Gestion du stage
		private Object closeWindow(WindowEvent e) {
			this.doCancel();
			e.consume();
			return null;
		}
		
		@FXML
		private Button btnModifierPrelevement;
		@FXML
		private Button btnNouveauPrelevement;
		@FXML
		private Label lblInfosClient;
		@FXML
		private Label lblInfosCompte;
		@FXML
		private ListView<PrelevementAutomatique> lvPrelevement;
		
		/*
		 * Permet de fermer la fenêtre au clique d'un bouton
		 */
		@FXML
		private void doCancel() {
			this.primaryStage.close();
		}

}
