package fpt.capstone.vuondau.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.request.CreateClassRequest;
import fpt.capstone.vuondau.entity.response.SubjectResponse;
import fpt.capstone.vuondau.service.IClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {

    private final IClassService iClassService;

    public ClassController(IClassService iClassService) {
        this.iClassService = iClassService;
    }


    @Operation(summary = "Giáo viên quest tao class ( subject - course) chờ admin phê duyệt ")
    @PostMapping({"/{teacherId}"})
    public ResponseEntity<ApiResponse<Boolean>> teacherRequestCreateClass(@PathVariable Long teacherId,@Nullable @RequestBody CreateClassRequest createClassRequest) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.teacherRequestCreateClass(teacherId, createClassRequest)));

    }

    @Operation(summary = "Admin phê duyệt request tao class của teacher ")
    @PostMapping({"/{id}/approve-class"})
    public ResponseEntity<ApiResponse<ClassDto>> adminApproveRequestCreateClass(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.adminApproveRequestCreateClass(id)));
    }

    @Operation(summary = "Tạo class qua moodle")
    @PostMapping({"/create-class-to-moodle"})
    public ResponseEntity<ApiResponse<Boolean>> synchronizedClassToMoodle(@RequestBody MoodleCourseDataRequest moodleCourseDataRequest) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.synchronizedClassToMoodle(moodleCourseDataRequest)));
    }





}
