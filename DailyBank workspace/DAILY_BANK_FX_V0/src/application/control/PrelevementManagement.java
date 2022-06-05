package application.control;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.StageManagement;
import application.view.ComptesManagementController;
import application.view.OperationsManagementController;
import application.view.PrelevementManagementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.Client;
import model.data.CompteCourant;

/**
 * @author yann
 * classe dédié à la gestion de la fenêtre "gestion des prélèvements"
 */
public class PrelevementManagement {
	
	/**
	 * Attributs
	 */
	
	private Stage primaryStage; //la fenêtre
	private DailyBankState dbs;
	private Client clientDuCompte;
	private CompteCourant compteConcerne; //le compte du client
	private PrelevementManagementController pmc; //le controller
	
	/**
	 * @param _parentStage
	 * @param _dbstate
	 * @param client
	 * @param compte
	 * représente la fenêtre "gestion des prélèvements" après avoir cliquer sur le bouton
	 * "prélèvement automatique" des opérations d'un compte d'un client
	 */
	public PrelevementManagement(Stage _parentStage, DailyBankState _dbstate, Client client, CompteCourant compte) {

		this.clientDuCompte = client;
		this.compteConcerne = compte;
		this.dbs = _dbstate;
		try {
			FXMLLoader loader = new FXMLLoader(
					OperationsManagementController.class.getResource("prelevementmanagement.fxml"));
			BorderPane root = loader.load();

			Scene scene = new Scene(root, 900 + 20, 350 + 10);
			scene.getStylesheets().add(DailyBankApp.class.getResource("application.css").toExternalForm());

			this.primaryStage = new Stage();
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(_parentStage);
			StageManagement.manageCenteringStage(_parentStage, this.primaryStage);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Gestion des prélèvements");
			this.primaryStage.setResizable(false);

			this.pmc = loader.getController();
			this.pmc.initContext(this.primaryStage, this, _dbstate, client, this.compteConcerne);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doPrelevementManagementDialog() {
		this.pmc.displayDialog();
	}

}
