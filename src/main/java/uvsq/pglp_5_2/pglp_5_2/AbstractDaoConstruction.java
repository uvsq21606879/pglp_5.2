package uvsq.pglp_5_2.pglp_5_2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public class AbstractDaoConstruction {	
	

	/**
     * Afin de recuperer une fabrique.
     * @param t type de fabrique souhaitee
     * @return la fabrique
     */
    public static Object getConstruction(TypeImpDao type) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:derby:MaBdd;create=false");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(type != null) {
        if (type == TypeImpDao.CRUD)
            return new DaoConstructionCRUD();
        else 
            return new DaoConstructionJDBC(connection);
        }

        else  return null;
    }
    
    public enum TypeImpDao {
        
        CRUD,
        JDBC;
    }

}
