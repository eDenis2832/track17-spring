package track.messenger;

/**
 *
 */
public class User {
    public long id;

    public String login;

    public String city;

    public String password;

    public User(long id, String login, String city, String password) {
        this.id = id;
        this.login = login;
        this.city = city;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{login=" + login + "," +
                "city=" + city + "," +
                "password=" + password +
                "}";
    }
}