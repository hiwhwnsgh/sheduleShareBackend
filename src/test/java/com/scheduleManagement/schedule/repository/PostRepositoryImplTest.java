package com.scheduleManagement.schedule.repository;

import com.scheduleManagement.schedule.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class PostRepositoryImplTest {

    @InjectMocks
    private PostRepositoryImpl postRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Post> query;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        // 가짜 포스트 엔티티 목록 생성
        Post post1 = new Post();
        post1.setId(20L);
        post1.setTitle("롤");
        post1.setContent("<p>안녕</p>");

        Post post2 = new Post();
        post2.setId(21L);
        post2.setTitle("오버워치");
        post2.setContent("<p>방구 뀌기</p>");

        List<Post> fakePosts = Arrays.asList(post1, post2);

        // entityManager.find() 메서드를 가짜 목록으로 대체
        when(entityManager.createQuery("SELECT p FROM Post p", Post.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(fakePosts);

        // findAll() 메서드 호출
        List<Post> posts = postRepository.findAll();

        // 결과 검증
        verify(entityManager).createQuery("SELECT p FROM Post p", Post.class);
        verify(query).getResultList();

        // 결과 리스트의 크기가 2여야 함
        assert(posts.size() == 2);
        // 테스트 결과 출력
        System.out.println("테스트 결과:");
        for (Post post : posts) {
            System.out.println("포스트 ID: " + post.getId());
            System.out.println("post.getTitle() = " + post.getTitle());
            System.out.println("post.getContent() = " + post.getContent());
            System.out.println("post.getRegistrationDate() = " + post.getRegistrationDate());
        }
    }

    @Test
    public void testFindById() {
        // 가짜 포스트 엔티티 생성
        Post fakePost = new Post();
        fakePost.setId(1L);


        // entityManager.find() 메서드를 가짜 엔티티로 대체
        when(entityManager.find(Post.class, 1L)).thenReturn(fakePost);

        // findById() 메서드 호출
        Optional<Post> post = postRepository.findById(1L);

        // 결과 검증
        verify(entityManager).find(Post.class, 1L);

        // 결과로 포스트 엔티티가 반환되어야 함
        assert(post.isPresent());
        assert(post.get().getId() == 1L);
    }

    @Test
    public void testSave() {
        // 가짜 포스트 엔티티 생성
        Post fakePost = new Post();
        fakePost.setId(1L);

        // save() 메서드 호출
        Post savedPost = postRepository.save(fakePost);

        // 결과 검증
        verify(entityManager).persist(fakePost);

        // save() 메서드는 엔티티를 그대로 반환해야 함
        assert(savedPost == fakePost);
    }

}

