package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.StudentRequest;
import fpt.capstone.vuondau.entity.response.StudentResponse;
import fpt.capstone.vuondau.service.IStudentService;
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(studentService.getAll()));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success(studentService.searchByName(name)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> create(StudentRequest studentRequest) {
        return ResponseEntity.ok(ApiResponse.success(studentService.create(studentRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> update(StudentRequest studentRequest, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(studentService.update(studentRequest, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> update(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(studentService.delete(id)));
    }
}
