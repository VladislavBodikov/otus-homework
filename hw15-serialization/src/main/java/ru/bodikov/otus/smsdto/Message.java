package ru.bodikov.otus.smsdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty("ROWID")
    Long rowId;
    String attributedBody;
    @JsonProperty("belong_number")
    String belongNumber;
    Long date;
    @JsonProperty("date_read")
    Long dateRead;
    String guid;
    @JsonProperty("handle_id")
    Long handleId;
    @JsonProperty("has_dd_results")
    Integer hasDdResults;
    @JsonProperty("is_deleted")
    Integer isDeleted;
    @JsonProperty("is_from_me")
    Integer isFromMe;
    @JsonProperty("send_date")
    String sendDate;
    @JsonProperty("send_status")
    Integer sendStatus;
    String service;
    String text;
}
