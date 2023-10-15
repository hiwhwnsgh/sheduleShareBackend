package com.scheduleManagement.schedule.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "post_application")
public class PostApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "application_date")
    private Date applicationDate;

    @Column(name = "status")
    private String status;

    // 생성자, 게터, 세터 등 필요한 메서드 추가

}
