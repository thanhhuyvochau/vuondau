package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
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

    private final IClassService iClassService ;

    public ClassController(IClassService iClassService) {
        this.iClassService = iClassService;
    }


    @Operation(summary = "Giáo viên quest tao class + subject - course")
    @PostMapping({"/{teacherId}"})
    public ResponseEntity<ApiResponse<Boolean>> teacherRequestCreateClass(@PathVariable Long teacherId , @RequestBody CreateClassRequest createClassRequest ) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.teacherRequestCreateClass(teacherId, createClassRequest)));

    }



    @Operation(summary = "")
    @PostMapping({"/synchrized-document"})
    public ResponseEntity<ApiResponse<Boolean>>synchronizedClassToMoodle(@RequestBody MoodleCourseDataRequest moodleCourseDataRequest) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.synchronizedClassToMoodle( moodleCourseDataRequest)));

    }


}
