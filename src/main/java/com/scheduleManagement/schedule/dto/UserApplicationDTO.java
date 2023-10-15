package com.scheduleManagement.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scheduleManagement.schedule.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public class UserApplicationDTO {
    private Long id;
    private String loginId;
    private String nickname;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date applicationDate;

}
