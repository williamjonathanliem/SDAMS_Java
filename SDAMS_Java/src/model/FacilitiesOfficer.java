package model;

public class FacilitiesOfficer extends Staff {

    public FacilitiesOfficer(String id, String username, String password, String name, String phone) {
        super(id, username, password, name, phone);
    }

    @Override
    public String getRole() {
        return "FacilitiesOfficer";
    }
}
