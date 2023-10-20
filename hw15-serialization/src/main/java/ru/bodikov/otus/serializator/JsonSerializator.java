package ru.bodikov.otus.serializator;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.bodikov.otus.Result;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonSerializator implements Serializator<Result> {

    private final ObjectMapper om = new ObjectMapper();
    private static final String JSON_SER_FILENAME = "jsonSerialization.json";

    @Override
    public void serialize(Result result) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(JSON_SER_FILENAME))) {
            bw.write(om.writeValueAsString(result));
            bw.flush();
        }
    }

    @Override
    public Result deserialize() throws IOException {
        return om.readValue(new File(JSON_SER_FILENAME), Result.class);
    }
}
