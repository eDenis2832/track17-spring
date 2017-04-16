package track.messenger.messages;

/**
 * Created by denis on 15.04.17.
 */
public class LoginFailedMessage extends Message {

    @Override
    public String toString() {
        return "LoginFailedMessage{}";
    }

    public LoginFailedMessage() {
        type = Type.MSG_LOGIN_FAILED;
        senderId = -1L;
    }
}
