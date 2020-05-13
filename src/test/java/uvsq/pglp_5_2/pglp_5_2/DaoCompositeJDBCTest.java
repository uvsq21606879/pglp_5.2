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
    public void testRemove() {
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
    public void testSerialize() {
        DaoComposite dcp = new DaoComposite();
        GroupePersonnel cp = new GroupePersonnel();
        dcp.ajouter(cp);
        
        dcp.serialize("dcp.ser");
        DaoComposite dcp2 = DaoComposite.deserialize("dcp.ser");
        File f = new File("dcp.ser");
        f.delete();
        assertTrue(dcp.getList().toString().equals(dcp2.getList().toString()));
    }
    
    @Test
    public void testEchecDeserialize() {
        DaoComposite c = DaoComposite.deserialize("ccc");
        assertNull(c);
    }

}
