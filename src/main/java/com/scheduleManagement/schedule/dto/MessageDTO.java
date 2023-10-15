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
public class MessageDTO {
    private Long userId;
    private Long postId;
    private String nickName;
    private String loginId;
    private String content;
    private Date createdAt;
}
