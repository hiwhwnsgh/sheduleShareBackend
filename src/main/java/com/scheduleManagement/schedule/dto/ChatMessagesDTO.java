package com.scheduleManagement.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessagesDTO {
    private Long userId;
    private String nickName;
    private String content;
    private String loginId;
    private Date createdAt;
}
