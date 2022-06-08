package application.view;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import application.DailyBankState;
import application.tools.AlertUtilities;
import application.tools.ConstantesIHM;
import application.tools.EditionMode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.Client;
import model.data.CompteCourant;
import model.data.PrelevementAutomatique;
import model.orm.exception.DatabaseConnexionException;

/**
 * @author yann
 * controller relié à la classe PrelevementEditorPane pour la gestion d'un nouveau prélèvement
 */
public class PrelevementEditorPaneController implements Initializable{
	
	// Etat application
		private DailyBankState dbs;

		// Fenêtre physique
		private Stage primaryStage;

		// Données de la fenêtre
		private EditionMode em;
		private CompteCourant compte;
		private PrelevementAutomatique prelevementEdite;
		private PrelevementAutomatique prelevementResult;
		
	// Manipulation de la fenêtre
	public void initContext(Stage _primaryStage, DailyBankState _dbstate) {
		this.primaryStage = _primaryStage;
		this.dbs = _dbstate;
		this.configure();
	}
	
	private void configure() {
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
	}
	
	/*
	 * Configuration de la fenêtre d'édition d'un compte
	 * @param in client : le client
	 * @param in cpte : le client
	 * @param in mode : le mode de modification
	 * return le resultat
	 */
	public PrelevementAutomatique displayDialog(CompteCourant c, PrelevementAutomatique prelevement, EditionMode mode) {
		this.compte = c;
		this.em = mode;
		if (prelevement == null) {
			this.prelevementEdite = new PrelevementAutomatique(0, 0, 0 , "N", this.compte.idNumCompte);

		} else {
			this.prelevementEdite = new PrelevementAutomatique(prelevement);
		}
		this.prelevementResult = null;
		switch (mode) {
		case CREATION:
			this.txtIdPrelevement.setDisable(true);
			this.txtIdNumCompte.setDisable(true);
			this.btnOk.setText("Ajouter");
			this.btnCancel.setText("Annuler");
			break;
		case MODIFICATION:
			/*this.txtDecAutorise.setDisable(false);
			this.txtSolde.setDisable(true);
			this.lblMessage.setText("Modification du compte");
			this.btnOk.setText("Modifier");
			this.btnCancel.setText("Annuler");
			break;*/
		case SUPPRESSION:
			//break;
		}

		// initialisation du contenu des champs
		this.txtIdPrelevement.setText("" + this.prelevementEdite.idPrelev);
		this.txtIdNumCompte.setText("" + this.prelevementEdite.idNumCompte);
		this.txtMontant.setText("" + this.prelevementEdite.montant);
		this.txtDateRecurrente.setText("" + this.prelevementEdite.dateRecurrente);
		this.txtBeneficiaire.setText("" + this.prelevementEdite.beneficiaire);

		this.prelevementResult = null;

		this.primaryStage.showAndWait();
		return this.prelevementResult;
	}
	
	// Gestion du stage
		private Object closeWindow(WindowEvent e) {
			this.doCancel();
			e.consume();
			return null;
		}
	
	// Attributs de la scene + actions
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancel;
	@FXML
	private TextField txtIdPrelevement;
	@FXML
	private TextField txtMontant;
	@FXML
	private TextField txtDateRecurrente;
	@FXML
	private TextField txtBeneficiaire;
	@FXML
	private TextField txtIdNumCompte;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Permet de fermer une fenêtre au clique d'un bouton 
	 */
	@FXML
	private void doCancel() {
		this.prelevementResult = null;
		this.primaryStage.close();
	}
	
	/*
	 * Permet d'ajouter un prélèvement au clique d'un bouton
	 */
	@FXML
	private void doAjouter() throws DatabaseConnexionException, SQLException {
		switch (this.em) {
		case CREATION:
			
			if (this.isSaisieValide()) {
				this.prelevementResult = this.prelevementEdite;
				this.primaryStage.close();
			}


			break;
		
		/*case MODIFICATION:
			if (this.isSaisieValide()) {
				this.compteResult = this.compteEdite;
				this.primaryStage.close();

			}
			break;
		case SUPPRESSION:
			this.compteResult = this.compteEdite;
			this.primaryStage.close();

			break;*/
		}

	}
	
	/*
	 * Permet de vérifiez si les saisies sont valides : 
	 * renvoie une alerte si :
	 * - le montant n'est pas saisi
	 * - la date saisi est inférieur ou égal à 0 et supérieur à 28
	 * - le champ du bénéficaire ne doit pas être vide
	 */
	private boolean isSaisieValide() {
		this.prelevementEdite.beneficiaire = this.txtBeneficiaire.getText().trim();
		if (this.txtMontant.getText().equals("")) {
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le montant ne doit pas être vide",
					AlertType.WARNING);
			this.txtMontant.requestFocus();
			return false;
		}
		int date;
		date = Integer.parseInt(this.txtDateRecurrente.getText());
		if(date <= 0 || date > 28) {
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "La date doit être supérieur à 0 et "
					+ "inférieur à 28",
					AlertType.WARNING);
			this.txtDateRecurrente.requestFocus();
			return false;
		}
		
		if (this.prelevementEdite.beneficiaire.isEmpty()) {
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Le nom ne doit pas être vide",
					AlertType.WARNING);
			this.txtBeneficiaire.requestFocus();
			return false;
		}

		return true;
	}

}