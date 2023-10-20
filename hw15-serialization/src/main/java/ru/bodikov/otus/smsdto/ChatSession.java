package ru.bodikov.otus.smsdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatSession {

    @JsonProperty("chat_id")
    Long chatId;
    @JsonProperty("chat_identifier")
    String chatIdentifier;
    @JsonProperty("display_name")
    String displayName;
    @JsonProperty("is_deleted")
    Integer isDeleted;
    List<Member> members;
    List<Message> messages;
}
