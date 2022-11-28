package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.dto.FeedBackDto;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.RequestFormResponese;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.service.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final IAdminService iAdminService;

    public AdminController(IAdminService iAdminService) {
        this.iAdminService = iAdminService;
    }

    // MANGER ACCOUNT


    @Operation(summary = "cập nhật role cho account")
    @PostMapping("/{accountId}/update-role")
    public ResponseEntity<ApiResponse<AccountResponse>> updateRoleAccount(@PathVariable long accountId, @RequestParam EAccountRole eAccountRole) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.updateRoleAccount(accountId, eAccountRole)));
    }

    @Operation(summary = "cập nhật active cho account")
    @PostMapping("/{accountId}/approve-teacher-account")
    public ResponseEntity<ApiResponse<AccountResponse>> ApproveAccountTeacher(@PathVariable long accountId) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.ApproveAccountTeacher(accountId)));
    }


    @Operation(summary = "xem hoc sinh feedback lớp ")
    @GetMapping("/{classId}/-view-feadback")
    public ResponseEntity<ApiResponse<FeedBackDto>> viewStudentFeedbackClass(@PathVariable long classId) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.viewStudentFeedbackClass(classId)));
    }

    @Operation(summary = "Tìm Kiếm request form ")
    @GetMapping("/search-request-form")
    public ResponseEntity<ApiResponse<ApiPage<RequestFormResponese>>> searchRequestForm(@Nullable RequestSearchRequest query,
                                                                                        Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iAdminService.searchRequestForm(query, pageable)));
    }


}
