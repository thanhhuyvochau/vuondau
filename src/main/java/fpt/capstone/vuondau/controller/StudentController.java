package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.AccountEditRequest;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("api/students")
public class StudentController {

    private final IAccountService accountService;

    public StudentController(IAccountService accountService) {
        this.accountService = accountService;
    }


    @Operation(summary = "Cập nhật hồ sơ học sinh")
    @PutMapping("/{id}/account/profile")
    public ResponseEntity<AccountResponse> editProfile(@PathVariable long id, @RequestBody AccountEditRequest accountEditRequest) {
        return ResponseEntity.ok(accountService.editStudentProfile(id, accountEditRequest));
    }
    @PostMapping("/account")
    @Operation(summary = "Học sinh đăng ký tài khoản")
    public ResponseEntity<ApiResponse<StudentResponse>> studentCreateAccount(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(ApiResponse.success(accountService.createStudentAccount(studentRequest)));
    }

}
