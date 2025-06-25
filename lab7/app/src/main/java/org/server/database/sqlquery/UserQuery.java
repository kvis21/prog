package org.server.database.sqlquery;

public enum UserQuery {
    SAVE_USER("INSERT INTO users (username, user_password) VALUES (?, ?) RETURNING usr_id"),

    EXISTS_BY_USERNAME("SELECT COUNT(*) FROM users WHERE username = ?"),

    FIND_BY_ID("SELECT * FROM users WHERE usr_id = ?"),

    FIND_BY_USERNAME("SELECT * FROM users WHERE username = ?"),

    FIND_ALL("SELECT * FROM users");

    private final String query;

    UserQuery(String query) {
        this.query = query;
    }

    public String query() {
        return query;
    }
}
