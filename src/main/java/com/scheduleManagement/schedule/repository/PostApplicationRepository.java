package com.scheduleManagement.schedule.repository;

import com.scheduleManagement.schedule.domain.Post;
import com.scheduleManagement.schedule.domain.PostApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostApplicationRepository extends JpaRepository<PostApplication, Long> {
    List<PostApplication> findByPost(Post post);
    List<PostApplication> findByStatusAndPostId(String status, Long postId);
    PostApplication findByPostIdAndUserId(Long postId,Long userId);
    @Query("SELECT pa FROM PostApplication pa WHERE pa.user.id = :userId AND pa.status = '수락'")
    List<PostApplication> findByUserIdAndStatus(@Param("userId") Long userId);

}
