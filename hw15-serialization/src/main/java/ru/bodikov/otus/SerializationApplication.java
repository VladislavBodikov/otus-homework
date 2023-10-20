package ru.bodikov.otus;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.bodikov.otus.serializator.*;
import ru.bodikov.otus.smsdto.Sms;

import java.io.File;
import java.io.IOException;

public class SerializationApplication {


    //TODO Прочитать файл sms.json
    // 1. Десериализовать файл в java class
    // 2. Создать новую структуру: список из полей
    // <chat_sessions.chat_identifier> - <chat_sessions.members.last> - <chat_sessions.messages.belong_number> - <chat_sessions.messages.send_date> - <chat_sessions.messages.text> с группировкой по полю <chat_sessions.messages.belong_number> и сортировкой от более старых сообщений к более новым
    // 3. Данные дублироваться не должны (файл должен получиться как можно меньше)
    // 4. Сериализовать полученные данные и записать их в файл (текстовой или бинарный)
    // 5. Десериализовать полученные данный и вывести результат на консоль
    // 6. Обязательно (текстовой): json, xml, csv, yml (можно использовать любой вреймворк)
    // 7. Дополнительно (бинарный): PrtotoBuf, Java Serialization


    static ObjectMapper om = new ObjectMapper();
    static Serializator<Result> jsonSerializator = new JsonSerializator();
    static Serializator<Result> ymlSerializator = new YmlSerializator();
    static Serializator<Result> defaultBinSerializator = new DefaultBinSerializator();
    static Serializator<Result> protobufSerializator = new ProtoBufSerializatior();


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Sms sms = om.readValue(new File("hw15-serialization/src/main/resources/data/sms.json"), Sms.class);
        System.out.println();
        Result result = new Result(sms);
        System.out.println("\nResult: ");
        System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(result));


        jsonSerializator.serialize(result);
        Result resultJson = jsonSerializator.deserialize();
        System.out.println("\nResult after jsonSerialization: ");
        System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(resultJson));

        ymlSerializator.serialize(result);
        Result resultYml = ymlSerializator.deserialize();
        System.out.println("\nResult after ymlSerializator: ");
        System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(resultYml));


        defaultBinSerializator.serialize(result);
        Result resultAfterBinSerialize = defaultBinSerializator.deserialize();
        System.out.println("\nResult after default bin deserialization: ");
        System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(resultAfterBinSerialize));


        protobufSerializator.serialize(result);
        Result resultAfterProtobufBinSerialize = protobufSerializator.deserialize();
        System.out.println("\nResult after protobuf bin deserialization: ");
        System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(resultAfterProtobufBinSerialize));
    }


}
