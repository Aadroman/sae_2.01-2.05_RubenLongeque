package application.view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DailyBankState;
import application.control.ComptesManagement;
import application.control.OperationsManagement;
import application.control.PrelevementManagement;
import application.tools.NoSelectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class PrelevementManagementController implements Initializable{
	
	// Etat application
		private DailyBankState dbs;
		private PrelevementManagement pm;
		
	// Fenêtre physique
	private Stage primaryStage;
	
	// Données de la fenêtre
		private Client clientDesComptes;
		private ObservableList<PrelevementAutomatique> olPrelevement;
	
		// Manipulation de la fenêtre
		public void initContext(Stage _primaryStage, PrelevementManagement _pm, DailyBankState _dbstate, Client client) {
			this.pm = _pm;
			this.primaryStage = _primaryStage;
			this.dbs = _dbstate;
			this.clientDesComptes = client;
			this.configure();
		}
		
		private void configure() {
			String info;

			this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

			this.olPrelevement = FXCollections.observableArrayList();
			this.lvPrelevement.setItems(this.olPrelevement);
			this.lvPrelevement.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			this.lvPrelevement.getFocusModel().focus(-1);

			info = this.clientDesComptes.nom + "  " + this.clientDesComptes.prenom + "  (id : "
					+ this.clientDesComptes.idNumCli + ")";
			this.lblInfosClient.setText(info);

			//this.loadList();
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
		private ListView<PrelevementAutomatique> lvPrelevement;
		
		/*
		 * Permet de fermer la fenêtre au clique d'un bouton
		 */
		@FXML
		private void doCancel() {
			this.primaryStage.close();
		}
		
		/*
		 * Permet d'ajouter un nouveau compte
		 */
		@FXML
		private void doNouveauPrelevement() throws SQLException {
			PrelevementAutomatique prelevement;
			prelevement = this.pm.creerPrelevement();
			if (prelevement != null) {
				this.olPrelevement.add(prelevement);
			}
		}
		
		/*
		 * Permet de modifier les informations d'un compte
		 */
		@FXML
		private void doModifierPrelevement() {
			/*int selectedIndice = this.lvComptes.getSelectionModel().getSelectedIndex();
			if (selectedIndice >= 0) {
				CompteCourant cpt = this.olCompteCourant.get(selectedIndice);
				this.cm.modifierCompte(cpt);
			}
			this.loadList();*/
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			
		}
}
