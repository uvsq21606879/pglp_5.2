package uvsq.pglp_5_2.pglp_5_2;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Personnel implements Serializable, InterfacePersonnel {

	/**
	 * Le numero de serialization genere aleatoirement.
	 */
	private static final long serialVersionUID = -2080828874542305205L;
	/**
	 * identifiant du personnel.
	 */
	private int idPersonnel;

	/**
	 * Nom du personnel.
	 */
	private String Nom;
	/**
	 * Prenom du personnel.
	 */
	private String Prenom;
	/**
	 * date de naissance du personnel.
	 */
	private LocalDate dateNaissance;

	/**
	 * liste des numeros de telephone du personnel.
	 */
	ArrayList<String> NumsTelephone;

	/**
	 * construire le personnel avec le builder.
	 * @param le builder pour creer le personnel
	 */
	public Personnel(Builder builder) {
		idPersonnel = builder.idPersonnel;
		Nom = builder.Nom;
		Prenom = builder.Prenom;
		dateNaissance = builder.dateNaissance;
		NumsTelephone = builder.NumsTelephone;
	}


	/**
	 * Retourner l'identifiant d'un personnel
	 * @return id du personnel.
	 */
	public int getIdPersonnel() {
		return idPersonnel;
	}

	/**
	 * Retourner l'identifiant d'un personnel
	 * @return id du personnel.
	 */
	public String getPrenom() {
		return Prenom;
	}

	/**
	 * Retourner l'identifiant du personnel
	 * @return nom du personnel.
	 */
	public String getNom() {
		return Nom;
	}

	/**
	 * Retourner la date de naissance du personnel
	 * @return date de naissance du personnel.
	 */
	public LocalDate getDateNaissance() {
		return dateNaissance;

	}

	/**
	 * Retourner la liste des numeros de tel du personnel
	 * @return liste des numeros du personnel.
	 */
	public ArrayList<String> getNumsTelephone() {
		@SuppressWarnings("unchecked")
		ArrayList<String> clone = (ArrayList<String>) NumsTelephone.clone();
		return clone;
	}

	/**
	 * Builder de la classe personnel.
	 */

	public static class Builder {

		private int idPersonnel;
		private String Nom;
		private String Prenom;
		private LocalDate dateNaissance;
		ArrayList<String> NumsTelephone;

		/**
		 * Generer  un id unique pour chaque personnel.
		 */
		private int idGenerator = 0;
		
		 /**
         * constructeur de la classe Builder.
         * @param nom du personnel.
         * @param prenom du personnel.
         * @param date de naissance du personnel.
         * @param les numEros de telephone du personnel
         */
		public Builder(String nomPersonnel, String Prenom, LocalDate dateNaissance, ArrayList<String> numsTelP) {
			this.Nom = nomPersonnel;
			this.Prenom = Prenom;
			this.dateNaissance = dateNaissance;
			this.NumsTelephone = numsTelP;
			this.idPersonnel = idGenerator = idGenerator + 1;

		}
		
		 /**
         * construire un type Personnel avec le builder.
         * @return le Personnel construit avec ce builder
         */
		public Personnel build() {
			return new Personnel(this);
		}

	}
	
	 /**
     * retourner un personnel sous forme d'un String.
     * @return le personnel sous forme d'une chaine de caracteres. 
     */
	
	public String getPersonnel() {

		String P = this.Nom + "  " + this.Prenom + ", date de naissance:" + this.dateNaissance.toString()
		+ ", Nomeros de telephone : ";
		String Numeros = "";

		for (String i : this.NumsTelephone)
			Numeros = Numeros + i;

		P = P + Numeros;

		return P;

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
			System.out.println("erreur de serialization vers le fichier /" + path);
		}

		try {
			if(writer != null) {
				writer.flush();
				writer.close();
			}
		}catch(IOException E) {
			E.printStackTrace();
		}
	}
	
	/**
     * deserialization vers un fichier.
     * @param nom ou chemin du fichier vers lequel deserializer.
     * @return instance des classes creees a partir de le deserialization
     */

	public static Personnel deserialize(String path) {

		ObjectInputStream reader = null;
		Personnel p =null;

		try {
			FileInputStream file = new FileInputStream(path);
			reader = new ObjectInputStream(file);
			p = (Personnel) reader.readObject();
		}catch(IOException e) {
			System.err.println( "echec deserialization  /" + path );

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			if(reader != null) {
				reader.close();
			}
		}catch (IOException E) {
			E.printStackTrace();
		}
		return p;

	}


	public int getIdComposite() {
		// TODO Auto-generated method stub
		return idPersonnel;
	}
	
	 /**
     * modifier l'identifiant du personnel.
     * @param newId nouvel identifiant
     */
    public void setIdPersonel(final int newId) {
        idPersonnel = newId;
    }

}

