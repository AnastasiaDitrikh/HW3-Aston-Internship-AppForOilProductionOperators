package repository.impl;

import exceptions.ConnectionDoesNotExistException;
import jdbc.ConnectionManager;
import model.Location;
import repository.LocationDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationDaoImpl implements LocationDao {
    private static final LocationDao locationDao = new LocationDaoImpl();
    private final String DELETE_BY_ID = "DELETE FROM location WHERE id=?";
    private static final String SAVE = "INSERT INTO location (lat, lon) VALUES (?,?)";

    private static final String UPDATE_LOCATION = "UPDATE location SET lat = ?, lon = ? WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, lat, lon FROM location";
    private static final String GET_BY_ID = FIND_ALL + " WHERE id = ?";

    private LocationDaoImpl() {

    }

    public static LocationDaoImpl getLocationDao() {
        return (LocationDaoImpl) locationDao;

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
    public Location save(Location location) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, location.getLat());
            preparedStatement.setDouble(2, location.getLon());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                location.setId(generatedKeys.getLong("id"));
            }
            return location;
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    @Override
    public void update(Location location) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LOCATION)) {
            preparedStatement.setDouble(1, location.getLat());
            preparedStatement.setDouble(2, location.getLon());
            preparedStatement.setLong(3, location.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }


    @Override
    public Optional<Location> getById(Long id) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Location location = null;
            if (resultSet.next()) {
                location = getLocation(resultSet);
            }
            return Optional.ofNullable(location);
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    @Override
    public List<Location> findAll() {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Location> locations = new ArrayList<>();
            while (resultSet.next()) {
                locations.add(getLocation(resultSet));
            }
            return locations;
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    private Location getLocation(ResultSet resultSet) throws SQLException {
        return new Location(resultSet.getLong("id"),
                resultSet.getDouble("lat"),
                resultSet.getDouble("lon"));
    }
}