package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.SubjectRequest;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.service.ISubjectService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/subjects")
public class SubjectController {

    private final ISubjectService subjectService;

    public SubjectController(ISubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Operation(summary = "Tạo mới subject")
    @PostMapping
    public ResponseEntity<ApiResponse<SubjectResponse>> createNewSubject(@Valid @RequestBody SubjectRequest subjectRequest) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.createNewSubject(subjectRequest)));
    }


    @Operation(summary = "Sửa subject bằng id ")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectResponse>> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectRequest subjectRequest) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.updateSubject(id, subjectRequest)));
    }

    @Operation(summary = "Xóa subject bằng  Id")
    @DeleteMapping("/{subjectId}")
    public ResponseEntity<ApiResponse<Long>> deleteSubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.deleteSubject(subjectId)));
    }


    @GetMapping("/{subjectId}")
    @Operation(summary = "lấy subject bang id")
    public ResponseEntity<ApiResponse<SubjectResponse>> getSubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.getSubject(subjectId)));
    }

    @GetMapping("/{studentId}/suggest-subject")
    @Operation(summary = "Gợi ý subject cho học sinh")
    public ResponseEntity<ApiResponse<ApiPage<SubjectResponse>>> suggestSubjectForStudent(@PathVariable Long studentId, Pageable pageable ) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.suggestSubjectForStudent(studentId, pageable)));
    }

}
