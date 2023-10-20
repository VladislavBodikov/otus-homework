package ru.bodikov.otus.serializator;

import ru.bodikov.otus.Result;
import ru.bodikov.otus.ResultProto;
import ru.bodikov.otus.serializator.mapper.ResultMapper;
import ru.bodikov.otus.serializator.mapper.ResultMapperImpl;

import java.io.*;

public class ProtoBufSerializatior implements Serializator<Result>{

    ResultMapper mapper = new ResultMapperImpl();

    private static final String PROTOBUF_SER_FILENAME = "protobufSerialization.bin";

    @Override
    public void serialize(Result result) throws IOException {
        ResultProto.Result resultForSerialize = mapper.map(result);

        try (FileOutputStream fos = new FileOutputStream(PROTOBUF_SER_FILENAME)) {
            resultForSerialize.writeTo(fos);
        }
    }

    @Override
    public Result deserialize() throws IOException, ClassNotFoundException {
        ResultProto.Result deserialized;
        try (FileInputStream fis = new FileInputStream(PROTOBUF_SER_FILENAME)) {
            deserialized = ResultProto.Result.newBuilder().mergeFrom(fis).build();
        }
        return mapper.map(deserialized);
    }

}
