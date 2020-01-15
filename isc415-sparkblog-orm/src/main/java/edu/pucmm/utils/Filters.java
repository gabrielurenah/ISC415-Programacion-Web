package edu.pucmm.utils;

import edu.pucmm.Models.User;
import edu.pucmm.services.Users;
import spark.Request;
import spark.Response;
import spark.Session;

import static spark.Spark.before;

public class Filters {

    static private void redirect(Request req, Response res) {
        User user = req.session().attribute("user");
        if (user == null) {
            res.redirect("/");
        }
    }
    static public void applyFilters() {
        before((request, response) -> {
            User user = request.session().attribute("user");
            if (request.cookie("USER") != null && user == null) {
                String userUID = request.cookie("USER");
                user = Users.getInstance().find(userUID);
                Session session = request.session(true);
                session.attribute("user", user);
            }
        });

        before("/new-article", Filters::redirect);

        before("/articles/:id/edit", Filters::redirect);

        before("/articles/:id/delete", Filters::redirect);

        before("/comments/*", Filters::redirect);

        before("/login", (request, response) -> {
            User user = Users.getInstance().findByObject(request.session().attribute("user"));
            if (user != null) {
                response.redirect("/");
            }
        });

        before("/create-user", (request, response) -> {
            User user = Users.getInstance().findByObject(request.session().attribute("user"));
            if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
                response.redirect("/");
            }
        });

    }
}
