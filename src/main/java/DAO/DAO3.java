package DAO;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO3 {
    private static final Path DATABASE_PATH = Path.of("src", "main", "core", "libmandb.db")
            .toAbsolutePath().normalize();
    private static final String JDBC_URL = "jdbc:sqlite:" + DATABASE_PATH;

    public static Connection con;

    static {
        // In Tomcat, DriverManager does not auto-discover drivers in WEB-INF/lib ->
        // register explicitly.
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("SQLite JDBC driver not found on classpath", e);
        }
    }

    public DAO3() {
        try {
            if (con == null || con.isClosed()) {
                if (!Files.isRegularFile(DATABASE_PATH)) {
                    throw new IllegalStateException("Database file not found: " + DATABASE_PATH);
                }
                con = DriverManager.getConnection(JDBC_URL);
                try (Statement statement = con.createStatement()) {
                    statement.execute("PRAGMA foreign_keys = ON");
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not connect to database: " + DATABASE_PATH, e);
        }
    }
}
