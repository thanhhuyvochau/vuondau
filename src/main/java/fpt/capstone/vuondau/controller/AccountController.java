package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.service.IAccountService;
import fpt.capstone.vuondau.service.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/accounts")
public class AccountController {
    private final IAccountService accountService;
    private final IAdminService adminService;

    public AccountController(IAccountService userService, IAdminService adminService) {
        this.accountService = userService;
        this.adminService = adminService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> getAccounts(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(accountService.getAccounts(pageable)));
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> getStudentAccounts(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(accountService.getStudentAccounts(pageable)));
    }

    @GetMapping("/teachers")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> getTeacherAccounts(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(accountService.getTeacherAccounts(pageable)));
    }

    @Operation(summary = "Cập nhật ảnh dại diện cho tài khoản")
    @PostMapping("/{id}/avatar")
    public ResponseEntity<Boolean> uploadAvatar(@PathVariable long id, @ModelAttribute UploadAvatarRequest uploadAvatarRequest) throws IOException {
        return ResponseEntity.ok(accountService.uploadAvatar(id, uploadAvatarRequest));
    }

    @Operation(summary = "Tìm Kiếm account")
    @GetMapping("/search-account")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> searchAccount(@Nullable AccountSearchRequest query,
                                                                               Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(accountService.searchAccount(query, pageable)));
    }


    @Operation(summary = "Xem thông tin giáo viên giáo viên")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getInfoTeacher (@PathVariable long id ) {

        return ResponseEntity.ok(accountService.getInfoTeacher(id)) ;
    }



    @Operation(summary = "Ban / Unban account")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> banAndUbBanAccount(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(accountService.banAndUbBanAccount(id)));
    }

}
