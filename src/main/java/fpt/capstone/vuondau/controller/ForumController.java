package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.ForumDto;
import fpt.capstone.vuondau.service.IForumService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forums")
public class ForumController {
    private final IForumService forumService;

    public ForumController(IForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/class-type")
    public ResponseEntity<ApiResponse<ApiPage<ForumDto>>> getClassesForumForStudent(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(forumService.getAllClassForumsOfStudent(pageable)));
    }

    @GetMapping("/subject-type")
    public ResponseEntity<ApiResponse<ApiPage<ForumDto>>> getSubjectForumForStudent(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(forumService.getAllSubjectForumsOfStudent(pageable)));
    }

    @GetMapping("/class-type/{classId}")
    public ResponseEntity<ApiResponse<ForumDto>> getDetailClassForum(@PathVariable Long classId) {
        return ResponseEntity.ok(ApiResponse.success(forumService.getForumByClass(classId)));
    }

    @GetMapping("/subject-type/{subjectId}")
    public ResponseEntity<ApiResponse<ForumDto>> getSubjetClassForum(@PathVariable Long subjectId) {
        return ResponseEntity.ok(ApiResponse.success(forumService.getForumBySubject(subjectId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ForumDto>> updateForum(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(forumService.updateForum(id)));
    }

}
