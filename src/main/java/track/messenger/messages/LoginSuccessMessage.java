package track.messenger.messages;

import track.messenger.User;

/**
 * Created by denis on 15.04.17.
 */
public class LoginSuccessMessage extends Message {
    public User user;

    public LoginSuccessMessage() {
        type = Type.MSG_LOGIN_SUCCESS;
        senderId = -1L;
    }

    public LoginSuccessMessage(User user) {
        this.type = Type.MSG_LOGIN_SUCCESS;
        this.senderId = -1L;
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginSuccessMessage{" +
                "id=" + user.id +
                ",login=" + user.login +
                ",city" + user.city +
                ",password=" + user.password +
                "}";
    }
}
