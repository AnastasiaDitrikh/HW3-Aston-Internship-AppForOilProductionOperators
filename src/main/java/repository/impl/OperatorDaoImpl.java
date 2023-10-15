package repository.impl;

import exceptions.ConnectionDoesNotExistException;
import jdbc.ConnectionManager;
import model.Operator;
import repository.OperatorDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class OperatorDaoImpl implements OperatorDao {
    private static final OperatorDao operatorDao = new OperatorDaoImpl();
    private static final String DELETE_BY_ID = "DELETE FROM operators WHERE id=?";
    private static final String SAVE_OPERATOR = "INSERT INTO operators(name, surname) VALUES (?,?)";
    private static final String UPDATE_OPERATOR = "UPDATE operators SET name = ?, surname = ? WHERE id = ?";
    public static final String FIND_ALL = "SELECT id, name, surname FROM operators";
    public static final String GET_BY_ID = FIND_ALL + " WHERE id = ?";

    private OperatorDaoImpl() {

    }

    public static OperatorDaoImpl getOperatorDao() {
        return (OperatorDaoImpl) operatorDao;
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
    public Operator save(Operator operator) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_OPERATOR, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, operator.getName());
            preparedStatement.setString(2, operator.getSurname());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                operator.setId(generatedKeys.getLong("id"));
            }
            return operator;
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    @Override
    public void update(Operator operator) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_OPERATOR)) {
            preparedStatement.setString(1, operator.getName());
            preparedStatement.setString(2, operator.getSurname());
            preparedStatement.setLong(3, operator.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    @Override
    public Optional<Operator> getById(Long id) {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Operator operator = null;
            if (resultSet.next()) {
                operator = getOperator(resultSet);
            }
            return Optional.ofNullable(operator);
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    @Override
    public List<Operator> findAll() {
        try (var connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Operator> operators = new ArrayList<>();
            while (resultSet.next()) {
                operators.add(getOperator(resultSet));
            }
            return operators;
        } catch (SQLException throwables) {
            throw new ConnectionDoesNotExistException("Соединение не установлено");
        }
    }

    private Operator getOperator(ResultSet resultSet) throws SQLException {
        return new Operator(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("surname"));
    }
}