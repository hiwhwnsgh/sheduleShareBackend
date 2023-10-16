package com.scheduleManagement.schedule.controller;

import com.scheduleManagement.schedule.domain.*;
import com.scheduleManagement.schedule.dto.*;
import com.scheduleManagement.schedule.service.ChatService;
import com.scheduleManagement.schedule.service.PostService;
import com.scheduleManagement.schedule.service.TagService;

import com.scheduleManagement.schedule.service.UserService;
import com.scheduleManagement.schedule.util.DateUtils;
import com.scheduleManagement.schedule.util.TagUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Date;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // 허용할 출처 지정
public class PostController {

    private PostService postService;
    private TagService tagService;
    private UserService userService;
    private ChatService chatService;

    public PostController(PostService postService, TagService tagService, UserService userService, ChatService chatService) {
        this.postService = postService;
        this.tagService = tagService;
        this.userService = userService;
        this.chatService = chatService;
    }

    @GetMapping("/community")
    public ResponseEntity<List<PostListDTO>> getAllPosts() {
        List<Post> posts = postService.getAllPostsWithTags();
        List<PostListDTO> postListDtos = new ArrayList<>();

        for (Post post : posts){
            PostListDTO postListDTO = new PostListDTO();
            postListDTO.setId(post.getId());
            postListDTO.setTitle(post.getTitle());
            postListDTO.setContent(post.getContent());
            postListDTO.setRegistrationDate(post.getRegistrationDate());
            postListDTO.setTags(post.getTags());
            postListDTO.setNickname(post.getUser().getNickname());
            postListDtos.add(postListDTO);
        }


        return ResponseEntity.ok(postListDtos);
    }
    @GetMapping("/community/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<PostListDTO>> getPostsByPage(@PathVariable int pageNumber, @PathVariable int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> posts = postService.getAllPosts(pageable);
        Page<PostListDTO> postListDTOS = posts.map(post -> {
            PostListDTO postListDTO = new PostListDTO();
            postListDTO.setId(post.getId());
            postListDTO.setTitle(post.getTitle());
            postListDTO.setContent(post.getContent());
            postListDTO.setRegistrationDate(post.getRegistrationDate());
            postListDTO.setTags(post.getTags());
            postListDTO.setNickname(post.getUser().getNickname());
            return postListDTO;
        });
        return ResponseEntity.ok(postListDTOS);
    }


    @PostMapping("/postInsert")
    public ResponseEntity<String> createPost(@RequestBody JsonNode requestBody) {
        try {
            // 게시물 내용 및 태그 처리를 위한 변수
            String content = null;
            String title = null;
            Date registerDate = null;
            Date startDate;
            Date endDate;
            List<Tag> tags = new ArrayList<>();
            String user_id = null;
            PostParticipant postParticipant = new PostParticipant();
            PostApplication postApplication = new PostApplication();
            // 필수 필드인 내용(contentInput)를 가져옴
            JsonNode contentNode = requestBody.get("contentInput");
            if (contentNode == null || !contentNode.isTextual()) {
                return ResponseEntity.badRequest().body("게시물 내용이 없거나 유효하지 않습니다.");
            }
            content = contentNode.asText();

            // 선택적 필드인 제목(titleInput)을 가져옴
            JsonNode titleNode = requestBody.get("titleInput");
            if (titleNode != null && titleNode.isTextual()) {
                title = titleNode.asText();
            } else {
                title = ""; // 제목이 없는 경우 빈 문자열로 설정
            }

            // 시작일(startDate)을 파싱
            JsonNode startDateNode = requestBody.get("startDate");
            if (startDateNode == null || !startDateNode.isTextual()) {
                return ResponseEntity.badRequest().body("시작일이 없거나 유효하지 않습니다.");
            }


            // 종료일(endDate)을 파싱
            JsonNode endDateNode = requestBody.get("endDate");
            if (endDateNode == null || !endDateNode.isTextual()) {
                return ResponseEntity.badRequest().body("종료일이 없거나 유효하지 않습니다.");
            }


            // 등록날짜(registerDate)을 파싱
            JsonNode registerDateNode = requestBody.get("currentdate");
            if (registerDateNode == null || !registerDateNode.isTextual()){
                return ResponseEntity.badRequest().body("등록날짜가 없거나 유효하지 않습니다.");
            }
            try {
                startDate = DateUtils.parseDateString(startDateNode.asText());

                endDate = DateUtils.parseDateString(endDateNode.asText());
                registerDate = DateUtils.parseDateString(registerDateNode.asText());
            } catch (ParseException e) {
                return ResponseEntity.badRequest().body("날짜 형식이 올바르지 않습니다.");
            }

            // 유저 아이디 가져오기
            JsonNode userIdNode = requestBody.get("userId");
            if (userIdNode == null || !userIdNode.isTextual()){
                return ResponseEntity.badRequest().body("유저 아이디가 없거나 유효하지 않습니다");
            }
            user_id = userIdNode.asText();

            // 사용자 정보 가져오기
            User user = userService.getLoginUserByLoginId(user_id);

            // 태그 정보를 추출하고 유효성 검사 및 생성
            List<String> tagNames = TagUtils.extractTagNamesFromRequestBody(requestBody);
            for (String tagName : tagNames) {
                Optional<Tag> existingTag = tagService.findTagByName(tagName);
                if (existingTag.isPresent()) {
                    tags.add(existingTag.get());
                } else {
                    // 존재하지 않는 태그인 경우 생성
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    Tag savedTag = tagService.createTag(newTag);
                    tags.add(savedTag);
                }
            }

            // 게시물 생성


            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setStartDate(startDate);
            post.setEndDate(endDate);
            post.setRegistrationDate(registerDate);
            post.setTags(tags);
            post.setUser(user);
            Post savedPost = postService.createPost(post);

            postParticipant.setUser(user);
            postParticipant.setPost(post);
            java.util.Date currentDate = new java.util.Date();
            postParticipant.setParticipationDate(currentDate);

            ChatRoom chatRoom = new ChatRoom(post);
            chatService.createChatRoom(chatRoom);
            chatService.addParticipant(chatRoom,user);

            // 사용자 정보와 게시글 정보 설정
            postApplication.setUser(user);
            postApplication.setPost(post);
            postApplication.setApplicationDate(currentDate);
            postApplication.setStatus("수락");

            // 게시글 참가자 정보와 게시글 신청 정보 저장
            postService.savePostParticipant(postParticipant);
            postService.savePostApplication(postApplication);

            return ResponseEntity.ok("게시글이 성공적으로 저장되었습니다. ID: " + savedPost.getId());
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 저장 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            PostDTO postDTO = new PostDTO(post.getId(), post.getTitle(),
                    post.getContent(), post.getUser().getNickname(), post.getStartDate(), post.getEndDate(),
                    post.getRegistrationDate(), post.getTags());
            return ResponseEntity.ok(postDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/info/{loginId}")
    public ResponseEntity<List<InfoListDTO>> getUserPosts(@PathVariable String loginId) {
        User user = userService.getLoginUserByLoginId(loginId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Post> posts = postService.getPostsByUser(user);
        List<InfoListDTO> userPosts = new ArrayList<>();
        for(Post post : posts){
            InfoListDTO infoListDTO = new InfoListDTO(post.getId(),post.getTitle(),
                    post.getContent(),post.getUser().getNickname(),post.getStartDate(),post.getEndDate(),
                    DateUtils.formatDate(post.getRegistrationDate()),post.getTags());
            userPosts.add(infoListDTO);
        }
        return ResponseEntity.ok(userPosts);
    }
    @PostMapping("/post/{id}/{loginId}")
    public ResponseEntity<String> applyForPost(
            @PathVariable Long id,
            @PathVariable String loginId) {

        try {
            // 게시글 참가자 정보 생성
            PostParticipant postParticipant = new PostParticipant();
            User user = userService.getLoginUserByLoginId(loginId);
            Post post = postService.getPostById(id);

            if(postService.getPostApplication(post.getId(),user.getId()) != null){
                return ResponseEntity.ok("이미 참가 신청을 했습니다.");
            }
            // 사용자 정보와 게시글 정보 설정
            postParticipant.setUser(user);
            postParticipant.setPost(post);
            java.util.Date currentDate = new java.util.Date();
            postParticipant.setParticipationDate(currentDate);

            // 게시글 신청 정보 생성
            PostApplication postApplication = new PostApplication();
            // 사용자 정보와 게시글 정보 설정
            postApplication.setUser(user);
            postApplication.setPost(post);
            postApplication.setApplicationDate(currentDate);
            postApplication.setStatus("대기 중"); // 예시로 "Pending" 상태 설정

            // 게시글 참가자 정보와 게시글 신청 정보 저장
            postService.savePostParticipant(postParticipant);
            postService.savePostApplication(postApplication);

            return ResponseEntity.ok("참가 신청 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Application failed");
        }
    }
    @GetMapping("/info/{loginId}/{id}") // 게시글에 대한 참가 신청에 대한 유저 정보를 반환하는 새로운 엔드포인트
    public ResponseEntity<List<UserApplicationDTO>> getUsersForApplications(
            @PathVariable Long id) {
        List<PostApplication> applications = postService.getPendingPostApplicationsByPostId(id);
        List<UserApplicationDTO> userApplicationDTOs = new ArrayList<>();
        UserApplicationDTO userApplicationDTO = null;
        for (PostApplication application : applications) {
            User user = application.getUser();
            userApplicationDTO = new UserApplicationDTO(
                    user.getId(),user.getLoginId(),user.getNickname(),application.getApplicationDate()
            );
            userApplicationDTOs.add(userApplicationDTO);
        }
            return ResponseEntity.ok(userApplicationDTOs);
    }
    @PutMapping("/info/acceptRequest")
    public ResponseEntity<String> acceptRequest(@RequestBody JsonNode requestData) {
        // postId와 userId를 사용하여 Post_application 테이블의 status 필드를 업데이트합니다.
        // 적절한 서비스 메서드를 호출하여 업데이트 작업을 수행합니다.
        JsonNode postIdNode = requestData.get("postId");
        JsonNode userIdNode = requestData.get("userId");
        if (postIdNode == null) {
            return ResponseEntity.badRequest().body("postId가 없거나 유효하지 않습니다.");
        }
        if (userIdNode == null) {
            return ResponseEntity.badRequest().body("userId가 없거나 유효하지 않습니다.");
        }
        Long postId = postIdNode.asLong();
        Long userId = userIdNode.asLong();

        User user = userService.getLoginUserById(userId);
        ChatRoom chatRoom = chatService.getChatRoomByPostId(postId);

        chatService.addParticipant(chatRoom,user);
        PostApplication updatedPostApplication = postService.acceptRequest(postId, userId);
        // 업데이트된 데이터를 클라이언트에 응답으로 보냅니다.
        return ResponseEntity.ok("수락 성공");
    }

    @PutMapping("/info/rejectRequest")
    public ResponseEntity<String> rejectRequest(@RequestBody JsonNode requestData) {
        // postId와 userId를 사용하여 Post_application 테이블의 status 필드를 업데이트합니다.
        // 적절한 서비스 메서드를 호출하여 업데이트 작업을 수행합니다.
        JsonNode postIdNode = requestData.get("postId");
        JsonNode userIdNode = requestData.get("userId");
        if (postIdNode == null) {
            return ResponseEntity.badRequest().body("postId가 없거나 유효하지 않습니다.");
        }
        if (userIdNode == null) {
            return ResponseEntity.badRequest().body("userId가 없거나 유효하지 않습니다.");
        }
        Long postId = postIdNode.asLong();
        Long userId = userIdNode.asLong();
        PostApplication updatedPostApplication = postService.rejectRequest(postId, userId);

        // 업데이트된 데이터를 클라이언트에 응답으로 보냅니다.
        return ResponseEntity.ok("거절 성공");
    }
    @GetMapping("/schedule/{userId}")
    public ResponseEntity<List<ScheduleDTO>> getAcceptedPostApplications(@PathVariable Long userId) {
        List<Post> acceptedApplications = postService.getAcceptedPostApplications(userId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for(Post post : acceptedApplications){
            ScheduleDTO scheduleDTO = new ScheduleDTO(post.getId(),post.getTitle(),post.getContent(),
                    post.getStartDate(),post.getEndDate(),post.getTags());
            scheduleDTOS.add(scheduleDTO);
        }

        return ResponseEntity.ok(scheduleDTOS);
    }







}
