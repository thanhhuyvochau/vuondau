package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.service.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final IStudentService studentService;

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(description = "Lấy tất cả học sinh")
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(studentService.getAll()));
    }

    @Operation(description = "Lấy tất cả học sinh với trạng thái tài khoản active hoặc inactive")
    @GetMapping("/account/is-active")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllByActive(@RequestParam Boolean active) {
        return ResponseEntity.ok(ApiResponse.success(studentService.getAllByActive(active)));
    }

    @Operation(description = "Tìm kiếm học sinh bằng tên (first name hoạc last name)")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success(studentService.searchByName(name)));
    }

    @Operation(description = "Tạo tài và khoản học sinh")
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> create(StudentRequest studentRequest) {
        return ResponseEntity.ok(ApiResponse.success(studentService.create(studentRequest)));
    }

    @Operation(description = "Cập nhật thông tin học sinh ")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> update(StudentRequest studentRequest, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(studentService.update(studentRequest, id)));
    }

    @Operation(description = "Xóa học sinh ")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(studentService.delete(id)));
    }
}
