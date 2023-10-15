package repository.impl;

import exceptions.ConnectionDoesNotExistException;
import jdbc.ConnectionManager;

import repository.WellClusterDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WellClusterDaoImpl implements WellClusterDao {
    private final WellClusterDao wellClusterDao = new WellClusterDaoImpl();
    private final String DELETE_BY_ID = "DELETE FROM well_clusters WHERE id=?";

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
}