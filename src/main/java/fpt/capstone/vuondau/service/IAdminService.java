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

    FeedBackDto viewStudentFeedbackClass(Long classId) ;

    ApiPage <RequestFormResponese> searchRequestForm(RequestSearchRequest query, Pageable pageable);
}
