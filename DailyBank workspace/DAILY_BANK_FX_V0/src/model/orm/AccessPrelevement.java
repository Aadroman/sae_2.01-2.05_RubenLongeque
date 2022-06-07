package model.orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.data.CompteCourant;
import model.data.PrelevementAutomatique;
import model.orm.exception.DataAccessException;
import model.orm.exception.DatabaseConnexionException;
import model.orm.exception.Order;
import model.orm.exception.Table;

public class AccessPrelevement {
	
	public AccessPrelevement() {
	}
	
	
	/**
	 * Recherche des prélèvements d'un compte à partir de son id.
	 *
	 * @param idNumCompte id du compte dont on cherche les prélèvements
	 * @return Tous les PrelevementAutomatique de idNumCOmpte (ou liste vide)
	 * @throws DataAccessException
	 * @throws DatabaseConnexionException
	 */
	public ArrayList<PrelevementAutomatique> getPrelevement(int idNumCompte)
			throws DataAccessException, DatabaseConnexionException {
		ArrayList<PrelevementAutomatique> alResult = new ArrayList<>();

		try {
			Connection con = LogToDatabase.getConnexion();
			String query = "SELECT * FROM PrelevementAutomatique where idNumCompte = ?";
			query += " ORDER BY idNumCompte";

			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, idNumCompte);
			System.err.println(query);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				int idPrelev = rs.getInt("idPrelevement");
				double montant = rs.getDouble("montant");
				int dateReccurrente = rs.getInt("dateReccurrente");
				String beneficiaire = rs.getString("beneficiaire");
				int idNumCompteTROUVEE = rs.getInt("idNumCompte");

				alResult.add(new PrelevementAutomatique(idPrelev, montant, dateReccurrente, beneficiaire, idNumCompteTROUVEE));
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			throw new DataAccessException(Table.PrelevementAutomatique, Order.SELECT, "Erreur accès", e);
		}

		return alResult;
	}

}
