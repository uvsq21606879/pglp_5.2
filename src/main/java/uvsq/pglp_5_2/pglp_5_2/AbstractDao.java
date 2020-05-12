package uvsq.pglp_5_2.pglp_5_2;

import java.sql.Connection;

public abstract class AbstractDao<T> {
	/**
	 * Pour la connection a la BDD.
	 */
	protected Connection connect;
	/**
	 * constructeur de la classe.
	 * @param connection a la BDD.
	 */
	public AbstractDao(final Connection connection) {
		connect = connection;
	}

	/**
	 * Mettre a jour un element du DAO.
	 * @param object l'element a modifier
	 * @return la modification
	 */
	public abstract T MiseAjour(T object);
	/**
	 * supprimer un element du DAO.
	 * @param l'element a supprimer
	 */
	public abstract void Suprimer(T object);

	/**
	 * ajoute un element au DAO.
	 * @param l'element a ajouter
	 * @return la creation
	 */
	public abstract T create(T object);
	/**
	 * obtenir un element par son identifiant.
	 * @param id identifiant de l'element e obtenir
	 * @return l'element souhaite
	 */
	public abstract T find(int id);


}
