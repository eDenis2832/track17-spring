package track.messenger.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import track.messenger.messages.*;
import track.messenger.User;

/**
 * Простейший протокол передачи данных
 */
public class StringProtocol implements Protocol {

    static Logger log = LoggerFactory.getLogger(StringProtocol.class);

    public static final String DELIMITER = ";";

    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        String str = new String(bytes);
        log.info("decoded: {}", str);
        String[] tokens = str.split(DELIMITER);
        Type type = Type.valueOf(tokens[0]);
        switch (type) {
            case MSG_TEXT: //
                TextMessage textMsg = new TextMessage();
                textMsg.type = type;
                textMsg.senderId = parseLong(tokens[1]);

                textMsg.chatId = parseLong(tokens[2]);
                textMsg.senderName = tokens[3];
                textMsg.text = tokens[4];
                return textMsg;
            case MSG_LOGIN:
                LoginMessage loginMsg = new LoginMessage();
                loginMsg.type = type;
                loginMsg.senderId = parseLong(tokens[1]);

                loginMsg.login = tokens[2];
                loginMsg.password = tokens[3];

                return loginMsg;

            case MSG_LOGIN_FAILED:
                LoginFailedMessage loginFailedMessage = new LoginFailedMessage();
                loginFailedMessage.type = type;
                loginFailedMessage.senderId = parseLong(tokens[1]);

                return loginFailedMessage;

            case MSG_LOGIN_SUCCESS:
                LoginSuccessMessage loginSuccessMessage = new LoginSuccessMessage();
                loginSuccessMessage.type = type;
                loginSuccessMessage.senderId = parseLong(tokens[1]);
                loginSuccessMessage.user = new User(parseLong(tokens[2]),
                        tokens[3], tokens[4], tokens[5]);
                return loginSuccessMessage;
            default:
                throw new ProtocolException("Invalid type: " + type);
        }
    }

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        StringBuilder builder = new StringBuilder();
        Type type = msg.type;
        builder.append(type).append(DELIMITER);
        builder.append(String.valueOf(msg.senderId)).append(DELIMITER);
        switch (type) {
            case MSG_TEXT:
                TextMessage sendMessage = (TextMessage) msg;
                builder.append(sendMessage.chatId).append(DELIMITER);
                builder.append(sendMessage.senderName).append(DELIMITER);
                builder.append(sendMessage.text).append(DELIMITER);
                break;
            case MSG_LOGIN:
                LoginMessage loginMsg = (LoginMessage) msg;
                builder.append(loginMsg.login).append(DELIMITER);
                builder.append(loginMsg.password).append(DELIMITER);
                break;
            case MSG_LOGIN_FAILED:

                break;
            case MSG_LOGIN_SUCCESS:
                LoginSuccessMessage loginSuccessMessage = (LoginSuccessMessage) msg;
                builder.append(loginSuccessMessage.user.id).append(DELIMITER);
                builder.append(loginSuccessMessage.user.login).append(DELIMITER);
                builder.append(loginSuccessMessage.user.city).append(DELIMITER);
                builder.append(loginSuccessMessage.user.password).append(DELIMITER);
                break;
            default:
                throw new ProtocolException("Invalid type: " + type);


        }
        log.info("encoded: {}", builder.toString());
        return builder.toString().getBytes();
    }

    private Long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            // who care
        }
        return null;
    }
}