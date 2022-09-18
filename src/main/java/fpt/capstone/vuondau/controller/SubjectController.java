package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.SubjectRequest;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.service.ISubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/subject")
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
    @PutMapping("/{panoId}")
    public ResponseEntity<ApiResponse<SubjectResponse>> updateSubject(@PathVariable Long subjectId, @Valid @RequestBody SubjectRequest subjectRequest) {
        return ResponseEntity.ok(ApiResponse.success(subjectService.updateSubject(subjectId, subjectRequest)));
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
}
