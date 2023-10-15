package com.scheduleManagement.schedule.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.scheduleManagement.schedule.domain.Post;
import com.scheduleManagement.schedule.repository.PostRepository;
import com.scheduleManagement.schedule.service.PostService;

public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPosts() {
        // 가짜 포스트 엔티티 목록 생성
        Post post1 = new Post();
        post1.setId(1L);
        Post post2 = new Post();
        post2.setId(2L);
        List<Post> fakePosts = Arrays.asList(post1, post2);

        // postRepository.findAll() 메서드를 가짜 목록으로 대체
        when(postRepository.findAll()).thenReturn(fakePosts);

        // getAllPosts() 메서드 호출
        List<Post> posts = postService.getAllPosts();

        // 결과 검증
        for (Post post : posts) {
            System.out.println("포스트 ID: " + post.getId());
        }
    }

    // 다른 메서드에 대한 테스트도 작성 가능
}
