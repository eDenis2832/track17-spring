package track.messenger.store;

import track.messenger.messages.Message;

import java.util.List;

/**
 * Created by denis on 11.04.17.
 */
public class RamMessageStore implements MessageStore {
    /**
     * получаем список ид пользователей заданного чата
     */
    public List<Long> getChatsByUserId(Long userId) {
        return null;
    }

    /**
     * получить информацию о чате
     */
    //Chat getChatById(Long chatId);

    /**
     * Список сообщений из чата
     */
    public List<Long> getMessagesFromChat(Long chatId) {
        return null;
    }

    /**
     * Получить информацию о сообщении
     */
    public Message getMessageById(Long messageId) {
        return null;
    }

    /**
     * Добавить сообщение в чат
     */
    public void addMessage(Long chatId, Message message) {

    }

    /**
     * Добавить пользователя к чату
     */
    public void addUserToChat(Long userId, Long chatId) {

    }
}
