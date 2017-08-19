package friendlymanager;

/**
 * Created by Chris on 18.08.2017.
 */
public class User {
    private String username=null, password=null;
    private String [] schedule;

    public User(String username, String password, String[] schedule) {
        this.username = username;
        this.password = password;
        this.schedule=schedule;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String[] getSchedule() {
        return schedule;
    }
}
