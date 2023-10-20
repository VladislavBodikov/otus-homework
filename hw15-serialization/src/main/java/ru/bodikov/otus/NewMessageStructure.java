package ru.bodikov.otus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Value
@Builder
@Jacksonized
public class NewMessageStructure implements Comparable<NewMessageStructure>, Serializable {

    @JsonProperty("chat_identifier")
    String chatIdentifier;
    String last;
    @JsonProperty("belong_number")
    String belongNumber;
    @JsonProperty("send_date")
    String sendDate;
    String text;

    @Override
    public int compareTo(NewMessageStructure o) {
        return getSendDate().compareTo(o.getSendDate());
    }
}
