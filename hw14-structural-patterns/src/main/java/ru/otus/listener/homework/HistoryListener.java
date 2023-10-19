package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class HistoryListener implements Listener, HistoryReader {

    Map<Long, Message> messageHistory = new ConcurrentHashMap<>();


    @Override
    public void onUpdated(Message msg) {
        messageHistory.put(
                msg.getId(),
                msg.toBuilder().field13(ObjectForMessage.from(msg.getField13())).build()
        );
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        if (messageHistory.containsKey(id)){
            return Optional.of(messageHistory.get(id).toBuilder().build());
        }
        return Optional.empty();
    }
}
