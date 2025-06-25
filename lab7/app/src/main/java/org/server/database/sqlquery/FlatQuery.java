package org.server.database.sqlquery;

public enum FlatQuery {
    FIND_ALL("SELECT * FROM flats"),

    SAVE_FLAT("INSERT INTO flats (user_id, name, coord_x, coord_y, area, number_of_rooms, balcony, number_of_bathrooms, flat_view, house_name, year, number_of_flats_on_room) "
           + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id"),
    
    UPDATE_FLAT_BY_ID("UPDATE flats SET user_id = ?, name = ?, coord_x = ?, coord_y = ?, area = ?, number_of_rooms = ?, balcony = ?, number_of_bathrooms = ?, flat_view = ?, house_name = ?, year = ?, number_of_flats_on_room = ? WHERE id = ?"),

    REMOVE_BY_ID("DELETE FROM flats WHERE id = ?"),

    REMOVE_BY_USER_ID("DELETE FROM flats WHERE user_id = ?"),

    REMOVE_ALL("DELETE FROM flats");

    private final String query;

    FlatQuery(String query) {
        this.query = query;
    }

    public String query() {
        return query;
    }
}
