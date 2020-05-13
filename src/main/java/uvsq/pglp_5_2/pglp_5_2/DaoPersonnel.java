package uvsq.pglp_5_2.pglp_5_2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

/**
 * Le DAO pour la classe Personnel.
 */
public class DaoPersonnel implements DAO<Personnel>, Serializable {
	
	/**
	 * Le numero de serialization genere aleatoirement.
	 */
	private static final long serialVersionUID = 3843900432749650407L;
	
	 /**
     * Liste contenant les Personnels du DAO.
     */
	private ArrayList<Personnel> liste;
	
	 /**
     * Constructeur de cette classe .
     */
	public DaoPersonnel() {
		liste = new ArrayList<Personnel>();
	}
	
	 /**
     * ajoute d'un Personnel dans la liste DAO.
     * @param element a ajouter.
     */
	public void ajouter(Personnel personnel) {
		// TODO Auto-generated method stub
		liste.add(personnel);
	}
	
	 /**
     * retourner un personnel du DAO.
     * @param id identifiant de la personne a retourner
     * @return retourne le personnel sinn null si il n'existe pas 
     */
	public Personnel get(int IdPersonnel) {

		for (Personnel P : liste) 
			if (P.getIdPersonnel() == IdPersonnel) 
				return P;
		return null;
	}
	
	 /**
     * suppression d'un personnel du DAO.
     * @param Personnel a supprimer
     */

public void supprimer(Personnel personnel) {
	// TODO Auto-generated method stub
	liste.remove(personnel);

}
	
	  /**
     * Obtenir la liste des personnels du DAO.
     * @return la liste des personnels du DAO
     */
	@SuppressWarnings("unchecked")
	public ArrayList<Personnel> getList() {
		return (ArrayList<Personnel>) liste.clone();
	}
	
	  /**
     * mise a jour d'un personnel
     * @param Le personnel a mettre a jour
     * @param params les parametres a modifier
     */
	
	@SuppressWarnings({ "unchecked" })
	public void miseAjour(Personnel personnel, Map<String, Object> params) {
		// TODO Auto-generated method stub
		String Nom = "";
		String Prenom = "";
		LocalDate dateNaissance;
		ArrayList<String> NumsTelephone;
		if (liste.remove(personnel)) {

			if (params.containsKey("Nom")) {
				Nom = (String) params.get("Nom");
			} else {
				Nom = personnel.getNom();
			}

			if (params.containsKey("Prenom")) {
				Prenom = (String) params.get("Prenom");
			} else {
				Prenom = personnel.getPrenom();
			}

			if (params.containsKey("dateNaissance")) {
				dateNaissance = (LocalDate) params.get("dateNaissance");
			} else {
				dateNaissance = personnel.getDateNaissance();
			}

			if (params.containsKey("NumsTelephone")) {
				ArrayList<String> tmp;
				tmp = (ArrayList<String>) params.get("NumsTelephone");
				NumsTelephone = (ArrayList<String>) tmp.clone();
			} else {
				NumsTelephone = personnel.getNumsTelephone();
			}
			Personnel p = new Personnel.Builder(Nom, Prenom, dateNaissance, NumsTelephone).build();
			liste.add(p);
		}

	}
	
	 /**
     * serialization vers un fichier.
     * @param nom ou chemin du fichier vers lequel serializer.
     */

	public void serialize(final String path) {
		ObjectOutputStream writer = null;
		try {
			FileOutputStream file = new FileOutputStream(path);
			writer = new ObjectOutputStream(file);
			writer.writeObject(this);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("La serialization a echoue vers le fichier ");
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
	
	 public static DaoPersonnel deserialize(final String path) {
	        ObjectInputStream reader = null;
	        DaoPersonnel daoPersonnel = null;
	        try {
	            FileInputStream file = new FileInputStream(path);
	            reader = new ObjectInputStream(file);
	            daoPersonnel = (DaoPersonnel) reader.readObject();
	        } catch (IOException e) {
	            System.err.println("La deserialization a échoué depuis le fichier");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        try {
	            if (reader != null) {
	                reader.close();
	            }
	        } catch (IOException E) {
	            E.printStackTrace();
	        }
	        return daoPersonnel;
}
	

}

