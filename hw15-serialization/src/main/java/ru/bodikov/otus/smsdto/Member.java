package ru.bodikov.otus.smsdto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member {

    String first;
    @JsonProperty("handle_id")
    Long handleId;
    @JsonProperty("image_path")
    String imagePath;
    String last;
    String middle;
    @JsonProperty("phone_number")
    String phoneNumber;
    String service;
    @JsonProperty("thumb_path")
    String thumbPath;
}
