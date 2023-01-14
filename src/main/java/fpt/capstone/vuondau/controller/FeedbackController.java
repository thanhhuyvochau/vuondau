package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.StudentFeedbackRequest;
import fpt.capstone.vuondau.entity.response.StudentFeedbackResponse;
import fpt.capstone.vuondau.service.IFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedbacks")
public class FeedbackController {

    private final IFeedbackService iFeedbackService;


    public FeedbackController(IFeedbackService iFeedbackService) {
        this.iFeedbackService = iFeedbackService;
    }


    @Operation(summary = "Học sinh feedback giao viên")
    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<List<StudentFeedbackResponse>>> studentFeedbackTeacher(@RequestBody List<StudentFeedbackRequest> studentFeedbackRequest)  {
        return ResponseEntity.ok(ApiResponse.success(iFeedbackService.studentFeedbackTeacher(studentFeedbackRequest)));

    }


    @Operation(summary = "Học sinh lấy tất cả giáo viên cần feedback")
    @GetMapping("/teachers")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ApiResponse<List<Long>>> studentGetTeacherNeededFeedback()  {
        return ResponseEntity.ok(ApiResponse.success(iFeedbackService.studentGetTeacherNeededFeedback()));

    }


}
