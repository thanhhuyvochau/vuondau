package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.MoodleRepository.Response.MoodleSectionResponse;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EForumType;
import fpt.capstone.vuondau.entity.dto.ForumDto;
import fpt.capstone.vuondau.entity.dto.SimpleForumDto;
import fpt.capstone.vuondau.entity.request.UpdateForumRequest;
import org.springframework.data.domain.Pageable;

public interface IForumService {
    ForumDto createForumForClass(Long classId, MoodleSectionResponse moodleSectionResponse);

    ForumDto createForumForSubject(Long subjectId);

    ForumDto updateForum(Long id, UpdateForumRequest request);

    ForumDto getForumByClass(Long classId);

    ForumDto getForumBySubject(Long subjectId);

    ApiPage<SimpleForumDto> getAllForumByTypes(Pageable pageable,EForumType forumType);


}
