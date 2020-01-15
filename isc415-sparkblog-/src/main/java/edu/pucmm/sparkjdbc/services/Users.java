package edu.pucmm.sparkjdbc.services;

import edu.pucmm.sparkjdbc.Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Users {
    private static Users instance;

    public static Users getInstance() {
        if (instance == null) instance = new Users();
        return instance;
    }

    public boolean createUser(User user) {
        boolean created = false;
        Connection connection = null;
        try {
            String query = "INSERT INTO USERS(uid, username, name, password, role) VALUES (?,?,?,?,?)";
            connection = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            String uniqueID = UUID.randomUUID().toString();
            preparedStatement.setString(1, uniqueID);
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getUsername());
            int row = preparedStatement.executeUpdate();
            created = row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return created;
    }

    public User getUser(String uid) {
        User user = null;
        Connection connection = null;
        try {
            String query = "SELECT * FROM USERS WHERE uid = ?";
            connection = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uid);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("uid");
                String username = rs.getString("username");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");

                user = new User(id, name, password, role, username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public User validateCredentials(String username, String password){
        User user = null;
        Connection connection = null;
        try {
            String query = "SELECT *FROM USERS WHERE username = ? AND password = ?";
            connection = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("uid");
                String name = rs.getString("name");
                String role = rs.getString("role");
                user = new User(id, name, password, role, username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
