package model.data;


public class Emprunt {
	public int capitalEmprunt;
	public int periodeEmprunt;
	public double tauxPretAnnuel;
	
	
	public Emprunt(int capital, int duree, double tauxPret) {
		if(capital > 100000)
			this.capitalEmprunt=capital;
		if(duree > 2)
			this.periodeEmprunt=duree;
		if(tauxPret>0)
			this.tauxPretAnnuel=tauxPret;
	}
	
	public double getTauxApplicable(double tauxAnnuel) {
		double res = tauxAnnuel/100/12;
		return res;
	}
}
