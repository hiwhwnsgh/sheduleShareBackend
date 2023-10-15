package com.scheduleManagement.schedule.repository;

import com.scheduleManagement.schedule.domain.ChatMessage;
import com.scheduleManagement.schedule.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom room);
    List<ChatMessage> findByCreatedAtAfter(Date date);
}