package com.scheduleManagement.schedule.service;

import com.scheduleManagement.schedule.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.scheduleManagement.schedule.repository.TagRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    // ID로 태그 조회
    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("태그를 찾을 수 없습니다: " + id));
    }
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }
    public Optional<Tag> findTagByName(String name) {
        return tagRepository.findByName(name);
    }

    // 태그 수정
    public Tag updateTag(Long id, Tag updatedTag) {
        Tag tag = getTagById(id);
        tag.setName(updatedTag.getName());
        return tagRepository.save(tag);
    }

    // 태그 삭제
    public void deleteTag(Long id) {
        Tag tag = getTagById(id);
        tagRepository.delete(tag);
    }
}
