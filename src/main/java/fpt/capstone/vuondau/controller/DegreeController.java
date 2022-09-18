package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.DegreeRequest;
import fpt.capstone.vuondau.entity.response.DegreeResponse;
import fpt.capstone.vuondau.service.IDegreeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/degrees")
public class DegreeController {

    private final IDegreeService iDegreeService ;

    public DegreeController(IDegreeService iDegreeService) {
        this.iDegreeService = iDegreeService;
    }


    @Operation(summary = "Tạo mới grade")
    @PostMapping
    public ResponseEntity<ApiResponse<DegreeResponse>> createNewDegree(@Valid @RequestBody DegreeRequest degreeRequest) {
        return ResponseEntity.ok(ApiResponse.success(iDegreeService.createNewDegree(degreeRequest)));
    }


    @Operation(summary = "Sửa grade bằng id ")
    @PutMapping("/{degreeId}")
    public ResponseEntity<ApiResponse<DegreeResponse>> updateDegree(@PathVariable Long degreeId, @Valid @RequestBody DegreeRequest degreeRequest) {
        return ResponseEntity.ok(ApiResponse.success(iDegreeService.updateDegree(degreeId, degreeRequest)));
    }

    @Operation(summary = "Xóa subject bằng  Id")
    @DeleteMapping("/{gradeId}")
    public ResponseEntity<ApiResponse<Long>> deleteDegree(@PathVariable Long degreeId) {
        return ResponseEntity.ok(ApiResponse.success(iDegreeService.deleteDegree(degreeId)));
    }


    @GetMapping("/{degreeId}")
    @Operation(summary = "lấy subject bang id")
    public ResponseEntity<ApiResponse<DegreeResponse>> getDegree(@PathVariable Long degreeId) {
        return ResponseEntity.ok(ApiResponse.success(iDegreeService.getDegree(degreeId)));
    }
}
