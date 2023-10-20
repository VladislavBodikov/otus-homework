package ru.bodikov.otus.serializator;

import ru.bodikov.otus.Result;

import java.io.*;

public class DefaultBinSerializator implements Serializator<Result> {

    private static final String DEFAULT_BIN_SER_FILENAME = "defaultJavaSerialization.bin";

    @Override
    public void serialize(Result result) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(DEFAULT_BIN_SER_FILENAME))) {
            objectOutputStream.writeObject(result);
        }
    }

    @Override
    public Result deserialize() throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("defaultJavaSerialization.bin"))) {
            return (Result) objectInputStream.readObject();
        }
    }
}
