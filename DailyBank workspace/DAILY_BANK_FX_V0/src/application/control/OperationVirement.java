package application.control;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.CategorieOperation;
import application.tools.StageManagement;
import application.view.OperationEditorPaneController;
import application.view.OperationVirementController;
import application.view.OperationsManagementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.Client;
import model.data.CompteCourant;
import model.data.Operation;
import model.orm.AccessOperation;
import model.orm.exception.ApplicationException;
import model.orm.exception.DatabaseConnexionException;

public class OperationVirement {
	
	/**
	 * Attributs
	 */
	
	private Stage primaryStage; //la fenêtre
	private DailyBankState dbs;
	private OperationVirementController ovc;//le controller de gestion des opérations
	private OperationEditorPaneController epc;
	private CompteCourant compteConcerne; //le compte du client

	
	/**
	 * @param _parentStage
	 * @param _dbstate
	 * représente la fenêtre d'enregistrement d'une opération de virement
	 */
	public OperationVirement(Stage _parentStage, DailyBankState _dbstate) {

		try {
			FXMLLoader loader = new FXMLLoader(
					OperationVirementController.class.getResource("operationVirement.fxml"));
			BorderPane root = loader.load();

			Scene scene = new Scene(root, 500 + 20, 250 + 10);
			
			this.primaryStage = new Stage();
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(_parentStage);
			StageManagement.manageCenteringStage(_parentStage, this.primaryStage);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Enregistrement d'une opération");
			this.primaryStage.setResizable(false);

			this.ovc = loader.getController();
			this.ovc.initContext(this.primaryStage, _dbstate);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return une opération lorsqu'on effectue un virement de compte à compte
	 */
	public Operation enregistrerVirement() {

		OperationVirement opv = new OperationVirement(this.primaryStage, this.dbs);
		Operation op = opv.doOperationEditorDialog(this.compteConcerne, CategorieOperation.VIREMENT);
		if (op != null) {
			try {
				AccessOperation ao = new AccessOperation();

				ao.insertVirement(this.compteConcerne.idNumCompte, op.montant, op.idTypeOp);

			} catch (DatabaseConnexionException e) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
				ed.doExceptionDialog();
				this.primaryStage.close();
				op = null;
			} catch (ApplicationException ae) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
				ed.doExceptionDialog();
				op = null;
			}
		}
		return op;
	}
	
	public Operation doOperationEditorDialog(CompteCourant cpte, CategorieOperation cm) {
		return this.epc.displayDialog(cpte, cm);
	}
}
