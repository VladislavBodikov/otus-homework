package ru.otus.processor;

import ru.otus.model.Message;

/**
 * процессор, который поменяет местами значения field11 и field12
 */
public class SwitchFieldProcessor implements Processor{

    @Override
    public Message process(Message message) {
        return message.toBuilder().field11(message.getField12()).field12(message.getField11()).build();
    }
}
