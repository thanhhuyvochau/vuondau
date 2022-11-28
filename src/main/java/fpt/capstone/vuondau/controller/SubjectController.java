package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.SubjectRequest;
import fpt.capstone.vuondau.entity.request.SubjectSearchRequest;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.service.ISubjectService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @Operation(summary = "Xóa subject bằng id")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deleteSubject(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.deleteSubject(id)));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Lấy môn bằng id")
    public ResponseEntity<ApiResponse<SubjectResponse>> getSubject(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.getSubject(id)));
    }

    @GetMapping("/suggest")
    @Operation(summary = "Gợi ý subject cho học sinh")
    public ResponseEntity<ApiResponse<ApiPage<SubjectResponse>>> suggestSubjectForStudent(@RequestParam Long studentId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.suggestSubjectForStudent(studentId, pageable)));
    }


    @Operation(summary = "Lấy tất cả các môn không phân trang")
    @GetMapping("/unpage")
    public ResponseEntity<ApiResponse<List<SubjectResponse>>> getSubjectsWithoutPaging() {
        return ResponseEntity.ok(ApiResponse.success(subjectService.getAllWithoutPaging()));
    }

    @Operation(summary = "Lấy tất cả các môn có phân trang")
    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<SubjectResponse>>> getSubjectsWithPaging(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.getAllWithPaging(pageable)));
    }

    @Operation(summary = "Tìm Kiếm subject")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ApiPage<SubjectResponse>>> searchSubject(@Nullable SubjectSearchRequest query,
                                                                               Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.searchSubject(query, pageable)));
    }
}
