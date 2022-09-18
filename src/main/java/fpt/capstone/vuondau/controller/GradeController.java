package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.GradeRequest;
import fpt.capstone.vuondau.entity.response.GradeResponse;
import fpt.capstone.vuondau.service.IGradeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/grade")
public class GradeController {

    private final IGradeService iGradeService;

    public GradeController(IGradeService iGradeService) {
        this.iGradeService = iGradeService;
    }


    @Operation(summary = "Tạo mới grade")
    @PostMapping
    public ResponseEntity<ApiResponse<GradeResponse>> createNewGrade(@RequestBody GradeRequest gradeRequest) {
        return ResponseEntity.ok(ApiResponse.success(iGradeService.createNewGrade(gradeRequest)));
    }


    @Operation(summary = "Sửa grade bằng id ")
    @PutMapping("/{gradeId}")
    public ResponseEntity<ApiResponse<GradeResponse>> updateGrade(@PathVariable Long gradeId, @Valid @RequestBody GradeRequest gradeRequest) {
        return ResponseEntity.ok(ApiResponse.success(iGradeService.updateGrade(gradeId, gradeRequest)));
    }

    @Operation(summary = "Xóa subject bằng  Id")
    @DeleteMapping("/{gradeId}")
    public ResponseEntity<ApiResponse<Long>> deleteGrade(@PathVariable Long gradeId) {
        return ResponseEntity.ok(ApiResponse.success(iGradeService.deleteGrade(gradeId)));
    }


    @GetMapping("/{gradeId}")
    @Operation(summary = "lấy subject bang id")
    public ResponseEntity<ApiResponse<GradeResponse>> getGrade(@PathVariable Long gradeId) {
        return ResponseEntity.ok(ApiResponse.success(iGradeService.getGrade(gradeId)));
    }
}
