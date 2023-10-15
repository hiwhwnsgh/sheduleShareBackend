package com.scheduleManagement.schedule.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scheduleManagement.schedule.util.TagListSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
@Entity
@Getter
@Setter
@Table(name = "post") // 테이블 이름을 "post"로 정의합니다.
@JsonSerialize(using = TagListSerializer.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column
    private Date startDate;

    @Column
    private Date endDate;


    @Column(name = "registration_date") // 데이터베이스 테이블의 컬럼 이름을 지정합니다.
    private Date registrationDate;


    @ManyToOne
    @JoinColumn(name = "login_Id") // post 테이블의 loginId 컬럼과 매핑
    private User user;

    // 다대다 관계 매핑
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostApplication> postApplications;

    // 생성자, 게터, 세터, 기타 메서드 추가
}
