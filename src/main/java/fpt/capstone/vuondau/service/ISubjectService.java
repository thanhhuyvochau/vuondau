package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.request.SubjectRequest;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ISubjectService {

    SubjectResponse createNewSubject(SubjectRequest subjectRequest);
    SubjectResponse updateSubject(Long subjectId, SubjectRequest subjectRequest);

    Long deleteSubject(Long subjectId);

    SubjectResponse getSubject(Long subjectId);

    ApiPage<SubjectResponse> suggestSubjectForStudent(Long studentId, Pageable pageable );
}
