package com.scheduleManagement.schedule.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @OneToOne(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @JsonBackReference  // 관리되는 쪽
    private Post post;

    @OneToMany(mappedBy = "chatRoom")
    @JsonBackReference  // 역쪽
    private List<ChatMessage> messages;

    @Column(name = "created_at")
    private Date createdAt;

    // 생성자, getter 및 setter 메서드

    // 게시글과 연결된 채팅방을 생성할 때 사용하는 생성자
    public ChatRoom(Post post) {
        this.post = post;
        this.createdAt = post.getRegistrationDate();
    }

    // 다른 필요한 메서드 및 로직 추가
}
