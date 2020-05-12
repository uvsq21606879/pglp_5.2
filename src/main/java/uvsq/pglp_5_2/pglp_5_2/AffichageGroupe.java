package uvsq.pglp_5_2.pglp_5_2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * Affichage par groupe d'une InterfacePersonnel.
 */

public class AffichageGroupe implements Iterable<InterfacePersonnel>, Serializable {

	/**
	 * Le numero de serialization genere aleatoirement.
	 */
	private static final long serialVersionUID = -3373682237002368918L;

	public Iterator<InterfacePersonnel> iterator() {
		// TODO Auto-generated method stub
		return listeIpersonnel.iterator();
	}
	
	 /**
     * identifiant du personnel.
     */
	private int idPersonnel;
	
	/**
     * afin de generer un id unique pour chaque personnel.
     */
	private int idGenerator = 0;

	/**
	 * liste a remplir contenant de Interfaces personnel.
	 */
	ArrayDeque<InterfacePersonnel> listeIpersonnel;

	/**
	 * constructeur de la classe.
	 */

	public AffichageGroupe() {
		listeIpersonnel = new ArrayDeque<InterfacePersonnel>();
		idPersonnel = idGenerator = idGenerator + 1;

	}

	/**
	 * Pour acceder a l'identifiant du Personnel.
	 * @return idPersonnel
	 */

	public int getIdPersonnel() {
		return this.idPersonnel;
	}
	
	/**
	 * Ajouter un Personnel a la liste de Interface personnel.
	 * @return Personnel a ajouter
	 */
	public void ajouter(final InterfacePersonnel IntrPersonnel) {
		listeIpersonnel.add(IntrPersonnel);
	}
	
	/**
	 * Supprimer tout les elments de la liste.
	 */
	public void reset() {
		while (!listeIpersonnel.isEmpty()) {
			listeIpersonnel.removeFirst();
		}
	}
	
	  /**
     * Retourner tout le contenu de la listeIpesonnel sous forme d'une chaine de caracteres.
     * @return le parcours de la listeIpersonnel en String.
     */
	@Override
	public String toString() {
		String personnel = "";
		GroupePersonnel tmp;
		// affichage du parcours
		for (InterfacePersonnel c2 : listeIpersonnel) {
			if (c2.getClass().equals(GroupePersonnel.class)) {
				tmp = (GroupePersonnel) c2;
				personnel += tmp.getIdPersonnel() + "\n";
			} else {
				personnel += ((Personnel) c2).toString();
			}
		}
		return personnel;
	}
	
	 /**
     * serialization vers un fichier.
     * @param nom ou chemin du fichier vers lequel serializer.
     */
	
	public void serialize(final String chemin) {
		ObjectOutputStream writer = null;
		try {
			FileOutputStream fichier = new FileOutputStream(chemin);
			writer = new ObjectOutputStream(fichier);
			writer.writeObject(this);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("La serialization a echoue vers le fichier");
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

	public static AffichageGroupe deserialize(final String chemin) {
		ObjectInputStream reader = null;
		AffichageGroupe apg = null;
		try {
			FileInputStream fichier = new FileInputStream(chemin);
			reader = new ObjectInputStream(fichier);
			apg = (AffichageGroupe) reader.readObject();
		} catch (IOException e) {
			System.err.println("La deserialization a echoue vers le fichier");
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
		return apg;
	}

	public void parcoursGroupes(final InterfacePersonnel personnel) {
		if (personnel.getClass() == GroupePersonnel.class) {
			InterfacePersonnel p1, p2;
			GroupePersonnel Ptmp;
			listeIpersonnel = new ArrayDeque<InterfacePersonnel>();
			ArrayDeque<InterfacePersonnel> d = new ArrayDeque<InterfacePersonnel>();
			d.add(personnel);
			while (!d.isEmpty()) {
				p1 = d.pollFirst();
				listeIpersonnel.add(p1);
				if (p1.getClass() == GroupePersonnel.class) {
					Ptmp = (GroupePersonnel) p1;
					Iterator<InterfacePersonnel> ite = Ptmp.iterator();
					while (ite.hasNext()) {
						p2 = ite.next();
						if (!d.contains(p2) && !listeIpersonnel.contains(p2))
							d.add(p2);
					}
				}
			}
		}
	}

}

