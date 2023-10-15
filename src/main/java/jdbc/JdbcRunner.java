package jdbc;

import org.postgresql.Driver;

import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        Class<Driver> driver= Driver.class;

        try (var connection = ConnectionManager.get()){
            System.out.println(connection.getTransactionIsolation());
        }
    }
}
