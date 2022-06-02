package application.view;

import java.net.URL;
import java.util.ResourceBundle;

import application.DailyBankState;
import application.control.SimulationEmpruntPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SimulationEmpruntController implements Initializable{
	
	// Etat application
		private DailyBankState dbs;
		private SimulationEmpruntPane sim;
	
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
	
	public void displayDialog() {
		this.primaryStage.showAndWait();
	}

	// Gestion du stage
	private Object closeWindow(WindowEvent e) {
		this.doCancel();
		e.consume();
		return null;
	}
	
	
	// Attributs de la scene + actions
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Permet de fermer la fenêtre au clique d'un bouton
	 */
	@FXML
	private void doCancel() {
		this.primaryStage.close();
	}


}
