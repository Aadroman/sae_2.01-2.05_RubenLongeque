package application.view;

import java.net.URL;
import java.util.ResourceBundle;

import application.DailyBankState;
import application.control.SimulationEmpruntPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.Emprunt;

public class SimulationEmpruntController implements Initializable{
	
	// Etat application
		private DailyBankState dbs;
		private SimulationEmpruntPane sim;
		private Emprunt emprunt;
	
	// Fenêtre physique
	private Stage primaryStage;
	
	// Données de la fenêtre
	
	
	// Manipulation de la fenêtre
	public void initContext(Stage _primaryStage, SimulationEmpruntPane _sim, DailyBankState _dbstate) {
		this.sim = _sim;
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
	
	
	// Attributs de la scene + actions
	@FXML
	private TextField capital;
	@FXML
	private TextField duree;
	@FXML
	private TextField tauxAnnuel;
	@FXML
	private TextField tauxApplicable;
	@FXML
	private TextField nbPeriodes;
	@FXML
	private TextField mensualiteSansA;
	@FXML
	private TextField coutCredit;
	@FXML
	private TextField fraisDossier;
	@FXML
	private TextField total;
	@FXML
	private TextField tauxAssurance;
	@FXML
	private TextField mensualiteAssurance;
	@FXML
	private TextField mensualiteAvecA;
	@FXML
	private TextField coutAssurance;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	/*
	 * Permet de fermer la fenêtre au clique d'un bouton
	 */
	@FXML
	private void doCancel() {
		this.primaryStage.close();
	}


}
