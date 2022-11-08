package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.service.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/students")
public class StudentController {
    private final IStudentService iStudentService;

    public StudentController(IStudentService iStudentService) {
        this.iStudentService = iStudentService;
    }


    @PostMapping
    @Operation(summary = "Hoc sinh đăng ký tài khoản")
    public ResponseEntity<ApiResponse<StudentResponse>> studentCreateAccount(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(ApiResponse.success(iStudentService.studentCreateAccount(studentRequest)));
    }

}
