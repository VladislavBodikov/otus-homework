package ru.bodikov.otus.smsdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class Sms {

    @JsonProperty("chat_sessions")
    List<ChatSession> chatSessions;

}
