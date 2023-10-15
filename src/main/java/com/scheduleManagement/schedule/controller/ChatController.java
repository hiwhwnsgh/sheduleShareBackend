package com.scheduleManagement.schedule.controller;

import com.scheduleManagement.schedule.domain.ChatMessage;
import com.scheduleManagement.schedule.domain.ChatRoom;
import com.scheduleManagement.schedule.domain.PostParticipant;
import com.scheduleManagement.schedule.domain.User;
import com.scheduleManagement.schedule.dto.ChatMessagesDTO;
import com.scheduleManagement.schedule.dto.MessageDTO;
import com.scheduleManagement.schedule.service.ChatService;
import com.scheduleManagement.schedule.service.PostService;
import com.scheduleManagement.schedule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // 허용할 출처 지정
public class ChatController {

    ChatService chatService;
    UserService userService;
    PostService postService;
    @Autowired
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(ChatService chatService,
                          UserService userService,
                          PostService postService,
                          SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.userService = userService;
        this.postService = postService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/sendChat") // 클라이언트에서 메시지를 보낼 때 사용하는 주소
    public ChatMessage sendChat(@Payload MessageDTO Message) {
        System.out.println("ChatController.sendChat 구독 완료");
        System.out.println("chatMessage.getContent() = " + Message.getContent());
        User user = userService.getLoginUserById(Message.getUserId());
        ChatRoom chatRoom = chatService.getChatRoomByPostId(Message.getPostId());
        PostParticipant postParticipant = postService.getPostParticipant(Message.getPostId(),Message.getUserId());
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setUser(user);
        chatMessage.setContent(Message.getContent());
        chatMessage.setCreatedAt(new Date());
        chatService.saveMessage(chatMessage);
        Message.setNickName(user.getNickname());
        Message.setLoginId(user.getLoginId());
        // 클라이언트로부터 수신한 메시지를 처리하는 로직을 구현
        // 채팅 메시지를 브로드캐스트하여 모든 구독자에게 전송
        simpMessagingTemplate.convertAndSend("/topic/"+Message.getPostId(),Message);
        return chatMessage;
    }

    @GetMapping("/chat/messages")
    public ResponseEntity<List<ChatMessagesDTO>> getMessages(
            @RequestParam(name="loginId") String loginId,
            @RequestParam(name="page") Long postId
    ){
        User user = userService.getLoginUserByLoginId(loginId);
        System.out.println("user.getId()+postId = " + user.getId()+postId);
        PostParticipant postParticipant = postService.getPostParticipant(postId,user.getId());
        List<ChatMessage> messages = chatService.getChatMessages(postParticipant.getParticipationDate());
        List<ChatMessagesDTO> chatMessagesDTOS = new ArrayList<>();
        for(ChatMessage chatMessage : messages){
            ChatMessagesDTO chatMessagesDTO = new ChatMessagesDTO(
                    chatMessage.getUser().getId(),
                    chatMessage.getUser().getNickname(),
                    chatMessage.getContent(),
                    chatMessage.getUser().getLoginId(),
                    chatMessage.getCreatedAt()
            );
            chatMessagesDTOS.add(chatMessagesDTO);
        }
        return ResponseEntity.ok(chatMessagesDTOS);

    }

}
