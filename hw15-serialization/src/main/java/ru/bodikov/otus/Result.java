package ru.bodikov.otus;

import lombok.*;
import ru.bodikov.otus.smsdto.ChatSession;
import ru.bodikov.otus.smsdto.Member;
import ru.bodikov.otus.smsdto.Message;
import ru.bodikov.otus.smsdto.Sms;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
public class Result implements Serializable {

    Map<String, List<NewMessageStructure>> belongNumberToMessageMap;

    public Result(Sms sms) {
        Set<NewMessageStructure> result = new TreeSet<>();

        System.out.println("Builded messages:");
        for (ChatSession session : sms.getChatSessions()) {
            for (Member member : session.getMembers()) {
                for (Message message : session.getMessages()) {
                    NewMessageStructure newMessage = NewMessageStructure.builder()
                            .chatIdentifier(session.getChatIdentifier())
                            .last(member.getLast())
                            .belongNumber(message.getBelongNumber())
                            .sendDate(message.getSendDate())
                            .text(message.getText())
                            .build();
                    System.out.println(newMessage);
                    result.add(newMessage);
                }
            }
        }

        this.belongNumberToMessageMap = result.stream().collect(Collectors.groupingBy(NewMessageStructure::getBelongNumber));
    }

}
