package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.ClassExerciseDto;
import fpt.capstone.vuondau.entity.dto.FeedBackDto;
import fpt.capstone.vuondau.service.IExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/exercise")
public class ExerciseController {

    private final IExerciseService iExerciseService ;

    public ExerciseController(IExerciseService iExerciseService) {
        this.iExerciseService = iExerciseService;
    }

    @Operation(summary = "Lấy bài tập theo lớp ")
    @GetMapping("/class/exercises")
    public ResponseEntity<ApiResponse<ApiPage<ClassExerciseDto>>> getExerciseOfLClass(Pageable pageable ) {
        return ResponseEntity.ok(ApiResponse.success(iExerciseService.getExerciseOfLClass(pageable)));
    }



}
