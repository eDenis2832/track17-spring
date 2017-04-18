package track.messenger.messages;

import java.io.Serializable;

/**
 *
 */
public abstract class Message implements Serializable {
    //public Long id;

    public Type type;

    public Long senderId; // -1 server; 0 nobody;
}
