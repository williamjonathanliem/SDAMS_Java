package model;

public class AttendanceOfficer extends User {

    public AttendanceOfficer(String id, String username, String password, String name, String phone) {
        super(id, username, password, name, phone);
    }

    @Override
    public String getRole() {
        return "AttendanceOfficer";
    }
}
