package fpt.capstone.vuondau.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleRecourseClassResponse;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;


import fpt.capstone.vuondau.entity.request.*;
import fpt.capstone.vuondau.entity.response.ClassCourseResponse;
import fpt.capstone.vuondau.entity.response.ClassSubjectResponse;
import fpt.capstone.vuondau.entity.response.CourseDetailResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.service.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/courses")
public class CourseController {



    private final ICourseService courseService;

    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }


    @Operation(summary = "giáo viên request các topic cho subject")
    @PostMapping("/{teacherId}/create-topics-subject")
    public ResponseEntity<ApiResponse<TopicsSubjectRequest>> teacherCreateTopicInSubject(@PathVariable Long teacherId  , @RequestBody TopicsSubjectRequest topicsSubjectRequest ) {
        System.out.println(teacherId);
        return ResponseEntity.ok(ApiResponse.success(courseService.teacherCreateTopicInSubject(teacherId,topicsSubjectRequest)));
    }


    @Operation(summary = "Giáo viên đăng ký subject")
    @PostMapping({"/{teacherId}"})
    public ResponseEntity<ApiResponse<ClassSubjectResponse>> createRegisterSubject(@PathVariable Long teacherId ,  Long subjectId , @RequestBody ClassRequest classRequest ) {

        return ResponseEntity.ok(ApiResponse.success(courseService.createRegisterSubject(teacherId,subjectId, classRequest)));
    }



    // MANGER COURSE
    @Operation(summary = "Tìm Kiếm course")
    @GetMapping("/search-cource")
    public ResponseEntity<ApiResponse<ApiPage<CourseResponse>>> searchCourse(@Nullable CourseSearchRequest query,
                                                                                   Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(courseService.searchCourse(query, pageable)));
    }

    @Operation(summary = "Lấy tất cả course ")
    @GetMapping("/get-all-course")
    public ResponseEntity<ApiResponse<ApiPage<CourseResponse>>> viewAllCourse(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(courseService.viewAllCourse( pageable)));
    }

    @Operation(summary = "xem chi tiết course ")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDetailResponse>> viewSubjectCourse(@PathVariable long id ) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(courseService.viewCourseDetail(id )));
    }

    @Operation(summary = "Get resource course từ moodle")
    @GetMapping("get-resource-from-moodle")
    public ResponseEntity<ApiResponse<  List<MoodleRecourseClassResponse>>> synchronizedResource(@RequestParam Long classId) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(courseService.synchronizedResource(classId)));
    }

    @Operation(summary = "sửa course")
    @PutMapping("/{id}/update-course")
    public ResponseEntity<ApiResponse<CourseDetailResponse>> updateCourse(@PathVariable long id , @RequestBody CourseRequest subjectRequest) {
        return ResponseEntity.ok(ApiResponse.success(courseService.updateCourse(id, subjectRequest)));
    }

    @Operation(summary = "Học sinh xem lich sử course")
    @GetMapping("/{studentId}/histoty-course")
    public ResponseEntity<ApiResponse<List<ClassCourseResponse>>> viewHistoryCourse(@PathVariable long studentId ) {
        return ResponseEntity.ok(ApiResponse.success(courseService.viewHistoryCourse(studentId)));
    }

    @Operation(summary = "Học sinh enroll vao class - course")
    @PostMapping("/{studentId}/enroll-course")
    public ResponseEntity<ApiResponse<ClassCourseResponse>> studentEnrollCourse(@PathVariable long studentId , long courseId,  long classId ) {
        return ResponseEntity.ok(ApiResponse.success(courseService.studentEnrollCourse(studentId, courseId,classId)));
    }

    @Operation(description = "Tạo khóa học")
    @PostMapping("/create-course")
    public ResponseEntity<ApiResponse<Boolean>> createCourse(@RequestBody CourseRequest courseRequest) {
        return ResponseEntity.ok(ApiResponse.success(courseService.createCourse(courseRequest)));
    }


//    @Operation(description = "Lấy tất cả khóa học")
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<CourseDetailResponse>>> getAll() {
//        return ResponseEntity.ok(ApiResponse.success(courseService.getAll()));
//    }
//
//
//    @Operation(description = "Tìm kiếm khóa học bằng ")
//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse<List<CourseDetailResponse>>> searchByName(@RequestParam String name) {
//        return ResponseEntity.ok(ApiResponse.success(courseService.searchCourseByName(name)));
//    }
//
//
//    @Operation(description = "Tạo khóa học")
//    @PostMapping
//    public ResponseEntity<ApiResponse<CourseDetailResponse>> create(@RequestBody CourseRequest courseRequest) {
//        return ResponseEntity.ok(ApiResponse.success(courseService.create(courseRequest)));
//    }
//
//    @Operation(description = "Cập nhật khóa học")
//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse<CourseDetailResponse>> update(@RequestBody CourseRequest courseRequest, Long id) {
//        return ResponseEntity.ok(ApiResponse.success(courseService.update(courseRequest, id)));
//    }
//    @Operation(description = "Xóa khóa ")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {
//        return ResponseEntity.ok(ApiResponse.success(courseService.delete(id)));
//    }

}
