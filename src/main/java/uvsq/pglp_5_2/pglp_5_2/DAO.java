package uvsq.pglp_5_2.pglp_5_2;

import java.util.ArrayList;
import java.util.Map;

public interface DAO<O> {


	/**
	 * get un element du DAO
	 * @param Identifiant de l'element
	 * @return Id l'element souhaite
	 */

	O get(int id);

	/**
	 * ajoute d'un element au DAO.
	 * @param element a ajouter
	 */


	void ajouter(O object);

	/**
	 * retourner le liste du DAO.
	 * @return liste des elm du DAO
	 */

	ArrayList<O> getList();

	/**
	 * modifier un elm du DAO.
	 * @param l'element a modifier
	 * @param params les params a modifier
	 */

	void miseAjour(O object, Map<String, Object> params);

	/**
	 * supprimer un element du DAO.
	 * @param element a supprimer
	 */


	void supprimer(O object);

}
