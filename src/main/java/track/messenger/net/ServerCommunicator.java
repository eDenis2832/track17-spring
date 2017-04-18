package track.messenger.net;

import track.messenger.messages.Message;

/**
 * Created by denis on 18.04.17.
 */
public interface ServerCommunicator extends Communicator {
    void sendMessage(Long userId, Message message);

    void addUser(Long userId);
}
