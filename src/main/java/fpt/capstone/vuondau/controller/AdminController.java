package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.Dto.FeedBackDto;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.entity.response.RequestFormResponese;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.service.IAdminService;
import fpt.capstone.vuondau.util.specification.AccountSpecificationBuilder;
import fpt.capstone.vuondau.util.specification.SubjectSpecificationBuilder;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final IAdminService iAdminService;

    public AdminController(IAdminService iAdminService) {
        this.iAdminService = iAdminService;
    }

    // MANGER ACCOUNT
    @Operation(summary = "Tìm Kiếm account")
    @GetMapping("/search-account")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> searchAccount(@Nullable AccountSearchRequest query,
                                                                               Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.searchAccount(query, pageable)));
    }

    @Operation(summary = "Lấy tất cả account ")
    @GetMapping ("/get-all-account")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> viewAllAccountDetail(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewAllAccountDetail( pageable)));
    }

    @Operation(summary = "xem chi tiết account ")
    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponse<AccountResponse>> viewAccountDetail(@PathVariable long accountId ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewAccountDetail(accountId)));
    }

    @Operation(summary = "ban / unban account")
    @PostMapping("/{accountId}")
    public ResponseEntity<ApiResponse<Boolean>> banAndUbBanAccount(@PathVariable long accountId ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.banAndUbBanAccount(accountId)));
    }

    @Operation(summary = "cập nhật role cho account")
    @PostMapping("/{accountId}/update-role")
    public ResponseEntity<ApiResponse<AccountResponse>> updateRoleAccount(@PathVariable long accountId , @RequestParam EAccountRole eAccountRole) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.updateRoleAccount(accountId, eAccountRole)));
    }

    @Operation(summary = "cập nhật active cho account")
    @PostMapping("/{accountId}/approve-teacher-account")
    public ResponseEntity<ApiResponse<AccountResponse>> ApproveAccountTeacher(@PathVariable long accountId ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.ApproveAccountTeacher(accountId)));
    }


    @Operation(summary = "xem hoc sinh feedback lớp ")
    @GetMapping("/{classId}/-view-feadback")
    public ResponseEntity<ApiResponse<FeedBackDto>> viewStudentFeedbackClass(@PathVariable long classId ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewStudentFeedbackClass(classId)));
    }

    //MANAGE IT REQUEST FROM




    // MANAGE SUBJECT
    @Operation(summary = "Tìm Kiếm subject")
    @GetMapping("/search-subject")
    public ResponseEntity<ApiResponse<ApiPage<SubjectResponse>>> searchSubject(@Nullable SubjectSearchRequest query ,
                                                                               Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.searchSubject(query, pageable)));
    }

    @Operation(summary = "Lấy tất cả subject ")
    @GetMapping("/get-all-subject")
    public ResponseEntity<ApiResponse<ApiPage<SubjectResponse>>> viewAllSubject(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewAllSubject( pageable)));
    }

    @Operation(summary = "xem chi tiết subject ")
    @GetMapping("/{subjectId}")
    public ResponseEntity<ApiResponse<SubjectResponse>> viewSubjectDetail(@PathVariable long subjectId ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewSubjectDetail(subjectId)));
    }

    @Operation(summary = "sửa subject")
    @PostMapping("/{subjectId}/update-subject")
    public ResponseEntity<ApiResponse<SubjectResponse>> updateSubject(@PathVariable long subjectId , @RequestBody SubjectRequest subjectRequest) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.updateSubject(subjectId, subjectRequest)));
    }

    @Operation(summary = "xoá chi tiết subject ")
    @DeleteMapping("/{subjectId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteSubject(@PathVariable long subjectId ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.deleteSubject(subjectId)));
    }


    // MANGER COURSE
    @Operation(summary = "Tìm Kiếm course")
    @GetMapping("/search-cource")
    public ResponseEntity<ApiResponse<ApiPage<CourseResponse>>> searchCourse(@Nullable CourseSearchRequest query,
                                                                             Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.searchCourse(query, pageable)));
    }

    @Operation(summary = "Lấy tất cả course ")
    @GetMapping("/get-all-course")
    public ResponseEntity<ApiResponse<ApiPage<CourseResponse>>> viewAllCourse(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewAllCourse( pageable)));
    }

    @Operation(summary = "xem chi tiết course ")
    @GetMapping("/{courseID}")
    public ResponseEntity<ApiResponse<CourseResponse>> viewSubjectCourse(@PathVariable long courseID ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewCourseDetail(courseID)));
    }

    @Operation(summary = "sửa course")
    @PostMapping("/{courseID}/update-course")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@PathVariable long courseID , @RequestBody CourseRequest subjectRequest) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.updateCourse(courseID, subjectRequest)));
    }

// MANAGE TOPIC

//MANGER IT REQUEST FROM
@Operation(summary = "Tìm Kiếm request form ")
@GetMapping("/search-request-form")
public ResponseEntity<ApiResponse<ApiPage<RequestFormResponese>>> searchRequestForm(@Nullable RequestSearchRequest query,
                                                                                    Pageable pageable) {
    return ResponseEntity.ok(ApiResponse.success(iAdminService.searchRequestForm(query, pageable)));
}



}
