package com.scheduleManagement.schedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestData {
    @JsonProperty("postId")
    private Long postId;
    @JsonProperty("userId")
    private Long userId;
}
