package repository.impl;

import exceptions.ConnectionDoesNotExistException;
import jdbc.ConnectionManager;
import model.WellCluster;
import repository.WellClusterDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WellClusterDaoImpl implements WellClusterDao {
    private static final WellClusterDao wellClusterDao = new WellClusterDaoImpl();
    private static final String DELETE_BY_ID = "DELETE FROM well_clusters WHERE id=?";
    private static final String SAVE = "INSERT INTO well_clusters(location_id, operator_id, number) VALUES (?,?,?)";

    private static final String UPDATE_WELL_CLUSTER = "UPDATE well_clusters SET location_id = ?, operator_id = ?, number = ? WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, location_id, operator_id, number FROM well_clusters";
    private static final String GET_BY_ID = FIND_ALL + " WHERE id = ?";

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
            preparedStatement.setLong(1, wellCluster.getLocationId());
            preparedStatement.setLong(2, wellCluster.getOperatorId());
            preparedStatement.setLong(3, wellCluster.getNumber());

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

    @Override
    public void update(WellCluster wellCluster) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WELL_CLUSTER)) {
            preparedStatement.setLong(1, wellCluster.getLocationId());
            preparedStatement.setLong(2, wellCluster.getOperatorId());
            preparedStatement.setLong(3, wellCluster.getNumber());
            preparedStatement.setLong(4, wellCluster.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    @Override
    public Optional<WellCluster> getById(Long id) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            WellCluster wellCluster = null;
            if (resultSet.next()) {
                wellCluster = getWellCluster(resultSet);
            }
            return Optional.ofNullable(wellCluster);
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    @Override
    public List<WellCluster> findAll() {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            List<WellCluster> wellClusters = new ArrayList<>();
            while (resultSet.next()) {
                wellClusters.add(getWellCluster(resultSet));
            }
            return wellClusters;
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    private WellCluster getWellCluster(ResultSet resultSet) throws SQLException {
        return new WellCluster(resultSet.getLong("id"),
                resultSet.getLong("location_id"),
                resultSet.getLong("operator_id"),
                resultSet.getLong("number"));
    }
}