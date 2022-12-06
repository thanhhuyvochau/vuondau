package fpt.capstone.vuondau.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Subject;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.ClassDetailDto;
import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.dto.ClassStudentDto;
import fpt.capstone.vuondau.entity.dto.StudentDto;
import fpt.capstone.vuondau.entity.request.ClassSearchRequest;
import fpt.capstone.vuondau.entity.request.CreateClassRequest;
import fpt.capstone.vuondau.entity.request.SubjectSearchRequest;
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


    @Operation(summary = "Giáo viên yêu cầu tạo class ( subject - course) chờ admin phê duyệt ")
    @PostMapping({"/teacher-request-create-class"})
    public ResponseEntity<ApiResponse<Boolean>> teacherRequestCreateClass(@Nullable @RequestBody CreateClassRequest createClassRequest) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.teacherRequestCreateClass( createClassRequest)));

    }

    @Operation(summary = "lấy tất cả class chờ duyệt")
    @GetMapping({"/class-request"})
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> getClassRequesting(@Nullable ClassSearchRequest query, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.getClassRequesting(query, pageable)));
    }

    @Operation(summary = "lấy tất cả class có phân trang")
    @GetMapping
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> getAllClass( Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.getAllClass( pageable)));
    }

    @Operation(summary = "Admin phê duyệt request tao class của teacher ")
    @PostMapping({"/{id}/approve-class"})
    public ResponseEntity<ApiResponse<ClassDto>> adminApproveRequestCreateClass(@PathVariable Long id) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.adminApproveRequestCreateClass(id)));
    }

    @Operation(summary = "Tạo class qua moodle")
    @PostMapping({"/create-class-to-moodle"})
    public ResponseEntity<ApiResponse<Boolean>> synchronizedClassToMoodle(@RequestBody MoodleCourseDataRequest moodleCourseDataRequest) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.synchronizedClassToMoodle(moodleCourseDataRequest)));
    }


    @Operation(summary = "Hoc sinh đăng ký vào class")
    @PostMapping({"/{studentId}/student-enroll-class"})
    public ResponseEntity<ApiResponse<Boolean>> studentEnrollClass(@PathVariable Long studentId , Long classId ) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.studentEnrollClass(studentId , classId)));
    }

    @Operation(summary = "lấy danh sachs tất cả hoc sinh dang  chờ duyệt để vào class")
    @GetMapping({"/{classId}/student-waiting-approve"})
    public ResponseEntity<ApiResponse<List<ClassStudentDto>>> getStudentWaitingIntoClass(@PathVariable Long classId) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.getStudentWaitingIntoClass(classId)));
    }

    @Operation(summary = "Tìm Kiếm class")
    @GetMapping("/search-class")
    public ResponseEntity<ApiResponse<List<ClassDto>>> searchClass(@Nullable ClassSearchRequest query
           ) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.searchClass(query)));
    }


    @Operation(summary = "chi tiết class")
    @GetMapping("/{id}/class-detail")
    public ResponseEntity<ApiResponse<ClassDetailDto>> classDetail(@PathVariable Long id) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.classDetail(id)));
    }


    @Operation(summary = "lấy tất cả hoc sinh request vao lớp ")
    @GetMapping({"{classId}/students-approve-class"})
    public ResponseEntity<ApiResponse<List<ClassDto>>> studentWaitingApproveIntoClass(@PathVariable Long classId) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.studentWaitingApproveIntoClass(classId)));
    }

    @Operation(summary = "học sinh / giáo viên xem lớp bằng thời gian (was study, is studying, will study) ")
    @GetMapping("/{accountId}/class-of-account")
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> accountFilterClass( @Nullable ClassSearchRequest query, Pageable pageable) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.accountFilterClass(query, pageable)));
    }


    @Operation(summary = "Gợi ý lớp học - hs.phụ huynh đăng ký tìm gia sư ")
    @GetMapping("{infoFindTutorId}/class-suggestion")
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> classSuggestion(@PathVariable long infoFindTutorId ,  Pageable pageable)  {
        return ResponseEntity.ok(ApiResponse.success(iClassService.classSuggestion(infoFindTutorId,  pageable)));
    }

}
