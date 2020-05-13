package uvsq.pglp_5_2.pglp_5_2;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;

import org.junit.Test;


public class GroupePersonnelTest {
	

	@Test
	public void GroupPersonnelTest() {
		GroupePersonnel personnel = new GroupePersonnel();
		Iterator<InterfacePersonnel> ItPersonnel = personnel.iterator();
		assertFalse(ItPersonnel.hasNext());
	}
	@Test
	public void ajouterTest() {
		GroupePersonnel personnel = new GroupePersonnel();
		personnel.ajouter(new GroupePersonnel());
		Iterator<InterfacePersonnel> ItPersonnel = personnel.iterator();
		assertTrue(ItPersonnel.hasNext());
	}
	
	@Test
	public void SerializationTest() {
	    GroupePersonnel personnel = new GroupePersonnel();
	    GroupePersonnel personnel2 = new GroupePersonnel();
        personnel.ajouter(personnel2);
        
        personnel.serialize("CompositeP.ser");
        GroupePersonnel personnel3 = GroupePersonnel.deserialize("CompositeP.ser");
        File f = new File("CompositeP.ser");
        f.delete();
        assertTrue(personnel.toString().equals(personnel3.toString()));
	}
	@Test
	public void testSuppression() {
		GroupePersonnel personnel = new GroupePersonnel();
		GroupePersonnel personnel2 = new GroupePersonnel();
		InterfacePersonnel iterator = personnel2;
		personnel.ajouter(personnel2);
		personnel.supprimer(iterator);
		Iterator<InterfacePersonnel> IntrPersonnel = personnel.iterator();
		assertFalse(IntrPersonnel.hasNext());
	}
	

}
