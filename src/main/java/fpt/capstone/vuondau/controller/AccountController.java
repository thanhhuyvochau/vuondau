package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.service.IAccountService;
import fpt.capstone.vuondau.service.IAdminService;
import fpt.capstone.vuondau.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("api/accounts")
public class AccountController {
    private final IAccountService accountService;
    private final IAdminService adminService;
    private final IRoleService roleService;

    public AccountController(IAccountService userService, IAdminService adminService, IRoleService roleService) {
        this.accountService = userService;
        this.adminService = adminService;
        this.roleService = roleService;
    }


    @GetMapping
    @Operation(summary = "Lấy tất cả tài khoản")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> getAccounts(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(accountService.getAccounts(pageable)));
    }

    @GetMapping("/students")
    @Operation(summary = "Lấy tất cả tài khoản học sinh")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> getStudentAccounts(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(accountService.getStudentAccounts(pageable)));
    }

    @GetMapping("/teachers")
    @Operation(summary = "Lấy tất cả tài khoản giáo viên")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> getTeacherAccounts(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(accountService.getTeacherAccounts(pageable)));
    }

    @Operation(summary = "Cập nhật ảnh dại diện cho tài khoản")
    @PostMapping("/{id}/avatar")
    @PreAuthorize("hasAnyAuthority('STUDENT','MANAGER','TEACHER')")
    public ResponseEntity<Boolean> uploadAvatar(@PathVariable long id, @ModelAttribute UploadAvatarRequest uploadAvatarRequest) throws IOException {
        return ResponseEntity.ok(accountService.uploadAvatar(id, uploadAvatarRequest));
    }

    @Operation(summary = "Tìm Kiếm account")
    @GetMapping("/search-account")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> searchAccount(@Nullable AccountSearchRequest query,
                                                                               Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(accountService.searchAccount(query, pageable)));
    }


    @Operation(summary = "Xem thông tin tài khoản")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER','TEACHER')")
    public ResponseEntity<AccountResponse> getInfoAccount(@PathVariable long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @Operation(summary = "Ban / Unban account")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ApiResponse<Boolean>> banAndUbBanAccount(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(accountService.banAndUbBanAccount(id)));
    }

    //    @Operation(summary = "Cập nhật role cho account")
//    @PutMapping("/{id}/role")
//    public ResponseEntity<ApiResponse<AccountResponse>> updateAccountRole(@PathVariable long id, @RequestParam EAccountRole eAccountRole) {
//        return ResponseEntity.ok(ApiResponse.success(accountService.updateRoleAccount(id, eAccountRole)));
//    }

    @Operation(summary = "cập nhật role-active cho account")
    @PutMapping("/{id}/role-active")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccountRole(@PathVariable long id,
                                                                          @Nullable @RequestBody AccountEditRequest accountEditRequest) {
        return ResponseEntity.ok(ApiResponse.success(accountService.updateRoleAndActiveAccount(id, accountEditRequest)));
    }

    @Operation(summary = "Chê duyệt tài khoản giáo viên")
    @PutMapping("/{id}/active")
    @PreAuthorize("hasAuthority('MANAGER')")
    public ResponseEntity<ApiResponse<AccountResponse>> ApproveAccountTeacher(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(accountService.approveTeacherAccount(id)));
    }


    @Operation(summary = "Cập nhật hồ sơ account")
    @PutMapping("/{id}/account/profile")
    public ResponseEntity<AccountResponse> editProfile(@PathVariable long id,
                                                       @RequestBody AccountEditRequest accountEditRequest) {
        return ResponseEntity.ok(accountService.editStudentProfile(id, accountEditRequest));
    }

    @GetMapping("/detail")
    @Operation(summary = "Lấy tất thông tin tài khoản")
    @PreAuthorize("hasAnyAuthority('STUDENT','MANAGER','TEACHER')")
    public ResponseEntity<ApiResponse<AccountResponse>> getSelfAccount() {
        return ResponseEntity.ok(ApiResponse.success(accountService.getSelfAccount()));
    }
}
