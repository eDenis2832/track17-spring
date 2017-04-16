package track.messenger.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.rmi.runtime.Log;
import track.messenger.messages.LoginFailedMessage;
import track.messenger.messages.LoginMessage;
import track.messenger.messages.LoginSuccessMessage;
import track.messenger.messages.Message;
import track.messenger.store.MessageStore;
import track.messenger.store.UserStore;
import track.messenger.User;

/**
 * Created by denis on 11.04.17.
 */
public class CommunicatingThread extends Thread {

    static Logger log = LoggerFactory.getLogger(CommunicatingThread.class);

    User user;

    private Protocol protocol;

    private UserStore userStore;
    private MessageStore messageStore;

    private Socket socket;

    private InputStream in;
    private OutputStream out;


    void onMessage(Message message) {
        log.info("Message received: {}", message);
        switch (message.type) {
            case MSG_LOGIN:
                    LoginMessage loginMessage = (LoginMessage) message;
                    User currentUser = userStore.getUser(loginMessage.login, loginMessage.password);
                    if (currentUser == null) {
                        log.info("Logging in with login {} and password {} failed",
                                loginMessage.login, loginMessage.password);
                        LoginFailedMessage loginFailedMessage = new LoginFailedMessage();
                        sendMessage(loginFailedMessage);
                    } else {
                        log.info("User {} is logged in", currentUser.login);
                        LoginSuccessMessage loginSuccessMessage = new LoginSuccessMessage(currentUser);
                        sendMessage(loginSuccessMessage);
                        user = currentUser;
                    }
                break;

            case MSG_TEXT:
                break;

            case MSG_INFO:
                break;

            case MSG_CHAT_LIST:
                break;

            case MSG_CHAT_CREATE:
                break;

            case MSG_CHAT_HIST:
                break;

            default:
                log.error("Invalid message, ignoring it: {}", message);
        }
    }

    void sendMessage(Message message) {
        try {
            log.info("Sending message " + message.toString());
            out.write(protocol.encode(message));
            out.flush();
        } catch (Exception e) {
            log.error("Error while sending message: {}", e);
        }

    }

    @Override
    public void run() {
        int read;
        byte[] buf = new byte[1024 * 60];

        log.info("Starting new thread communicating with {}", socket.getInetAddress());

        while (!Thread.currentThread().isInterrupted()) {
            try {
                read = in.read(buf);
                Message msg = protocol.decode(Arrays.copyOf(buf, read));
                onMessage(msg);
            } catch (Exception e) {
                log.error("Some error occured: {}", e);
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public CommunicatingThread(Socket socket, Protocol protocol,
                               UserStore userStore, MessageStore messageStore) {
        try {
            this.socket = socket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
            this.protocol = protocol;
            this.userStore = userStore;
            this.messageStore = messageStore;
        } catch (Exception e) {
            log.error("Some error when initializing thread: {}", e);
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
