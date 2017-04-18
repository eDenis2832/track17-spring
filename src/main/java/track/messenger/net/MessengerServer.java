package track.messenger.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
import track.messenger.messages.*;
import track.messenger.store.MessageStore;
import track.messenger.store.RamMessageStore;
import track.messenger.store.RamUserStore;
import track.messenger.store.UserStore;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class MessengerServer {

    static Logger log = LoggerFactory.getLogger(MessengerServer.class);

    private int port;

    ServerSocket serverSocket;

    Protocol protocol;

    UserStore userStore;
    MessageStore messageStore;

    public class CommunicatingThread extends Thread {

        User user;

        private ServerCommunicator communicator;

        private Socket socket; //?

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
                        communicator.sendMessage(loginFailedMessage);
                    } else {
                        log.info("User {} is logged in", currentUser.login);
                        LoginSuccessMessage loginSuccessMessage = new LoginSuccessMessage(currentUser);
                        communicator.sendMessage(loginSuccessMessage);
                        user = currentUser;
                        communicator.addUser(user.id); //or setUserOnline
                    }
                    break;

                case MSG_TEXT:
                    TextMessage textMessage = (TextMessage) message;
                    messageStore.addMessage(textMessage.chatId, textMessage);

                    for (Long i: messageStore.getUsersByChatId(textMessage.chatId)) {
                        communicator.sendMessage(i, textMessage);
                    }

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


        @Override
        public void run() {
            int read;
            byte[] buf = new byte[1024 * 60];

            log.info("Starting new thread communicating with {}", socket.getInetAddress());

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Message msg = communicator.getMessage();
                    onMessage(msg);
                } catch (Exception e) {
                    log.error("Some error occured: {}", e);
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }

        public CommunicatingThread(Socket socket) {
            try {
                this.socket = socket; //?
                this.communicator = new SerializingServerCommunicator(socket);
            } catch (Exception e) {
                log.error("Some error when initializing thread: {}", e);
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    private CommunicatingThread newClientThread() throws Exception {
            Socket cs = serverSocket.accept();
            return new CommunicatingThread(cs);

    }

    public static void main(String[] args) {
        Socket cs;
        MessengerServer server;
        try {
            server = new MessengerServer(19000);
        } catch (Exception e) {
            log.error("Error when initalizing: " + e);
            return;
        }

        while (true) {
            try {
                CommunicatingThread comThread = server.newClientThread();
                comThread.start();
            } catch (Exception e) {
                log.error("Some error occured: {}", e);
            }
        }
    }

    public MessengerServer(int port) throws IOException {
        this.port = port;

        serverSocket = new ServerSocket(this.port);

        userStore = new RamUserStore();
        messageStore = new RamMessageStore();
        protocol = new StringProtocol();
    }
}
