package uvsq.pglp_5_2.pglp_5_2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class DaoComposite implements DAO<GroupePersonnel>, Serializable {

	/**
	 * Le numero de serialization genere aleatoirement.
	 */
	private static final long serialVersionUID = -2495845398722940995L;

	/**
	 * liste de CompositePersonnels du DAO.
	 */

	private ArrayList<GroupePersonnel> List;

	/**
	 * Constructeur de cette classe.
	 */

	public DaoComposite() {
		List = new ArrayList<GroupePersonnel>();
	}
	
	/**
	 * retourne un CompositePersonnels du DAO.
	 * @param element a retourner
	 * @return retourne le composite personnel sinon null
	 * 
	 */

	public GroupePersonnel get(int id) {

		for (GroupePersonnel Gp : List) {
			if (Gp.getIdPersonnel() == id) {
				return Gp;}
				}

		return null;
	}

	/**
	 * ajout d'un CompositePersonnels à la liste du DAO.
	 * @param element a ajouter
	 */

	public void ajouter(GroupePersonnel object) {
		// TODO Auto-generated method stub
		List.add(object);

	}
	
    /**
     * retourner la liste de CompositePersonnels du DAO.
     * @return la liste de CompositePersonnels du DAO.
     */

	@SuppressWarnings("unchecked")
	public ArrayList<GroupePersonnel> getList() {

		return (ArrayList<GroupePersonnel>) List.clone();
	}
	
	 /**
     * mise a jour  d'un CompositePersonnels.
     * @param l'element a modifier.
     * @param les parametres a modifier.
     */

	public void miseAjour(GroupePersonnel object, Map<String, Object> params) {

		if (List.contains(object)) {
			if (params.containsKey("Personnels")) {
				@SuppressWarnings("unchecked")
				ArrayList<InterfacePersonnel> replace = (ArrayList<InterfacePersonnel>) params.get("Personnels");
				object.reset();
				for (InterfacePersonnel ip : replace) {
					object.ajouter(ip);
				}
			}
		}

	}
	
	  /**
     * supprimer un CompositePersonnels du DAO.
     * @param element a supprimer
     */

	public void supprimer(GroupePersonnel object) {

		List.remove(object);

	}
	
	 /**
     * serialization vers un fichier.
     * @param nom ou chemin du fichier vers lequel serializer.
     */

	public void serialize(final String path) {
		ObjectOutputStream writer = null;
		try {
			FileOutputStream F = new FileOutputStream(path);
			writer = new ObjectOutputStream(F);
			writer.writeObject(this);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("La serialization a échoué vers le fichier ");
		}
		try {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		} catch (IOException E) {
			E.printStackTrace();
		}
	}
	
	 /**
     * deserialization vers un fichier.
     * @param nom ou chemin du fichier vers lequel deserializer.
     * @return instance des classes creees a partir de le deserialization
     */

	
	public static DaoComposite deserialize(final String path) {
		ObjectInputStream reader = null;
		DaoComposite daoCP = null;
		try {
			FileInputStream file = new FileInputStream(path);
			reader = new ObjectInputStream(file);
			daoCP = (DaoComposite) reader.readObject();
		} catch (IOException e) {
			System.err.println("La deserialization a échoué depuis le fichier ");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return daoCP;
	}

}
