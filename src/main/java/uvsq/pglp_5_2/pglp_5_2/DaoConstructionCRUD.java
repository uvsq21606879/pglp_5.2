package uvsq.pglp_5_2.pglp_5_2;


public class DaoConstructionCRUD extends AbstractDaoConstruction {

	public DaoConstructionCRUD() {
		}
	
	 /**
    * Construction du Dao pour le Personnel.
    * @param le chargemment d'une sauvegarde.
    * @return DaoPersonnel deserealize.
    */

	public static DAO<Personnel> getDaoPersonnel(String Deserialize) {
		if (Deserialize == null) {
			return new DaoPersonnel();
		} else {
			return DaoPersonnel.deserialize(Deserialize);
		}
	}
		
	/**
    * Construction du Dao pour le GroupePersonnel.
    * @param le chargemment d'une sauvegarde.
    * @return DaoComposite deserealize.
    */
	
	public static DAO<GroupePersonnel> getDaoCompositeGroupePersonnel(String Deserialize) {
		if (Deserialize == null) {
			return new DaoComposite();
		} else {
			return DaoComposite.deserialize(Deserialize);
		}
	}
	

}
