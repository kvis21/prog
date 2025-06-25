package org.server.database.dbmanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.server.database.ConnectionManager;
import org.server.database.security.PasswordUtil;
import org.server.database.sqlquery.UserQuery;
import org.shared.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBUserManager {
    private static final Logger log = LoggerFactory.getLogger(DBUserManager.class);

    public DBUserManager() {
    }

    public Optional<UserDTO> saveUser(String username, String password) {
        try (PreparedStatement query = ConnectionManager.getInstance().prepare(UserQuery.SAVE_USER.query())) {
            query.setString(1, username);
            query.setString(2, PasswordUtil.hashPassword(password));

            ;
            try (ResultSet generatedKeys = query.executeQuery()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return findById((long) id);
                }
            }
            throw new SQLException("Создание пользователя не удалось");
        } catch (SQLException e) {
            log.error("Ошибка при добавлении пользователя в БД", e);
            return Optional.empty();
        }
    }

    public boolean existsByUsername(String username) {
        try (PreparedStatement query = ConnectionManager.getInstance().prepare(UserQuery.EXISTS_BY_USERNAME.query())) {
            query.setString(1, username);

            try (ResultSet result = query.executeQuery()) {
                return result.next() && result.getInt(1) > 0;
            }

        } catch (SQLException e) {
            log.error("Ошибка при поиске пользователя по имени в БД.");
            return false;
        }
    }

    public Optional<UserDTO> findByUsername(String username) {
        if (username == null) return Optional.empty();
        try (PreparedStatement query = ConnectionManager.getInstance().prepare(UserQuery.FIND_BY_USERNAME.query())) {
            query.setString(1, username);

            try (ResultSet result = query.executeQuery()) {
                return map(result);
            }
        } catch (SQLException e) {
            log.error("Ошибка при поиске пользователя по имени в БД.");
            return Optional.empty();
        }
    }

    public Optional<UserDTO> findById(Long id) {
        if (id == null) return Optional.empty();
        try (PreparedStatement query = ConnectionManager.getInstance().prepare(UserQuery.FIND_BY_ID.query())) {
            query.setLong(1, id);

            try (ResultSet result = query.executeQuery()) {
                return map(result);
            }
        } catch (SQLException e) {
            log.error("Ошибка при поиске пользователя по ID в БД.");
            return Optional.empty();
        }
    }

    private Optional<UserDTO> map(ResultSet result) throws SQLException {
        if (result == null) return Optional.empty();
        return result.next() ?
                Optional.of(
                        new UserDTO(
                                result.getInt("usr_id"),
                                result.getString("username"),
                                result.getString("user_password")
                        )
                )
                : Optional.empty();
    }
}
