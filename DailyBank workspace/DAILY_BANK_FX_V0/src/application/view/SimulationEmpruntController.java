package application.view;

import java.net.URL;
import java.util.ResourceBundle;

import application.DailyBankState;
import application.control.SimulationEmpruntPane;
import application.tools.AlertUtilities;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.Emprunt;

public class SimulationEmpruntController implements Initializable{
	
	// Etat application
		private Emprunt emprunt;
	
	// Fenêtre physique
	private Stage primaryStage;
	
	// Données de la fenêtre
	
	
	// Manipulation de la fenêtre
	public void initContext(Stage _primaryStage) {
		this.primaryStage = _primaryStage;
		this.configure();
	}
		
	private void configure() {
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
	
		this.validateComponent();
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
	@FXML
	private Button simuler;
	
	
	
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
	
	
	private void setTauxApplicable(TextField txt, Emprunt em) {
		String res = String.valueOf(em.getTauxApplicable());
		txt.setText(res);
	}
	
	private void setPeriode(TextField txt, Emprunt em) {
		String res = String.valueOf(em.getNbPeriode());
		txt.setText(res);
	}
	
	private void setMensualiteSansAssurance(TextField txt, Emprunt em) {
		String res = String.valueOf(em.getMensualiteSansAss());
		txt.setText(res);
	}
	
	private void setMensualiteAvecAssurance(TextField txt, Emprunt em) {
		String res = String.valueOf(em.getMensualiteAvecAss());
		txt.setText(res);
	}
	
	private void setMensualiteAssurance(TextField txt, Emprunt em) {
		String res = String.valueOf(em.getMensualiteAssurance());
		txt.setText(res);
	}
	
	private void setCoutCredit(TextField txt, Emprunt em) {
		String res = String.valueOf(em.coutCredit());
		txt.setText(res);
	}
	
	private void setCoutAssurance(TextField txt, Emprunt em) {
		String res = String.valueOf(em.coutAssurance());
		txt.setText(res);
	}
	
	private void setTotal(TextField txt, Emprunt em) {
		String res = String.valueOf(em.coutTotal(toDouble(this.fraisDossier.getText().trim())));
		txt.setText(res);
	}
	
	@FXML
	private void displayEmprunt() {
		if (this.isSaisieValide()) {
			setTauxApplicable(tauxApplicable,emprunt);
			setPeriode(nbPeriodes,emprunt);
			setMensualiteSansAssurance(mensualiteSansA,emprunt);
			setMensualiteAvecAssurance(mensualiteAvecA,emprunt);
			setMensualiteAssurance(mensualiteAssurance,emprunt);
			setCoutAssurance(coutAssurance,emprunt);
			setCoutCredit(coutCredit,emprunt);
			setTotal(total,emprunt);
		}
		//essayer de mettre issaisievalide dans configure avec actions sur txtfield
	}
	
	
	/**
	 * @param number
	 * @return
	 */
	private double toDouble(String number) {
		try {
			return Double.parseDouble(number);
		} catch (Exception e) {
			return -1;
		}
	}
	
	/** Vérifie si les entrées sont valides
     * @return Vrai si les entrées sont valides, Faux sinon
     */
    private boolean isSaisieValide() {
    	//initialisation de la variable Emprunt depuis les textfield
    	emprunt = new Emprunt( toDouble(this.capital.getText().trim()), (int) toDouble(this.duree.getText().trim()), toDouble(this.tauxAnnuel.getText().trim()), toDouble(this.tauxAssurance.getText().trim()));
        
        //reinitialisation du css pour afficher le champ avec erreur
        this.capital.getStyleClass().remove("borderred");
        this.duree.getStyleClass().remove("borderred");
        this.tauxAnnuel.getStyleClass().remove("borderred");
        this.tauxAssurance.getStyleClass().remove("borderred");
        this.fraisDossier.getStyleClass().remove("borderred");
        
        //verification de la valitdité des champs
		try {
			if (emprunt.capitalEmprunt < 10000) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nbF) {
			this.capital.getStyleClass().add("borderred");
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Capital inférieur à 10 000 euros",AlertType.WARNING);
			this.capital.requestFocus();
			return false;
		}

		try {
			if (emprunt.dureeEmprunt < 2) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nbF) {
			this.duree.getStyleClass().add("borderred");
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Durée ifnrérieur à 2 ans",AlertType.WARNING);
			this.duree.requestFocus();
			return false;
		}
        
        
		try {
			if (emprunt.tauxPretAnnuel < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nbF) {
			this.tauxAnnuel.getStyleClass().add("borderred");
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Taux de prêt annuel négatif",AlertType.WARNING);
			this.tauxAnnuel.requestFocus();
			return false;
		}
		
		try {
			if (emprunt.tauxAssurance < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nbF) {
			this.tauxAssurance.getStyleClass().add("borderred");
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Taux de l'assurance négatif",AlertType.WARNING);
			this.tauxAssurance.requestFocus();
			return false;
		}
        
        
        try {
			if (toDouble(this.fraisDossier.getText().trim()) < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nbF) {
			this.fraisDossier.getStyleClass().add("borderred");
			AlertUtilities.showAlert(this.primaryStage, "Erreur de saisie", null, "Frais de dossier négatif",AlertType.WARNING);
			this.fraisDossier.requestFocus();
			return false;
		}

        return true;
    } 
	
	private void validateComponent	() {
		this.tauxApplicable.setEditable(false);
		this.nbPeriodes.setEditable(false);
		this.coutCredit.setEditable(false);
		this.mensualiteAssurance.setEditable(false);
		this.mensualiteSansA.setEditable(false);
		this.mensualiteAvecA.setEditable(false);
		this.coutAssurance.setEditable(false);
		this.total.setEditable(false);
	}

}
