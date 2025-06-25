package org.server.database.dbmanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.shared.models.Flat;
import org.shared.models.House;
import org.shared.models.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.server.database.ConnectionManager;
import org.server.database.sqlquery.FlatQuery;
import org.shared.models.Coordinates;

public class DBFlatManager {

    private static final Logger log = LoggerFactory.getLogger(DBFlatManager.class);
    private static DBFlatManager instance;
    
    public static DBFlatManager getInstance() {
        return instance == null ? instance = new DBFlatManager() : instance;
    }

    public Set<Flat> findAll() {
        try (PreparedStatement query = ConnectionManager.getInstance().prepare(FlatQuery.FIND_ALL.query())) {
            try (ResultSet result = query.executeQuery()) {
                Set<Flat> flats = new HashSet<>();
                while (result.next()) {
                    map(result).ifPresent(flats::add);
                };
                return flats;
            }
        } catch (SQLException e) {
            log.error("Failed to find all flats", e);
            return Collections.emptySet();
        }
    }

    public Optional<Flat> save(Flat flat) {
        try (PreparedStatement query = ConnectionManager.getInstance().prepare(FlatQuery.SAVE_FLAT.query())) {
            query.setLong(1, flat.getUserId());
            query.setString(2, flat.getName());
            query.setInt(3, flat.getCoordinates().getX());
            query.setInt(4, flat.getCoordinates().getY());
            query.setFloat(5, flat.getArea());
            query.setInt(6, flat.getNumberOfRooms());
            query.setBoolean(7, flat.getBalcony());
            query.setInt(8, flat.getNumberOfBathrooms());
            query.setString(9, flat.getView().name());
            query.setString(10, flat.getHouse().getName());
            query.setLong(11, flat.getHouse().getYear());
            query.setLong(12, flat.getHouse().getNumberOfFlatsOnFloor());

            try (ResultSet generatedKeys = query.executeQuery()) {
                if (generatedKeys.next()) flat.setId(generatedKeys.getInt(1));
                else throw new SQLException("Creating flat failed, no ID obtained.");
            }

            log.info("Квартира сохранена в БД: {}", flat);
            return Optional.of(flat);
        } catch (SQLException e) {
            log.error("Ошибка при сохранении квартиры в БД", e);
            return Optional.empty();
        }
    }

    public Optional<Flat> update(Flat flat, int id) {
        try (PreparedStatement query = ConnectionManager.getInstance().prepare(FlatQuery.UPDATE_FLAT_BY_ID.query())) {
            query.setLong(1, flat.getUserId());
            query.setString(2, flat.getName());
            query.setInt(3, flat.getCoordinates().getX());
            query.setInt(4, flat.getCoordinates().getY());
            query.setFloat(5, flat.getArea());
            query.setInt(6, flat.getNumberOfRooms());
            query.setBoolean(7, flat.getBalcony());
            query.setInt(8, flat.getNumberOfBathrooms());
            query.setString(9, flat.getView().name());
            query.setString(10, flat.getHouse().getName());
            query.setLong(11, flat.getHouse().getYear());
            query.setLong(12, flat.getHouse().getNumberOfFlatsOnFloor());
            query.setInt(13, id);

            try (ResultSet generatedKeys = query.executeQuery()) {
                if (generatedKeys.next()) flat.setId(generatedKeys.getInt(1));
                else throw new SQLException("не удалось обновить элемент по текущему ID.");
            }

            log.info("Квартира обновлена в БД по ID: {}", id);
            return Optional.of(flat);
        } catch (SQLException e) {
            log.error("Ошибка при обновлении квартиры в БД", e);
            return Optional.empty();
        }
    }

    public void removeById(int id) {
        try (PreparedStatement query = ConnectionManager.getInstance().prepare(FlatQuery.REMOVE_BY_ID.query())) {
            query.setInt(1, id);
            query.executeUpdate();
            log.info("Удалена квартира с id = {}", id);
        } catch (SQLException e) {
            log.error("Ошибка при удалении квартиры", e);
        }
    }

    public void removeByUserId(int userId) {
        try (PreparedStatement query = ConnectionManager.getInstance().prepare(FlatQuery.REMOVE_BY_USER_ID.query())) {
            query.setInt(1, userId);
            query.executeUpdate();
            log.info("Удалены все квартиры пользователя с id = {}", userId);
        } catch (SQLException e) {
            log.error("Ошибка при удалении квартиры пользователя", e.getMessage());
        }
    }

    public void removeAll() {
        try (PreparedStatement query = ConnectionManager.getInstance().prepare(FlatQuery.REMOVE_ALL.query())) {
            query.executeUpdate();
            log.info("БД была очищена");
        } catch (SQLException e) {
            log.error("Ошибка при очистке БД", e);
        }
    }

     private Optional<Flat> map(ResultSet result) throws SQLException {
        if (result == null) return Optional.empty();
        return result!=null ?
                Optional.of(
                    new Flat(
                        result.getInt("id"),
                        result.getInt("user_id"),
                        result.getString("name"),
                        new Coordinates(
                            result.getInt("coord_x"),
                            result.getInt("coord_y")
                        ),
                        result.getTimestamp("creation_date").toLocalDateTime(),
                        result.getFloat("area"),
                        result.getInt("number_of_rooms"),
                        result.getBoolean("balcony"),
                        result.getInt("number_of_bathrooms"),
                        View.valueOf(result.getString("flat_view")),
                        new House(
                            result.getString("house_name"),
                            result.getLong("year"),
                            result.getLong("number_of_flats_on_room")
                        )
                    )
                )
                : Optional.empty();
    }
}
