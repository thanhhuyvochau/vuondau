package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.Dto.FeedBackDto;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.AccountSearchRequest;
import fpt.capstone.vuondau.entity.request.CourseSearchRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.service.IAdminService;
import fpt.capstone.vuondau.util.specification.AccountSpecificationBuilder;
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
    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> viewAllAccountDetail(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewAllAccountDetail( pageable)));
    }

    @Operation(summary = "xem chi tiết account ")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> viewAccountDetail(@PathVariable long id ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewAccountDetail(id)));
    }

    @Operation(summary = "ban / unban account")
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> banAndUbBanAccount(@PathVariable long id ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.banAndUbBanAccount(id)));
    }

    @Operation(summary = "cập nhật role cho account")
    @PostMapping("/{id}/update-role")
    public ResponseEntity<ApiResponse<AccountResponse>> updateRoleAccount(@PathVariable long id , @RequestParam EAccountRole eAccountRole) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.updateRoleAccount(id, eAccountRole)));
    }

    @Operation(summary = "cập nhật active cho account")
    @PostMapping("/{id}/approve-teacher-account")
    public ResponseEntity<ApiResponse<AccountResponse>> ApproveAccountTeacher(@PathVariable long id ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.ApproveAccountTeacher(id)));
    }


    @Operation(summary = "xem hoc sinh feedback lớp ")
    @GetMapping("/{classId}/-view-feadback")
    public ResponseEntity<ApiResponse<FeedBackDto>> viewStudentFeedbackClass(@PathVariable long classId ) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewStudentFeedbackClass(classId)));
    }

    //MANAGE IT REQUEST FROM




    // MANAGE SUBJECT




    // MANGER COURSE
    @Operation(summary = "Tìm Kiếm course")
    @GetMapping("/cource")
    public ResponseEntity<ApiResponse<ApiPage<CourseResponse>>> searchCourse(@Nullable CourseSearchRequest query,
                                                                             Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.searchCourse(query, pageable)));
    }

// MANAGE TOPIC

}
