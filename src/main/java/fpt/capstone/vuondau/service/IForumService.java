package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.MoodleRepository.Response.MoodleRecourseClassResponse;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.dto.ForumDto;

public interface IForumService {
    ForumDto createForumForClass(Long classId, MoodleRecourseClassResponse moodleRecourseClassResponse);

    ForumDto createForumForSubject(Long subjectId);

    ForumDto updateForum(Long id);

    ForumDto getForumByClass(Long classId);

    ForumDto getForumBySubject(Long subjectId);

}
