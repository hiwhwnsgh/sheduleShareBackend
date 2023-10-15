package com.scheduleManagement.schedule.service;

import com.scheduleManagement.schedule.domain.Post;
import com.scheduleManagement.schedule.domain.PostApplication;
import com.scheduleManagement.schedule.domain.PostParticipant;
import com.scheduleManagement.schedule.domain.User;
import com.scheduleManagement.schedule.repository.PostApplicationRepository;
import com.scheduleManagement.schedule.repository.PostParticipantRepository;
import com.scheduleManagement.schedule.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {
    private PostRepository postRepository;
    private PostParticipantRepository postParticipantRepository;
    private PostApplicationRepository postApplicationRepository;

    public PostService(PostRepository postRepository,
                       PostParticipantRepository postParticipantRepository,
                       PostApplicationRepository postApplicationRepository) {
        this.postRepository = postRepository;
        this.postParticipantRepository = postParticipantRepository;
        this.postApplicationRepository = postApplicationRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isEmpty()){return null;}
        return optionalPost.get();
    }
    public List<Post> getPostsByUser(User user) {
        Optional<List<Post>> optionalPosts = postRepository.findByUser(user);

        if (optionalPosts.isPresent()) {
            return optionalPosts.get();
        } else {
            return Collections.emptyList(); // Optional이 비어있을 경우 빈 리스트 반환
        }
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post updatedPost) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            return postRepository.save(post);
        }
        return null;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    public List<Post> getAllPostsWithTags() {
        return postRepository.findAllWithTags();
    }
    // 게시글 참가자 정보 저장
    public void savePostParticipant(PostParticipant postParticipant) {
        postParticipantRepository.save(postParticipant);
    }

    public List<PostApplication> getApplicationsByPost(Post post) {
        return postApplicationRepository.findByPost(post);
    }
    public List<PostApplication> getPendingPostApplicationsByPostId(Long postId) {
        return postApplicationRepository.findByStatusAndPostId("대기 중", postId);
    }
    // 게시글 신청 정보 저장
    public void savePostApplication(PostApplication postApplication) {
        postApplicationRepository.save(postApplication);
    }
    @Transactional
    public PostApplication acceptRequest(Long postId, Long userId) {
        // postId와 userId를 사용하여 해당 게시글 신청을 찾습니다.
        PostApplication postApplication = postApplicationRepository.findByPostIdAndUserId(postId, userId);

        if (postApplication != null) {
            // 게시글 신청의 상태를 '수락'으로 업데이트합니다.
            postApplication.setStatus("수락");

            // 업데이트된 게시글 신청을 저장합니다.
            return postApplicationRepository.save(postApplication);
        }

        return null; // 게시글 신청이 없는 경우 또는 업데이트에 실패한 경우 null을 반환합니다.
    }

    @Transactional
    public PostApplication rejectRequest(Long postId, Long userId) {
        // postId와 userId를 사용하여 해당 게시글 신청을 찾습니다.
        PostApplication postApplication = postApplicationRepository.findByPostIdAndUserId(postId, userId);

        if (postApplication != null) {
            // 게시글 신청의 상태를 '거절'로 업데이트합니다.
            postApplication.setStatus("거절");

            // 업데이트된 게시글 신청을 저장합니다.
            return postApplicationRepository.save(postApplication);
        }

        return null; // 게시글 신청이 없는 경우 또는 업데이트에 실패한 경우 null을 반환합니다.
    }
    public List<Post> getAcceptedPostApplications(Long userId){
        List<Post> posts = postRepository.findByPostApplicationsUserIdAndPostApplicationsStatus(userId,"수락");
        return postRepository.findByPostApplicationsUserIdAndPostApplicationsStatus(userId,"수락");
    }
    public PostApplication getPostApplication(Long postId, Long userId) {
        return postApplicationRepository.findByPostIdAndUserId(postId, userId);
    }
    public PostParticipant getPostParticipant(Long postId, Long userId){
        return postParticipantRepository.findByPostIdAndUserId(postId,userId);
    }
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }


}
