package model;

public class Instructor extends Staff {

    public Instructor(String id, String username, String password, String name, String phone) {
        super(id, username, password, name, phone);
    }

    @Override
    public String getRole() {
        return "Instructor";
    }
}
