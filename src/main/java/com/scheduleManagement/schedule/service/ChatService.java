package com.scheduleManagement.schedule.service;

import com.scheduleManagement.schedule.domain.ChatMessage;
import com.scheduleManagement.schedule.domain.ChatParticipant;
import com.scheduleManagement.schedule.domain.ChatRoom;
import com.scheduleManagement.schedule.domain.User;
import com.scheduleManagement.schedule.repository.ChatMessageRepository;
import com.scheduleManagement.schedule.repository.ChatParticipantRepository;
import com.scheduleManagement.schedule.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ChatService {
    private final ChatMessageRepository messageRepository;
    private final ChatParticipantRepository participantRepository;
    private final ChatRoomRepository roomRepository;

    @Autowired
    public ChatService(ChatMessageRepository messageRepository,
                       ChatParticipantRepository participantRepository,
                       ChatRoomRepository roomRepository) {
        this.messageRepository = messageRepository;
        this.participantRepository = participantRepository;
        this.roomRepository = roomRepository;
    }

    public ChatRoom getChatRoomByPostId(Long postId) {
        // 게시글과 연결된 채팅방을 조회
        return roomRepository.findByPostId(postId);
    }
    public ChatRoom getRoomById(Long roomId) {
        // 채팅방 ID를 사용하여 채팅방 조회
        return roomRepository.findById(roomId).orElse(null);
    }



    //
    // 새로운 채팅 메시지 저장
    public ChatMessage saveMessage(ChatMessage message) {
        return messageRepository.save(message);
    }

    // 채팅 참여자 추가
    public ChatParticipant addParticipant(ChatRoom chatRoom, User user) {
        ChatParticipant participant = new ChatParticipant();
        participant.setChatRoom(chatRoom);
        participant.setUser(user);
        return participantRepository.save(participant);
    }

    // 특정 채팅방의 채팅 메시지 조회
    public List<ChatMessage> getMessagesByRoom(ChatRoom room) {
        return messageRepository.findByChatRoom(room);
    }

    // 특정 채팅방의 참여자 조회
    public List<ChatParticipant> getParticipantsByRoom(ChatRoom room) {
        return participantRepository.findByChatRoom(room);
    }

    // 채팅방 생성
    public ChatRoom createChatRoom(ChatRoom room) {
        return roomRepository.save(room);
    }
    public List<ChatMessage> getChatMessages(Date date){
        return messageRepository.findByCreatedAtAfter(date);
    }


}
