package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.MoodleRepository.Response.MoodleSectionResponse;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.ForumDto;
import org.springframework.data.domain.Pageable;

public interface IForumService {
    ForumDto createForumForClass(Long classId, MoodleSectionResponse moodleSectionResponse);

    ForumDto createForumForSubject(Long subjectId);

    ForumDto updateForum(Long id);

    ForumDto getForumByClass(Long classId);

    ForumDto getForumBySubject(Long subjectId);

    ApiPage<ForumDto> getAllClassForums(Pageable pageable);

    ApiPage<ForumDto> getAllSubjectForums(Pageable pageable);

}
