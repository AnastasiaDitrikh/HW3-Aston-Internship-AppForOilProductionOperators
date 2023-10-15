package repository.impl;

import exceptions.ConnectionDoesNotExistException;
import jdbc.ConnectionManager;
import model.Location;
import repository.LocationDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class LocationDaoImpl implements LocationDao {
    private static final LocationDao locationDao = new LocationDaoImpl();
    private final String DELETE_BY_ID = "DELETE FROM location WHERE id=?";
    public static final String SAVE = "INSERT INTO location (lat, lon) VALUES (?,?)";

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
}
