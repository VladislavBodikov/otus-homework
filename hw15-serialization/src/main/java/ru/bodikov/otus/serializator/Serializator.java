package ru.bodikov.otus.serializator;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface Serializator<T> {

    void serialize(T t) throws IOException;

    T deserialize() throws IOException, ClassNotFoundException;
}
