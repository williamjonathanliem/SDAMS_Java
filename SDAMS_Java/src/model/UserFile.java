package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Reads and writes users.txt so every form doesn't need its own file code.
// users.txt line: id,username,password,name,phone,role
public class UserFile {

    private static final String FILE = "src/data/users.txt";

    // Reads every line of users.txt and turns it into the right kind of User
    public static ArrayList<User> readAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            FileReader fr = new FileReader(FILE);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String parts[] = line.split(",");
                if (parts.length < 6) {
                    continue; // skip empty or broken lines
                }
                User user = createUser(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                if (user != null) {
                    users.add(user);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Cannot read users.txt");
        }
        return users;
    }

    // Creates the correct child class for the role.
    // The return type is the parent (User), but the object is the child (inheritance).
    public static User createUser(String id, String username, String password, String name, String phone, String role) {
        if (role.equals("Manager")) {
            return new Manager(id, username, password, name, phone);
        } else if (role.equals("Administrator")) {
            return new Administrator(id, username, password, name, phone);
        } else if (role.equals("Instructor")) {
            return new Instructor(id, username, password, name, phone);
        } else if (role.equals("Student")) {
            return new Student(id, username, password, name, phone);
        } else if (role.equals("FacilitiesOfficer")) {
            return new FacilitiesOfficer(id, username, password, name, phone);
        } else if (role.equals("AttendanceOfficer")) {
            return new AttendanceOfficer(id, username, password, name, phone);
        }
        return null;
    }

    // Replaces the whole file with this list (used for update and delete)
    public static void writeAll(ArrayList<User> users) {
        try {
            FileWriter fw = new FileWriter(FILE); // no "true" = overwrite
            for (User user : users) {
                fw.write(user.toFileString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Cannot write users.txt");
        }
    }

    // Adds one new user
    public static void add(User user) {
        ArrayList<User> users = readAll();
        users.add(user);
        writeAll(users);
    }

    // Makes the next ID for a prefix. If IN001 and IN002 exist, nextId("IN") gives IN003
    public static String nextId(String prefix) {
        int highest = 0;
        for (User user : readAll()) {
            String id = user.getId();
            if (id.startsWith(prefix)) {
                try {
                    int number = Integer.parseInt(id.substring(prefix.length()));
                    if (number > highest) {
                        highest = number;
                    }
                } catch (NumberFormatException e) {
                    // not a number after the prefix, skip it
                }
            }
        }
        return prefix + String.format("%03d", highest + 1); // %03d = 3 digits, e.g. 003
    }

    // True if someone else already uses this username.
    // ignoreId = the user being updated (so they don't clash with themselves)
    public static boolean usernameExists(String username, String ignoreId) {
        for (User user : readAll()) {
            if (user.getUsername().equalsIgnoreCase(username) && !user.getId().equals(ignoreId)) {
                return true;
            }
        }
        return false;
    }
}
