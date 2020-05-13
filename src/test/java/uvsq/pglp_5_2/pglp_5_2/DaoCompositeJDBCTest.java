package uvsq.pglp_5_2.pglp_5_2;

import static org.junit.Assert.*;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class DaoCompositeJDBCTest {

	@Test
    public void testAddGet() {
        DaoComposite dcp = new DaoComposite();
        GroupePersonnel cp = new GroupePersonnel();
        dcp.ajouter(cp);
        assertTrue(dcp.getList().size() == 1 && dcp.get(cp.getIdPersonnel()) == cp);
    }

    @Test
    public void testSuprimer() {
        DaoComposite dcp = new DaoComposite();
        GroupePersonnel cp = new GroupePersonnel();
        dcp.ajouter(cp);
        dcp.supprimer(cp);
        assertTrue(dcp.getList().isEmpty());
    }
    
    @Test
    public void testgetNull() {
        DaoComposite dcp = new DaoComposite();
        assertNull(dcp.get(0));
    }
    
    @Test
    public void testMiseAjour() {
        DaoComposite DaoComp = new DaoComposite();
        GroupePersonnel cp = new GroupePersonnel();
        GroupePersonnel cp2 = new GroupePersonnel();
        GroupePersonnel cp3 = new GroupePersonnel();
        cp.ajouter(cp2);
        cp.ajouter(cp3);
        DaoComp.ajouter(cp);
        Map<String, Object> params = new HashMap<String, Object> ();
        params.put("personnels", new ArrayList<InterfacePersonnel> ());
        DaoComp.miseAjour(cp, params);
        String reponse = "Id_Personnel = " + cp.getIdPersonnel() + "\n";
        
        assertNotNull(DaoComp.get(cp.getIdPersonnel()).toString().equals(reponse));
    }
    
    @Test
    public void testSerialize() {
        DaoComposite DaoComp = new DaoComposite();
        GroupePersonnel cp = new GroupePersonnel();
        DaoComp.ajouter(cp);
        DaoComp.serialize("DaoComp.ser");
        DaoComposite dcp2 = DaoComposite.deserialize("DaoComp.ser");
        File f = new File("DaoComp.ser");
        f.delete();
        assertNotNull(DaoComp.getList().toString().equals(dcp2.getList().toString()));
    }
    
    @Test
    public void testEchecDeserialize() {
        DaoComposite c = DaoComposite.deserialize("test");
        assertNull(c);
    }

}
