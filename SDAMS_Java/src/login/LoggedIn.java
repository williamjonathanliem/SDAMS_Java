package login;

// Remembers who is logged in, so every dashboard can show their name.
public class LoggedIn {
    public static String userId;
    public static String username;
    public static String name;
    public static String role;

    public static void setUserId(String userId) {
        LoggedIn.userId=userId;
    }
    public static String getUserId(){
        return userId;
    }

    public static void setUsername(String username) {
        LoggedIn.username=username;
    }
    public static String getUsername(){
        return username;
    }

    public static void setName(String name) {
        LoggedIn.name=name;
    }
    public static String getName(){
        return name;
    }

    public static void setRole(String role) {
        LoggedIn.role=role;
    }
    public static String getRole(){
        return role;
    }

    // Used by the Logout button
    public static void clear(){
        userId=null;
        username=null;
        name=null;
        role=null;
    }
}
