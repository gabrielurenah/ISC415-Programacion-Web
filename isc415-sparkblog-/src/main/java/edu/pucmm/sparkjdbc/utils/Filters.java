package edu.pucmm.sparkjdbc.utils;

import edu.pucmm.sparkjdbc.Models.User;
import edu.pucmm.sparkjdbc.services.Users;
import spark.Session;

import static spark.Spark.before;

public class Filters {
    public static void applyFilters() {
        before((request, response) -> {
            User user = request.session().attribute("user");
            if(request.cookie("MY-COOKIE") != null && user == null){
                String userID = request.cookie("MY-COOKIE");
                user = Users.getInstance().getUser(userID);
                Session session = request.session(true);
                session.attribute("user", user);
            }
        });

        before("/new-article", (request, response) -> {
            User user = request.session().attribute("user");
            if(user == null){
                response.redirect("/");
            }
        });

        before("/articles/:id/edit", (request, response) -> {
            User user = request.session().attribute("user");
            if(user == null){
                response.redirect("/");
            }
        });

        before("/login", (request, response) -> {
            User user = request.session().attribute("user");
            if(user != null){
                response.redirect("/");
            }
        });

        before("/create-user", (request, response) -> {
            User user = request.session().attribute("user");

            if(user == null || !user.getRole().equalsIgnoreCase("admin")){
                response.redirect("/");
            }

        });

        before("/comments/*", (request, response) -> {
            User user = request.session().attribute("user");
            if(user == null){
                response.redirect("/");
            }
        });

        before("/articles/:id/delete", (request, response) -> {
            User user = request.session().attribute("user");
            System.out.println(user);
            if(user == null){
                response.redirect("/");
            }
        });

        before("/articles/:id/delete", (request, response) -> {
            User user = request.session().attribute("user");
            System.out.println(user);
            if(user == null){
                response.redirect("/");
            }
        });
    }
}
