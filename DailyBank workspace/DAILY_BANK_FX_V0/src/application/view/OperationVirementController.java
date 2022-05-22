package application.view;

import java.net.URL;
import java.util.ResourceBundle;

import application.DailyBankState;
import application.control.OperationVirement;
import application.control.OperationsManagement;
import application.tools.NoSelectionModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.Client;
import model.data.CompteCourant;
import model.data.Operation;

public class OperationVirementController extends OperationEditorPaneController implements Initializable {
	
	// Etat application
		private DailyBankState dbs;
		private OperationVirement ov;
		
	// Fenêtre physique
		private Stage primaryStage;
		
	// Données de la fenêtre
		private Client clientDuCompte;
		private CompteCourant compteConcerne;
		
		// Manipulation de la fenêtre
		public void initContext(Stage _primaryStage, DailyBankState _dbstate) {
			this.primaryStage = _primaryStage;
			this.dbs = _dbstate;
			this.configure();
		}
	
	private void configure() {
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
	}
	
	// Gestion du stage
		private Object closeWindow(WindowEvent e) {
			this.doCancel();
			e.consume();
			return null;
		}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	// Attributs de la scene + actions
	@FXML
	private TextField txtNumCompte1;
	
	@FXML
	private void doCancel() {
		this.primaryStage.close();
	}

}
