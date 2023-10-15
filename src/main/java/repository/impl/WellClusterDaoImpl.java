package repository.impl;

import exceptions.ConnectionDoesNotExistException;
import jdbc.ConnectionManager;
import model.WellCluster;
import repository.WellClusterDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class WellClusterDaoImpl implements WellClusterDao {
    private static final WellClusterDao wellClusterDao = new WellClusterDaoImpl();
    private static final String DELETE_BY_ID = "DELETE FROM well_clusters WHERE id=?";
    public static final String SAVE = "INSERT INTO well_clusters(location_id, operator_id, number) VALUES (?,?,?)";

    private WellClusterDaoImpl() {

    }

    public static WellClusterDaoImpl getWellClusterDao() {
        return (WellClusterDaoImpl) wellClusterDao;
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
    public WellCluster save(WellCluster wellCluster) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, wellCluster.getLocality().getId());
            preparedStatement.setLong(2, wellCluster.getOperator().getId());
            preparedStatement.setInt(3, wellCluster.getNumber());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                wellCluster.setId(generatedKeys.getLong("id"));
            }
            return wellCluster;
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }
}