package com.scheduleManagement.schedule.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantId;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 채팅방과의 관계 (다대일 관계)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 사용자와의 관계 (다대일 관계)

    // 생성자, getter, setter, 등 필요한 메서드 추가
}
