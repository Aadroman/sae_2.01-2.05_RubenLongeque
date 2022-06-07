package model.data;


/**
 * @author yann
 * classe d'un prélèvement automatique
 */
public class PrelevementAutomatique {
	
	/**Attributs**/
	public int idPrelev;
	public double montant;
	public int dateRecurrente;
	public String beneficiaire;
	public int idNumCompte;
	
	/**constructeurs**/
	public PrelevementAutomatique(int pfIdPrelev, double pfMontant, int pfDateRecurrente, String pfBeneficiaire,
			int pfIdNumCompte) {
		super();
		this.idPrelev = pfIdPrelev;
		this.montant = pfMontant;
		this.dateRecurrente = pfDateRecurrente;
		this.beneficiaire = pfBeneficiaire;
		this.idNumCompte = pfIdNumCompte;
	}
	
	public PrelevementAutomatique(PrelevementAutomatique pa) {
		this(pa.idPrelev, pa.montant, pa.dateRecurrente, pa.beneficiaire, pa.idNumCompte);
	}
	
	public PrelevementAutomatique() {
		this(0,0,0,"N",0);
	}
	
	@Override
	public String toString() {
		
		return "Prélèvement automatique [idPrélèvement=" + this.idPrelev + ", montant=" + this.montant
				+ ", dateRecurrente=" + this.dateRecurrente
				+ ", bénéficiaire=" + this.beneficiaire + ", idNumCompte=" + this.idNumCompte + "]";
	}
}
