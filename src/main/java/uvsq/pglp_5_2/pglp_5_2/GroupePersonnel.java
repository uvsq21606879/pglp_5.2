package uvsq.pglp_5_2.pglp_5_2;

import java.util.ArrayList;

import java.util.Iterator;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Classe du GroupePersonnel.
 * @author Said
 *
 */

public class GroupePersonnel implements Iterable<InterfacePersonnel>, Serializable, InterfacePersonnel {

	/**
	 * Le numero de serialization genere aleatoirement.
	 */
	private static final long serialVersionUID = -2268954669830584120L;

	/**
	 * pour identifier un composite.
	 */
	private int idPersonnel;
	
	/**
	 * pour generer un identifiant unique lors de chaque construction.
	 */
	private int idGenerator;
	
	/**
	 * liste des personnes.
	 */
	private ArrayList<InterfacePersonnel> Personnels;
	
	 /**
     * constructeur de cette classe.
     */

	public GroupePersonnel() {
		idPersonnel = idGenerator++;
		Personnels = new ArrayList<InterfacePersonnel>();
	}
	
	/**
     * retourner un CompositePersonnels.
     * @return Un string qui represente le groupe personnel.
     */
	@Override
	public String toString() {
		String S = "IdPersonnel = " + idPersonnel + "\n";
		for (InterfacePersonnel I : Personnels) {
			S += I.toString();
		}
		return S;
	}
	
	/**
     * ajout d'un personnel a la liste si il n'est pas deja dedans.
     * @param le personnel a ajouter
     */
	public GroupePersonnel ajouter(final InterfacePersonnel ip) {
		if (!Personnels.contains(ip)) {

			Personnels.add(ip);
		}
		return this;
	}
	
	/**
     * supprimee un personnel de la liste.
     * @param le personnel à retirer de la liste
     */
	public GroupePersonnel supprimer(InterfacePersonnel ip) {
		System.out.println(Personnels.remove(ip));
		return this;
	}
	
	
	public int getIdPersonnel() {
		return idPersonnel;
	}
	
	/**
     * Get un iterator sur la liste du personnel de ce composite.
     * @return iterateur sur la liste des personnel de ce composite.
     */
	public Iterator<InterfacePersonnel> iterator() {

		return Personnels.iterator();
	}
	
	/**
     * vide la liste du personnel.
     */
	public void reset() {
		Personnels.clear();
	}
	
	 /**
     * serialization vers un fichier.
     * @param nom ou chemin du fichier vers lequel serializer.
     */
	
	public void serialize(String path) {
		ObjectOutputStream writer = null;
		try {
			FileOutputStream file = new FileOutputStream(path);
			writer = new ObjectOutputStream(file);
			writer.writeObject(this);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("La serialization a échoué vers le fichier \"" + path + "\"");
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

	public static GroupePersonnel deserialize(final String path) {
		ObjectInputStream read = null;
		GroupePersonnel cp = null;
		try {
			FileInputStream file = new FileInputStream(path);
			read = new ObjectInputStream(file);
			cp = (GroupePersonnel) read.readObject();
		} catch (IOException e) {
			System.err.println("La deserialization a échoué depuis le fichier \"" + path + "\"");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (read != null) {
				read.close();
			}
		} catch (IOException E) {
			E.printStackTrace();
		}
		return cp;
	}

}

