package com.banking.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/** Creates MySQL connections using DriverManager. Settings come from db.properties. */
public final class DBConnection {

    private static final Properties PROPS = new Properties();

    static {
        // 1) classpath (Maven copies resources/ onto the classpath)
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                PROPS.load(in);
            } else {
                // 2) fall back to the project folder when run without Maven
                try (InputStream file = new FileInputStream("resources/db.properties")) {
                    PROPS.load(file);
                }
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Cannot read db.properties: " + e.getMessage());
        }
    }

    private DBConnection() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPS.getProperty("db.url"),
                PROPS.getProperty("db.user"),
                PROPS.getProperty("db.password"));
    }

    /** Startup check using a plain Statement. */
    public static void testConnection() throws SQLException {
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT 1")) {
            rs.next();
        }
    }
}
