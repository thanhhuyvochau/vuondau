package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.CommentDto;
import fpt.capstone.vuondau.entity.request.CreateCommentRequest;
import fpt.capstone.vuondau.service.ICommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentDto>>> getCommentsByQuestion(@RequestParam Long questionId) {
        List<CommentDto> comments = commentService.getCommentByQuestion(questionId);
        return ResponseEntity.ok(ApiResponse.success(comments));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CommentDto>> createComment(@RequestBody CreateCommentRequest createCommentRequest) {
        return ResponseEntity.ok(ApiResponse.success(commentService.createComment(createCommentRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentDto>> putComment(@PathVariable Long id, @RequestBody CreateCommentRequest createCommentRequest) {
        return ResponseEntity.ok(ApiResponse.success(commentService.updateComment(id, createCommentRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(commentService.deleteComment(id)));
    }

}
