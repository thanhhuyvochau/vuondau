package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.RequestFormDto;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.response.RequestFormResponse;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.service.IAccountService;
import fpt.capstone.vuondau.service.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/students")
public class StudentController {
    private final IStudentService iStudentService;
    private final IAccountService iAccountService;
    public StudentController(IStudentService iStudentService, IAccountService iAccountService) {
        this.iStudentService = iStudentService;
        this.iAccountService = iAccountService;
    }


    @PostMapping
    @Operation(summary = "Hoc sinh đăng ký tài khoản")
    public ResponseEntity<ApiResponse<StudentResponse>> studentCreateAccount(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(ApiResponse.success(iAccountService.studentCreateAccount(studentRequest)));
    }


    @PostMapping("/{id}/upload-request")
    public ResponseEntity<ApiResponse<RequestFormResponse>> uploadRequestForm(@PathVariable Long id , @ModelAttribute RequestFormDto requestFormDto) {
        return ResponseEntity.ok(ApiResponse.success(iStudentService.uploadRequestForm(id,requestFormDto)));
    }

}
