package track.messenger.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.messages.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by denis on 18.04.17.
 */
public class SerializingServerCommunicator implements ServerCommunicator {
    private static Map<Long, ObjectOutputStream> UserOuts = new TreeMap<>();

    static Logger log = LoggerFactory.getLogger(MessengerServer.class);

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public void sendMessage(Message message) {
        try {
            log.info("Sending message " + message.toString());
            //out.write(protocol.encode(message));
            //out.flush();

            oos.writeObject(message);
            oos.flush();
        } catch (Exception e) {
            log.error("Error while sending message: {}", e);
        }


    }

    public void sendMessage(Long userId, Message message) {
        ObjectOutputStream toos = UserOuts.get(userId);
        if (toos != null) { //если юзер онлайн
            try {
                log.info("Sending message " + message.toString() + " to user " + userId);
                toos.writeObject(message);
                toos.flush();
            } catch (Exception e) {
                log.error("Error while sending message: {}", e);
            }
        }
    }

    public Message getMessage() throws Exception {
        return (Message) ois.readObject();
    }

    public void addUser(Long userId) {
        UserOuts.put(userId, oos);
    }

    public SerializingServerCommunicator(Socket socket) throws Exception{
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }
}
