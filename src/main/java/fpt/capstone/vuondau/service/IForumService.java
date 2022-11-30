package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.MoodleRepository.Response.MoodleRecourseClassResponse;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.ForumDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IForumService {
    ForumDto createForumForClass(Long classId, MoodleRecourseClassResponse moodleRecourseClassResponse);

    ForumDto createForumForSubject(Long subjectId);

    ForumDto updateForum(Long id);

    ForumDto getForumByClass(Long classId);

    ForumDto getForumBySubject(Long subjectId);

    ApiPage<ForumDto> getAllClassForumsOfStudent(Pageable pageable);
    ApiPage<ForumDto> getAllSubjectForumsOfStudent(Pageable pageable);
}
