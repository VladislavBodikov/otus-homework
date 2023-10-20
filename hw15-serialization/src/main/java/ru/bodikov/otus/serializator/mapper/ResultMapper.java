package ru.bodikov.otus.serializator.mapper;

import org.mapstruct.Mapper;
import ru.bodikov.otus.NewMessageStructure;
import ru.bodikov.otus.Result;
import ru.bodikov.otus.ResultProto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface ResultMapper {

    default Result map(ResultProto.Result proto) {
        Map<String, List<NewMessageStructure>> collect = proto.getBelongNumberToMessageMapMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> map(entry.getValue())));

        Result result = new Result();
        result.setBelongNumberToMessageMap(collect);
        return result;
    }

    default ResultProto.Result map(Result result) {
        Map<String, ResultProto.ListOfSmsMessages> collect = result.getBelongNumberToMessageMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> map(entry.getValue())));
        return ResultProto.Result.newBuilder().putAllBelongNumberToMessageMap(collect).build();
    }


    default List<NewMessageStructure> map(ResultProto.ListOfSmsMessages protoList) {
        return protoList.getMessageList().stream().map(this::map).toList();
    }

    default ResultProto.ListOfSmsMessages map(List<NewMessageStructure> messages) {
        return ResultProto.ListOfSmsMessages.newBuilder()
                .addAllMessage(messages.stream().map(this::map).toList())
                .build();
    }

    NewMessageStructure map(ResultProto.NewMessageStructure messageStructure);

    ResultProto.NewMessageStructure map(NewMessageStructure messageStructure);
}
