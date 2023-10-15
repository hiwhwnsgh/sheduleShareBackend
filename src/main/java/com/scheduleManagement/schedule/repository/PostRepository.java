package com.scheduleManagement.schedule.repository;
import com.scheduleManagement.schedule.domain.Post;
import com.scheduleManagement.schedule.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface PostRepository{
    List<Post> findAll();
    public Optional<Post> findById(Long id);
    public Optional<List<Post>> findByUser(User user);
    public Post save(Post post);
    public void deleteById(Long id);
    public List<Post> findAllWithTags();
    List<Post> findByPostApplicationsUserIdAndPostApplicationsStatus(Long userId, String status);
    Page<Post> findAll(Pageable pageable);
}
