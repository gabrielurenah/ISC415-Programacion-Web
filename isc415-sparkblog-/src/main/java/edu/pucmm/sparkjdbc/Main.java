package edu.pucmm.sparkjdbc;

import edu.pucmm.sparkjdbc.Handlers.SQL;
import edu.pucmm.sparkjdbc.Models.Article;
import edu.pucmm.sparkjdbc.Models.Comment;
import edu.pucmm.sparkjdbc.Models.Tag;
import edu.pucmm.sparkjdbc.Models.User;
import edu.pucmm.sparkjdbc.services.*;
import edu.pucmm.sparkjdbc.utils.Filters;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.SQLException;
import java.util.*;

import static spark.Spark.*;

public class Main {
    private static String renderFreemarker(Map<String, Object> model, String templatePath) {
        return new FreeMarkerEngine().render(new ModelAndView(model, templatePath));
    }

    public static void main(String[] args) throws SQLException {
        staticFiles.location("/public");
        SQL.startDb();
        SQL.createTables();
        Filters.applyFilters();
        //--------------------------- 💬START ARTICLE CRUD💬 ----------------------------------------//
        get("/", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("articles", Articles.getInstance().getArticles());
            obj.put("tags", Tags.getInstance().getTags());
            obj.put("user", request.session().attribute("user"));
            return renderFreemarker(obj, "index.ftl");
        });

        get("/new-article", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("user", request.session().attribute("user"));
            return renderFreemarker(obj, "new-article.ftl");
        });

        post("/new-article", (request, response) -> {
            Date todaysDate = new Date();
            java.sql.Timestamp date = new java.sql.Timestamp(todaysDate.getTime());

            User user = request.session().attribute("user");
            String[] tags = request.queryParams("tags").split(",");

            List<Tag> tagList = edu.pucmm.sparkjdbc.utils.Tags.arrayToTagList(tags);
            Article article = new Article(user, date, request.queryParams("article-body"), tagList, request.queryParams("title"));
            Articles.getInstance().createArticle(article);
            response.redirect("/");
            return "";
        });

        get("/articles/:id", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            Article article = Articles.getInstance().getArticle(request.params("id"));
            List<Comment> comments = Comments.getInstance().getComments(request.params("id"));
            obj.put("article", article);
            obj.put("comments", comments);
            obj.put("tags", article.getTags());
            obj.put("user", request.session().attribute("user"));
            return renderFreemarker(obj, "show-article.ftl");
        });

        post("/articles/:id", (request, response) -> {
            Article article = Articles.getInstance().getArticle(request.params("id"));
            article.setTitle(request.queryParams("title"));
            article.setInformation(request.queryParams("article-body"));

            String[] tags = request.queryParams("tags").split(",");
            List<Tag> tagList = edu.pucmm.sparkjdbc.utils.Tags.arrayToTagList(tags);
            article.setTags(tagList);

            Articles.getInstance().updateArticle(article);
            response.redirect("/articles/" + request.params("id"));
            return "";
        });

        get("/articles/:id/edit", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            Article article = Articles.getInstance().getArticle(request.params("id"));
            List<Tag> tags = article.getTags();
            String tagsTxt = "";
            for (Tag tag : tags) {
                tagsTxt += tag.getTag() + ",";
            }
            if (tagsTxt.endsWith(",")) tagsTxt = tagsTxt.substring(0, tagsTxt.length() - 1);
            obj.put("article", article);
            obj.put("tags", tagsTxt);
            obj.put("user", request.session().attribute("user"));
            return renderFreemarker(obj, "edit-article.ftl");
        });

        post("/articles/:id/delete", (request, response) -> {
            Articles.getInstance().deleteArticle(request.params("id"));
            System.out.println("ds");
            response.redirect("/");
            return "";
        });
        //--------------------------- 💬FINISH ARTICLE CRUD💬 ----------------------------------------//

        // ---------------------------- 👩🏽‍💻👨🏽‍💻START USER CRUD👩🏽‍💻👨🏽‍💻 -------------------------------------//
        get("/login", (request, response) -> renderFreemarker(null, "login.ftl"));

        post("/login", (request, response) -> {
            request.queryParams("username");
            User user = Users.getInstance().validateCredentials(request.queryParams("username"), request.queryParams("password"));
            boolean rememberMe = false;
            if(request.queryParams("remember-me") != null) rememberMe = true;

            if(user != null){
                Session session = request.session(true);
                session.attribute("user", user);
                if(rememberMe) response.cookie("MY-COOKIE", user.getId(), 604800);
                response.redirect("/");
            }else response.redirect("/login");
            return "";
        });

        get("/create-user", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("user", request.session().attribute("user"));
            return renderFreemarker(obj, "new-user.ftl");
        });

        post("/create-user", (request, response) -> {
            User user = new User(request.queryParams("username"),request.queryParams("name"), request.queryParams("password"), request.queryParams("role"));
            boolean result = Users.getInstance().createUser(user);
            if(result) response.redirect("/");
            else response.redirect("/create-user");

            return "";
        });

        get("/logout", (request, response) -> {
            request.session().removeAttribute("user");
            response.removeCookie("MY-COOKIE");
            response.redirect("/login");
            return "";
        });
        // ---------------------------- 👩🏽‍💻👨🏽‍💻FINISH USER CRUD👩🏽‍💻👨🏽‍💻 -------------------------------------//
        // ---------------------------- 💻START COMMENTS CRUD💻 --------------------------------------//
        post("/comments/new/:article_id", (request, response) -> {
            Comment comment = new Comment();
            comment.setComment(request.queryParams("comment"));
            User user = request.session().attribute("user");
            Comments.getInstance().createComment(request.params("article_id"), user.getId(), comment);
            response.redirect("/articles/" + request.params("article_id"));
            return "";
        });

        post("/articles/:article_id/comments/:comment_id", (request, response) -> {
            Comments.getInstance().deleteComment(request.params("comment_id"));
            response.redirect("/articles/" + request.params("article_id"));
            return "";
        });
        // ---------------------------- 💻FINISH COMMENTS CRUD💻 --------------------------------------//
    }
}
