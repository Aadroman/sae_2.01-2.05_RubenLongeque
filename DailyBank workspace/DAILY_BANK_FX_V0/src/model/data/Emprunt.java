package model.data;


public class Emprunt {
	public int capitalEmprunt;
	public int dureeEmprunt;
	public double tauxPretAnnuel;
	
	
	public Emprunt(int capital, int duree, double tauxPret) {
		if(capital > 100000)
			this.capitalEmprunt=capital;
		if(duree > 2)
			this.dureeEmprunt=duree;
		if(tauxPret>0)
			this.tauxPretAnnuel=tauxPret;
	}
	
	public int getCapitalEmprunt() {
		return capitalEmprunt;
	}
	
	public int getPeriodeEmprunt() {
		return dureeEmprunt;
	}
	
	public double getTauxPretAnnuel() {
		return tauxPretAnnuel;
	}
	
	public double getTauxApplicable() {
		double res = this.tauxPretAnnuel/100/12;
		return res;
	}
	
	public int getNbPeriode() {
		int res = this.dureeEmprunt*12;
		return res;
	}
	
	public double getMensualite() {
		double res = this.capitalEmprunt*(this.getTauxApplicable()/ Math.pow((1-(1+this.getTauxApplicable())),this.getNbPeriode()) );
		return res;
	}
}
