package com.scheduleManagement.schedule.repository;

import com.scheduleManagement.schedule.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByPostId(Long postId);
}
