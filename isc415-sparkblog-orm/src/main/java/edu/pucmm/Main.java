package edu.pucmm;

import edu.pucmm.Handlers.DB;
import edu.pucmm.Models.*;
import edu.pucmm.services.*;
import edu.pucmm.services.Recommendations;
import edu.pucmm.utils.Filters;
import edu.pucmm.utils.Utils;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static spark.Spark.*;

public class Main {
    private static String renderFreemarker(Map<String, Object> model, String templatePath) {
        return new FreeMarkerEngine().render(new ModelAndView(model, templatePath));
    }
    public static void main(String[] args) throws SQLException {

        staticFiles.location("/public");

        // Starting the server
        DB.startDb();

        // Creating tables
        DB.initDatabase();

        Filters.applyFilters();

        //--------------------------- 💬START ARTICLE CRUD💬 ----------------------------------------//
        get("/", (request, response) -> {

            int pageNumber = request.queryParams("page") != null ? Integer.parseInt(request.queryParams("page")) : 1;
            int articles = Articles.getInstance().findAll().size();
            List<Article> articlesList = Articles.getInstance().lazyFind(pageNumber);
            int totalPages = (int) (Math.ceil((double) articles / 5)) + 1;
            Map<String, Object> obj = new HashMap<>();
            obj.put("articles", articlesList);
            obj.put("tags", Tags.getInstance().findAll());
            obj.put("user", request.session().attribute("user"));
            obj.put("pages", totalPages);

            return renderFreemarker(obj, "index.ftl");
        });

        get("/new-article", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("user", request.session().attribute("user"));
            return renderFreemarker(obj, "new-article.ftl");
        });

        post("/new-article", (request, response) -> {
            String[] tags = request.queryParams("tags").split(",");
            Articles.getInstance().create(new Article(
                    UUID.randomUUID().toString(),
                    request.queryParams("title"),
                    request.queryParams("article-body"),
                    request.session().attribute("user"),
                    new Timestamp(new Date().getTime()),
                    Utils.arrayToTagsSet(tags))
                );
            response.redirect("/");
            return "";
        });

        get("/articles/:id", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            Article article = Articles.getInstance().find(request.params("id"));
            List<Comment> comments = Comments.getInstance().findAllByArticleUid(request.params("id"));
            User user = request.session().attribute("user");
            Recommendation recommendation = Recommendations.getInstance().find(new RecommendationId(article, user));
            Boolean userRecomendation = recommendation != null ? recommendation.getLike() : null;
            int likesTotal = Recommendations.getInstance().numberOfRecommendations(article, true);
            int dislikesTotal = Recommendations.getInstance().numberOfRecommendations(article, false);
            obj.put("article", article);
            obj.put("comments", comments);
            obj.put("tags", article.getTags());
            obj.put("user", request.session().attribute("user"));
            obj.put("like", String.valueOf(userRecomendation));
            obj.put("likesTotal", likesTotal);
            obj.put("dislikesTotal", dislikesTotal);
            return renderFreemarker(obj, "show-article.ftl");
        });

        post("/articles/:id", (request, response) -> {
            Article article = Articles.getInstance().find(request.params("id"));
            article.setTitle(request.queryParams("title"));
            article.setInformation(request.queryParams("article-body"));
            for (Tag tag : article.getTags()) tag.remove(article);
            for (Tag tag : Tags.getInstance().findAll()) {
                if (!tag.hasArticles()) Tags.getInstance().delete(tag.getUid());
            }
            String[] tags = request.queryParams("tags").split(",");
            Set<Tag> tagList = Utils.arrayToTagsSet(tags);
            article.setTags(tagList);
            Articles.getInstance().update(article);
            response.redirect("/articles/" + request.params("id"));
            return "";
        });

        get("/articles/:id/edit", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            Article article = Articles.getInstance().find(request.params("id"));
            String tagsTxt = "";
            for (Tag tag : article.getTags()) tagsTxt += tag.getTag() + ",";
            if (tagsTxt.endsWith(",")) tagsTxt = tagsTxt.substring(0, tagsTxt.length() - 1);
            obj.put("article", article);
            obj.put("tags", tagsTxt);
            obj.put("user", request.session().attribute("user"));
            return renderFreemarker(obj, "edit-article.ftl");
        });

        post("/articles/:id/delete", (request, response) -> {
            Articles.getInstance().delete(request.params("id"));
            response.redirect("/");
            return "";
        });

        post("/articles/:id/like", (request, response) -> {
            Article article = Articles.getInstance().find(request.params("id"));
            User user = request.session().attribute("user");
            Utils.likeDislike(true, article, user);
            response.redirect("/articles/" + request.params("id"));
            return "";
        });

        post("/articles/:id/dislike", (request, response) -> {
            Article article = Articles.getInstance().find(request.params("id"));
            User user = request.session().attribute("user");
            edu.pucmm.Models.Recommendation recommendation = Recommendations.getInstance().find(new RecommendationId(article, user));
            RecommendationId recommendationId = new RecommendationId(article, user);

            Utils.likeDislike(false, article, user);

            response.redirect("/articles/" + request.params("id"));
            return "";
        });

        get("/articles/tag/:id", (request, response) -> {
            Tag tag = Tags.getInstance().find(request.params("id"));
            Map<String, Object> obj = new HashMap<>();
            obj.put("articles", tag.getArticles());
            obj.put("tag", tag);
            return renderFreemarker(obj, "show-tag-articles.ftl");
        });

        //--------------------------- 💬FINISH ARTICLE CRUD💬 ----------------------------------------//
        // ---------------------------- 👩🏽‍💻👨🏽‍💻START USER CRUD👩🏽‍💻👨🏽‍💻 -------------------------------------//
        get("/login", (request, response) -> renderFreemarker(null, "login.ftl"));

        post("/login", (request, response) -> {
            request.queryParams("username");
            User user = Users.getInstance().validateCredentials(request.queryParams("username"), request.queryParams("password"));
            boolean rememberMe = false;
            if (request.queryParams("remember-me") != null) rememberMe = true;
            if (user != null) {
                Session session = request.session(true);
                session.attribute("user", user);
                if (rememberMe) response.cookie("USER", user.getUid(), 604800);
                response.redirect("/");
            } else response.redirect("/login");
            return "";
        });

        get("/create-user", (request, response) -> {
            Map<String, Object> obj = new HashMap<>();
            obj.put("user", request.session().attribute("user"));
            return renderFreemarker(obj, "new-user.ftl");
        });

        post("/create-user", (request, response) -> {
            User user = new User(UUID.randomUUID().toString(), request.queryParams("username"), request.queryParams("name"), request.queryParams("password"), request.queryParams("role"));
            boolean result = Users.getInstance().create(user);
            if (result) response.redirect("/");
            else response.redirect("/create-user");
            return "";
        });

        get("/logout", (request, response) -> {
            request.session().removeAttribute("user");
            response.removeCookie("USER");
            response.redirect("/login");
            return "";
        });
        // ---------------------------- 👩🏽‍💻👨🏽‍💻FINISH USER CRUD👩🏽‍💻👨🏽‍💻 -------------------------------------//
        // ---------------------------- 💻START COMMENTS CRUD💻 --------------------------------------//
        post("/comments/new/:article_id", (request, response) -> {
            User user = Users.getInstance().findByObject(request.session().attribute("user"));
            Article article = Articles.getInstance().find(request.params("article_id"));
            Comment comment = new Comment(UUID.randomUUID().toString(), article, request.queryParams("comment"), user);
            Comments.getInstance().create(comment);
            response.redirect("/articles/" + request.params("article_id"));
            return "";
        });

        post("/articles/:article_id/comments/:comment_id", (request, response) -> {
            Comments.getInstance().delete(request.params("comment_id"));
            response.redirect("/articles/" + request.params("article_id"));
            return "";
        });
        // ---------------------------- 💻FINISH COMMENTS CRUD💻 --------------------------------------//
    }
}
