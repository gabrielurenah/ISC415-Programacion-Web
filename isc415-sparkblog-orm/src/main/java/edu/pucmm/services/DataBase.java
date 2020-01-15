package edu.pucmm.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static DataBase instance;

    private DataBase() {
        registerDriver();
    }

    public static DataBase getInstance() {
        return instance == null ? new DataBase() : instance;
    }

    private void registerDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        Connection con = null;
        String URL = "jdbc:h2:tcp://localhost/~/spark-jdbc";
        con = DriverManager.getConnection(URL, "sa", "");
        return con;
    }
}
