package fpt.capstone.vuondau.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.common.EResourceMoodleType;
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
@RequestMapping("/api/exercises")
public class ExerciseController {

private final IExerciseService iExerciseService ;

    public ExerciseController(IExerciseService iExerciseService) {
        this.iExerciseService = iExerciseService;
    }

    @Operation(summary = "hoc sinh xem chi tiết resource (bai tập) của lớp")
    @GetMapping("/{classId}/student")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<List<MoodleRecourseDtoResponse>>> studentGetExerciseInClass(@PathVariable Long classId  ) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iExerciseService.getExerciseInClass(classId)));
    }

    @Operation(summary = "Giao vien xem chi tiết resource (bai tập) của lớp")
    @GetMapping("/{classId}/teacher")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<ApiResponse<List<MoodleRecourseDtoResponse>>> teacherGetExerciseInClass(@PathVariable Long classId) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iExerciseService.teacherGetExerciseInClass(classId)));
    }

    

}
