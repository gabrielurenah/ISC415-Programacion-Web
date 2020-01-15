package edu.pucmm.sparkjdbc.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static DB db;
    private String URL = "jdbc:h2:tcp://localhost/~/spark-jdbc";

    private DB() {
        registerDriver();
    }

    public static DB getInstance() {
        if (db == null) db = new DB();
        return db;
    }

    private void registerDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
