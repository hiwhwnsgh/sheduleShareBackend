package com.scheduleManagement.schedule.repository;

import com.scheduleManagement.schedule.domain.ChatParticipant;
import com.scheduleManagement.schedule.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    List<ChatParticipant> findByChatRoom(ChatRoom room);
}
