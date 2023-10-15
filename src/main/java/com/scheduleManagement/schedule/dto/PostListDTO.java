package com.scheduleManagement.schedule.dto;

import com.scheduleManagement.schedule.domain.Tag;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class PostListDTO {
    private Long id;
    private String title;
    private String content;
    private Date registrationDate;
    private String nickname;
    private List<Tag> tags;

}
