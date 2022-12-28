package fpt.capstone.vuondau.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.common.EClassStatus;
import fpt.capstone.vuondau.entity.dto.*;
import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.*;
import fpt.capstone.vuondau.service.IClassService;
import fpt.capstone.vuondau.service.IForumService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {

    private final IClassService iClassService;
    private final IForumService forumService;

    public ClassController(IClassService iClassService, IForumService forumService) {
        this.iClassService = iClassService;
        this.forumService = forumService;
    }


    @Operation(summary = "Giáo viên yêu cầu tạo class (class) chờ admin phê duyệt ")
    @PostMapping({"/teacher-request-create-class"})
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<ApiResponse<Long>> teacherRequestCreateClass(@Nullable @RequestBody TeacherCreateClassRequest createClassRequest) throws JsonProcessingException, ParseException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.teacherRequestCreateClass(createClassRequest)));

    }

    @Operation(summary = "Giáo viên yêu cầu tạo class (subject-course) chờ admin phê duyệt ")
    @PostMapping({"/{id}/teacher-request-create-class-subject-course"})
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<ApiResponse<Long>> teacherRequestCreateClassSubjectCourse(@PathVariable Long id, @Nullable @RequestBody CreateClassSubjectRequest createClassRequest) throws JsonProcessingException, ParseException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.teacherRequestCreateClassSubjectCourse(id, createClassRequest)));
    }

    @Operation(summary = "lấy tất cả class chờ duyệt")
    @GetMapping({"/class-request"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> getClassRequesting(@Nullable ClassSearchRequest query, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.getClassRequesting(query, pageable)));
    }

    @Operation(summary = "lấy tất cả class có phân trang")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> getAllClass(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.getAllClass(pageable)));
    }

    @Operation(summary = "lấy tất cả class có phân trang cho trang chủ (chỉ truyền status NEW hoặc RECRUITING)")
    @GetMapping("/for-user")
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> getAllClassForUser(Pageable pageable, @RequestParam EClassStatus classStatus) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.getAllClassForUser(pageable, classStatus)));
    }

    @Operation(summary = "Admin phê duyệt request tao class của teacher ")
    @PostMapping({"/{id}/approve-class"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<ClassDto>> adminApproveRequestCreateClass(@PathVariable Long id) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.adminApproveRequestCreateClass(id)));
    }

    @Operation(summary = "Tạo class qua moodle")
    @PostMapping({"/create-class-to-moodle"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Boolean>> synchronizedClassToMoodle(@RequestBody MoodleCourseDataRequest moodleCourseDataRequest) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.synchronizedClassToMoodle(moodleCourseDataRequest)));
    }


    @Operation(summary = "Hoc sinh đăng ký vào class")
    @PostMapping({"/{studentId}/student-enroll-class"})
    public ResponseEntity<ApiResponse<Boolean>> studentEnrollClass(@PathVariable Long studentId, Long classId) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.studentEnrollClass(studentId, classId)));
    }


    @Operation(summary = "lấy danh sách tất cả hoc sinh dang  chờ duyệt để vào class")
    @GetMapping({"/{classId}/student-waiting-approve"})
    public ResponseEntity<ApiResponse<List<ClassStudentDto>>> getStudentWaitingIntoClass(@PathVariable Long classId) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.getStudentWaitingIntoClass(classId)));
    }

    @Operation(summary = "Tìm Kiếm class")
    @GetMapping("/search-class")
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> searchClass(@Nullable ClassSearchRequest query, Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.searchClass(query, pageable)));

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
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> accountFilterClass(@Nullable ClassSearchRequest query, Pageable pageable) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.accountFilterClass(query, pageable)));
    }


    @Operation(summary = "Gợi ý lớp học - hs.phụ huynh đăng ký tìm gia sư ")
    @GetMapping("{infoFindTutorId}/class-suggestion")
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> classSuggestion(@PathVariable long infoFindTutorId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.classSuggestion(infoFindTutorId, pageable)));
    }

    @Operation(summary = "Admin tạo class để tuyển giáo viên ")
    @PostMapping({"/for-recruiting"})
    public ResponseEntity<ApiResponse<Long>> createClassForRecruiting(@Nullable @RequestBody CreateClassRequest createClassRequest) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.createClassForRecruiting(createClassRequest)));
    }

    @Operation(summary = "Giáo viên ứng tuyển dạy")
    @PostMapping({"/apply"})
    public ResponseEntity<ApiResponse<Boolean>> applyToRecruitingClass(@RequestBody Long classId) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.applyToRecruitingClass(classId)));
    }

    @Operation(summary = "Chọn giáo viên cho lớp")
    @PutMapping({"/choose-candicate"})
    public ResponseEntity<ApiResponse<AccountResponse>> chooseCandicateTeacher(@RequestBody ClassCandicateRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.chooseCandicateForClass(request)));
    }

    @Operation(summary = "Lấy các giáo viên ứng viên của lớp")
    @GetMapping({"/{classId}/candicates"})
    public ResponseEntity<ApiResponse<ApiPage<CandicateResponse>>> getClassCandicates(@PathVariable Long classId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.getClassCandicate(classId, pageable)));
    }

    @Operation(summary = "Lấy các lớp đang tuyển gia sư")
    @GetMapping({"/status/recruiting"})
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> getRecruitingClass(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.getRecruitingClasses(pageable)));
    }

    @Operation(summary = "(search) lấy tất cả class của giáo viên / học sinh")
    @GetMapping("/account")
    public ResponseEntity<ApiResponse<ApiPage<ClassDto>>> getClassByAccount(EClassStatus status, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(iClassService.getClassByAccount(status, pageable)));
    }

//    @Operation(summary = "admin seach lơp")
//    @GetMapping("/getClass")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<ApiResponse<Boolean>> adminGetClass(@RequestBody EClassStatus status, Pageable pageable) throws JsonProcessingException {
//        return ResponseEntity.ok(ApiResponse.success(iClassService.adminGetClass(status, pageable)));
//    }

    @Operation(summary = "hoc sinh/ giao vien xem chi tiết class")
    @GetMapping("/{id}/account/class-detail")
    public ResponseEntity<ApiResponse<ClassDetailDto>> accountGetClassDetail(@PathVariable Long id) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.accountGetClassDetail(id)));
    }

    @Operation(summary = "hoc sinh/ giao vien xem chi tiết resource của lớp")
    @GetMapping("/{id}/resource")
    public ResponseEntity<ApiResponse<ClassResourcesResponse>> accountGetResourceOfClass(@PathVariable Long id) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.accountGetResourceOfClass(id)));
    }

    @Operation(summary = "giao vien xem  hoc sinh của lớp")
    @GetMapping("/{id}/students")
    public ResponseEntity<ApiResponse<ApiPage<AccountResponse>>> accountGetStudentOfClass(@PathVariable Long id, Pageable pageable) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.accountGetStudentOfClass(id, pageable)));
    }

//    @Operation(summary = "giao vien/ học sinh xem chi tiết  thời khoá biểu của lớp")
//    @GetMapping("/{id}/timetable")
//    public ResponseEntity<ApiResponse<List<ClassTimeTableResponse>>> accountGetTimeTableOfClass(@PathVariable Long id) throws JsonProcessingException {
//        return ResponseEntity.ok(ApiResponse.success(iClassService.accountGetTimeTableOfClass(id)));
//    }

    @Operation(summary = "giao vien/ học sinh xem thời khoá biểu/điểm danh của môt lớp")
    @GetMapping("/{id}/attendance")
    public ResponseEntity<ApiResponse<ClassAttendanceResponse>> accountGetAttendanceOfClass(@PathVariable Long id) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.accountGetAttendanceOfClass(id)));

    }


    @Operation(summary = "học sinh xem thông tin giáo viên của lớp")
    @GetMapping("/{id}/teacher")
    public ResponseEntity<ApiResponse<ClassTeacherResponse>> studentGetTeacherInfoOfClass(@PathVariable Long id) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iClassService.studentGetTeacherInfoOfClass(id)));
    }

    @Operation(summary = "Lấy chi tiết diễn đàn của một lớp học")
    @GetMapping("/forum")
    public ResponseEntity<ApiResponse<ForumDto>> getForumOfClass(@RequestParam Long classId) {
        return ResponseEntity.ok(ApiResponse.success(forumService.getForumByClass(classId)));
    }
}
