package org.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.shared.configs.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    private static ConnectionManager instance;

    private Connection connection;

    private ConnectionManager() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(
                    DatabaseConfiguration.URL,
                    DatabaseConfiguration.USER,
                    DatabaseConfiguration.PASSWORD
            );
            logger.info("Подключение к базе данных: {}", DatabaseConfiguration.URL);

        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Ошибка подключения к базе данных: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static ConnectionManager getInstance() throws SQLException {
        return (instance == null || instance.connection.isClosed()) ? instance = new ConnectionManager() : instance;
    }

    public PreparedStatement prepare(final String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public void close() throws SQLException {
        connection.close();
    }

}
