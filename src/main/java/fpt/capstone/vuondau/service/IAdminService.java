package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.dto.FeedBackDto;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.*;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseDetailResponse;
import fpt.capstone.vuondau.entity.response.SubjectResponse;

import org.springframework.data.domain.Pageable;

public interface IAdminService {

    ApiPage<AccountResponse> searchAccount(AccountSearchRequest query, Pageable pageable);

    ApiPage<AccountResponse> viewAllAccountDetail(Pageable pageable);

    AccountResponse viewAccountDetail(long id);

    Boolean banAndUbBanAccount(long id);

    AccountResponse updateRoleAccount(long id, EAccountRole eAccountRole);

    ApiPage<CourseDetailResponse> searchCourse(CourseSearchRequest query, Pageable pageable);

    AccountResponse ApproveAccountTeacher(long id);

    FeedBackDto viewStudentFeedbackClass(Long classId) ;

    ApiPage<SubjectResponse> searchSubject(SubjectSearchRequest query , Pageable pageable);

    ApiPage<SubjectResponse> viewAllSubject(Pageable pageable);

    SubjectResponse viewSubjectDetail(long subjectId);

    Boolean deleteSubject(long subjectId);

    SubjectResponse updateSubject(long subjectId,  SubjectRequest subjectRequest);

    ApiPage <RequestFormResponese> searchRequestForm(RequestSearchRequest query, Pageable pageable);
}
