package edu.pucmm.sparkjdbc.services;

import edu.pucmm.sparkjdbc.Models.Comment;
import edu.pucmm.sparkjdbc.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class Comments {

    private static Comments comments;

    public static Comments getInstance() {
        if (comments == null) comments = new Comments();
        return comments;
    }

    public boolean createComment(String uidArticle, String uidAuthor, Comment comment) {
        boolean created = false;
        Connection con = null;
        try {
            String query = "INSERT INTO COMMENTS(uid,body,author_id,article_id) VALUES (?,?,?,?)";
            con = DB.getInstance().getConnection();
            String uniqueID = UUID.randomUUID().toString();
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, uniqueID);
            preparedStatement.setString(2, comment.getComment());
            preparedStatement.setString(3, uidAuthor);
            preparedStatement.setString(4, uidArticle);
            int row = preparedStatement.executeUpdate();
            created = row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return created;
    }

    public ArrayList<Comment> getComments(String articleId) {
        ArrayList<Comment> comments = new ArrayList<>();
        Connection connection = null;
        try {
            String query = "SELECT * FROM COMMENTS WHERE article_id = ?";
            connection = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, articleId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getString("uid"));
                comment.setComment(rs.getString("body"));
                String authorId = rs.getString("author_id");
                User author = Users.getInstance().getUser(authorId);
                comment.setAuthor(author);
                comments.add(comment);
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
        return comments;
    }

    public boolean deleteComment(String uid) {
        boolean deleted = false;
        Connection con = null;
        try {
            String query = "DELETE FROM COMMENTS WHERE uid = ?";
            con = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, uid);
            int row = preparedStatement.executeUpdate();
            deleted = row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deleted;
    }
}
