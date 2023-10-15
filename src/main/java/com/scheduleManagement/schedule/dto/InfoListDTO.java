package com.scheduleManagement.schedule.dto;

import com.scheduleManagement.schedule.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class InfoListDTO {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private Date startDate;
    private Date endDate;
    private String registrationDate; // String 타입으로 변경
    private List<Tag> tags;
}
