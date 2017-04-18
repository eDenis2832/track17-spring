package track.messenger.net;

import track.messenger.messages.Message;

/**
 * Created by denis on 18.04.17.
 */
public interface Communicator {

    void sendMessage(Message message);



    Message getMessage() throws Exception;

}
