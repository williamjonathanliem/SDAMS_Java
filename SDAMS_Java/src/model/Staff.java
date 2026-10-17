package model;

// Middle parent for staff: User -> Staff -> Instructor / FacilitiesOfficer / Administrator
public abstract class Staff extends User {

    public Staff(String id, String username, String password, String name, String phone) {
        super(id, username, password, name, phone); // calls the User constructor
    }
}
