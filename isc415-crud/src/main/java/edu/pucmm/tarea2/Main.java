package edu.pucmm.tarea2;

import edu.pucmm.tarea2.Handlers.Controller;
import edu.pucmm.tarea2.Models.Student;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        Controller controller = Controller.getInstance();
        staticFiles.location("/public");

        //CREATE
        get("/register/", (request, response) -> {
            return new FreeMarkerEngine().render(new ModelAndView(null,"registerStudent.ftl"));
        });

        post("/addStudent/", (request, response) -> {
            int id = Integer.parseInt(request.queryParams("matricula"));
            String name = request.queryParams("nombre");
            String lastName = request.queryParams("apellido");
            String phone = request.queryParams("telefono");

            Student student = new Student(id,name,lastName,phone);

            controller.addStudent(student);
            response.redirect("/");
            return "";
        });

        //READ
        get("/", (request,response) -> {
            Map<String,Object> attributes = new HashMap<>();
            attributes.put("students", controller.getMyStudents());

            return new FreeMarkerEngine().render(new ModelAndView(attributes,"allStudents.ftl"));
        });

        get("/:index/",(request, response) -> {
            int i = Integer.parseInt(request.params("index"));
            Map<String,Object> attributes = new HashMap<>();
            attributes.put("student", controller.findStudentById(i));
            attributes.put("index", i);
            return new FreeMarkerEngine().render(new ModelAndView(attributes, "studentInformation.ftl"));
        });

        //UPDATE
        get("/update/:index/", (request, response) -> {
            int i = Integer.parseInt(request.params("index"));
            Map<String,Object> attributes = new HashMap<>();
            attributes.put("student", controller.findStudentById(i));
            attributes.put("index", i);

            return new FreeMarkerEngine().render(new ModelAndView(attributes, "updateStudent.ftl"));
        });

        post("/updateStudent/",(request, response) -> {
            int id = Integer.parseInt(request.queryParams("matricula"));
            String name = request.queryParams("nombre");
            String lastName = request.queryParams("apellido");
            String phone = request.queryParams("telefono");

            int i = Integer.parseInt(request.queryParams("index"));

            controller.updateStudent(i, id, name, lastName, phone);

            response.redirect("/");
            return "";
        });

        //DELETE
        get("/delete/:index/", (request, response) -> {
            int i = Integer.parseInt(request.params("index"));
            controller.removeStudent(i);
            response.redirect("/");
            return "";
        });
    }
     /**
     * Metodo para setear el puerto en Heroku
     * @return
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }
}
