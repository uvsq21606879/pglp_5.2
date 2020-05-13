package uvsq.pglp_5_2.pglp_5_2;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import uvsq.pglp_5_2.pglp_5_2.AbstractDaoConstruction.TypeImpDao;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DaoCompositeJDBC
extends AbstractDao<GroupePersonnel> {


	public DaoCompositeJDBC(Connection connection) {
		super(connection);
	}
	/**
	 * creation de la relation de composition entre 2 GroupePersonnel.
	 * @param le composite
	 * @param le composant
	 * @throws SQLException en cas d'erreur de creation
	 */
	private void createComposantComposite(int IdComposite, int IdComposant) {
		PreparedStatement prepare;
		try {
			prepare = connect.prepareStatement(
					"INSERT INTO ComposantComposite (Id_Composite, Id_Composant) VALUES(?, ?)");
			prepare.setInt(1, IdComposite);
			prepare.setInt(2, IdComposant);
			prepare.executeUpdate();
		} catch (SQLException E) {
		}
	}

	/**
	 * La relation de composition entre un GroupePersonnel et un Personnel.
	 * @param le compose
	 * @param le composant
	 * @throws SQLException en cas d'erreur de creation
	 */
	public void createComposantPersonnel(int Id_GPersonnel, int Id_Personnel) {
		try {
			PreparedStatement prepare = connect.prepareStatement(
					"INSERT INTO CompositePersonnel (Id_Composite,Id_Personnel) VALUES(?, ?)");
			prepare.setInt(1, Id_GPersonnel);
			prepare.setInt(2, Id_Personnel);
			prepare.executeUpdate();
		} catch (SQLException e) {
		}
	}
	/**
	 * chercher les composants du composite de type GroupePersonnel.
	 * @param le composite.
	 * @return liste de ses composants.
	 * @throws SQLException en cas d'erreur de creation.
	 */
	private ArrayList<InterfacePersonnel> ChearchComposantComposite(int Id_Composite) throws SQLException {
		ArrayList<InterfacePersonnel> liste = new ArrayList<InterfacePersonnel>();
		PreparedStatement prepare = connect.prepareStatement(
				"SELECT Id_Composant FROM ComposantComposite"
						+ " WHERE Id_Composite = ?");
		prepare.setInt(1, Id_Composite);
		ResultSet result = prepare.executeQuery();
		while (result.next()) {
			GroupePersonnel res = this.find(result.getInt("Id_Composant"));
			if (res != null) {
				liste.add(res);
			}
		}
		return liste;
	}
	/**
	 * chercher les composants du composite de type Personnel.
	 * @param idComposite le composite en question
	 * @return une liste de ses composants
	 * @throws SQLException échec de la recherche
	 */
	@SuppressWarnings("static-access")
	private ArrayList<InterfacePersonnel> CherchComposantPersonnel(
			int Id_Composite) throws SQLException {

		ArrayList<InterfacePersonnel> liste = new ArrayList<InterfacePersonnel>();

		DaoConstructionJDBC ConsJDBC = (DaoConstructionJDBC) AbstractDaoConstruction.getConstruction(TypeImpDao.JDBC);

		DaoPersonnelJDBC daoPersonnel = (DaoPersonnelJDBC) ConsJDBC.getDaoPersonnel();
		PreparedStatement prepare = connect.prepareStatement(
				"SELECT Id_Composite FROM CompositePersonnel WHERE Id_Composite = ?");
		prepare.setInt(1, Id_Composite);
		ResultSet result = prepare.executeQuery();
		while (result.next()) {
			Personnel res = daoPersonnel.find(
					result.getInt("Id_Personnel"));
			if (res != null) {
				liste.add(res);
			}
		}
		return liste;
	}
	/**
	 * chercher les composants du composite.
	 * @param idComposite le composite en question
	 * @return une liste de ses composants
	 * @throws SQLException échec de la recherche
	 */
	private ArrayList<InterfacePersonnel> findComposant(
			final int Id_Composite) throws SQLException {
		ArrayList<InterfacePersonnel> ListeC = ChearchComposantComposite(Id_Composite);
		ArrayList<InterfacePersonnel> ListeP = CherchComposantPersonnel(Id_Composite);
		for (InterfacePersonnel ip : ListeP) 
			ListeC.add(ip);

		return ListeC;
	}
	/**
	 * supprimer la relation de composition d'un GroupePersonnel.
	 * @param idComposite le composite composé d'éléments
	 * @throws SQLException échec de suppression
	 */
	private void deleteComposantComposite(final int Id_Composite)
			throws SQLException {
		PreparedStatement prepare = connect.prepareStatement(
				"DELETE FROM ComposantComposite WHERE Id_Composite = ?");
		prepare.setInt(1, Id_Composite);
		prepare.executeUpdate();
	}
	/**
	 * supprimer la relation composition d'un Personnel.
	 * @param le composite 
	 * @throws SQLException en cas d'erreur de supression
	 */
	private void deleteComposantPersonnel(int Id_Composite)
			throws SQLException {
		PreparedStatement prepare = connect.prepareStatement(
				"DELETE FROM CompositePersonnel WHERE Id_Composite = ?");
		prepare.setInt(1, Id_Composite);
		prepare.executeUpdate();
	}
	/**
	 * supprimer la relation de composition.
	 * @param le composite
	 * @throws SQLException en cas d'erreur de supression
	 */
	private void deleteComposant(int Id_Composite)
			throws SQLException {
		deleteComposantPersonnel(Id_Composite);
		deleteComposantComposite(Id_Composite);
	}
	/**
	 * creation d'un element dans la Base de donnees.
	 * @param element a creer
	 */
	@SuppressWarnings("static-access")
	@Override
	public GroupePersonnel create(final GroupePersonnel object) {
		try {
			PreparedStatement prepare = connect.prepareStatement(
					"INSERT INTO GroupePersonnel (id) VALUES(?)");
			prepare.setInt(1, object.getIdPersonnel());
			prepare.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (InterfacePersonnel Ip : object.getList()) {
			if (Ip.getClass() == GroupePersonnel.class) {
				this.create((GroupePersonnel) Ip);
				createComposantComposite(object.getIdPersonnel(), Ip.getIdComposite());
			} else {
				DaoConstructionJDBC ConsJDBC = (DaoConstructionJDBC) AbstractDaoConstruction.getConstruction(TypeImpDao.JDBC);
				DaoPersonnelJDBC Pjdbc = (DaoPersonnelJDBC) ConsJDBC.getDaoPersonnel();
				Pjdbc.create((Personnel) Ip);
				createComposantPersonnel(object.getIdPersonnel(), Ip.getIdComposite());
			}
		}
		return object;
	}
	/**
	 * chercher un element dans la base de donnees.
	 * @param identifiant a trouver
	 */
	@Override
	public GroupePersonnel find(int Id) {
		GroupePersonnel Gp = null;
		try {
			PreparedStatement prepare = connect.prepareStatement(
					"SELECT * FROM GroupePersonnel WHERE Id = ?");
			prepare.setInt(1, Id);
			ResultSet result = prepare.executeQuery();
			if (result.next()) {
				Gp = new GroupePersonnel();
				Gp.setId(Id);
				ArrayList<InterfacePersonnel> liste = this.findComposant(Id);
				for (InterfacePersonnel Ip : liste)
					Gp.ajouter(Ip);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return Gp;
	}
	/**
	 * modifier un CompositePersonnels.
	 * @param object données pour modifier
	 */
	@Override
	public GroupePersonnel MiseAjour(GroupePersonnel object) {
		GroupePersonnel before = this.find(object.getIdPersonnel());
		if (before != null) {
			this.Suprimer(before);
			this.create(object);
			if (this.find(object.getIdPersonnel()) == null) {
				this.create(before);
				return before;
			}
		} else {
			return null;
		}
		return object;
	}
	/**
	 * supprimer de la BDD.
	 * @param objet a supprimer
	 */
	@Override
	public void Suprimer(GroupePersonnel object) {
		try {
			this.deleteComposant(object.getIdPersonnel());
			PreparedStatement prepare = connect.prepareStatement("DELETE FROM GroupePersonnel WHERE Id = ?");
			prepare.setInt(1, object.getIdPersonnel());
			prepare.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}