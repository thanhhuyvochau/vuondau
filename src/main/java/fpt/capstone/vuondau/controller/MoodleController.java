package fpt.capstone.vuondau.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.moodle.response.MoodleCategoryResponse;
import fpt.capstone.vuondau.moodle.response.MoodleCourseResponse;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.service.IMoodleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moodle")
public class MoodleController {

    private final IMoodleService iMoodleService;

    public MoodleController(IMoodleService iMoodleService) {
        this.iMoodleService = iMoodleService;
    }

    @Operation(summary = "Get Category(subject) từ moodle")
    @GetMapping("get-subject-from-moodle")
    public ResponseEntity<ApiResponse<List<MoodleCategoryResponse>>> getCategoryFromMoodle() throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iMoodleService.getCategoryFromMoodle()));
    }


//    @Operation(summary = "Tạo Category(subject) qua moodle")
//    @PostMapping({"/create-category-to-moodle"})
//    public ResponseEntity<ApiResponse<Boolean>> crateCategoryToMoodle(@RequestBody MoodleCreateCategoryRequest.MoodleCreateCategoryBody moodleCreateCategoryBody) throws JsonProcessingException {
//        return ResponseEntity.ok(ApiResponse.success(iMoodleService.crateCategoryToMoodle(moodleCreateCategoryBody)));
//    }

    @Operation(summary = "Get từ moodle")
    @GetMapping("get-class-from-moodle")
    public ResponseEntity<ApiResponse<ApiPage<MoodleCourseResponse>>> synchronizedClass() throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iMoodleService.synchronizedClassFromMoodle()));
    }

    @Operation(summary = "Đồng bộ course content")
    @GetMapping("/sync-course-content")
    public ResponseEntity<ApiResponse<Boolean>> synchronizedCourseContent() throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iMoodleService.synchronizedClassDetailFromMoodle()));
    }

}
