package track.messenger.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.messages.Message;
import track.messenger.store.MessageStore;
import track.messenger.store.RamMessageStore;
import track.messenger.store.RamUserStore;
import track.messenger.store.UserStore;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


public class MessengerServer {

    static Logger log = LoggerFactory.getLogger(MessengerServer.class);

    private int port;

    ServerSocket serverSocket;

    private InputStream in;
    private OutputStream out;

    UserStore userStore;
    MessageStore messageStore;

    private CommunicatingThread newClientThread() throws Exception {
            Socket cs = serverSocket.accept();
            return new CommunicatingThread(cs, new StringProtocol(), userStore, messageStore);

    }

    public static void main(String[] args) {
        Socket cs;

        MessengerServer server = new MessengerServer(19000);


        while (true) {
            try {
                CommunicatingThread comThread = server.newClientThread();
                comThread.start();
            } catch (Exception e) {
                log.error("Some error occured: {}", e);
            }
        }
    }

    public MessengerServer(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (Exception e) {
            log.error("Error when creating ServerSocket: {}", e);
        }

        userStore = new RamUserStore();
        messageStore = new RamMessageStore();
    }
}
