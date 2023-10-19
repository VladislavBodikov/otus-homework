package ru.otus.processor;

import ru.otus.model.Message;

/**
 * Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
 * Секунда должна определяьться во время выполнения.
 * Тест - важная часть задания
 */
public class EvenSecondsExceptionProcessor implements Processor{
    @Override
    public Message process(Message message) {
        long seconds = System.currentTimeMillis() / 1000;
        if (seconds % 2 == 0){
            throw new RuntimeException("Even seconds exception");
        }
        return message;
    }
}
