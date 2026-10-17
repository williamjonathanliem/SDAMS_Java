package model;

public class Administrator extends Staff {

    public Administrator(String id, String username, String password, String name, String phone) {
        super(id, username, password, name, phone);
    }

    @Override
    public String getRole() {
        return "Administrator";
    }
}
