package uvsq.pglp_5_2.pglp_5_2;

import static org.junit.Assert.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

public class DaoConstructionCRUDTest {

	 @Test
	    public void testDaoPersonnel() {
	        DAO<Personnel> dao = DaoConstructionCRUD.getDaoPersonnel(null);
	        assertTrue(dao.getList().isEmpty());
	    }

	    @Test
	    public void testDaoCompositePersonnels() {
	        DAO<GroupePersonnel> dao = DaoConstructionCRUD.getDaoCompositeGroupePersonnel(null);
	        assertTrue(dao.getList().isEmpty());
	    }
	    
	    @Test
	    public void testDaoPersonnelDeserialize() {
	        DaoPersonnel DaoP = (DaoPersonnel) DaoConstructionCRUD.getDaoPersonnel(null);
	        ArrayList<String> numero = new ArrayList<String>();
	        numero.add("06 81 63 15 91");
	        numero.add("07 51 48 13 15");
	        Personnel P = new Personnel.Builder("Said", "Seghir", LocalDate.of(1997, 05, 10), numero).build();
	        DaoP.ajouter(P);
	        DaoP.serialize("DaoP.ser");
	        DaoPersonnel DaoP2 = DaoPersonnel.deserialize("DaoP.ser");
	        File f = new File("DaoP.ser");
	        f.delete();
	        assertTrue(DaoP2.get(P.getIdPersonnel()).getNom().equals("Said") && DaoP2.get(P.getIdPersonnel()).getPrenom().equals("Seghir"));
	    }

	    @Test
	    public void testDaoCompositePersonnelsDeserialize() {
	        DaoComposite DaoCompP = (DaoComposite) DaoConstructionCRUD.getDaoCompositeGroupePersonnel(null);
	        GroupePersonnel CompP = new GroupePersonnel();
	        DaoCompP.ajouter(CompP);
	        DaoCompP.serialize("DaoCompP.ser");
	        DaoComposite DaoCompP2 = (DaoComposite) DaoConstructionCRUD.getDaoCompositeGroupePersonnel("DaoCompP.ser");
	        File f = new File("DaoCompP.ser");
	        f.delete();
	        assertTrue(DaoCompP.getList().toString().equals(DaoCompP2.getList().toString()));
	    }
	    
	    @Test
	    public void testDaoPersonnelEchec() {
	        assertNull(DaoConstructionCRUD.getDaoPersonnel("file"));
	    }
	    
	    @Test
	    public void testDaoCompositePersonnelsEchec() {
	        assertNull(DaoConstructionCRUD.getDaoCompositeGroupePersonnel("file"));
	    }

}
