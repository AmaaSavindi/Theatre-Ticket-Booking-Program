import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;

    // Constructor for initialise the objects
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Setter methods

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }




}
