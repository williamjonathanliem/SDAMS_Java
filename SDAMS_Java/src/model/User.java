package model;

// Parent class of every user. "abstract" = you cannot do new User(...),
// you must create a real role instead, e.g. new Student(...).
public abstract class User {

    // private = only this class can touch these directly
    private String id;
    private String username;
    private String password;
    private String name;
    private String phone;

    public User(String id, String username, String password, String name, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Every role must write its own version of this
    public abstract String getRole();

    // Turns the user into one line for users.txt
    // e.g. ST001,student,123,Default Student,0120000004,Student
    public String toFileString() {
        return id + "," + username + "," + password + "," + name + "," + phone + "," + getRole();
    }
}
