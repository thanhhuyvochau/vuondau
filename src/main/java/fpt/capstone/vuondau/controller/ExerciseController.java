package fpt.capstone.vuondau.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.response.ClassResourcesResponse;
import fpt.capstone.vuondau.moodle.response.MoodleRecourseDtoResponse;
import fpt.capstone.vuondau.service.IExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

private final IExerciseService iExerciseService ;

    public ExerciseController(IExerciseService iExerciseService) {
        this.iExerciseService = iExerciseService;
    }

    @Operation(summary = "hoc sinh xem chi tiết resource (bai tập) của lớp")
    @GetMapping("{classId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<List<MoodleRecourseDtoResponse>>> getExerciseInClass(@PathVariable Long classId) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iExerciseService.getExerciseInClass(classId)));
    }



}
