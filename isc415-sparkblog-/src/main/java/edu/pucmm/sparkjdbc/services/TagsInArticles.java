package edu.pucmm.sparkjdbc.services;

import edu.pucmm.sparkjdbc.Models.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagsInArticles {
    private static TagsInArticles tagsInArticles;

    public static TagsInArticles getInstance() {
        if (tagsInArticles == null) tagsInArticles = new TagsInArticles();
        return tagsInArticles;
    }

    public List<Tag> getArticleTags(String uidArticle) {
        List<Tag> articleTags = new ArrayList<>();
        Connection connection = null;
        try {
            String query = "SELECT * FROM ARTICLESTAGS WHERE article_id = ?";
            connection = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uidArticle);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Tag tag = Tags.getInstance().getTagByUid(rs.getString("tag_id"));
                articleTags.add(tag);
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

        return articleTags;
    }

    public boolean createArticleTag(String uidArticle, String uidTag) {
        boolean created = false;
        Connection connection = null;
        try {
            String query = "INSERT INTO ARTICLESTAGS(article_id,tag_id) VALUES(?,?)";
            connection = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uidArticle);
            preparedStatement.setString(2, uidTag);
            int row = preparedStatement.executeUpdate();
            created = row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if (connection != null) connection.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return created;
    }

    public boolean deleteArticleTags(String uidArticle) {
        boolean deleted = false;
        Connection connection = null;
        try {
            String query = "DELETE FROM ARTICLESTAGS WHERE article_id=?";
            connection = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uidArticle);
            int row = preparedStatement.executeUpdate();
            deleted = row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            }
            catch (SQLException e)  {
                e.printStackTrace();
            }
        }

        return deleted;
    }
}
