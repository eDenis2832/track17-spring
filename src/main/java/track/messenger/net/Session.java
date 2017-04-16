package track.messenger.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
//import track.messenger.commands.OnLoginMessage;
import track.messenger.messages.Message;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
public class Session {
    static Logger log = LoggerFactory.getLogger(Session.class);

    /**
     * Пользователь сессии, пока не прошел логин, user == null
     * После логина устанавливается реальный пользователь
     */
    private User user;

    // сокет на клиента
    private Socket socket;

    /**
     * С каждым сокетом связано 2 канала in/out
     */
    private InputStream in;
    private OutputStream out;

    public void send(Message msg) throws ProtocolException, IOException {
        // TODO: Отправить клиенту сообщение


    }

    public void onMessage(Message msg) {
        // TODO: Пришло некое сообщение от клиента, его нужно обработать
        log.info("Message received: {}", msg);
        switch (msg.type) {
            case MSG_LOGIN:
                try {
                    //OnLoginMessage onLoginMessage = new OnLoginMessage();
                    //onLoginMessage.execute(this, msg);
                } catch (Exception e) {
                    log.error("Error when processing login message {}", e);
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
                log.error("Invalid message, ignoring it: {}", msg);
        }
    }

    public void close() {
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
    }

    public InputStream getIn() { return in; }

    public InetAddress getInetAddress() {
        return socket.getInetAddress();
    }

    public Session(Socket socket){
        try {
            this.socket = socket;
            this.in = socket.getInputStream();
            this.out = socket.getOutputStream();
        } catch (Exception e) {
            log.info("Error when initializing sessein: {}", e);
        }
    }
}