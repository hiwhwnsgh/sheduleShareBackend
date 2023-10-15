package com.scheduleManagement.schedule.dto;

import com.scheduleManagement.schedule.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Long id;
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;
    private List<Tag> tags;
}
