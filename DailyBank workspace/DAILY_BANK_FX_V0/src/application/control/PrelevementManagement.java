package application.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.EditionMode;
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
import model.data.PrelevementAutomatique;
import model.orm.AccessCompteCourant;
import model.orm.AccessPrelevement;
import model.orm.LogToDatabase;
import model.orm.exception.ApplicationException;
import model.orm.exception.DatabaseConnexionException;
import model.orm.exception.Order;
import model.orm.exception.Table;

/**
 * @author yann
 * classe dédié à la gestion de la fenêtre "gestion des prélèvements"
 */
public class PrelevementManagement {
	
	/**
	 * Attributs
	 */
	
	private Stage primaryStage; //la fenêtre principale
	private PrelevementManagementController pmc; //le controller relié au prélèvement
	private DailyBankState dbs;
	private CompteCourant compte; //un compte courant
	
	/**
	 * @param _parentStage
	 * @param _dbstate
	 * @param client
	 * @param compte
	 * représente la fenêtre "gestion des prélèvements" après avoir cliquer sur le bouton
	 * "prélèvement automatique" des opérations d'un compte d'un client
	 */
	public PrelevementManagement(Stage _parentStage, DailyBankState _dbstate, Client client, CompteCourant Compte) {

		this.compte = Compte;
		this.dbs = _dbstate;
		try {
			FXMLLoader loader = new FXMLLoader(PrelevementManagementController.class.getResource("prelevementmanagement.fxml"));
			BorderPane root = loader.load();

			Scene scene = new Scene(root, root.getPrefWidth()+50, root.getPrefHeight()+10);
			scene.getStylesheets().add(DailyBankApp.class.getResource("application.css").toExternalForm());

			this.primaryStage = new Stage();
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(_parentStage);
			StageManagement.manageCenteringStage(_parentStage, this.primaryStage);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Gestion des prélèvements");
			this.primaryStage.setResizable(false);

			this.pmc = loader.getController();
			this.pmc.initContext(this.primaryStage, this, _dbstate, client);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doPrelevementManagementDialog() {
		this.pmc.displayDialog();
	}
	
	/**
	 * @return le prélèvement automatique que l'on créer
	 * @throws SQLException 
	 */
	public PrelevementAutomatique creerPrelevement() throws SQLException {
		PrelevementAutomatique prelevement;
		PrelevementEditorPane pep = new PrelevementEditorPane(this.primaryStage, this.dbs);
		prelevement = pep.doPrelevementEditorDialog(this.compte, null, EditionMode.CREATION);
		if (prelevement != null) {
			try {
				
				Connection con = LogToDatabase.getConnexion(); //Connexion à la base de données
                
                String query = "INSERT INTO PRELEVEMENTAUTOMATIQUE VALUES (" + "seq_id_client.NEXTVAL" + ", " + "?" + ", " + "?" + ", " + "?" + ", " + "?" +")";
                
                PreparedStatement pst = con.prepareStatement(query);
                pst.setDouble(1, prelevement.montant);
                pst.setInt(2, prelevement.dateRecurrente);
                pst.setString(3, prelevement.beneficiaire);
                pst.setInt(4, prelevement.idNumCompte);
                
                int result = pst.executeUpdate();
                pst.close();
				
				
                
                if (result != 1) {
                    con.rollback();
                    System.out.println("erreur lors du insert");
                }else {
                    con.commit();
                }
                
				// existe pour compiler les catchs dessous
				if (Math.random() < -1) {
					throw new ApplicationException(Table.PrelevementAutomatique, Order.INSERT, "todo : test exceptions", null);
				}
			} catch (DatabaseConnexionException e) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
				ed.doExceptionDialog();
				this.primaryStage.close();
			} catch (ApplicationException ae) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
				ed.doExceptionDialog();
			}
		}
		return prelevement;
	}
	
	/**
	 * Permet de modifier le prélèvement d'un compte
	 * @param prelevement : prélèvement du compte séléctionné
	 * @return prélèvement modifié
	 */
	public PrelevementAutomatique modifierPrelevement(PrelevementAutomatique p) {
		PrelevementEditorPane pep = new PrelevementEditorPane(this.primaryStage, this.dbs);
		PrelevementAutomatique result = pep.doPrelevementEditorDialog(this.compte, p, EditionMode.MODIFICATION);
		if(result != null) {
			try {
				AccessPrelevement acpv = new AccessPrelevement();
				acpv.updatePrelevement(result);
				
			}catch (DatabaseConnexionException e) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
				ed.doExceptionDialog();
				this.primaryStage.close();
				result = null;
			} catch (ApplicationException ae) {
				ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
				ed.doExceptionDialog();
				result = null;
			}
		}
		
		return result;
	}
	
	/**
	 * @return une liste de prélèvement en fonction du compte sélectionné
	 */
	public ArrayList<PrelevementAutomatique> getPrelevement() {
		ArrayList<PrelevementAutomatique> listeP = new ArrayList<>();

		try {
			AccessPrelevement ap = new AccessPrelevement();
			listeP = ap.getPrelevements(this.compte.idNumCompte);
		} catch (DatabaseConnexionException e) {
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
			ed.doExceptionDialog();
			this.primaryStage.close();
			listeP = new ArrayList<>();
		} catch (ApplicationException ae) {
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
			ed.doExceptionDialog();
			listeP = new ArrayList<>();
		}
		return listeP;
	}

}
