package repository.impl;

import exceptions.ConnectionDoesNotExistException;
import jdbc.ConnectionManager;
import model.Well;
import repository.WellDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class WellDaoImpl implements WellDao {
    private static final WellDao wellDao = new WellDaoImpl();
    private static final String DELETE_BY_ID = "DELETE FROM wells WHERE id=?";
    private static final String SAVE = "INSERT INTO wells(equipment, productivity, well_cluster_id) VALUES (?,?,?)";

    private WellDaoImpl() {

    }

    public static WellDaoImpl getWellDao() {
        return (WellDaoImpl) wellDao;
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    @Override
    public Well save(Well well) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, well.getEquipment());
            preparedStatement.setDouble(2, well.getProductivity());
            preparedStatement.setLong(3, well.getWellCluster().getId());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                well.setId(generatedKeys.getLong("id"));
            }
            return well;
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }
}
