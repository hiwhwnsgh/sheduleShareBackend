package com.scheduleManagement.schedule.dto;

import com.scheduleManagement.schedule.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private Date startDate;
    private Date endDate;
    private Date registrationDate;
    private List<Tag> tags;
}
