package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.request.SubjectRequest;
import fpt.capstone.vuondau.entity.response.SubjectResponse;

import java.util.Optional;

public interface ISubjectService {

    SubjectResponse createNewSubject(SubjectRequest subjectRequest);
    SubjectResponse updateSubject(Long subjectId, SubjectRequest subjectRequest);

    Long deleteSubject(Long subjectId);

    SubjectResponse getSubject(Long subjectId);
}
