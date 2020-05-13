package uvsq.pglp_5_2.pglp_5_2;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;


public class PersonnelTest {
	@Test
	public void test() {
		ArrayList<String> Nums = new ArrayList<String>();
		Nums.add("06 81 64 62 78");
		Nums.add("07 71 64 92 18");
		Personnel p = new Personnel.Builder("Seghir", "Said", LocalDate.of(1997, 03, 18), Nums).build();
		assertTrue(p.getNom().equals("Seghir") && p.getPrenom() == "Said"
				&& p.getDateNaissance().equals(LocalDate.of(1997, 03, 18)) && p.getNumsTelephone().containsAll(Nums));
	}

	@Test
	public void TestSerialize() {

		ArrayList<String> Nums = new ArrayList<String>();
		Nums.add("06 81 64 62 74");
		Nums.add("07 71 64 92 18");
		Personnel personnel = new Personnel.Builder("Seghir", "Said", LocalDate.of(1997, 03, 18), Nums).build();

		personnel.serialize("personne.ser");
		Personnel personnel2 = Personnel.deserialize("personne.ser");
		File f = new File("personne.ser");
		f.delete();
		
		assertTrue(personnel.getPersonnel().equals(personnel2.getPersonnel()));

	}


}
