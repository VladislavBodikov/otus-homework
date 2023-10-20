package ru.bodikov.otus.serializator;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import ru.bodikov.otus.Result;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class YmlSerializator implements Serializator<Result> {

    private final YAMLMapper yamlMapper = new YAMLMapper();
    private static final String YML_SER_FILENAME = "ymlSerialization.yml";

    @Override
    public void serialize(Result result) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(YML_SER_FILENAME))) {
            bw.write(yamlMapper.writeValueAsString(result));
            bw.flush();
        }
    }

    @Override
    public Result deserialize() throws IOException {
        return yamlMapper.readValue(new File(YML_SER_FILENAME), Result.class);
    }
}
