package track.messenger.teacher.client;


import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import track.messenger.User;
import track.messenger.messages.*;
import track.messenger.messages.Type.*;
import track.messenger.net.Communicator;
import track.messenger.net.Protocol;
import track.messenger.net.ProtocolException;
import track.messenger.net.StringProtocol;

import static java.lang.Long.parseLong;


/**
 *
 */
public class MessengerClient {


    /**
     * Механизм логирования позволяет более гибко управлять записью данных в лог (консоль, файл и тд)
     * */
    static Logger log = LoggerFactory.getLogger(MessengerClient.class);

    /**
     * Протокол, хост и порт инициализируются из конфига
     *
     * */
    private Protocol protocol;
    private int port;
    private String host;

    /**
     * С каждым сокетом связано 2 канала in/out
     */
    private InputStream in;
    private OutputStream out;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    Communicator clientCommunicator;

    Thread socketListenerThread;


    User user;

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void initSocket() throws Exception {
        Socket socket = new Socket(host, port);
        //in = socket.getInputStream();
        //out = socket.getOutputStream();

        clientCommunicator = new ClientCommunicator(socket);
        //ois = new ObjectInputStream(socket.getInputStream());
        //oos = new ObjectOutputStream(socket.getOutputStream());



        /*
      Тред "слушает" сокет на наличие входящих сообщений от сервера
     */
        socketListenerThread = new Thread(() -> {
            final byte[] buf = new byte[1024 * 64];
            log.info("Starting listener thread...");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Здесь поток блокируется на ожидании данных
                    /*
                    int read = in.read(buf);
                    if (read > 0) {

                        // По сети передается поток байт, его нужно раскодировать с помощью протокола
                        Message msg = protocol.decode(Arrays.copyOf(buf, read));
                        onMessage(msg);
                    }
                    */

                    //Message msg = (Message) ois.readObject();

                    Message msg = clientCommunicator.getMessage();
                    onMessage(msg);

                } catch (Exception e) {
                    log.error("Failed to process connection: {}", e);
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });

        socketListenerThread.start();
    }

    /**
     * Реагируем на входящее сообщение
     */
    public void onMessage(Message msg) {

        log.info("Message received: {}", msg);
        switch (msg.type) {
            case MSG_LOGIN_SUCCESS:
                LoginSuccessMessage loginSuccessMessage = (LoginSuccessMessage) msg;
                user = loginSuccessMessage.user;
                System.out.print("Logged succesful! You are " + user);
                break;
            case MSG_TEXT:
                TextMessage textMessage = (TextMessage) msg;
                System.out.print("Message from chat " + textMessage.chatId +
                ": " + textMessage.senderName + ": " + textMessage.text);
                break;
            default:
                log.error("Invalid message, ignoring it: {}", msg);
        }
    }

    /**
     * Обрабатывает входящую строку, полученную с консоли
     * Формат строки можно посмотреть в вики проекта
     */
    public void processInput(String line) throws IOException, ProtocolException {
        String[] tokens = line.split(" ");
        log.info("Tokens: {}", Arrays.toString(tokens));
        String cmdType = tokens[0];
        switch (cmdType) {
            case "/login":
                // TODO: реализация

                if (tokens.length == 3) {
                    LoginMessage loginMessage = new LoginMessage();
                    loginMessage.login = tokens[1];
                    loginMessage.password = tokens[2];
                    clientCommunicator.sendMessage(loginMessage);
                } else if (tokens.length == 1) {
                    LoginMessage loginMessage = new LoginMessage();
                    loginMessage.login = "nobody";
                    loginMessage.password = "nobody";
                    clientCommunicator.sendMessage(loginMessage);
                } else {
                    System.out.println("Incorrect input! Please, try again.");
                }

                break;
            case "/help":
                // TODO: реализация
                break;
            case "/text":
                // FIXME: пример реализации для простого текстового сообщения
                TextMessage sendMessage = new TextMessage();
                sendMessage.type = Type.MSG_TEXT;
                sendMessage.senderId = user.id;

                sendMessage.senderName = user.login;

                sendMessage.chatId = parseLong(tokens[1]);


                StringBuilder strB = new StringBuilder();
                for (int i = 2; i < tokens.length; i++) {
                    strB.append(tokens[i]);
                    if (i != tokens.length - 1) {
                        strB.append(" ");
                    }
                }

                sendMessage.text = strB.toString();
                clientCommunicator.sendMessage(sendMessage);
                break;
            // TODO: implement another types from wiki
            case "/q":
                socketListenerThread.interrupt();
                return;
            default:
                log.error("Invalid input: " + line);
        }
    }

    /**
     * Отправка сообщения в сокет клиент -> сервер
     */
    /*
    public void send(Message msg) throws IOException, ProtocolException {
        log.info(msg.toString());
        //out.write(protocol.encode(msg));
        //out.flush(); // принудительно проталкиваем буфер с данными

        oos.writeObject(msg);
        oos.flush();
    }
*/

    public static void main(String[] args) throws Exception {

        MessengerClient client = new MessengerClient();
        client.setHost("localhost");
        client.setPort(19000);
        client.setProtocol(new StringProtocol());


        try {
            client.initSocket();
            // Цикл чтения с консоли
            Scanner scanner = new Scanner(System.in);
            System.out.println("$");
            while (true) {
                String input = scanner.nextLine();

                try {
                    client.processInput(input);
                } catch (ProtocolException | IOException e) {
                    log.error("Failed to process user input", e);
                }
            }
        } catch (Exception e) {
            log.error("Application failed.", e);
        } finally {

        }
    }

}