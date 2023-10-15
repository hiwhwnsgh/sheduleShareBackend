package com.scheduleManagement.schedule.repository;

import com.scheduleManagement.schedule.domain.Post;
import com.scheduleManagement.schedule.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final EntityManager entityManager;

    public PostRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Post> findAll() {
        // EntityManager를 사용하여 원하는 쿼리를 작성하여 데이터를 조회합니다.
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p", Post.class);
        return query.getResultList(); 
        //return null;
    }

    @Override
    public Optional<Post> findById(Long id) {
        // EntityManager를 사용하여 원하는 쿼리를 작성하여 데이터를 조회합니다.
        return Optional.ofNullable(entityManager.find(Post.class, id));
        //return Optional.empty();
    }

    @Override
    public Post save(Post post) {
        // EntityManager를 사용하여 원하는 쿼리를 작성하여 데이터를 저장합니다.
        entityManager.persist(post);
        return post; // 저장된 엔티티를 반환
    }

    @Override
    public void deleteById(Long id) {
        // EntityManager를 사용하여 원하는 쿼리를 작성하여 데이터를 삭제합니다.
        Post post = entityManager.find(Post.class, id);
        if (post != null) {
            entityManager.remove(post);
        }
    }
    @Override
    public List<Post> findAllWithTags() {
        return entityManager.createQuery("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.tags ORDER BY p.registrationDate DESC", Post.class).getResultList();
    }
    @Override
    public Optional<List<Post>> findByUser(User user){
        String jpql = "SELECT p FROM Post p WHERE p.user = :user";
        TypedQuery<Post> query = entityManager.createQuery(jpql, Post.class);
        query.setParameter("user", user);

        List<Post> posts = query.getResultList();
        return Optional.of(posts);
    }
    @Override
    public List<Post> findByPostApplicationsUserIdAndPostApplicationsStatus(Long userId, String status){
        String jpql = "SELECT p FROM Post p " +
                "JOIN FETCH p.postApplications pa " +
                "WHERE pa.user.id = :userId AND pa.status = :status";
        TypedQuery<Post> query = entityManager.createQuery(jpql, Post.class);
        query.setParameter("userId", userId);
        query.setParameter("status", status);
        return query.getResultList();
    }
    @Override
    public Page<Post> findAll(Pageable pageable) {
        TypedQuery<Post> query = entityManager.createQuery("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.tags", Post.class);
        List<Post> posts = query.getResultList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), posts.size());

        // 페이지 수가 실제 게시물 수를 초과하는 경우
        if (start > posts.size() || start < 0) {
            start = 0;
        }
        if (end > posts.size() || end < 0) {
            end = posts.size();
        }

        List<Post> subList = posts.subList(start, end);

        return new PageImpl<>(subList, pageable, posts.size());
    }

}
