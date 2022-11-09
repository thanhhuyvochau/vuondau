package fpt.capstone.vuondau.service;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Dto.FeedBackDto;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.request.AccountSearchRequest;
import fpt.capstone.vuondau.entity.request.CourseSearchRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.util.specification.AccountSpecificationBuilder;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAdminService {

    ApiPage<AccountResponse> searchAccount(AccountSearchRequest query, Pageable pageable);

    ApiPage<AccountResponse> viewAllAccountDetail(Pageable pageable);

    AccountResponse viewAccountDetail(long id);

    Boolean banAndUbBanAccount(long id);

    AccountResponse updateRoleAccount(long id, EAccountRole eAccountRole);

    ApiPage<CourseResponse> searchCourse(CourseSearchRequest query, Pageable pageable);

    AccountResponse ApproveAccountTeacher(long id);

    FeedBackDto viewStudentFeedbackClass(Long classId) ;
}
