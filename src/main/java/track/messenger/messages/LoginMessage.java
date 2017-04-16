package track.messenger.messages;

/**
 * Created by denis on 11.04.17.
 */
public class LoginMessage extends Message {
    public String login;
    public String password;


    public LoginMessage(){
        this.type = Type.MSG_LOGIN;
        this.senderId = 0L;
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "login='" + login + '\'' + "," +
                "password='" + password + '\'' +
                '}';
    }
}
