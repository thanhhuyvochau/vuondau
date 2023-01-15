package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.MarkResponse;
import fpt.capstone.vuondau.service.IMarkService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mark")
public class MarkController {

    private final IMarkService markService;

    public MarkController(IMarkService markService) {
        this.markService = markService;
    }


    @Operation(summary = "Lấy điểm của học sinh trong lớp")
    @GetMapping({"/{studentId}"})
    @PreAuthorize("hasAnyAuthority('MANAGER','STUDENT','TEACHER')")
    public ResponseEntity<ApiResponse<List<MarkResponse>>> getClassRequesting(@RequestParam Long classId, @PathVariable Long studentId) {
        return ResponseEntity.ok(ApiResponse.success(markService.getStudentMark(classId, studentId)));
    }


}
