package uvsq.pglp_5_2.pglp_5_2;

import java.sql.Connection;

public class DaoConstructionJDBC extends AbstractDaoConstruction {
	 
    private static Connection connection;
    
    public DaoConstructionJDBC(final Connection connection) {
        DaoConstructionJDBC.connection = connection;
    }
    
    /**
     * Construction de Dao pour la classe Personnel.
     * @return Dao pour la classe Personnel.
     */
    public static AbstractDao<Personnel> getDaoPersonnel() {
        return new DaoPersonnelJDBC(connection);
    }
    
    /**
     * Construction de Dao pour la classe GroupePersonnel
     * @return Dao pour la classe GroupePersonnel
     */
    public static AbstractDao<GroupePersonnel>
    getDaoCompositePersonnels() {
        return new DaoCompositeJDBC(connection);
    }
}
