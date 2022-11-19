package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;


import fpt.capstone.vuondau.entity.request.ClassRequest;
import fpt.capstone.vuondau.entity.request.CourseRequest;
import fpt.capstone.vuondau.entity.request.CourseSearchRequest;
import fpt.capstone.vuondau.entity.request.TopicsSubjectRequest;
import fpt.capstone.vuondau.entity.response.ClassSubjectResponse;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.service.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


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
    @GetMapping("/{courseID}")
    public ResponseEntity<ApiResponse<CourseResponse>> viewSubjectCourse(@PathVariable long courseID ) {
        return ResponseEntity.ok(ApiResponse.success(courseService.viewCourseDetail(courseID)));
    }


    @Operation(summary = "sửa course")
    @PostMapping("/{courseID}/update-course")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@PathVariable long courseID , @RequestBody CourseRequest subjectRequest) {
        return ResponseEntity.ok(ApiResponse.success(courseService.updateCourse(courseID, subjectRequest)));
    }



//    @Operation(description = "Lấy tất cả khóa học")
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAll() {
//        return ResponseEntity.ok(ApiResponse.success(courseService.getAll()));
//    }
//
//
//    @Operation(description = "Tìm kiếm khóa học bằng ")
//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse<List<CourseResponse>>> searchByName(@RequestParam String name) {
//        return ResponseEntity.ok(ApiResponse.success(courseService.searchCourseByName(name)));
//    }
//
//
//    @Operation(description = "Tạo khóa học")
//    @PostMapping
//    public ResponseEntity<ApiResponse<CourseResponse>> create(@RequestBody CourseRequest courseRequest) {
//        return ResponseEntity.ok(ApiResponse.success(courseService.create(courseRequest)));
//    }
//
//    @Operation(description = "Cập nhật khóa học")
//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse<CourseResponse>> update(@RequestBody CourseRequest courseRequest, Long id) {
//        return ResponseEntity.ok(ApiResponse.success(courseService.update(courseRequest, id)));
//    }
//    @Operation(description = "Xóa khóa ")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {
//        return ResponseEntity.ok(ApiResponse.success(courseService.delete(id)));
//    }

}
