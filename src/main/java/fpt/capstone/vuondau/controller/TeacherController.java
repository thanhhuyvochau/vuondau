package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.AccountEditRequest;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.service.IAccountService;
import fpt.capstone.vuondau.service.ITeacherService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/teachers")
public class TeacherController {
    private final ITeacherService iTeacherService;
    private final IAccountService accountService;

    public TeacherController(ITeacherService iTeacherService, IAccountService accountService) {
        this.iTeacherService = iTeacherService;
        this.accountService = accountService;
    }

    @Operation(summary = "Cập nhật hồ sơ giáo viên")
    @PutMapping("/{id}/account/profile")
    public ResponseEntity<AccountResponse> editProfile(@PathVariable long id, @RequestBody AccountEditRequest accountEditRequest) {
        return ResponseEntity.ok(accountService.editTeacherProfile(id, accountEditRequest));
    }
    @Operation(summary = "Tạo tài khoản cho giáo viên")
    @PostMapping("/account")
    public ResponseEntity<ApiResponse<AccountTeacherResponse>> createTeacherAccount(@RequestBody AccountExistedTeacherRequest accountRequest) {
        return ResponseEntity.ok(ApiResponse.success(accountService.createTeacherAccount(accountRequest)));
    }
}
