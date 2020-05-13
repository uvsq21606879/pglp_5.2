package uvsq.pglp_5_2.pglp_5_2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.PreparedStatement;

import java.util.ArrayList;

import uvsq.pglp_5_2.pglp_5_2.AbstractDaoConstruction.TypeImpDao;

public class DaoPersonnelJDBC extends AbstractDao<Personnel> {
    /**
     * constructeur de la classe.
     * @param connect se connecter
     */
    public DaoPersonnelJDBC(final Connection connect) {
        super(connect);
    }
    /**
     * insérer les valeurs de numéro de téléphone du personnel.
     * @param object le numero de téléphone à insérer
     * @param idPersonnel l'identifiant du personnel associé
     * @return le numéro de téléphone
     * @throws SQLException si la création échoue
     */
    private String createNumeroTelephone(
            final String object, final int idPersonnel) {
        final int un = 1, deux = 2;
        PreparedStatement prepare;
        try {
            prepare = connect.prepareStatement(
            "INSERT INTO numeroTelephone"
            + " (idPersonnel,numero)"
            + " VALUES(?, ?)");
            prepare.setInt(un, idPersonnel);
            prepare.setString(deux, object);
            prepare.executeUpdate();
        } catch (SQLException e) {
            return null;
        }
        return object;
    }
    /**
     * rechercher tous les numéro de téléphone d'un personnel.
     * @param idPersonnel l'id du personnel
     * @return un tableau avec les numéros de téléphone
     * @throws SQLException si la recherche échoue
     */
    private ArrayList<String> findNumeroTelephone(final int idPersonnel)
            throws SQLException {
        final int un = 1;
        ArrayList<String> numeroTelephone = new ArrayList<String>();
        PreparedStatement prepare = connect.prepareStatement(
                "SELECT numero FROM numeroTelephone WHERE idPersonnel = ?");
        prepare.setInt(un, idPersonnel);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            numeroTelephone.add(result.getString("numero"));
        }
        return numeroTelephone;
    }
    /**
     * retire de la bdd tous les numéros de téléphones d'un personnel.
     * @param idPersonnel l'id du personnel
     * @throws SQLException si la suppression échoue
     */
    private void deleteNumeros(final int idPersonnel) throws SQLException {
        final int un = 1;
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM numeroTelephone WHERE idPersonnel = ?");
        prepare.setInt(un, idPersonnel);
        prepare.executeUpdate();
    }
    /**
     * rechercher les composites qui composent ce personnel.
     * @param idPersonnel id du personnel
     * @return la liste des composites qui contiennent ce personnel
     * @throws SQLException erreur sql
     */
    @SuppressWarnings("static-access")
    private ArrayList<GroupePersonnel> findComposantPersonnel(
            final int idPersonnel) throws SQLException {
        ArrayList<GroupePersonnel> array =
                new ArrayList<GroupePersonnel>();
        DaoConstructionJDBC factorytmp = (DaoConstructionJDBC)
                AbstractDaoConstruction.getConstruction(TypeImpDao.JDBC);
        DaoCompositeJDBC daoComposite = (DaoCompositeJDBC)
                factorytmp.getDaoCompositePersonnels();
        PreparedStatement prepare = connect.prepareStatement(
                "SELECT idComposite FROM composantPersonnel"
                + " WHERE idPersonnel = ?");
        prepare.setInt(1, idPersonnel);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            GroupePersonnel finder =
                    daoComposite.find(result.getInt("idComposite"));
            if (finder != null) {
                array.add(finder);
            }
        }
        return array;
    }
    /**
     * supprime les relations de compositions de ce Personnel.
     * @param idPersonnel le Personnel en question
     * @throws SQLException échec de la recherche
     */
    private void deleteComposantPersonnel(
            final int idPersonnel) throws SQLException {
        PreparedStatement prepare = connect.prepareStatement(
                "DELETE FROM composantPersonnel WHERE idPersonnel = ?");
        prepare.setInt(1, idPersonnel);
        prepare.executeUpdate();
    }
    /**
     * crée un élément dans la bdd.
     * @param object element à ajouter
     */
    @Override
    public Personnel create(final Personnel object) {
        final int un = 1, deux = 2, trois = 3, quatre = 4;
        try {
            PreparedStatement prepare = connect.prepareStatement(
            "INSERT INTO personnel"
            + " (id,nom,prenom,dateNaissance)"
            + " VALUES(?, ?, ?, ?)");
            prepare.setInt(un, object.getIdPersonnel());
            prepare.setString(deux, object.getNom());
            prepare.setString(trois, object.getPrenom());
            Date date = Date.valueOf(object.getDateNaissance());
            prepare.setDate(quatre, date);
            int result = prepare.executeUpdate();
            assert result == un;
            for (String num : object.getNumsTelephone()) {
                createNumeroTelephone(num, object.getIdPersonnel());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }
    /**
     * cherche un element dans la bdd.
     * @param id identifiant de l'objet a chercher
     */
    @Override
    public Personnel find(final int id) {
        final int un = 1;
        Personnel p = null;
        LocalDate dateNaissance;
        try {
            PreparedStatement prepare = connect.prepareStatement(
                    "SELECT * FROM personnel WHERE id = ?");
            prepare.setInt(un, id);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                dateNaissance = result.getDate("dateNaissance").toLocalDate();
                result.getString("nom");
                p = new Personnel.Builder(
                        result.getString("nom"),
                        result.getString("prenom"),
                        dateNaissance,
                        this.findNumeroTelephone(id)
                        ).build();
                p.setIdPersonel(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return p;
    }
    /**
     * modifier un personnel.
     * @param object données pour modifier
     */
    @SuppressWarnings("static-access")
    @Override
    public Personnel MiseAjour(final Personnel object) {
        final int un = 1, deux = 2, trois = 3, quatre = 4;
        final Personnel before = this.find(object.getIdPersonnel());
        ArrayList<GroupePersonnel> composantBefore = null;
        try {
            composantBefore = findComposantPersonnel(object.getIdPersonnel());
        } catch (SQLException e1) {
            return before;
        }
        if (before != null) {
            try {
                PreparedStatement prepare = connect.prepareStatement(
                "UPDATE personnel SET nom = ?, prenom = ?, dateNaissance = ?"
                + " WHERE id = ?");
                prepare.setString(un, object.getNom());
                prepare.setString(deux, object.getPrenom());
                Date date = Date.valueOf(object.getDateNaissance());
                prepare.setDate(trois, date);
                prepare.setInt(quatre, object.getIdPersonnel());
                int result = prepare.executeUpdate();
                assert result == un;
                this.deleteNumeros(object.getIdPersonnel());
                for (String num : object.getNumsTelephone()) {
                    this.createNumeroTelephone(num, object.getIdPersonnel());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                this.Suprimer(object);
                this.create(before);
                DaoConstructionJDBC factorytmp = (DaoConstructionJDBC)
                        AbstractDaoConstruction.getConstruction(TypeImpDao.JDBC);
                DaoCompositeJDBC daoComposite =
                        (DaoCompositeJDBC)
                        factorytmp.getDaoCompositePersonnels();
                for (GroupePersonnel cp : composantBefore) {
                    daoComposite.createComposantPersonnel(
                            cp.getIdPersonnel(), object.getIdPersonnel());
                }
                return before;
            }
        } else {
            return null;
        }
        return object;
    }
    /**
     * supprime de la bdd.
     * @param object l'objet a supprimer
     */
    @Override
    public void Suprimer(final Personnel object) {
        final int un = 1;
        try {
            this.deleteComposantPersonnel(object.getIdPersonnel());
            this.deleteNumeros(object.getIdPersonnel());
            PreparedStatement prepare = connect.prepareStatement(
                    "DELETE FROM personnel WHERE id = ?");
            prepare.setInt(un, object.getIdPersonnel());
            int result = prepare.executeUpdate();
            assert result == un;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
}
