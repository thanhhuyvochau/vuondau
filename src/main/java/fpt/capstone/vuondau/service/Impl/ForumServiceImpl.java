package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.dto.ForumDto;
import fpt.capstone.vuondau.repository.ForumRepository;
import fpt.capstone.vuondau.service.IForumService;
import org.springframework.stereotype.Service;

@Service
public class ForumServiceImpl implements IForumService {
    private final ForumRepository forumRepository;

    public ForumServiceImpl(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Override
    public ForumDto createForumForClass(Class clazz) {
        return null;
    }

    @Override
    public ForumDto createForumForSubject(Subject subject) {

        return null;
    }

    @Override
    public ForumDto updateForum(Long id) {
        return null;
    }

    @Override
    public ForumDto getForumByClass(Long classId) {
        return null;
    }

    @Override
    public ForumDto getForumBySubject(Long subjectId) {
        return null;
    }
}
