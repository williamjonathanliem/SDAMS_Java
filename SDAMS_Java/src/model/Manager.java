package model;

public class Manager extends User {

    public Manager(String id, String username, String password, String name, String phone) {
        super(id, username, password, name, phone);
    }

    @Override
    public String getRole() {
        return "Manager";
    }
}
