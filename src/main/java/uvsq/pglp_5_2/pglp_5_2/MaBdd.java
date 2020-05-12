package uvsq.pglp_5_2.pglp_5_2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
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
		
		//Creation de la table Personnel
		MaBdd.CreateTablePersonnel(connection);
		//Creation de la table contenant les Numeros.
		MaBdd.CreateTableNumsTel(connection);
		//Creation de la table pour le composite personnel
		CreateTableGroupePersonnel(connection);
		
	}
	/**
	 * creation de ma base de donnees.
	 * @throws SQLException.
	 */
	public static void CreateDataBase() throws SQLException {
		DriverManager.getConnection(
				"jdbc:derby:compositePattern;create=true");
	}


	/**
	 * cration de la table personnel.
	 * @param La connexion a la BDD
	 * @throws SQLException.
	 */
	private static void CreateTablePersonnel(final Connection connection) throws SQLException {
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
	private static void CreateTableNumsTel(final Connection connect) throws SQLException {
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
	private static void CreateTableGroupePersonnel(final Connection connect) throws SQLException {
		String table = "create table GroupePersonnel (Id int primary key)";
		Statement stat = connect.createStatement();
		stat.execute(table);
	}
}
	