package model;

public class Student extends User {

    public Student(String id, String username, String password, String name, String phone) {
        super(id, username, password, name, phone);
    }

    @Override
    public String getRole() {
        return "Student";
    }
}
