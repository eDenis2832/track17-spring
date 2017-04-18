package track.messenger.teacher.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.messages.Message;
import track.messenger.net.Communicator;
import track.messenger.net.MessengerServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by denis on 18.04.17.
 */
public class ClientCommunicator implements Communicator {
    private static Map<Long, ObjectOutputStream> UserOuts = new TreeMap<>();

    static Logger log = LoggerFactory.getLogger(MessengerServer.class);

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public void sendMessage(Message message) {
        try {
            log.info("Sending message " + message.toString());

            oos.writeObject(message);
            oos.flush();
        } catch (Exception e) {
            log.error("Error while sending message: {}", e);
        }
    }

    public Message getMessage() throws Exception {
        return (Message) ois.readObject();
    }

    public ClientCommunicator(Socket socket) throws Exception{
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
    }
}
