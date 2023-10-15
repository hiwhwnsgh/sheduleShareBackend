package com.scheduleManagement.schedule.repository;

import com.scheduleManagement.schedule.domain.PostApplication;
import com.scheduleManagement.schedule.domain.PostParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostParticipantRepository extends JpaRepository<PostParticipant, Long> {
    PostParticipant findByPostIdAndUserId(Long postId, Long userId);
}