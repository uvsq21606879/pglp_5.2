package uvsq.pglp_5_2.pglp_5_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * classe pour la creation de ma base de donnees.
 */
public abstract class MaBdd {
	/**
	 * creation de ma base de donnees.
	 * @throws Exception en cas d'erreur de creation.
	 */
	public static void resetDataBase() throws Exception {
		Connection connection = DriverManager.getConnection(
				"jdbc:derby:MaBdd; create=false");
		//Supression au prealable des tables si elles existent.
		MaBdd.SupressionDesTables(connection);
		//Creation de la table Personnel
		MaBdd.CreateTablePersonnel(connection);
		//Creation de la table contenant les Numeros.
		MaBdd.CreateTableNumsTel(connection);
		//Creation de la table pour le composite personnel
		CreateTableGroupePersonnel(connection);
		//Creation de la table pour la relation composite composant
		CreateTableCopisiteCopmposant(connection);
		//Creation de la table pour la relation composite personnel
		CreateTableCompositePersonnel(connection);
	}
	/**
	 * creation de ma base de donnees.
	 * @throws SQLException.
	 */
	public static void CreateDataBase() throws SQLException {
		DriverManager.getConnection(
				"jdbc:derby:MaBdd;create=true");
	}


	/**
	 * cration de la table personnel.
	 * @param La connexion a la BDD
	 * @throws SQLException.
	 */
	private static void CreateTablePersonnel(final Connection connection)
			throws SQLException {
		String table = "create table Personnel ("
				+ "Id int primary key,"
				+ "nom varchar(20),"
				+ "prenom varchar(20),"
				+ "dateNaissance date)";
		Statement stat = connection.createStatement();
		stat.execute(table);
	}

	
	/**
	 * creation la table NumsTel contenant les numeros de tel.
	 * @param connexion a la BDD.
	 * @throws SQLException.
	 */
	private static void CreateTableNumsTel(final Connection connect)
			throws SQLException {
		String table = "create table NumsTel ("
				+ "idPersonnel int,"
				+ "numero varchar(20) primary key,"
				+ "foreign key (idPersonnel) references personnel (id))";
		Statement stat = connect.createStatement();
		stat.execute(table);
	}
	/**
	 * creation la table du Composite GroupePersonnel.
	 * @param connexion a la BDD
	 * @throws SQLException si on a un erreur de creation.
	 */
	private static void CreateTableGroupePersonnel(final Connection connect)
			throws SQLException {
		String table = "create table GroupePersonnel (Id int primary key)";
		Statement stat = connect.createStatement();
		stat.execute(table);
	}
	/**
	 * cration la table de CompositeComposant relation entre le composite et le composant.
	 * @param connexion a la BDD.
	 * @throws SQLException si on a un erreur de creation.
	 */
	private static void CreateTableCopisiteCopmposant(final Connection connect)
			throws SQLException {
		String table = "create table ComposantComposite ("
				+ "Id_Composite int,"
				+ "Id_Composant int,"
				+ "primary key (Id_Composite,Id_Composant),"
				+ "foreign key (Id_Composite) references GroupePersonnel (Id),"
				+ "foreign key (Id_Composant) references GroupePersonnel (Id))";
		Statement stat = connect.createStatement();
		stat.execute(table);
	}
	/**
	 * Creation de la table de composition entre le Composite et le Personnel
	 * @param connexion a la BDD
	 * @throws SQLException si on a un erreur de creation.
	 */
	private static void CreateTableCompositePersonnel(final Connection connect)
			throws SQLException {
		String table = "create table CompositePersonnel ("
				+ "Id_Composite int,"
				+ "Id_Personnel int,"
				+ "primary key (Id_Composite,Id_Personnel),"
				+ "foreign key (Id_Composite) references GroupePersonnel (Id),"
				+ "foreign key (Id_Personnel) references Personnel (Id))";
		Statement stat = connect.createStatement();
		stat.execute(table);
	}
	
	/**
	 * suppression des tables l'ordre de supression est important car on a des cles etrangeres.
	 * @param connection de la BDD
	 */
	private static void SupressionDesTables(final Connection connect) {
		Statement statement = null;
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			statement.execute("drop table NumsTel");
		} catch (SQLException e) {
		}
		try {
			statement.execute("drop table ComposantComposite");
		} catch (SQLException e) {
		}
		try {
			statement.execute("drop table GroupePersonnell");
		} catch (SQLException e) {
		}
		try {
			statement.execute("drop table CompositePersonnel");
		} catch (SQLException e) {
		}
		try {
			statement.execute("drop table Personnel");
		} catch (SQLException e) {
		}
	}

}
