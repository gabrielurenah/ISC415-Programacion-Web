package edu.pucmm.tarea2.Handlers;
import edu.pucmm.tarea2.Models.Student;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static Controller controller;
    private List<Student> myStudents;

    private Controller(){
        super();
        this.myStudents = new ArrayList<>();
    }

    public static Controller getInstance() {
        if(controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public Student findStudentById(int i) {
        return myStudents.get(i);
    }

    public void addStudent(Student student) {
        myStudents.add(student);
    }

    public void removeStudent(int i) {
        myStudents.remove(i);
    }

    public List<Student> getMyStudents() {
        return myStudents;
    }

    public void setMyStudents(List<Student> myStudents) {
        this.myStudents = myStudents;
    }

    public void updateStudent(int i, int id, String name, String lastName, String phone) {
        Student student = controller.findStudentById(i);

        student.setId(id);
        student.setName(name);
        student.setLastName(lastName);
        student.setPhone(phone);

        controller.getMyStudents().set(i,student);
    }
}
