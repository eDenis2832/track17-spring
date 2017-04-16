package track.messenger.store;

import track.messenger.messages.Message;

import java.util.ArrayList;
import java.util.List;

public interface MessageStore {
    /**
     * получаем список ид пользователей заданного чата
     */
    List<Long> getChatsByUserId(Long userId);


    List<Long> getUsersByChatId(Long chatId);

    /**
     * получить информацию о чате
     */
    //Chat getChatById(Long chatId);

    /**
     * Список сообщений из чата
     */
    List<Long> getMessagesFromChat(Long chatId);

    /**
     * Получить информацию о сообщении
     */
    Message getMessageById(Long messageId);

    /**
     * Добавить сообщение в чат
     */
    void addMessage(Long chatId, Message message);

    /**
     * Добавить пользователя к чату
     */
    void addUserToChat(Long userId, Long chatId);

}
