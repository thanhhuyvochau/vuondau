package fpt.capstone.vuondau.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.CreateClassRequest;
import fpt.capstone.vuondau.service.IClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {

    private final IClassService iClassService;

    public ClassController(IClassService iClassService) {
        this.iClassService = iClassService;
    }


    @Operation(summary = "Giáo viên quest tao class + subject - course")
    @PostMapping({"/{teacherId}"})
    public ResponseEntity<ApiResponse<Boolean>> teacherRequestCreateClass(@PathVariable Long teacherId, @RequestBody CreateClassRequest createClassRequest) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.teacherRequestCreateClass(teacherId, createClassRequest)));

    }


    @Operation(summary = "Tạo class qua moodle")
    @PostMapping({"/create-class-to-moodle"})
    public ResponseEntity<ApiResponse<Boolean>> synchronizedClassToMoodle(@RequestBody MoodleCourseDataRequest moodleCourseDataRequest) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.synchronizedClassToMoodle(moodleCourseDataRequest)));
    }

//    @Operation(summary = "Get class từ moodle")
//    @GetMapping("get-class-from-moodle")
//    public ResponseEntity<ApiResponse<  List<MoodleClassResponse> >> synchronizedClass() throws JsonProcessingException {
//        return ResponseEntity.ok(ApiResponse.success(iClassService.synchronizedClass()));
//    }


}
