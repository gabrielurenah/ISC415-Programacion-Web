package edu.pucmm.sparkjdbc.services;

import edu.pucmm.sparkjdbc.Models.Article;
import edu.pucmm.sparkjdbc.Models.Comment;
import edu.pucmm.sparkjdbc.Models.Tag;
import edu.pucmm.sparkjdbc.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Articles {

    private static Articles articles;

    public static Articles getInstance() {
        if (articles == null) articles = new Articles();
        return articles;
    }

    public ArrayList<Article> getArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        Connection con = null;
        try {
            String query = "SELECT * FROM ARTICLES ORDER BY article_date DESC";
            con = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Article article = getArticle(rs.getString("uid"));
                if (article.getInformation().length() > 70) {
                    article.setInformation(article.getInformation().substring(0,70) + "...");
                }
                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return articles;
    }

    public Article getArticle(String uid) {
        Article article = null;
        Connection connection = null;
        try {
            String query = "SELECT * FROM ARTICLES WHERE uid = ?";
            connection = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uid);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("uid");
                String title = rs.getString("title");
                String information = rs.getString("body");
                Timestamp date = rs.getTimestamp("article_date");
                User author = Users.getInstance().getUser(rs.getString("author_id"));
                List<Tag> tags = TagsInArticles.getInstance().getArticleTags(id);
                article = new Article(author, date, id, information, tags, title);
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
        return article;
    }

    public boolean createArticle(Article article) {
        boolean created = false;
        Connection con = null;
        try {
            String query = "INSERT INTO articles(uid,title,body,author_id,article_date) VALUES(?,?,?,?,?)";
            con = DB.getInstance().getConnection();
            String uniqueID = UUID.randomUUID().toString();
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, uniqueID);
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getInformation());
            preparedStatement.setString(4, article.getAuthor().getId());
            preparedStatement.setTimestamp(5, article.getDate());

            int row = preparedStatement.executeUpdate();
            created = row > 0;

            ArrayList<Tag> createdTags = Tags.getInstance().getTags();
            for (Tag tag : article.getTags()) {
                if (!edu.pucmm.sparkjdbc.utils.Tags.isTagInArray(tag, createdTags))
                    Tags.getInstance().createTag(tag);
            }

            for (Tag tag : article.getTags()) {
                TagsInArticles.getInstance().createArticleTag(uniqueID, Tags.getInstance().getTag(tag.getTag()).getId());
            }

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

    public boolean updateArticle(Article article) {
        boolean updated = false;
        Connection connection = null;
        try {
            String query = "UPDATE articles SET title=?, body=? WHERE uid =?";
            connection = DB.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getInformation());
            preparedStatement.setString(3, article.getId());
            int row = preparedStatement.executeUpdate();
            updated = row > 0;
            TagsInArticles.getInstance().deleteArticleTags(article.getId());
            ArrayList<Tag> createdTags = Tags.getInstance().getTags();
            for (Tag tag : article.getTags()) {
                if (!edu.pucmm.sparkjdbc.utils.Tags.isTagInArray(tag, createdTags))
                    Tags.getInstance().createTag(tag);
            }
            for (Tag tag : article.getTags()) {
                TagsInArticles.getInstance().createArticleTag(article.getId(), Tags.getInstance().getTag(tag.getTag()).getId());
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
        return updated;
    }

    public boolean deleteArticle(String uid) {
        boolean deleted = false;
        Connection con = null;
        try {
            Article article = getArticle(uid);

            for (Tag tag : article.getTags())
                TagsInArticles.getInstance().deleteArticleTags(uid);

            String query = "DELETE FROM articles WHERE uid = ?";
            con = DB.getInstance().getConnection();

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, uid);

            int row = preparedStatement.executeUpdate();
            deleted = row > 0;

            if (deleted) {
                for (Comment comment : article.getComments())
                    Comments.getInstance().deleteComment(comment.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return deleted;
    }
}
