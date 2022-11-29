package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.dto.ForumDto;

public interface IForumService {
    ForumDto createForumForClass(Class clazz);
    ForumDto createForumForSubject(Subject subject);
    ForumDto updateForum(Long id);
    ForumDto getForumByClass(Long classId);
    ForumDto getForumBySubject(Long subjectId);
}
