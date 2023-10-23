package repository.impl;

import exceptions.ConnectionDoesNotExistException;
import jdbc.ConnectionManager;
import model.Well;
import repository.WellDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WellDaoImpl implements WellDao {
    private static final WellDao wellDao = new WellDaoImpl();
    private static final String DELETE_BY_ID = "DELETE FROM wells WHERE id=?";
    private static final String SAVE = "INSERT INTO wells(equipment, productivity, well_cluster_id) VALUES (?,?,?)";

    private static final String UPDATE_WELL = "UPDATE wells SET equipment = ?, productivity = ?, well_cluster_id = ? WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, equipment, productivity, well_cluster_id FROM wells";
    private static final String GET_BY_ID = FIND_ALL + " WHERE id = ?";

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
            preparedStatement.setLong(3, well.getWellClusterId());

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

    @Override
    public void update(Well well) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_WELL)) {
            preparedStatement.setString(1, well.getEquipment());
            preparedStatement.setDouble(2, well.getProductivity());
            preparedStatement.setLong(3, well.getWellClusterId());
            preparedStatement.setLong(4, well.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    @Override
    public Optional<Well> getById(Long id) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Well well = null;
            if (resultSet.next()) {
                well = getWell(resultSet);
            }
            return Optional.ofNullable(well);
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    @Override
    public List<Well> findAll() {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Well> wells = new ArrayList<>();
            while (resultSet.next()) {
                wells.add(getWell(resultSet));
            }
            return wells;
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    private Well getWell(ResultSet resultSet) throws SQLException {
        return new Well(resultSet.getLong("id"),
                resultSet.getString("equipment"),
                resultSet.getDouble("productivity"),
                resultSet.getLong("well_cluster_id"));
    }
}