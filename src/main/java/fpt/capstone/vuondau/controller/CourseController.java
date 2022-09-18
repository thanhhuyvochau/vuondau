package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.CourseRequest;
import fpt.capstone.vuondau.entity.response.CourseResponse;
import fpt.capstone.vuondau.service.ICourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final ICourseService courseService;

    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(courseService.getAll()));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success(courseService.searchCourseByName(name)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> create(@RequestBody CourseRequest courseRequest) {
        return ResponseEntity.ok(ApiResponse.success(courseService.create(courseRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> update(@RequestBody CourseRequest courseRequest, Long id) {
        return ResponseEntity.ok(ApiResponse.success(courseService.update(courseRequest, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(courseService.delete(id)));
    }
}
